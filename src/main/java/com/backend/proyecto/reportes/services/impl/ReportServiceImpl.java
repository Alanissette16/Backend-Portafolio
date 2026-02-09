package com.backend.proyecto.reportes.services.impl;

import com.backend.proyecto.reportes.services.ReportService;
import com.backend.proyecto.asesorias.entities.AsesoriaEntity;
import com.backend.proyecto.asesorias.repositories.AsesoriaRepository;
import com.backend.proyecto.Proyectos.entities.ProyectoEntity;
import com.backend.proyecto.Proyectos.repositories.ProyectoRepository;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

//Implementación del servicio de reportes usando OpenPDF
@Service
public class ReportServiceImpl implements ReportService {

    private final AsesoriaRepository asesoriaRepository;
    private final ProyectoRepository proyectoRepository;

    //Constructor para inyección de dependencias
    public ReportServiceImpl(AsesoriaRepository asesoriaRepository,
            ProyectoRepository proyectoRepository) {
        this.asesoriaRepository = asesoriaRepository;
        this.proyectoRepository = proyectoRepository;
    }

    @Override
    public byte[] generarReporteAsesoriasPdf(Long programadorId, LocalDate desde, LocalDate hasta) {
        List<AsesoriaEntity> asesorias = obtenerAsesoriasFiltradas(programadorId, desde, hasta);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            //Título de la página
            Font fontTitle = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph title = new Paragraph("Reporte de Asesorías", fontTitle);
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            //Configuración de la tabla
            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            //Encabezados de la tabla
            addTableHeader(table, "Fecha", "Hora", "Cliente", "Programador", "Estado");

            //Datos de las asesorías
            for (AsesoriaEntity asesoria : asesorias) {
                table.addCell(asesoria.getFecha().toString());
                table.addCell(asesoria.getHoraInicio() + " - " + asesoria.getHoraFin());
                table.addCell(asesoria.getUsuarioExterno().getDisplayName());
                table.addCell(asesoria.getProgramador().getUsuario().getDisplayName());
                table.addCell(asesoria.getEstado().toString());
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF", e);
        }
    }

    @Override
    public byte[] generarReporteProyectosPdf(Long programadorId, Boolean activo) {
        List<ProyectoEntity> proyectos = obtenerProyectosFiltrados(programadorId, activo);

        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            Document document = new Document(PageSize.A4);
            PdfWriter.getInstance(document, out);
            document.open();

            Paragraph title = new Paragraph("Reporte de Proyectos",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18));
            title.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            PdfPTable table = new PdfPTable(4);
            table.setWidthPercentage(100);

            addTableHeader(table, "Nombre", "Programador", "Tecnologías", "Estado");

            for (ProyectoEntity proyecto : proyectos) {
                table.addCell(proyecto.getNombre());
                table.addCell(proyecto.getProgramador().getUsuario().getDisplayName());
                table.addCell(proyecto.getTecnologias() != null ? proyecto.getTecnologias() : "N/A");
                table.addCell(proyecto.getActivo() ? "Activo" : "Inactivo");
            }

            document.add(table);
            document.close();

            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("Error al generar PDF de proyectos", e);
        }
    }

    private void addTableHeader(PdfPTable table, String... headers) {
        for (String header : headers) {
            PdfPCell headerCell = new PdfPCell();
            headerCell.setPhrase(new Phrase(header));
            headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
            table.addCell(headerCell);
        }
    }

    private List<AsesoriaEntity> obtenerAsesoriasFiltradas(Long programadorId, LocalDate desde, LocalDate hasta) {
        List<AsesoriaEntity> list;
        if (programadorId != null) {
            list = asesoriaRepository.findByProgramadorId(programadorId);
        } else {
            list = asesoriaRepository.findAll();
        }

        if (desde != null)
            list = list.stream().filter(a -> !a.getFecha().isBefore(desde)).collect(Collectors.toList());
        if (hasta != null)
            list = list.stream().filter(a -> !a.getFecha().isAfter(hasta)).collect(Collectors.toList());

        return list;
    }

    private List<ProyectoEntity> obtenerProyectosFiltrados(Long programadorId, Boolean activo) {
        List<ProyectoEntity> list;
        if (programadorId != null) {
            if (activo != null && activo) {
                list = proyectoRepository.findByProgramadorIdAndActivoTrue(programadorId);
            } else {
                list = proyectoRepository.findByProgramadorId(programadorId);
            }
        } else {
            list = proyectoRepository.findAll();
            if (activo != null && activo) {
                list = list.stream().filter(ProyectoEntity::getActivo).collect(Collectors.toList());
            }
        }
        return list;
    }
}
