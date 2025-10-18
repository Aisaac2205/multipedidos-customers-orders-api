package com.multipedidos.clientes.repository;

import com.multipedidos.clientes.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
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
}

