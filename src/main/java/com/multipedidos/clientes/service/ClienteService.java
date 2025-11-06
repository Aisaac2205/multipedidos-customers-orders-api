package com.multipedidos.clientes.service;

import com.multipedidos.clientes.dto.ClienteDTO;
import com.multipedidos.clientes.dto.ClienteInputDTO;
import com.multipedidos.clientes.model.Cliente;
import com.multipedidos.clientes.repository.ClienteRepository;
import com.multipedidos.common.exceptions.DatosInvalidosException;
import com.multipedidos.common.exceptions.RecursoNoEncontradoException;
import com.multipedidos.common.utils.ValidadorCodigos;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Servicio para la gestión de clientes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;

    /**
     * Crea un nuevo cliente.
     */
    @Transactional
    public ClienteDTO crearCliente(ClienteInputDTO input) {
        log.info("Creando nuevo cliente: {}", input.getNombre());

        // Validar email
        if (!ValidadorCodigos.validarEmail(input.getCorreo())) {
            throw new DatosInvalidosException("El formato del correo es inválido");
        }

        // Verificar que el correo no esté registrado
        if (clienteRepository.existsByCorreo(input.getCorreo())) {
            throw new DatosInvalidosException("Ya existe un cliente con ese correo");
        }

        Cliente cliente = Cliente.builder()
                .nombre(input.getNombre())
                .correo(input.getCorreo())
                .build();

        Cliente guardado = clienteRepository.save(cliente);
        log.info("Cliente creado con ID: {}", guardado.getId());

        return mapearADTO(guardado);
    }

    /**
     * Obtiene todos los clientes.
     */
    @Transactional(readOnly = true)
    public List<ClienteDTO> listarClientes() {
        log.info("Listando todos los clientes");
        return clienteRepository.findAll().stream()
                .map(this::mapearADTO)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene un cliente por ID.
     */
    @Transactional(readOnly = true)
    public ClienteDTO obtenerCliente(Long id) {
        log.info("Buscando cliente con ID: {}", id);
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", id));
        return mapearADTO(cliente);
    }

    /**
     * Actualiza un cliente existente.
     */
    @Transactional
    public ClienteDTO actualizarCliente(Long id, ClienteInputDTO input) {
        log.info("Actualizando cliente con ID: {}", id);

        // Verificar que el cliente existe
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", id));

        // Validar email
        if (!ValidadorCodigos.validarEmail(input.getCorreo())) {
            throw new DatosInvalidosException("El formato del correo es inválido");
        }

        // Verificar que el correo no esté registrado por otro cliente
        if (!cliente.getCorreo().equals(input.getCorreo()) && 
            clienteRepository.existsByCorreo(input.getCorreo())) {
            throw new DatosInvalidosException("Ya existe un cliente con ese correo");
        }

        // Actualizar datos
        cliente.setNombre(input.getNombre());
        cliente.setCorreo(input.getCorreo());

        Cliente actualizado = clienteRepository.save(cliente);
        log.info("Cliente actualizado con ID: {}", actualizado.getId());

        return mapearADTO(actualizado);
    }

    /**
     * Elimina un cliente.
     */
    @Transactional
    public void eliminarCliente(Long id) {
        log.info("Eliminando cliente con ID: {}", id);

        // Verificar que el cliente existe
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente", id));

        // Verificar si tiene pedidos asociados
        if (clienteRepository.countPedidosByClienteId(id) > 0) {
            throw new DatosInvalidosException("No se puede eliminar el cliente porque tiene pedidos asociados");
        }

        clienteRepository.delete(cliente);
        log.info("Cliente eliminado con ID: {}", id);
    }

    /**
     * Verifica si un cliente existe.
     */
    @Transactional(readOnly = true)
    public boolean existeCliente(Long id) {
        log.info("Verificando existencia de cliente con ID: {}", id);
        return clienteRepository.existsById(id);
    }

    /**
     * Mapea una entidad Cliente a DTO.
     */
    private ClienteDTO mapearADTO(Cliente cliente) {
        return ClienteDTO.builder()
                .id(cliente.getId())
                .nombre(cliente.getNombre())
                .correo(cliente.getCorreo())
                .build();
    }
}

