package com.backend.proyecto.exceptions;

import java.time.LocalDateTime;
import java.util.List;

//DTO para respuestas de error estandarizadas
//Incluye información detallada del error para debugging y usuario final
public class ErrorResponse {

    private LocalDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private List<FieldError> fieldErrors;

    //Clase interna para errores de validación de campos
    public static class FieldError {
        private String field;
        private String message;

        //Constructor vacío
        public FieldError() {
        }

        //Constructor con parámetros
        public FieldError(String field, String message) {
            this.field = field;
            this.message = message;
        }

        //Getters y Setters
        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }

        //Setters
        public void setField(String field) {
            this.field = field;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    //Constructor vacío
    public ErrorResponse() {
    }

    //Constructor completo
    public ErrorResponse(LocalDateTime timestamp, int status, String error, String message, String path,
            List<FieldError> fieldErrors) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.fieldErrors = fieldErrors;
    }

    //Getters y Setters
    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public String getPath() {
        return path;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    //Setters
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public void setError(String error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
