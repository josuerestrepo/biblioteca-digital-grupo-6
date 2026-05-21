package com.grupo6.biblioteca_digital.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo6.biblioteca_digital.model.dto.CompraCreateDTO;
import com.grupo6.biblioteca_digital.model.dto.CompraDTO;
import com.grupo6.biblioteca_digital.service.CompraService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/compras")
@CrossOrigin(origins = "*")
public class CompraController {

    @Autowired
    private CompraService compraService;

    @GetMapping
    public ResponseEntity<List<CompraDTO>> listar() {
        return ResponseEntity.ok(compraService.listarCompras());
    }

    @PostMapping
    public ResponseEntity<CompraDTO> guardar(@Valid @RequestBody CompraCreateDTO compraDto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(compraService.registrarCompra(compraDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompraDTO> obtenerUno(@PathVariable Long id) {
        CompraDTO compra = compraService.buscarPorId(id);
        return compra != null ? ResponseEntity.ok(compra) : ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompraDTO> actualizar(@PathVariable Long id, @Valid @RequestBody CompraCreateDTO compraDto) {
        CompraDTO actualizada = compraService.actualizarCompra(id, compraDto);
        return actualizada != null ? ResponseEntity.ok(actualizada) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        boolean eliminado = compraService.eliminarCompra(id);
        return eliminado ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}