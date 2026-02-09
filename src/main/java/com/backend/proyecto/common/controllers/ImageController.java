package com.backend.proyecto.common.controllers;

import com.backend.proyecto.utils.CloudinaryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

//Controlador para la gestión de imágenes
@RestController
@RequestMapping("/api/images")
@Tag(name = "Images", description = "API para subida de imágenes")
@SecurityRequirement(name = "bearer-jwt")
public class ImageController {

    private final CloudinaryService cloudinaryService;

    //Constructor para inyección de dependencias
    public ImageController(CloudinaryService cloudinaryService) {
        this.cloudinaryService = cloudinaryService;
    }

    //Endpoint para subir una imagen
    @PostMapping("/upload")
    @Operation(summary = "Subir una imagen a Cloudinary", description = "Recibe un archivo y lo sube a la nube")
    @ApiResponse(responseCode = "200", description = "Imagen subida exitosamente")
    public ResponseEntity<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            String url = cloudinaryService.uploadImage(file);
            Map<String, String> response = new HashMap<>();
            response.put("url", url);
            return ResponseEntity.ok(response);
        } catch (IOException e) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Error al subir la imagen: " + e.getMessage());
            return ResponseEntity.internalServerError().body(error);
        }
    }
}
