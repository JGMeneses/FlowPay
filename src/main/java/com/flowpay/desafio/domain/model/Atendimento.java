package com.flowpay.desafio.domain.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assunto;

    private String status;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendente_id")
    private Atendente atendente;

    public void marcarComoFila() {
        this.status = "NA_FILA";
    }

    public void vincularAtendente(Atendente atendente) {
        this.atendente = atendente;
        this.status = "EM_ANDAMENTO";
    }
}