package com.clinica.sistema.services;

import com.clinica.sistema.dtos.PacienteCadastroDTO;
import com.clinica.sistema.entities.Paciente;
import com.clinica.sistema.repositories.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class PacienteService implements UserDetailsService {

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Usado para autenticação básica
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Paciente paciente = pacienteRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Paciente não encontrado com email: " + email));

        return new User(
                paciente.getEmail(),
                paciente.getSenha(),
                Collections.emptyList()
        );
    }

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
}
