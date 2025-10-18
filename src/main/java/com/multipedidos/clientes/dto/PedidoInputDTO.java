package com.multipedidos.clientes.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para entrada de datos de Pedido.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoInputDTO {

    @NotNull(message = "El ID del cliente es obligatorio")
    private Long clienteId;

    @NotEmpty(message = "El pedido debe tener al menos un producto")
    @Valid
    private List<ProductoDTO> productos;
}

