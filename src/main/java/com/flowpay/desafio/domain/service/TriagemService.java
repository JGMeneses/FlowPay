package com.flowpay.desafio.domain.service;

import com.flowpay.desafio.domain.model.Especialidade;
import org.springframework.stereotype.Service;

@Service
public class TriagemService {

    public Especialidade identificarPeloAssunto(String assunto) {
        if (assunto == null) return Especialidade.OUTROS;

        String texto = assunto.toLowerCase();
        if (texto.contains("cartão") || texto.contains("limite")) return Especialidade.CARTAO;
        if (texto.contains("pix") || texto.contains("transferência")) return Especialidade.PIX;
        if (texto.contains("trocas") || texto.contains("defeito")) return Especialidade.TROCAS;

        return Especialidade.OUTROS;
    }
}