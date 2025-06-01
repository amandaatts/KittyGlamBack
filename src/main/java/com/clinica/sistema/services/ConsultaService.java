package com.clinica.sistema.services;

import com.clinica.sistema.dtos.ConsultaDTO;
import com.clinica.sistema.entities.Consulta;
import com.clinica.sistema.entities.Paciente;
import com.clinica.sistema.entities.Profissional;
import com.clinica.sistema.repositories.ConsultaRepository;
import com.clinica.sistema.repositories.PacienteRepository;
import com.clinica.sistema.repositories.ProfissionalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public List<Consulta> listarTodas() {
        return consultaRepository.findAll();
    }

    public Optional<Consulta> buscarPorId(Long id) {
        return consultaRepository.findById(id);
    }

    public Consulta salvar(ConsultaDTO dto) {
        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + dto.getPacienteId()));
        Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + dto.getProfissionalId()));

        Consulta consulta = new Consulta();
        consulta.setData(dto.getData());
        consulta.setEspecialidade(dto.getEspecialidade());
        consulta.setPaciente(paciente);
        consulta.setProfissional(profissional);

        return consultaRepository.save(consulta);
    }

    public Consulta atualizar(Long id, ConsultaDTO dto) {
        return consultaRepository.findById(id).map(consulta -> {
            Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                    .orElseThrow(() -> new RuntimeException("Paciente não encontrado com ID: " + dto.getPacienteId()));
            Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                    .orElseThrow(() -> new RuntimeException("Profissional não encontrado com ID: " + dto.getProfissionalId()));

            consulta.setData(dto.getData());
            consulta.setEspecialidade(dto.getEspecialidade());
            consulta.setPaciente(paciente);
            consulta.setProfissional(profissional);

            return consultaRepository.save(consulta);
        }).orElseThrow(() -> new RuntimeException("Consulta não encontrada com ID: " + id));
    }

    public void deletar(Long id) {
        consultaRepository.deleteById(id);
    }

    // Filtros
    public List<Consulta> buscarPorProfissional(String nome) {
        return consultaRepository.findByProfissionalNomeContainingIgnoreCase(nome);
    }

    public List<Consulta> buscarPorEspecialidade(String especialidade) {
        return consultaRepository.findByEspecialidadeContainingIgnoreCase(especialidade);
    }

    public List<Consulta> buscarPorData(LocalDate data) {
        return consultaRepository.findByData(data);
    }

    public List<Consulta> buscarPorProfissionalEData(String nome, LocalDate data) {
        return consultaRepository.findByProfissionalNomeContainingIgnoreCaseAndData(nome, data);
    }

    public List<Consulta> buscarPorEspecialidadeEData(String especialidade, LocalDate data) {
        return consultaRepository.findByEspecialidadeContainingIgnoreCaseAndData(especialidade, data);
    }
}
