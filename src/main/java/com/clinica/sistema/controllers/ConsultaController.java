package com.clinica.sistema.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.clinica.sistema.dtos.ConsultaDTO;
import com.clinica.sistema.entities.Consulta;
import com.clinica.sistema.services.ConsultaService;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @GetMapping
    public List<ConsultaDTO> listarTodas() {
        return consultaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ConsultaDTO> buscarPorId(@PathVariable Long id) {
        return consultaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ConsultaDTO> salvar(@RequestBody ConsultaDTO dto) {
        Consulta consultaSalva = consultaService.salvar(dto);
        return ResponseEntity.ok(new ConsultaDTO(consultaSalva));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ConsultaDTO> atualizar(@PathVariable Long id, @RequestBody ConsultaDTO dto) {
        try {
            Consulta atualizada = consultaService.atualizar(id, dto);
            return ResponseEntity.ok(new ConsultaDTO(atualizada));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        consultaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/paciente/{id}")
    public List<ConsultaDTO> getConsultasPorPaciente(@PathVariable Long id) {
        return consultaService.buscarPorPacienteId(id);
    }

    @GetMapping("/profissional")
    public List<ConsultaDTO> buscarPorProfissional(@RequestParam String nome) {
        return consultaService.buscarPorProfissional(nome);
    }
    
    // NOVO endpoint para buscar consultas por ID do profissional
    @GetMapping("/profissional/{id}")
    public List<ConsultaDTO> buscarPorProfissionalId(@PathVariable Long id) {
        return consultaService.buscarPorProfissionalId(id);
    }

    @GetMapping("/especialidade")
    public List<ConsultaDTO> buscarPorEspecialidade(@RequestParam String especialidade) {
        return consultaService.buscarPorEspecialidade(especialidade);
    }

    @GetMapping("/data")
    public List<ConsultaDTO> buscarPorData(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return consultaService.buscarPorData(data);
    }

    @GetMapping("/profissional-data")
    public List<ConsultaDTO> buscarPorProfissionalEData(@RequestParam String nome,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return consultaService.buscarPorProfissionalEData(nome, data);
    }

    @GetMapping("/especialidade-data")
    public List<ConsultaDTO> buscarPorEspecialidadeEData(@RequestParam String especialidade,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate data) {
        return consultaService.buscarPorEspecialidadeEData(especialidade, data);
    }

    @GetMapping("/profissionais-por-especialidade")
    public ResponseEntity<?> buscarProfissionaisPorEspecialidade(@RequestParam String especialidade) {
        return ResponseEntity.ok(consultaService.buscarProfissionaisPorEspecialidadeParcial(especialidade));
    }
}
