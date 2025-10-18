package com.multipedidos.clientes.exceptions;

/**
 * Excepción personalizada para datos inválidos.
 */
public class DatosInvalidosException extends RuntimeException {

    public DatosInvalidosException(String mensaje) {
        super(mensaje);
    }

    public DatosInvalidosException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
