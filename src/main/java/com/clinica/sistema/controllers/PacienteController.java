package com.clinica.sistema.controllers;

import com.clinica.sistema.dtos.PacienteCadastroDTO;
import com.clinica.sistema.dtos.PacienteLoginDTO;
import com.clinica.sistema.entities.Paciente;
import com.clinica.sistema.services.PacienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // ✅ Login do paciente
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody PacienteLoginDTO loginDTO) {
        boolean autenticado = pacienteService.loginPaciente(loginDTO.getEmail(), loginDTO.getSenha());

        if (autenticado) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        }
        return new ResponseEntity<>("Email ou senha inválidos", HttpStatus.UNAUTHORIZED);
    }
}
