package com.multipedidos.clientes.utils;

import java.util.regex.Pattern;

/**
 * Clase de utilidad para validación de códigos y formatos.
 */
public class ValidadorCodigos {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$"
    );

    private static final Pattern TELEFONO_PATTERN = Pattern.compile(
        "^[+]?[0-9]{7,15}$"
    );

    /**
     * Valida si un email tiene formato correcto.
     *
     * @param email El email a validar
     * @return true si el email es válido, false en caso contrario
     */
    public static boolean esEmailValido(String email) {
        if (email == null || email.trim().isEmpty()) {
            return false;
        }
        return EMAIL_PATTERN.matcher(email.trim()).matches();
    }

    /**
     * Valida si un teléfono tiene formato correcto.
     *
     * @param telefono El teléfono a validar
     * @return true si el teléfono es válido, false en caso contrario
     */
    public static boolean esTelefonoValido(String telefono) {
        if (telefono == null || telefono.trim().isEmpty()) {
            return false;
        }
        return TELEFONO_PATTERN.matcher(telefono.trim()).matches();
    }

    /**
     * Valida si un código no está vacío.
     *
     * @param codigo El código a validar
     * @return true si el código no está vacío, false en caso contrario
     */
    public static boolean esCodigoValido(String codigo) {
        return codigo != null && !codigo.trim().isEmpty();
    }
}
