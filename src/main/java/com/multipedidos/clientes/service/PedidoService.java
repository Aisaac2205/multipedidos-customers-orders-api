package com.multipedidos.clientes.service;

import com.multipedidos.clientes.dto.PedidoDTO;
import com.multipedidos.clientes.dto.PedidoInputDTO;
import com.multipedidos.clientes.dto.ProductoDTO;
import com.multipedidos.clientes.model.Pedido;
import com.multipedidos.clientes.model.Producto;
import com.multipedidos.clientes.repository.PedidoRepository;
import com.multipedidos.common.exceptions.DatosInvalidosException;
import com.multipedidos.common.exceptions.RecursoNoEncontradoException;
import com.multipedidos.common.utils.CalculadoraDescuentos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de pedidos.
 * Utiliza la librería común para calcular totales con IVA y descuentos.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PedidoService {

    private final PedidoRepository pedidoRepository;
    private final ClienteService clienteService;

    /**
     * Crea un nuevo pedido.
     */
    @Transactional
    public PedidoDTO crearPedido(PedidoInputDTO input) {
        log.info("Creando nuevo pedido para cliente ID: {}", input.getClienteId());

        // Verificar que el cliente existe
        if (!clienteService.existeCliente(input.getClienteId())) {
            throw new DatosInvalidosException("El cliente con ID " + input.getClienteId() + " no existe");
        }

        // Validar que haya productos
        if (input.getProductos() == null || input.getProductos().isEmpty()) {
            throw new DatosInvalidosException("El pedido debe tener al menos un producto");
        }

        // Calcular subtotal
        BigDecimal subtotal = input.getProductos().stream()
                .map(ProductoDTO::getPrecio)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Usar la librería común para calcular el total con descuentos e IVA
        BigDecimal totalFinal = CalculadoraDescuentos.calcularTotalFinal(subtotal);

        // Convertir DTOs a entidades
        List<Producto> productos = input.getProductos().stream()
                .map(dto -> Producto.builder()
                        .nombre(dto.getNombre())
                        .precio(dto.getPrecio())
                        .build())
                .collect(Collectors.toList());

        // Crear pedido
        Pedido pedido = Pedido.builder()
                .clienteId(input.getClienteId())
                .productos(productos)
                .total(totalFinal)
                .estado(Pedido.EstadoPedido.PENDIENTE)
                .build();

        Pedido guardado = pedidoRepository.save(pedido);
        log.info("Pedido creado con ID: {} - Total: {}", guardado.getId(), totalFinal);

        return mapearADTO(guardado);
    }

    /**
     * Obtiene todos los pedidos.
     */
    @Transactional(readOnly = true)
    public List<PedidoDTO> listarPedidos() {
        log.info("Listando todos los pedidos");
        return pedidoRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un pedido por ID.
     */
    @Transactional(readOnly = true)
    public PedidoDTO obtenerPedido(Long id) {
        log.info("Buscando pedido con ID: {}", id);
        Pedido pedido = pedidoRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Pedido", id));
        return mapearADTO(pedido);
    }

    /**
     * Obtiene pedidos de un cliente específico.
     */
    @Transactional(readOnly = true)
    public List<PedidoDTO> listarPedidosPorCliente(Long clienteId) {
        log.info("Listando pedidos del cliente ID: {}", clienteId);
        return pedidoRepository.findByClienteId(clienteId).stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    /**
     * Mapea una entidad Pedido a DTO.
     */
    private PedidoDTO mapearADTO(Pedido pedido) {
        List<ProductoDTO> productosDTO = pedido.getProductos().stream()
                .map(p -> ProductoDTO.builder()
                        .nombre(p.getNombre())
                        .precio(p.getPrecio())
                        .build())
                .collect(Collectors.toList());

        return PedidoDTO.builder()
                .id(pedido.getId())
                .clienteId(pedido.getClienteId())
                .productos(productosDTO)
                .total(pedido.getTotal())
                .build();
    }
}

