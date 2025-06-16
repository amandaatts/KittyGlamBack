package com.clinica.sistema.dtos;

import java.time.LocalDate;

import com.clinica.sistema.entities.Consulta;

public class ConsultaDTO {
    private Long id;
    private LocalDate dataConsulta;
    private Long pacienteId;
    private String nomePaciente;
    private Long profissionalId;
    private String nomeProfissional;
    private String especialidade;

    public ConsultaDTO() {
    }

    public ConsultaDTO(Long id, LocalDate dataConsulta, Long pacienteId, String nomePaciente, Long profissionalId,
            String nomeProfissional, String especialidade) {
        this.id = id;
        this.dataConsulta = dataConsulta;
        this.pacienteId = pacienteId;
        this.nomePaciente = nomePaciente;
        this.profissionalId = profissionalId;
        this.nomeProfissional = nomeProfissional;
        this.especialidade = especialidade;
    }

    public ConsultaDTO(Consulta entity) {
        this.id = entity.getId();
        this.dataConsulta = entity.getData();
        this.pacienteId = entity.getPaciente().getId();
        this.nomePaciente = entity.getPaciente().getNome();
        this.profissionalId = entity.getProfissional().getId();
        this.nomeProfissional = entity.getProfissional().getNome();
        this.especialidade = entity.getProfissional().getEspecialidade();
    }

    // Getters e setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDataConsulta() {
        return dataConsulta;
    }

    public void setDataConsulta(LocalDate dataConsulta) {
        this.dataConsulta = dataConsulta;
    }

    public Long getPacienteId() {
        return pacienteId;
    }

    public void setPacienteId(Long pacienteId) {
        this.pacienteId = pacienteId;
    }

    public String getNomePaciente() {
        return nomePaciente;
    }

    public void setNomePaciente(String nomePaciente) {
        this.nomePaciente = nomePaciente;
    }

    public Long getProfissionalId() {
        return profissionalId;
    }

    public void setProfissionalId(Long profissionalId) {
        this.profissionalId = profissionalId;
    }

    public String getNomeProfissional() {
        return nomeProfissional;
    }

    public void setNomeProfissional(String nomeProfissional) {
        this.nomeProfissional = nomeProfissional;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }
}
