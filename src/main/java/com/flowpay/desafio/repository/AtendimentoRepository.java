package com.flowpay.desafio.repository;

import com.flowpay.desafio.domain.model.Atendimento;
import com.flowpay.desafio.domain.model.Especialidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AtendimentoRepository extends JpaRepository<Atendimento, Long> {

    Optional<Atendimento> findFirstByStatusAndEspecialidadeOrderByIdAsc(String status, Especialidade especialidade);

    @Query("SELECT a FROM Atendimento a LEFT JOIN FETCH a.atendente")
    List<Atendimento> findAllWithAtendente();
}