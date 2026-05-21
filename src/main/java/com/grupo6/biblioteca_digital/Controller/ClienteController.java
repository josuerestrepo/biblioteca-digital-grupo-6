package com.grupo6.biblioteca_digital.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo6.biblioteca_digital.model.dto.ClienteDTO;
import com.grupo6.biblioteca_digital.model.dto.ClienteRegistroDTO;
import com.grupo6.biblioteca_digital.service.ClienteService;

import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/v1/clientes")
@Tag(name = "Clientes", description = "Gestión de usuarios y lectores de la biblioteca")
public class ClienteController {
    
    @Autowired
    private ClienteService clienteService;

    @Operation(summary = "Registrar usuario con credenciales", description = "Crea un cliente incluyendo su contraseña y rol de acceso.")
    @PostMapping("/registro")
    public ResponseEntity<ClienteDTO> registrar(@Valid @RequestBody ClienteRegistroDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.registrar(dto));
    }
    
    @Operation(summary = "Registrar nuevo cliente", description = "Crea un nuevo cliente en el sistema y le asigna un ID único.")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos en el JSON enviado")
    })

    @PostMapping
    public ResponseEntity<ClienteDTO> crear(@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Datos planos del cliente a registrar") @Valid @RequestBody ClienteDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteService.guardar(dto));
    }


    // GET: Obtener TODOS los clientes
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> obtenerTodos() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

    // GET: Obtener un cliente específico por su ID
    @Operation(summary = "Obtener cliente por ID", description = "Recupera los detalles de un cliente especifico utilizando su ID unico.")
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obtenerPorId(@Parameter(description = "ID unico del cliente a recuperar")
        @PathVariable Long id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    // PUT: Actualizar un cliente existente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> actualizar(@PathVariable Long id, @Valid @RequestBody ClienteDTO dto) {
        // El servicio debería buscar por ID y luego actualizar con los datos del DTO
        return ResponseEntity.ok(clienteService.actualizar(id, dto));
    }

    // DELETE: Eliminar un cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        clienteService.eliminar(id);
        // Retorna 204 NO CONTENT, indicando que se eliminó con éxito y no hay cuerpo en la respuesta
        return ResponseEntity.noContent().build(); 
    }
}
