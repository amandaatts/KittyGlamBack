package com.clinica.sistema.dtos;

import java.time.LocalDate;

public class ProfissionalDTO {
    private Long id;
    private String nome;
    private String especialidade;
    private String email; 
    private String telefone;
    private LocalDate dataNasc;
    private String senha; // Adicionado para cadastro/autenticação

    public ProfissionalDTO() {}

    public ProfissionalDTO(Long id, String nome, String especialidade, String email, String telefone, LocalDate dataNasc, String senha) {
        this.id = id;
        this.nome = nome;
        this.especialidade = especialidade;
        this.email = email;
        this.telefone = telefone;
        this.dataNasc = dataNasc;
        this.senha = senha;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public LocalDate getDataNasc() {
        return dataNasc;
    }

    public void setDataNasc(LocalDate dataNasc) {
        this.dataNasc = dataNasc;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
