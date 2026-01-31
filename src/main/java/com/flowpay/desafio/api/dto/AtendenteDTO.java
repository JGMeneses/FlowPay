package com.flowpay.desafio.api.dto;

import com.flowpay.desafio.domain.model.Especialidade;
import io.swagger.v3.oas.annotations.media.Schema;

public record AtendenteDTO(
        Long id,
        @Schema(example = "Ricardo Silva") String nome,
        @Schema(example = "Squad Alpha") String time,
        @Schema(example = "CARTAO") Especialidade especialidade,
        int atendimentosAtivos
) {}