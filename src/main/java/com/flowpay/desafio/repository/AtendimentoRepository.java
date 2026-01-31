package com.flowpay.desafio.repository;

import com.flowpay.desafio.model.Atendimento;
import com.flowpay.desafio.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    Optional<Atendimento> findFirstByStatusAndEspecialidadeOrderByIdAsc(String status, Especialidade especialidade);}