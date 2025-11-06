package com.multipedidos.clientes.repository;

import com.multipedidos.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repositorio para la entidad Cliente.
 */
@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {
    
    /**
     * Busca un cliente por su correo electrónico.
     */
    Optional<Cliente> findByCorreo(String correo);
    
    /**
     * Verifica si existe un cliente con el correo dado.
     */
    boolean existsByCorreo(String correo);
    
    /**
     * Cuenta el número de pedidos asociados a un cliente.
     * Usa una consulta personalizada porque clienteId está en la entidad Pedido.
     */
    @Query("SELECT COUNT(p) FROM Pedido p WHERE p.clienteId = :clienteId")
    long countPedidosByClienteId(@Param("clienteId") Long clienteId);
}

