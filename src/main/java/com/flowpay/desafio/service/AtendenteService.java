package com.flowpay.desafio.service;

import com.flowpay.desafio.model.Atendente;
import com.flowpay.desafio.repository.AtendenteRepository;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Serviço responsável pelo gerenciamento de cadastro e consulta de atendentes.
 */
@Service
public class AtendenteService {

    private final AtendenteRepository atendenteRepository;

    public AtendenteService(AtendenteRepository atendenteRepository) {
        this.atendenteRepository = atendenteRepository;
    }

    /**
     * Persiste um novo atendente no sistema.
     */
    public Atendente salvarAtendente(Atendente atendente) {
        return atendenteRepository.save(atendente);
    }

    /**
     * Retorna a lista completa de atendentes cadastrados.
     */
    public List<Atendente> findAll() {
        return atendenteRepository.findAll();
    }
}