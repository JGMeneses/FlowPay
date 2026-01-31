package com.flowpay.desafio.api.dto;

import com.flowpay.desafio.domain.model.Especialidade;
import io.swagger.v3.oas.annotations.media.Schema;

public record AtendimentoRequestDTO(
        @Schema(description = "Resumo do problema", example = "Meu cartão está bloqueado")
        String assunto,

        @Schema(description = "Especialidade (opcional, se nulo o sistema faz a triagem)", example = "CARTAO")
        Especialidade especialidade
) {}