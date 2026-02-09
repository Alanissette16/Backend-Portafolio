package com.backend.proyecto.exceptions;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//Manejador global de excepciones para toda la aplicación
//Captura excepciones y las convierte en respuestas HTTP estandarizadas
@RestControllerAdvice
public class GlobalExceptionHandler {

    //Maneja excepciones de recurso no encontrado (404)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.NOT_FOUND.value());
        error.setError("Not Found");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    //Maneja excepciones de petición inválida (400)
    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(
            BadRequestException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Bad Request");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Maneja excepciones de recurso duplicado (409)
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(
            DuplicateResourceException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setError("Conflict");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    //Maneja excepciones de no autorizado (401)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ErrorResponse> handleUnauthorizedException(
            UnauthorizedException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setError("Unauthorized");
        error.setMessage(ex.getMessage());
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    //Maneja errores de lectura de JSON (ej: tipos de datos incorrectos)
    @ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadable(
            org.springframework.http.converter.HttpMessageNotReadableException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Malformed JSON or Type Mismatch");
        error.setMessage("No se pudo leer el cuerpo de la petición: " + ex.getMostSpecificCause().getMessage());
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Maneja errores de validación de campos (@Valid)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request) {

        List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.add(new ErrorResponse.FieldError(
                    error.getField(),
                    error.getDefaultMessage()));
        });

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Validation Failed");
        error.setMessage("Errores de validación en los campos");
        error.setPath(request.getRequestURI());
        error.setFieldErrors(fieldErrors);

        //Debug logging to file
        try {
            java.nio.file.Files.writeString(
                    java.nio.file.Paths.get("backend_validation_errors.log"),
                    "Validation Error at " + LocalDateTime.now() + "\n" +
                            "Path: " + request.getRequestURI() + "\n" +
                            "Errors: "
                            + fieldErrors.stream().map(e -> e.getField() + ": " + e.getMessage())
                                    .collect(java.util.stream.Collectors.joining(", "))
                            + "\n\n",
                    java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (Exception e) {
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Maneja violaciones de constraints de base de datos
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolation(
            DataIntegrityViolationException ex,
            HttpServletRequest request) {

        String message = "Error de integridad de datos";
        if (ex.getMessage() != null) {
            if (ex.getMessage().contains("unique constraint")) {
                message = "Ya existe un registro con esos datos";
            } else if (ex.getMessage().contains("foreign key constraint")) {
                message = "No se puede eliminar porque tiene registros relacionados";
            }
        }

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.CONFLICT.value());
        error.setError("Data Integrity Violation");
        error.setMessage(message);
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.CONFLICT);
    }

    //Maneja violaciones de constraints de validación
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ErrorResponse> handleConstraintViolation(
            ConstraintViolationException ex,
            HttpServletRequest request) {

        List<ErrorResponse.FieldError> fieldErrors = new ArrayList<>();
        ex.getConstraintViolations().forEach(violation -> {
            String propertyPath = violation.getPropertyPath().toString();
            String message = violation.getMessage();
            fieldErrors.add(new ErrorResponse.FieldError(propertyPath, message));
        });

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.BAD_REQUEST.value());
        error.setError("Constraint Violation");
        error.setMessage("Errores de validación");
        error.setPath(request.getRequestURI());
        error.setFieldErrors(fieldErrors);

        //Debug logging to file
        try {
            java.nio.file.Files.writeString(
                    java.nio.file.Paths.get("backend_validation_errors.log"),
                    "Constraint Violation at " + LocalDateTime.now() + "\n" +
                            "Path: " + request.getRequestURI() + "\n" +
                            "Errors: "
                            + fieldErrors.stream().map(e -> e.getField() + ": " + e.getMessage())
                                    .collect(java.util.stream.Collectors.joining(", "))
                            + "\n\n",
                    java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (Exception e) {
        }

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    //Maneja errores de autenticación de Spring Security
    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> handleAuthenticationException(
            AuthenticationException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.UNAUTHORIZED.value());
        error.setError("Authentication Failed");
        error.setMessage("Credenciales inválidas");
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    //Maneja errores de acceso denegado de Spring Security
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.FORBIDDEN.value());
        error.setError("Forbidden");
        error.setMessage("No tienes permisos para acceder a este recurso");
        error.setPath(request.getRequestURI());

        return new ResponseEntity<>(error, HttpStatus.FORBIDDEN);
    }

    //Maneja cualquier otra excepción no capturada (500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(
            Exception ex,
            HttpServletRequest request) {

        ErrorResponse error = new ErrorResponse();
        error.setTimestamp(LocalDateTime.now());
        error.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.setError("Internal Server Error");
        error.setMessage("Ocurrió un error inesperado en el servidor");
        error.setPath(request.getRequestURI());

        //Log the exception for debugging
        ex.printStackTrace();

        //Debug logging to file
        try {
            java.nio.file.Files.writeString(
                    java.nio.file.Paths.get("backend_validation_errors.log"),
                    "Generic Exception at " + java.time.LocalDateTime.now() + "\n" +
                            "Path: " + request.getRequestURI() + "\n" +
                            "Error: " + ex.getMessage() + "\n\n",
                    java.nio.file.StandardOpenOption.CREATE, java.nio.file.StandardOpenOption.APPEND);
        } catch (Exception e) {
        }

        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
