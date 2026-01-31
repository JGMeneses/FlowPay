package com.flowpay.desafio.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Atendimento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assunto;

    private String status; // "NA_FILA", "EM_ANDAMENTO", "FINALIZADO"

    @Enumerated(EnumType.STRING) // Salva o nome (ex: "CARTAO") em vez do índice (0, 1, 2)
    private Especialidade especialidade;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "atendente_id")
    private Atendente atendente;
}