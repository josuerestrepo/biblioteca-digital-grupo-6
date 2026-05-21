package com.grupo6.biblioteca_digital.model.dto;

import java.math.BigDecimal;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompraCreateDTO(
    @NotNull(message = "El ID del cliente es obligatorio") Long clienteId,
    @NotNull(message = "El ID del libro es obligatorio") Long libroId,
    @NotBlank(message = "El proveedor es obligatorio") String proveedor,
    @NotNull(message = "El monto es obligatorio") @DecimalMin(value = "0.01", message = "El monto debe ser mayor a 0") BigDecimal monto,
    @NotNull(message = "La cantidad es obligatoria") @Min(value = 1, message = "La cantidad debe ser al menos 1") Integer cantidad
) {}