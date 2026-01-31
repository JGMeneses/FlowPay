package com.flowpay.desafio.domain.service;

import com.flowpay.desafio.domain.model.Atendente;
import com.flowpay.desafio.repository.AtendenteRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * Serviço responsável pelo gerenciamento de cadastro e consulta de atendentes.
 */
@Service
@RequiredArgsConstructor
public class AtendenteService {
    private final AtendenteRepository atendenteRepository;

    @Transactional
    public Atendente salvarAtendente(Atendente atendente) {
        return atendenteRepository.save(atendente);
    }

    public List<Atendente> findAll() {
        return atendenteRepository.findAll();
    }
}