package com.multipedidos.clientes.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para Producto dentro de un Pedido.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {

    @NotBlank(message = "El nombre del producto es obligatorio")
    private String nombre;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precio;
}

