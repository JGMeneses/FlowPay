package com.flowpay.desafio.domain.service;

import com.flowpay.desafio.domain.model.Atendente;
import com.flowpay.desafio.domain.model.Atendimento;
import com.flowpay.desafio.exception.AtendenteNaoCadastradoException;
import com.flowpay.desafio.repository.AtendenteRepository;
import com.flowpay.desafio.repository.AtendimentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AtendimentoService {
    private final AtendenteRepository atendenteRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final TriagemService triagemService;

    @Transactional
    public Atendimento iniciarFluxoAtendimento(Atendimento atendimento) {
        if (atendimento.getEspecialidade() == null) {
            atendimento.setEspecialidade(triagemService.identificarPeloAssunto(atendimento.getAssunto()));
        }

        return atendenteRepository
                .findFirstByEspecialidadeAndAtendimentosAtivosLessThan(atendimento.getEspecialidade(), 3)
                .map(atendente -> vincular(atendimento, atendente))
                .orElseGet(() -> enviarParaFila(atendimento));
    }

    @Transactional
    public void finalizarAtendimento(Long atendimentoId) {
        Atendimento atendimento = atendimentoRepository.findById(atendimentoId)
                .orElseThrow(() -> new AtendenteNaoCadastradoException("Atendimento não encontrado: " + atendimentoId));

        if (!"EM_ANDAMENTO".equals(atendimento.getStatus())) return;

        Atendente atendente = atendimento.getAtendente();
        if (atendente != null) {
            // Regra de Domínio: Entidade gerencia seu estado
            atendente.finalizarAtendimento();
            atendenteRepository.save(atendente);

            atendimento.setStatus("FINALIZADO");
            atendimentoRepository.save(atendimento);

            // Tenta puxar o próximo da fila para o atendente que acabou de liberar slot
            tentarDistribuirProximoDaFila(atendente);
        }
    }

    private void tentarDistribuirProximoDaFila(Atendente atendente) {
        atendimentoRepository
                .findFirstByStatusAndEspecialidadeOrderByIdAsc("NA_FILA", atendente.getEspecialidade())
                .ifPresent(proximo -> vincular(proximo, atendente));
    }

    private Atendimento vincular(Atendimento atendimento, Atendente atendente) {
        atendente.adicionarAtendimento();
        atendimento.vincularAtendente(atendente); // Usando o método de domínio que criamos

        atendenteRepository.save(atendente);
        return atendimentoRepository.save(atendimento);
    }

    private Atendimento enviarParaFila(Atendimento atendimento) {
        atendimento.marcarComoFila(); // Comportamento explícito
        return atendimentoRepository.save(atendimento);
    }

    public List<Atendimento> listarTodos() {
        return atendimentoRepository.findAllWithAtendente();
    }

    public List<Atendimento> listarFila() {
        return atendimentoRepository.findAll().stream()
                .filter(a -> "NA_FILA".equals(a.getStatus()))
                .collect(Collectors.toList());
    }
}