package com.clinica.sistema.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.sistema.dtos.ConsultaDTO;
import com.clinica.sistema.entities.Consulta;
import com.clinica.sistema.entities.Paciente;
import com.clinica.sistema.entities.Profissional;
import com.clinica.sistema.repositories.ConsultaRepository;
import com.clinica.sistema.repositories.PacienteRepository;
import com.clinica.sistema.repositories.ProfissionalRepository;

@Service
public class ConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private PacienteRepository pacienteRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public List<ConsultaDTO> listarTodas() {
        List<Consulta> consultas = consultaRepository.findAll();
        return consultas.stream().map(ConsultaDTO::new).collect(Collectors.toList());
    }

    public Optional<ConsultaDTO> buscarPorId(Long id) {
        return consultaRepository.findById(id).map(ConsultaDTO::new);
    }

    public Consulta salvar(ConsultaDTO dto) {
        Consulta consulta = new Consulta();
        consulta.setData(dto.getDataConsulta());

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        consulta.setPaciente(paciente);
        consulta.setProfissional(profissional);

        return consultaRepository.save(consulta);
    }

    public Consulta atualizar(Long id, ConsultaDTO dto) {
        Consulta consulta = consultaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Consulta não encontrada"));

        consulta.setData(dto.getDataConsulta());

        Paciente paciente = pacienteRepository.findById(dto.getPacienteId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));

        Profissional profissional = profissionalRepository.findById(dto.getProfissionalId())
                .orElseThrow(() -> new RuntimeException("Profissional não encontrado"));

        consulta.setPaciente(paciente);
        consulta.setProfissional(profissional);

        return consultaRepository.save(consulta);
    }

    public void deletar(Long id) {
        consultaRepository.deleteById(id);
    }

    public List<ConsultaDTO> buscarPorProfissional(String nome) {
        List<Consulta> consultas = consultaRepository.findByProfissionalNomeContainingIgnoreCase(nome);
        return consultas.stream().map(ConsultaDTO::new).collect(Collectors.toList());
    }

    public List<ConsultaDTO> buscarPorEspecialidade(String especialidade) {
        List<Consulta> consultas = consultaRepository.findByProfissionalEspecialidadeIgnoreCase(especialidade);
        return consultas.stream().map(ConsultaDTO::new).collect(Collectors.toList());
    }

    public List<ConsultaDTO> buscarPorData(LocalDate data) {
        LocalDate start = data;
        LocalDate end = data;
        List<Consulta> consultas = consultaRepository.findByDataBetween(start, end);
        return consultas.stream().map(ConsultaDTO::new).collect(Collectors.toList());
    }

    public List<ConsultaDTO> buscarPorProfissionalEData(String nome, LocalDate data) {
        LocalDate start = data;
        LocalDate end = data;
        List<Consulta> consultas = consultaRepository.findByProfissionalNomeContainingIgnoreCaseAndDataBetween(nome, start, end);
        return consultas.stream().map(ConsultaDTO::new).collect(Collectors.toList());
    }

    public List<ConsultaDTO> buscarPorEspecialidadeEData(String especialidade, LocalDate data) {
        LocalDate start = data;
        LocalDate end = data;
        List<Consulta> consultas = consultaRepository.findByProfissionalEspecialidadeIgnoreCaseAndDataBetween(especialidade, start, end);
        return consultas.stream().map(ConsultaDTO::new).collect(Collectors.toList());
    }

    // Exemplo de método usando o findByProfissional_Id corrigido
    public List<ConsultaDTO> buscarPorProfissionalId(Long profissionalId) {
        List<Consulta> consultas = consultaRepository.findByProfissional_Id(profissionalId);
        return consultas.stream().map(ConsultaDTO::new).collect(Collectors.toList());
    }
}
