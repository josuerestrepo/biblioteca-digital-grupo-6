package com.grupo6.biblioteca_digital.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.grupo6.biblioteca_digital.model.dto.CategoriaDTO;
import com.grupo6.biblioteca_digital.service.CategoriaServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
@Tag(name = "Categorías", description = "Gestión de categorías de libros")
public class CategoriaController {
    private final CategoriaServices categoriaServices;

    public CategoriaController(CategoriaServices categoriaServices) {
        this.categoriaServices = categoriaServices;
    }

    @Operation(summary = "Listar categorías", description = "Devuelve las categorías disponibles para los libros")
    @GetMapping
    public ResponseEntity<List<CategoriaDTO>> listar() {
        return ResponseEntity.ok(categoriaServices.listarCategorias());
    }

    @Operation(summary = "Crear categoría", description = "Registra una nueva categoría de libros")
    @PostMapping
    public ResponseEntity<CategoriaDTO> guardar(@Valid @RequestBody CategoriaDTO categoria) {
        return ResponseEntity.status(HttpStatus.CREATED).body(categoriaServices.guardarCategoria(categoria));
    }
}
