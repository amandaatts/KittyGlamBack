package com.clinica.sistema.services;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clinica.sistema.entities.Consulta;
import com.clinica.sistema.entities.Profissional;
import com.clinica.sistema.repositories.ConsultaRepository;
import com.clinica.sistema.repositories.ProfissionalRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfWriter;

@Service
public class RelatorioConsultaService {

    @Autowired
    private ConsultaRepository consultaRepository;

    @Autowired
    private ProfissionalRepository profissionalRepository;

    public byte[] gerarPdfRelatorioPorProfissional(Long profissionalId) throws DocumentException {
        Optional<Profissional> profissionalOpt = profissionalRepository.findById(profissionalId);
        String nomeProfissional = profissionalOpt.map(Profissional::getNome).orElse("Profissional não encontrado");
        String especialidadeProfissional = profissionalOpt.map(Profissional::getEspecialidade).orElse("Especialidade não informada");

        List<Consulta> consultas = consultaRepository.findByProfissional_Id(profissionalId);

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document();
        PdfWriter.getInstance(document, out);
        document.open();

        document.add(new Paragraph("Relatório de Consultas do Profissional: " + nomeProfissional));
        document.add(new Paragraph("Especialidade: " + especialidadeProfissional));
        document.add(new Paragraph("Total de Consultas: " + consultas.size()));
        document.add(new Paragraph(" "));

        for (Consulta consulta : consultas) {
            String data = (consulta.getData() != null) ? consulta.getData().toString() : "Data não informada";

            String paciente = (consulta.getPaciente() != null && consulta.getPaciente().getNome() != null) 
                    ? consulta.getPaciente().getNome() : "Paciente não informado";

            String linha = "Data: " + data + ", Paciente: " + paciente;
            document.add(new Paragraph(linha));
        }

        document.close();
        return out.toByteArray();
    }
}
