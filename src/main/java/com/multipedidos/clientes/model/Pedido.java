package com.multipedidos.clientes.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidad Pedido para almacenar información de pedidos.
 */
@Entity
@Table(name = "pedidos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El cliente es obligatorio")
    @Column(name = "cliente_id", nullable = false)
    private Long clienteId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    @JoinColumn(name = "pedido_id")
    @Builder.Default
    private List<Producto> productos = new ArrayList<>();

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal total;

    @Column(name = "fecha_pedido", updatable = false)
    private LocalDateTime fechaPedido;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private EstadoPedido estado = EstadoPedido.PENDIENTE;

    @PrePersist
    protected void onCreate() {
        fechaPedido = LocalDateTime.now();
        if (estado == null) {
            estado = EstadoPedido.PENDIENTE;
        }
    }

    public enum EstadoPedido {
        PENDIENTE,
        PROCESANDO,
        COMPLETADO,
        CANCELADO
    }
}

