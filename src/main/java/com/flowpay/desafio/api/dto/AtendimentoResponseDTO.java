package com.flowpay.desafio.api.dto;

import com.flowpay.desafio.domain.model.Atendimento;

public record AtendimentoResponseDTO(
        Long id,
        String assunto,
        String status,
        String nomeAtendente
) {
    public static AtendimentoResponseDTO fromEntity(Atendimento a) {
        return new AtendimentoResponseDTO(
                a.getId(),
                a.getAssunto(),
                a.getStatus(),
                a.getAtendente() != null ? a.getAtendente().getNome() : "Aguardando na fila"
        );
    }
}
