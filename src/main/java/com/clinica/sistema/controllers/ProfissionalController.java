package com.clinica.sistema.controllers;

import com.clinica.sistema.dtos.ProfissionalDTO;
import com.clinica.sistema.dtos.ProfissionalLoginDTO;
import com.clinica.sistema.entities.Profissional;
import com.clinica.sistema.services.ProfissionalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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

    // Endpoint de login com retorno JSON incluindo id do profissional
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody ProfissionalLoginDTO loginDTO) {
        boolean autenticado = profissionalService.loginProfissional(loginDTO.getEmail(), loginDTO.getSenha());

        Map<String, Object> resposta = new HashMap<>();
        if (autenticado) {
            Optional<Profissional> optionalProfissional = profissionalService.buscarPorEmail(loginDTO.getEmail());
            if (optionalProfissional.isPresent()) {
                Profissional profissional = optionalProfissional.get();
                resposta.put("message", "Login realizado com sucesso!");
                resposta.put("id", profissional.getId());
                resposta.put("nome", profissional.getNome());
                resposta.put("email", profissional.getEmail());
                return ResponseEntity.ok(resposta);
            } else {
                resposta.put("message", "Profissional não encontrado");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(resposta);
            }
        } else {
            resposta.put("message", "Email ou senha incorreta");
            return new ResponseEntity<>(resposta, HttpStatus.UNAUTHORIZED);
        }
    }

    // Novo endpoint para buscar profissionais por especialidade
    @GetMapping("/especialidade")
    public List<Profissional> buscarPorEspecialidade(@RequestParam String especialidade) {
        return profissionalService.buscarPorEspecialidade(especialidade);
    }
}
