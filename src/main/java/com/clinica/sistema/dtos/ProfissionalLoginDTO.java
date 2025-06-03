package com.clinica.sistema.dtos;

public class ProfissionalLoginDTO {
    private String email;
    private String senha;

    public ProfissionalLoginDTO() {}

    public ProfissionalLoginDTO(String email, String senha) {
        this.email = email;
        this.senha = senha;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
