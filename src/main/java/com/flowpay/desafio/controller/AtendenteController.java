package com.flowpay.desafio.controller;

import com.flowpay.desafio.model.Atendente;
import com.flowpay.desafio.service.AtendenteService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * API para gestão de atendentes da central FlowPay.
 */
@RestController
@RequestMapping("/api/atendentes")
public class AtendenteController {

    private final AtendenteService atendenteService;

    public AtendenteController(AtendenteService atendenteService) {
        this.atendenteService = atendenteService;
    }

    /**
     * Endpoint para criação de novos atendentes.
     */
    @PostMapping
    public Atendente adicionarAtendente(@RequestBody Atendente atendente) {
        return atendenteService.salvarAtendente(atendente);
    }

    /**
     * Endpoint para listagem de todos os atendentes e seus status de ocupação.
     */
    @GetMapping
    public List<Atendente> listarAtendentes() {
        return atendenteService.findAll();
    }
}