package com.grupo6.biblioteca_digital.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "compras")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Compra extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id", nullable = false) // Asegura que el nombre de la columna sea cliente_id
    @JsonIgnoreProperties({"compras", "prestamos", "password"})
    private ClienteEntity cliente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "libro_id", nullable = false)
    private LibroEntity libro;

    @Column(nullable = false)
    private String proveedor;

    @Column(nullable = false)
    private BigDecimal monto;

    @Column(nullable = false)
    private Integer cantidad;

}