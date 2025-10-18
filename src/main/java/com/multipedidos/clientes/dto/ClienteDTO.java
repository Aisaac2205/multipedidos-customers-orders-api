package com.multipedidos.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para salida de datos de Cliente.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long id;
    private String nombre;
    private String correo;
}

