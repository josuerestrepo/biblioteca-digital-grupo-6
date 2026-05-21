package com.grupo6.biblioteca_digital.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.grupo6.biblioteca_digital.model.entity.ClienteEntity;
import com.grupo6.biblioteca_digital.Enums.TipoIdentidad;


@Repository
public interface ClienteRepository extends JpaRepository<ClienteEntity, Long> {
    Optional<ClienteEntity> findByNumeroIdentidad(String numeroIdentidad);
    Optional<ClienteEntity> findByTipoIdentidad(TipoIdentidad tipoIdentidad);
}
