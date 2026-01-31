package com.flowpay.desafio.service;

import com.flowpay.desafio.model.Atendente;
import com.flowpay.desafio.model.Atendimento;
import com.flowpay.desafio.model.Especialidade;
import com.flowpay.desafio.repository.AtendenteRepository;
import com.flowpay.desafio.repository.AtendimentoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Serviço responsável pela inteligência de triagem e distribuição de chamados.
 * Gerencia a alocação de atendentes por especialidade e o fluxo de fila de espera.
 */
@Service
public class AtendimentoService {

    private final AtendenteRepository atendenteRepository;
    private final AtendimentoRepository atendimentoRepository;

    public AtendimentoService(AtendenteRepository atendenteRepository, AtendimentoRepository atendimentoRepository) {
        this.atendenteRepository = atendenteRepository;
        this.atendimentoRepository = atendimentoRepository;
    }

    /**
     * Realiza a triagem do chamado e tenta alocar um atendente especialista disponível.
     * Caso não haja especialistas com slots livres, o chamado entra na fila de espera específica.
     */
    @Transactional
    public Atendimento distribuirAtendimento(Atendimento atendimento) {
        // Define a especialidade automaticamente se não for enviada pelo front-end
        if (atendimento.getEspecialidade() == null) {
            atendimento.setEspecialidade(triagemAutomatica(atendimento.getAssunto()));
        }

        // Busca o primeiro atendente da mesma especialidade com menos de 3 atendimentos ativos
        Optional<Atendente> disponivel = atendenteRepository
                .findFirstByEspecialidadeAndAtendimentosAtivosLessThan(atendimento.getEspecialidade(), 3);

        if (disponivel.isPresent()) {
            vincularAtendente(atendimento, disponivel.get());
        } else {
            atendimento.setStatus("NA_FILA");
        }

        return atendimentoRepository.save(atendimento);
    }

    /**
     * Finaliza o chamado, libera o slot do atendente e dispara a verificação
     * automática para puxar o próximo cliente da fila que coincida com a especialidade do atendente.
     */
    @Transactional
    public void finalizarAtendimento(Long atendimentoId) {
        Atendimento atendimento = atendimentoRepository.findById(atendimentoId)
                .orElseThrow(() -> new RuntimeException("Atendimento não encontrado"));

        if (!"EM_ANDAMENTO".equals(atendimento.getStatus())) return;

        Atendente atendente = atendimento.getAtendente();
        if (atendente != null) {
            // Atualiza o contador de carga do atendente
            atendente.setAtendimentosAtivos(Math.max(0, atendente.getAtendimentosAtivos() - 1));
            atendenteRepository.save(atendente);

            atendimento.setStatus("FINALIZADO");
            atendimentoRepository.save(atendimento);

            // Verifica se há chamados aguardando para a especialidade deste atendente que acabou de vagar
            tentarDistribuirProximoDaFila(atendente);
        }
    }

    /**
     * Regra de negócio de Fila: Busca o atendimento mais antigo (FIFO) que
     * corresponda exatamente à expertise do atendente que ficou disponível.
     */
    private void tentarDistribuirProximoDaFila(Atendente atendente) {
        atendimentoRepository
                .findFirstByStatusAndEspecialidadeOrderByIdAsc("NA_FILA", atendente.getEspecialidade())
                .ifPresent(proximo -> {
                    vincularAtendente(proximo, atendente);
                    atendimentoRepository.save(proximo);
                });
    }

    /**
     * Centraliza a lógica de associação entre as entidades, garantindo
     * a integridade do status e o incremento da carga de trabalho.
     */
    private void vincularAtendente(Atendimento atendimento, Atendente atendente) {
        atendimento.setAtendente(atendente);
        atendimento.setStatus("EM_ANDAMENTO");
        atendente.setAtendimentosAtivos(atendente.getAtendimentosAtivos() + 1);
        atendenteRepository.save(atendente);
    }

    /**
     * Motor de Triagem: Analisa palavras-chave no assunto para classificar
     * o atendimento dentro das categorias de negócio (Enums).
     */
    private Especialidade triagemAutomatica(String assunto) {
        if (assunto == null) return Especialidade.OUTROS;

        String busca = assunto.toLowerCase();

        if (busca.contains("cartão") || busca.contains("limite") || busca.contains("bloqueio")) {
            return Especialidade.CARTAO;
        }
        if (busca.contains("pix") || busca.contains("transferência") || busca.contains("chave")) {
            return Especialidade.PIX;
        }
        if (busca.contains("empréstimo") || busca.contains("crédito") || busca.contains("taxa")) {
            return Especialidade.EMPRESTIMO;
        }

        return Especialidade.OUTROS;
    }

    public List<Atendimento> listarAtendimentos() {
        return atendimentoRepository.findAll();
    }

    public List<Atendimento> listarFila() {
        return atendimentoRepository.findAll().stream()
                .filter(a -> "NA_FILA".equals(a.getStatus()))
                .collect(Collectors.toList());
    }
}