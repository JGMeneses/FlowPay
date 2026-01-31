package com.flowpay.desafio.api.controller;

import com.flowpay.desafio.api.dto.AtendenteDTO;
import com.flowpay.desafio.domain.model.Atendente;
import com.flowpay.desafio.domain.service.AtendenteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/atendentes")
@RequiredArgsConstructor
@Tag(name = "Atendentes", description = "Gestão de especialistas da central")
public class AtendenteController {

    private final AtendenteService atendenteService;

    @PostMapping
    @Operation(summary = "Cadastrar novo atendente especialista")
    public AtendenteDTO adicionarAtendente(@RequestBody AtendenteDTO dto) {
        // Converte DTO para Entity para o Domain Service
        Atendente atendente = Atendente.builder()
                .nome(dto.nome())
                .time(dto.time())
                .especialidade(dto.especialidade())
                .build();

        Atendente salvo = atendenteService.salvarAtendente(atendente);

        // Retorna o DTO
        return new AtendenteDTO(salvo.getId(), salvo.getNome(), salvo.getTime(), salvo.getEspecialidade(), salvo.getAtendimentosAtivos());
    }

    @GetMapping
    @Operation(summary = "Listar todos os atendentes e sua carga atual")
    public List<AtendenteDTO> listarAtendentes() {
        return atendenteService.findAll().stream()
                .map(a -> new AtendenteDTO(a.getId(), a.getNome(), a.getTime(), a.getEspecialidade(), a.getAtendimentosAtivos()))
                .collect(Collectors.toList());
    }
}