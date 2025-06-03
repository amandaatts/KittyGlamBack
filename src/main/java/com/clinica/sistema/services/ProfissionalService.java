package com.clinica.sistema.services;

import com.clinica.sistema.dtos.ProfissionalDTO;
import com.clinica.sistema.entities.Profissional;
import com.clinica.sistema.repositories.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ProfissionalService implements UserDetailsService {

    @Autowired
    private ProfissionalRepository profissionalRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Método para carregar o profissional pelo email para autenticação básica
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Profissional profissional = profissionalRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Profissional não encontrado com email: " + email));

        return new User(
                profissional.getEmail(),
                profissional.getSenha(),
                Collections.emptyList()
        );
    }

    // Listar todos os profissionais
    public List<Profissional> listarTodos() {
        return profissionalRepository.findAll();
    }

    // Buscar profissional por ID
    public Optional<Profissional> buscarPorId(Long id) {
        return profissionalRepository.findById(id);
    }

    // Cadastrar um novo profissional
    public Profissional cadastrar(ProfissionalDTO dto) {
        Profissional profissional = new Profissional();
        profissional.setNome(dto.getNome());
        profissional.setEmail(dto.getEmail());
        profissional.setTelefone(dto.getTelefone());
        profissional.setEspecialidade(dto.getEspecialidade());
        profissional.setDataNasc(dto.getDataNasc()); // já é LocalDate
        profissional.setSenha(passwordEncoder.encode(dto.getSenha())); // senha codificada
        return profissionalRepository.save(profissional);
    }

    // Atualizar um profissional existente
    public Profissional atualizar(Long id, ProfissionalDTO dto) {
        return profissionalRepository.findById(id).map(prof -> {
            prof.setNome(dto.getNome());
            prof.setEmail(dto.getEmail());
            prof.setTelefone(dto.getTelefone());
            prof.setEspecialidade(dto.getEspecialidade());
            prof.setDataNasc(dto.getDataNasc());
            if (dto.getSenha() != null && !dto.getSenha().isEmpty()) {
                prof.setSenha(passwordEncoder.encode(dto.getSenha()));
            }
            return profissionalRepository.save(prof);
        }).orElseThrow(() -> new RuntimeException("Profissional não encontrado"));
    }

    // Deletar um profissional
    public void deletar(Long id) {
        profissionalRepository.deleteById(id);
    }

    // Método de login: Verifica se o e-mail existe e se a senha está correta
    public boolean loginProfissional(String email, String senha) {
        Optional<Profissional> optionalProfissional = profissionalRepository.findByEmail(email);
        if (optionalProfissional.isEmpty()) {
            return false; // Profissional não encontrado
        }
        Profissional profissional = optionalProfissional.get();
        return passwordEncoder.matches(senha, profissional.getSenha()); // Verifica se a senha está correta
    }
}
