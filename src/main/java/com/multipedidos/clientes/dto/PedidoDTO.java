package com.multipedidos.clientes.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO para salida de datos de Pedido.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long id;
    private Long clienteId;
    private List<ProductoDTO> productos;
    private BigDecimal total;
}

