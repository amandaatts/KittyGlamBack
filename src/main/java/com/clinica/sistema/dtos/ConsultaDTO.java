package com.clinica.sistema.dtos;

import java.time.LocalDate;

public class ConsultaDTO {

    private Long id;
    private LocalDate data;
    private String especialidade;

    private Long profissionalId;
    private Long pacienteId;

    // Construtores
    public ConsultaDTO() {}

    public ConsultaDTO(Long id, LocalDate data, String especialidade, Long profissionalId, Long pacienteId) {
        this.id = id;
        this.data = data;
        this.especialidade = especialidade;
        this.profissionalId = profissionalId;
        this.pacienteId = pacienteId;
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }
}
