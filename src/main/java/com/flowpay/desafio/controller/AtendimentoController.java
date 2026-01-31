package com.flowpay.desafio.controller;

import com.flowpay.desafio.model.Atendimento;
import com.flowpay.desafio.service.AtendimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Queue;

@RestController
@RequestMapping("/api/atendimentos")
public class AtendimentoController {

    private final AtendimentoService atendimentoService;

    public AtendimentoController(AtendimentoService atendimentoService) {
        this.atendimentoService = atendimentoService;
    }

    @PostMapping
    public Atendimento distribuirAtendimento(@RequestBody Atendimento atendimento) {
        // Agora o service decide se vai para um atendente ou para a fila
        return atendimentoService.distribuirAtendimento(atendimento);
    }

    @GetMapping
    public List<Atendimento> listarTodos() {
        return atendimentoService.listarAtendimentos();
    }

    @GetMapping("/monitoramento/fila")
    public List<Atendimento> verFila() {
        // Retorna todos os que estão com status "NA_FILA"
        return atendimentoService.listarFila();
    }

    @PatchMapping("/finalizar/{atendimentoId}")
    public void finalizar(@PathVariable Long atendimentoId) {
        atendimentoService.finalizarAtendimento(atendimentoId);
    }
}