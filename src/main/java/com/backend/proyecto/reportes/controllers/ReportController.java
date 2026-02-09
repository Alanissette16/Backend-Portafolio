package com.backend.proyecto.reportes.controllers;

import com.backend.proyecto.reportes.services.ReportService;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

//Controlador para generación de reportes en PDF
@RestController
@RequestMapping("/api/reportes")
public class ReportController {

        private final ReportService reportService;

        // Constructor para inyección de dependencias
        public ReportController(ReportService reportService) {
                this.reportService = reportService;
        }

        // Descargar reporte de asesorías (PDF)
        @GetMapping("/asesorias.pdf")
        public ResponseEntity<ByteArrayResource> downloadAsesoriasPdf(
                        @RequestParam(required = false) Long programadorId,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
                        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta) {

                byte[] data = reportService.generarReporteAsesoriasPdf(programadorId, desde, hasta);
                ByteArrayResource resource = new ByteArrayResource(data);

                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=asesorias.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .contentLength(data.length)
                                .body(resource);
        }

        // Descargar reporte de proyectos (PDF)
        @GetMapping("/proyectos.pdf")
        public ResponseEntity<ByteArrayResource> downloadProyectosPdf(
                        @RequestParam(required = false) Long programadorId,
                        @RequestParam(required = false) Boolean activo) {

                byte[] data = reportService.generarReporteProyectosPdf(programadorId, activo);
                ByteArrayResource resource = new ByteArrayResource(data);

                return ResponseEntity.ok()
                                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=proyectos.pdf")
                                .contentType(MediaType.APPLICATION_PDF)
                                .contentLength(data.length)
                                .body(resource);
        }
}
