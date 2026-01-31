package com.flowpay.desafio.config;

import com.flowpay.desafio.model.Atendente;
import com.flowpay.desafio.model.Especialidade;
import com.flowpay.desafio.repository.AtendenteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initDatabase(AtendenteRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                Atendente a1 = new Atendente();
                a1.setNome("Ricardo Silva");
                a1.setTime("Squad Alpha");
                a1.setEspecialidade(Especialidade.CARTAO);
                a1.setAtendimentosAtivos(0);

                Atendente a2 = new Atendente();
                a2.setNome("Beatriz Matos");
                a2.setTime("Squad Beta");
                a2.setEspecialidade(Especialidade.PIX);
                a2.setAtendimentosAtivos(0);

                Atendente a3 = new Atendente();
                a3.setNome("Lucas Oliveira");
                a3.setTime("Squad Gamma");
                a3.setEspecialidade(Especialidade.EMPRESTIMO);
                a3.setAtendimentosAtivos(0);

                repository.saveAll(List.of(a1, a2, a3));

                System.out.println("✅ Atendentes iniciais criados com sucesso!");
            } else {
                System.out.println("ℹ️ Atendentes já existem no banco, pulando inicialização.");
            }
        };
    }
}