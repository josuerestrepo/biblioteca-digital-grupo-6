package com.grupo6.biblioteca_digital.model.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.grupo6.biblioteca_digital.Enums.Rol;
import com.grupo6.biblioteca_digital.Enums.TipoIdentidad;
import com.grupo6.biblioteca_digital.model.embeddable.Contacto;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
@JsonIgnoreProperties({"compras", "prestamos", "password"})
@Table(name = "clientes")
public class ClienteEntity extends BaseEntity {
    
    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "apellido", nullable = false)
    private String apellido;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_identidad", nullable = false)
    private TipoIdentidad tipoIdentidad;

    @Column(name = "numero_identidad", nullable = false, unique = true)
    private String numeroIdentidad;

    @Embedded
    private Contacto contacto;

    @Column(name = "activo", nullable = false)
    private Boolean activo = true;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Rol rol = Rol.CLIENTE; //por defecto todos son clientes

    @OneToMany(mappedBy = "cliente")
    private List<Compra> compras;

    @OneToMany(mappedBy = "cliente")
    private List<PrestamosEntity> prestamos;
}