package com.clinica.sistema.services;

import com.clinica.sistema.dtos.PacienteCadastroDTO;
import com.clinica.sistema.entities.Paciente;
import com.clinica.sistema.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import java.util.List;
import java.util.Optional;

@Service
public class PacienteService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    // CRUD
    public List<Paciente> listarTodos() {
        return pacienteRepository.findAll();
    }

    public Optional<Paciente> buscarPorId(Long id) {
        return pacienteRepository.findById(id);
    }

    public Optional<Paciente> buscarPorEmail(String email) {
        return pacienteRepository.findByEmail(email);
    }

    public Paciente cadastrar(PacienteCadastroDTO dto) {
        Paciente paciente = new Paciente();
        paciente.setNome(dto.getNome());
        paciente.setEmail(dto.getEmail());
        paciente.setTelefone(dto.getTelefone());
        paciente.setDataNasc(LocalDate.parse(dto.getDataNasc()));
        paciente.setSenha(passwordEncoder.encode(dto.getSenha()));
        return pacienteRepository.save(paciente);
    }

    public Paciente atualizar(Long id, PacienteCadastroDTO dto) {
        return pacienteRepository.findById(id).map(paciente -> {
            paciente.setNome(dto.getNome());
            paciente.setEmail(dto.getEmail());
            paciente.setTelefone(dto.getTelefone());
            paciente.setDataNasc(LocalDate.parse(dto.getDataNasc()));
            if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
                paciente.setSenha(passwordEncoder.encode(dto.getSenha()));
            }
            return pacienteRepository.save(paciente);
        }).orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
    }

    public void deletar(Long id) {
        pacienteRepository.deleteById(id);
    }

    // ✅ Novo método: login simples com email e senha
    public boolean loginPaciente(String email, String senha) {
        Optional<Paciente> optionalPaciente = pacienteRepository.findByEmail(email);
        if (optionalPaciente.isEmpty()) {
            return false;
        }

        Paciente paciente = optionalPaciente.get();
        return passwordEncoder.matches(senha, paciente.getSenha());
    }
}
