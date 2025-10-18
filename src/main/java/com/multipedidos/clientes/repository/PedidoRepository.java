package com.multipedidos.clientes.repository;

import com.multipedidos.clientes.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio para la entidad Pedido.
 */
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    
    /**
     * Busca todos los pedidos de un cliente específico.
     */
    List<Pedido> findByClienteId(Long clienteId);
    
    /**
     * Busca pedidos por estado.
     */
    List<Pedido> findByEstado(Pedido.EstadoPedido estado);
}

