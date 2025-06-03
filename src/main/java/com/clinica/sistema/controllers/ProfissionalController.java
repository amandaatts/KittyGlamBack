package com.clinica.sistema.controllers;

import com.clinica.sistema.dtos.ProfissionalDTO;
import com.clinica.sistema.dtos.ProfissionalLoginDTO;
import com.clinica.sistema.entities.Profissional;
import com.clinica.sistema.services.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/profissionais")
@CrossOrigin(origins = "*")
public class ProfissionalController {

    @Autowired
    private ProfissionalService profissionalService;

    @GetMapping
    public List<Profissional> listarTodos() {
        return profissionalService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Profissional> buscarPorId(@PathVariable Long id) {
        return profissionalService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Profissional cadastrar(@RequestBody ProfissionalDTO dto) {
        return profissionalService.cadastrar(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Profissional> atualizar(@PathVariable Long id, @RequestBody ProfissionalDTO dto) {
        return ResponseEntity.ok(profissionalService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        profissionalService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Novo endpoint para login
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody ProfissionalLoginDTO loginDTO) {
        boolean autenticado = profissionalService.loginProfissional(loginDTO.getEmail(), loginDTO.getSenha());

        if (autenticado) {
            return ResponseEntity.ok("Login realizado com sucesso!");
        }
        return new ResponseEntity<>("Credenciais inválidas", HttpStatus.UNAUTHORIZED);
    }
}
