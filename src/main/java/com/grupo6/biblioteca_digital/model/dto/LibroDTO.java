package com.grupo6.biblioteca_digital.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO que representa un libro")
public class LibroDTO {

    @Schema(description = "ID único del libro", example = "1")
    private Long id;

    @NotBlank(message = "El título del libro es obligatorio")
    @Schema(description = "Título del libro", example = "Drácula", requiredMode = Schema.RequiredMode.REQUIRED)
    private String titulo;

    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 0, message = "La cantidad no puede ser negativa")
    @Schema(description = "Cantidad disponible", example = "10", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer cantidad;

    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    @Schema(description = "Precio del libro", example = "45000", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double precio;

    @Schema(description = "Estado del libro", example = "true", accessMode = Schema.AccessMode.READ_ONLY)
    private Boolean estado;

    @NotBlank(message = "La categoría es obligatoria")
    @Schema(description = "Categoría del libro", example = "Terror", requiredMode = Schema.RequiredMode.REQUIRED)
    private String categoria;
}