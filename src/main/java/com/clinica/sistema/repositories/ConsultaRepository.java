package com.clinica.sistema.repositories;

import com.clinica.sistema.entities.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultaRepository extends JpaRepository<Consulta, Long> {

    // Buscar por nome do profissional
    List<Consulta> findByProfissionalNomeContainingIgnoreCase(String nome);

    // Buscar por especialidade
    List<Consulta> findByEspecialidadeContainingIgnoreCase(String especialidade);

    // Buscar por data exata
    List<Consulta> findByData(LocalDate data);

    // Buscar por profissional e data
    List<Consulta> findByProfissionalNomeContainingIgnoreCaseAndData(String nome, LocalDate data);

    // Buscar por especialidade e data
    List<Consulta> findByEspecialidadeContainingIgnoreCaseAndData(String especialidade, LocalDate data);
}
