package com.grupo6.biblioteca_digital.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO que representa una categoría de libro")
public class CategoriaDTO {

    @Schema(description = "ID único de la categoría", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre de la categoría es obligatorio")
    @Schema(description = "Nombre de la categoría", example = "Ciencia ficción", required = true)
    private String nombre;

    @Schema(description = "Descripción de la categoría", example = "Libros de ciencia ficción y fantasía")
    private String descripcion;
}
