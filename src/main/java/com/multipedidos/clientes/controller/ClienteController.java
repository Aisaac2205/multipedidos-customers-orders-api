package com.multipedidos.clientes.controller;

import com.multipedidos.clientes.dto.ClienteDTO;
import com.multipedidos.clientes.dto.ClienteInputDTO;
import com.multipedidos.clientes.service.ClienteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gestión de clientes.
 */
@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "API para gestión de clientes")
public class ClienteController {

    private final ClienteService clienteService;

    @PostMapping
    @Operation(summary = "Crear un cliente", description = "Crea un nuevo cliente en el sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente creado correctamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos")
    })
    public ResponseEntity<ClienteDTO> crearCliente(@Valid @RequestBody ClienteInputDTO input) {
        ClienteDTO cliente = clienteService.crearCliente(input);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @GetMapping
    @Operation(summary = "Listar todos los clientes", description = "Obtiene la lista completa de clientes")
    @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida correctamente")
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<ClienteDTO> clientes = clienteService.listarClientes();
        return ResponseEntity.ok(clientes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un cliente por ID", description = "Obtiene los detalles de un cliente específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    public ResponseEntity<ClienteDTO> obtenerCliente(@PathVariable Long id) {
        ClienteDTO cliente = clienteService.obtenerCliente(id);
        return ResponseEntity.ok(cliente);
    }
}

