package com.grupo6.biblioteca_digital.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
@Schema(description = "DTO que representa un libro")
public class LibroDTO {

    @Schema(description = "ID único del libro", example = "1")
    private Long id;

    @Schema(description = "Título del libro", example = "Drácula")
    private String titulo;

    @Schema(description = "Cantidad disponible", example = "10")
    private Integer cantidad;

    @Schema(description = "Precio del libro", example = "45000")
    private Double precio;

    @Schema(description = "Estado del libro", example = "DISPONIBLE")
    private Boolean estado;

    @Schema(description = "Categoría del libro", example = "Terror")
    private String categoria;

    @Schema(description = "Autor del libro", example = "Bram Stoker")
    private String autor;

    @Schema(description = "ISBN del libro", example = "978-0141439577")
    private String isbn;

    @Schema(description = "Editorial del libro", example = "Penguin Classics")
    private String editorial;
}