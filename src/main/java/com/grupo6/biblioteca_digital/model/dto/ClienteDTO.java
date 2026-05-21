package com.grupo6.biblioteca_digital.model.dto;

import com.grupo6.biblioteca_digital.Enums.TipoIdentidad;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "Objeto que transporta los datos del Cliente entre el Frontend y el Backend")
public class ClienteDTO {
    @Schema(description = "Identificador unico del cliente", example = "1", accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Schema(description = "Nombre del cliente", example = "Santiago", requiredMode = Schema.RequiredMode.REQUIRED)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Schema(description = "Apellido del cliente", example = "Sanchez", requiredMode = Schema.RequiredMode.REQUIRED)
    private String apellido;

    @NotNull(message = "El tipo de identidad es obligatorio")
    @Schema(description = "Tipo de identidad del cliente", example = "CEDULA", requiredMode = Schema.RequiredMode.REQUIRED)
    private TipoIdentidad tipoIdentidad;

    @NotBlank(message = "El número de identidad es obligatorio")
    @Schema(description = "Número de identidad del cliente", example = "123456789", requiredMode = Schema.RequiredMode.REQUIRED)
    private String numeroIdentidad;

    @NotBlank(message = "El correo electrónico es obligatorio")
    @Email(message = "El formato del correo es inválido")
    @Schema(description = "Correo electronico del cliente", example = "prueba123@gmail.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "Dirección del cliente", example = "Calle 123 #45-67")
    private String direccion;

    @Schema(description = "Teléfono del cliente", example = "555-1234")
    private String telefono;
}
