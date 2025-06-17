package com.clinica.sistema.repositories;

import com.clinica.sistema.entities.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    // Busca por e-mail (usado na autenticação)
    Optional<Profissional> findByEmail(String email);

    // Busca por especialidade exata (ignore case)
    List<Profissional> findByEspecialidadeIgnoreCase(String especialidade);

    // Busca por especialidade com correspondência parcial (para auto completar ou pesquisa mais flexível)
    List<Profissional> findByEspecialidadeContainingIgnoreCase(String especialidade);
}
