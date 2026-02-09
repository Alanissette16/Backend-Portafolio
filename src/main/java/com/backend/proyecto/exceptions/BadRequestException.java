package com.backend.proyecto.exceptions;

//Excepción lanzada cuando una petición es inválida (HTTP 400)
public class BadRequestException extends RuntimeException {

    //Constructor vacío
    public BadRequestException() {
        super();
    }

    //Constructor con mensaje
    public BadRequestException(String message) {
        super(message);
    }

    //Constructor con mensaje y causa
    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
