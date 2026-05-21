package com.grupo6.biblioteca_digital.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record PrestamoCreateDTO(
    @NotNull(message = "El ID del cliente es obligatorio") Long clienteId,
    @NotNull(message = "El ID del libro es obligatorio") Long libroId,
    @NotNull(message = "Los días de préstamo son obligatorios") @Min(value = 1, message = "El préstamo debe durar al menos 1 día") Integer diaPrestamo
) {} 
