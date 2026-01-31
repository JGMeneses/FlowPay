package com.flowpay.desafio.repository;

import com.flowpay.desafio.model.Atendente;
import com.flowpay.desafio.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AtendenteRepository extends JpaRepository<Atendente, Long> {
    Optional<Atendente> findFirstByEspecialidadeAndAtendimentosAtivosLessThan(Especialidade especialidade, int limite);}