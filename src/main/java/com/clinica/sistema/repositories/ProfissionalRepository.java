package com.clinica.sistema.repositories;

import com.clinica.sistema.entities.Profissional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProfissionalRepository extends JpaRepository<Profissional, Long> {

    Optional<Profissional> findByEmail(String email);

    // Método para buscar profissionais pela especialidade (para filtro no select)
    List<Profissional> findByEspecialidadeIgnoreCase(String especialidade);
}
