package com.flowpay.desafio.api.controller;

import com.flowpay.desafio.api.dto.AtendimentoRequestDTO;
import com.flowpay.desafio.api.dto.AtendimentoResponseDTO;
import com.flowpay.desafio.domain.model.Atendimento;
import com.flowpay.desafio.domain.service.AtendimentoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/atendimentos")
@RequiredArgsConstructor // Substitui o construtor manual, mantendo o código limpo
@Tag(name = "Atendimentos", description = "Operações de triagem e gestão de chamados")
public class AtendimentoController {

    private final AtendimentoService service;

    @PostMapping
    @Operation(summary = "Criar novo atendimento", description = "Realiza a triagem automática baseada no assunto e aloca um especialista.")
    @ApiResponse(responseCode = "200", description = "Atendimento criado/alocado com sucesso")
    public ResponseEntity<AtendimentoResponseDTO> criar(@RequestBody AtendimentoRequestDTO dto) {
        Atendimento atendimento = new Atendimento();
        atendimento.setAssunto(dto.assunto());
        atendimento.setEspecialidade(dto.especialidade());

        Atendimento processado = service.iniciarFluxoAtendimento(atendimento);
        return ResponseEntity.ok(AtendimentoResponseDTO.fromEntity(processado));
    }

    @GetMapping
    @Operation(summary = "Listar todos os atendimentos", description = "Retorna o histórico completo de chamados.")
    public ResponseEntity<List<AtendimentoResponseDTO>> listarTodos() {
        List<AtendimentoResponseDTO> lista = service.listarTodos().stream()
                .map(AtendimentoResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/monitoramento/fila")
    @Operation(summary = "Ver fila de espera", description = "Retorna apenas os atendimentos que estão com status NA_FILA.")
    public ResponseEntity<List<AtendimentoResponseDTO>> verFila() {
        List<AtendimentoResponseDTO> lista = service.listarFila().stream()
                .map(AtendimentoResponseDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(lista);
    }

    @PatchMapping("/finalizar/{atendimentoId}")
    @Operation(summary = "Finalizar atendimento", description = "Libera o atendente e automaticamente puxa o próximo da fila, se houver.")
    @ApiResponse(responseCode = "200", description = "Atendimento finalizado e fila processada.")
    public ResponseEntity<Void> finalizar(@PathVariable Long atendimentoId) {
        service.finalizarAtendimento(atendimentoId);
        return ResponseEntity.ok().build();
    }
}