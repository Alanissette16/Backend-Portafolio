package com.backend.proyecto.exceptions;

//Excepción lanzada cuando se intenta crear un recurso duplicado (HTTP 409)
public class DuplicateResourceException extends RuntimeException {

    //Constructor vacío
    public DuplicateResourceException() {
        super();
    }

    //Constructor con mensaje
    public DuplicateResourceException(String message) {
        super(message);
    }

    //Constructor con mensaje y causa
    public DuplicateResourceException(String message, Throwable cause) {
        super(message, cause);
    }

    //Constructor de conveniencia para recursos duplicados
    public DuplicateResourceException(String resourceName, String fieldName, Object fieldValue) {
        super(String.format("%s ya existe con %s: '%s'", resourceName, fieldName, fieldValue));
    }
}
