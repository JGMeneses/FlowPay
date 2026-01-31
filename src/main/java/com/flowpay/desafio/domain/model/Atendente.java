package com.flowpay.desafio.domain.model;

import com.flowpay.desafio.exception.AtendenteNaoDisponivelException;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Atendente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String time;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Builder.Default
    private int atendimentosAtivos = 0;

    public void adicionarAtendimento() {
        if (!podeAtender()) {
            throw new AtendenteNaoDisponivelException("Atendente já possui 3 chamados ativos.");
        }
        this.atendimentosAtivos++;
    }

    public void finalizarAtendimento() {
        if (this.atendimentosAtivos > 0) {
            this.atendimentosAtivos--;
        }
    }

    public boolean podeAtender() {
        return this.atendimentosAtivos < 3;
    }
}