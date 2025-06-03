package com.clinica.sistema.repositories;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.clinica.sistema.entities.Consulta;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    List<Consulta> findByProfissionalNomeContainingIgnoreCase(String nome);

    List<Consulta> findByProfissionalEspecialidadeIgnoreCase(String especialidade);

    List<Consulta> findByDataBetween(LocalDate start, LocalDate end);

    List<Consulta> findByProfissionalNomeContainingIgnoreCaseAndDataBetween(String nome, LocalDate start, LocalDate end);

    List<Consulta> findByProfissionalEspecialidadeIgnoreCaseAndDataBetween(String especialidade, LocalDate start, LocalDate end);

    // Método que falta para buscar por profissionalId
    List<Consulta> findByProfissional_Id(Long profissionalId);
}
