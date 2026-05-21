package com.grupo6.biblioteca_digital.model.dto;

import com.grupo6.biblioteca_digital.Enums.Rol;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "DTO para registrar un cliente con contraseña y rol")
public class ClienteRegistroDTO extends ClienteDTO {

    @NotBlank(message = "La contraseña es obligatoria")
    @Schema(description = "Contraseña del cliente", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;

    @NotNull(message = "El rol es obligatorio")
    @Schema(description = "Rol del cliente", example = "CLIENTE", requiredMode = Schema.RequiredMode.REQUIRED)
    private Rol rol;
}
