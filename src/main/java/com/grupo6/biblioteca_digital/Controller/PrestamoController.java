package com.grupo6.biblioteca_digital.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo6.biblioteca_digital.model.dto.PrestamoCreateDTO;
import com.grupo6.biblioteca_digital.model.dto.PrestamoDTO;
import com.grupo6.biblioteca_digital.service.PrestamoService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/prestamos")
@CrossOrigin(origins = "*")
public class PrestamoController {

    @Autowired
    private PrestamoService prestamoService;

    @GetMapping
    public ResponseEntity<List<PrestamoDTO>> listar() {
        return ResponseEntity.ok(prestamoService.listarTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PrestamoDTO> obtenerPorId(@PathVariable Long id) {
        return ResponseEntity.ok(prestamoService.buscarPorId(id));
    }

    @PostMapping
    public ResponseEntity<PrestamoDTO> crear(@Valid @RequestBody PrestamoCreateDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(prestamoService.registrarPrestamo(dto));
    }

    @PutMapping("/{id}/devolucion")
    public ResponseEntity<String> devolver(@PathVariable Long id) {
        prestamoService.devolverLibro(id);
        return ResponseEntity.ok("Libro devuelto exitosamente. El stock ha sido actualizado.");
    }
}