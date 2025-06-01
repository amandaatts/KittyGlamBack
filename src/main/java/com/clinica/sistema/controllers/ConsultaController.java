package com.clinica.sistema.controllers;

import com.clinica.sistema.dtos.ConsultaDTO;
import com.clinica.sistema.entities.Consulta;
import com.clinica.sistema.services.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public List<Consulta> listarTodas() {
        return consultaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Consulta> buscarPorId(@PathVariable Long id) {
        return consultaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Consulta salvar(@RequestBody ConsultaDTO dto) {
        return consultaService.salvar(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Consulta> atualizar(@PathVariable Long id, @RequestBody ConsultaDTO dto) {
        try {
            Consulta atualizada = consultaService.atualizar(id, dto);
            return ResponseEntity.ok(atualizada);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        consultaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/profissional")
    public List<Consulta> buscarPorProfissional(@RequestParam String nome) {
        return consultaService.buscarPorProfissional(nome);
    }

    @GetMapping("/especialidade")
    public List<Consulta> buscarPorEspecialidade(@RequestParam String especialidade) {
        return consultaService.buscarPorEspecialidade(especialidade);
    }

    @GetMapping("/data")
    public List<Consulta> buscarPorData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return consultaService.buscarPorData(data);
    }

    @GetMapping("/profissional/data")
    public List<Consulta> buscarPorProfissionalEData(
            @RequestParam String nome,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return consultaService.buscarPorProfissionalEData(nome, data);
    }

    @GetMapping("/especialidade/data")
    public List<Consulta> buscarPorEspecialidadeEData(
            @RequestParam String especialidade,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return consultaService.buscarPorEspecialidadeEData(especialidade, data);
    }
}
