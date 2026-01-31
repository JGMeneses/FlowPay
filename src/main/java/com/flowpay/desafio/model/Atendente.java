package com.flowpay.desafio.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Atendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    private String time; // Pode ser o nome da equipe

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade; // A área que este atendente domina

    private int atendimentosAtivos = 0;
}
