package com.grupo6.biblioteca_digital.Controller;

import java.util.List;
import java.util.Optional;

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

import jakarta.validation.Valid;

import com.grupo6.biblioteca_digital.model.dto.LibroDTO;
import com.grupo6.biblioteca_digital.service.LibroServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/libros")

@Tag(
    name = "Libros",
    description = "Gestión completa de libros de la biblioteca digital"
)

public class LibroController {

    private final LibroServices libroServices;

    public LibroController(LibroServices libroServices) {
        this.libroServices = libroServices;
    }

    // LISTAR LIBROS
 

    @Operation(
        summary = "Listar todos los libros",
        description = "Obtiene una lista completa de todos los libros registrados en el sistema"
    )

    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Lista de libros obtenida correctamente"
        )
    })

    @GetMapping
    public ResponseEntity<List<LibroDTO>> listarLibros() {

        return ResponseEntity.ok(
                libroServices.listarLibrosDTO());
    }

  
    // BUSCAR POR ID


    @Operation(
        summary = "Buscar libro por ID",
        description = "Obtiene un libro específico usando su ID único"
    )

    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Libro encontrado"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Libro no encontrado"
        )
    })

    @GetMapping("/{id}")
    public ResponseEntity<LibroDTO> buscarPorId(

            @Parameter(
                description = "ID único del libro"
            )

            @PathVariable Long id) {

        Optional<LibroDTO> libro =
                libroServices.buscarPorId(id);

        return libro
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREAR LIBRO
  

    @Operation(
        summary = "Registrar nuevo libro",
        description = "Crea un nuevo libro en el sistema"
    )

    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Libro creado exitosamente"
        ),
        @ApiResponse(
            responseCode = "400",
            description = "Datos inválidos enviados"
        )
    })

    @PostMapping
    public ResponseEntity<LibroDTO> crearLibro(

            @io.swagger.v3.oas.annotations.parameters.RequestBody(
                description = "Datos del libro a registrar",
                required = true,

                content = @Content(
                    schema = @Schema(
                        implementation = LibroDTO.class
                    )
                )
            )

            @Valid @RequestBody LibroDTO libroDTO) {

        LibroDTO nuevo =
                libroServices.guardarLibroDTO(libroDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }


    // ACTUALIZAR

    @Operation(
        summary = "Actualizar libro",
        description = "Actualiza los datos de un libro existente"
    )

    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Libro actualizado correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Libro no encontrado"
        )
    })

    @PutMapping("/{id}")
    public ResponseEntity<LibroDTO> actualizarLibro(

            @Parameter(
                description = "ID del libro a actualizar"
            )

            @PathVariable Long id,

            @Valid @RequestBody LibroDTO libroDTO) {

        LibroDTO actualizado =
                libroServices.actualizarLibro(id, libroDTO);

        return ResponseEntity.ok(actualizado);
    }

    // ELIMINAR
   

    @Operation(
        summary = "Eliminar libro",
        description = "Elimina un libro del sistema usando su ID"
    )

    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Libro eliminado correctamente"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Libro no encontrado"
        )
    })

    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarLibro(

            @Parameter(
                description = "ID del libro a eliminar"
            )

            @PathVariable Long id) {

        libroServices.eliminarLibro(id);

        return ResponseEntity.ok("Libro eliminado correctamente");
    }
}
