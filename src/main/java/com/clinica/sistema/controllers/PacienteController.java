package com.clinica.sistema.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clinica.sistema.dtos.PacienteCadastroDTO;
import com.clinica.sistema.dtos.PacienteLoginDTO;
import com.clinica.sistema.dtos.PacienteLoginResponseDTO;
import com.clinica.sistema.entities.Paciente;
import com.clinica.sistema.services.PacienteService;

@RestController
@RequestMapping("/api/pacientes")
@CrossOrigin(origins = "*")
public class PacienteController {

    @Autowired
    private PacienteService pacienteService;

    @GetMapping
    public List<Paciente> listarTodos() {
        return pacienteService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Paciente> buscarPorId(@PathVariable Long id) {
        return pacienteService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Paciente> cadastrar(@RequestBody PacienteCadastroDTO pacienteDTO) {
        Paciente novoPaciente = pacienteService.cadastrar(pacienteDTO);
        return ResponseEntity.ok(novoPaciente);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Paciente> atualizar(@PathVariable Long id, @RequestBody PacienteCadastroDTO pacienteDTO) {
        try {
            Paciente atualizado = pacienteService.atualizar(id, pacienteDTO);
            return ResponseEntity.ok(atualizado);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pacienteService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Login do paciente - retorna JSON com dados do paciente em caso de sucesso
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody PacienteLoginDTO loginDTO) {
        Optional<Paciente> pacienteAutenticado = pacienteService.autenticarPaciente(loginDTO.getEmail(), loginDTO.getSenha());

        if (pacienteAutenticado.isPresent()) {
            Paciente paciente = pacienteAutenticado.get();
            PacienteLoginResponseDTO responseDTO = new PacienteLoginResponseDTO(paciente.getId(), paciente.getNome(), paciente.getEmail());
            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email ou senha inválidos");
        }
    }
}
