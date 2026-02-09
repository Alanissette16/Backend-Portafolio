package com.backend.proyecto.exceptions;

//Excepción lanzada cuando un usuario no está autorizado (HTTP 401/403)
public class UnauthorizedException extends RuntimeException {

    //Constructor vacío
    public UnauthorizedException() {
        super();
    }

    //Constructor con mensaje
    public UnauthorizedException(String message) {
        super(message);
    }

    //Constructor con mensaje y causa
    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }
}
