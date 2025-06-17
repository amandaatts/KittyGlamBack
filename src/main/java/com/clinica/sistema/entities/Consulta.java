package com.clinica.sistema.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Consulta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate data;

    @ManyToOne
    @JsonBackReference  // Ignora serialização do paciente para evitar loop
    private Paciente paciente;

    @ManyToOne
    @JsonBackReference  // Ignora serialização do profissional para evitar loop
    private Profissional profissional;

    // Getters e setters
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
    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    public Profissional getProfissional() {
        return profissional;
    }
    public void setProfissional(Profissional profissional) {
        this.profissional = profissional;
    }
}
