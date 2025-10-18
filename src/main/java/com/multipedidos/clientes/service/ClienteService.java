package com.multipedidos.clientes.service;

import com.multipedidos.clientes.dto.ClienteDTO;
import com.multipedidos.clientes.dto.ClienteInputDTO;
import com.multipedidos.clientes.model.Cliente;
import com.multipedidos.clientes.repository.ClienteRepository;
import com.multipedidos.clientes.exceptions.DatosInvalidosException;
import com.multipedidos.clientes.exceptions.RecursoNoEncontradoException;
import com.multipedidos.clientes.utils.ValidadorCodigos;
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
        if (!ValidadorCodigos.esEmailValido(input.getCorreo())) {
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
                .orElseThrow(() -> new RecursoNoEncontradoException("Cliente con ID " + id + " no encontrado"));
        return mapearADTO(cliente);
    }

    /**
     * Verifica si un cliente existe.
     */
    public boolean existeCliente(Long id) {
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

