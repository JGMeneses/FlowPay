package com.flowpay.desafio.config;

import com.flowpay.desafio.domain.model.Atendente;
import com.flowpay.desafio.domain.model.Especialidade;
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
                Atendente a1 = Atendente.builder()
                        .nome("João Meneses")
                        .time("Squad Alpha")
                        .especialidade(Especialidade.CARTAO)
                        .atendimentosAtivos(0)
                        .build();

                Atendente a2 = Atendente.builder()
                        .nome("Álvaro Prates")
                        .time("Squad Beta")
                        .especialidade(Especialidade.PIX)
                        .atendimentosAtivos(0)
                        .build();

                Atendente a3 = Atendente.builder()
                        .nome("Luan Braz")
                        .time("Squad Gamma")
                        .especialidade(Especialidade.TROCAS)
                        .atendimentosAtivos(0)
                        .build();

                Atendente a4 = Atendente.builder()
                        .nome("Dário Rocha")
                        .time("Squad Gamma")
                        .especialidade(Especialidade.OUTROS)
                        .atendimentosAtivos(0)
                        .build();

                repository.saveAll(List.of(a1, a2, a3,a4));

                System.out.println("✅ Atendentes iniciais criados via DDD Builder!");
            }
        };
    }
}