package com.backend.proyecto.exceptions;

//Excepción lanzada cuando un recurso no es encontrado (HTTP 404)
public class ResourceNotFoundException extends RuntimeException {

    //Constructor vacío
    public ResourceNotFoundException() {
        super();
    }

    //Constructor con mensaje
    public ResourceNotFoundException(String message) {
        super(message);
    }

    //Constructor con mensaje y causa
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    //Constructor de conveniencia para recursos con ID
    public ResourceNotFoundException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s no encontrado con %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
