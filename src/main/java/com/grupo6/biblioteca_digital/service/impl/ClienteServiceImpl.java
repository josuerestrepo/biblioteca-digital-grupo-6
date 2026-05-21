package com.grupo6.biblioteca_digital.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.grupo6.biblioteca_digital.Enums.Rol;
import com.grupo6.biblioteca_digital.exception.BadRequestException;
import com.grupo6.biblioteca_digital.exception.ResourceNotFoundException;
import com.grupo6.biblioteca_digital.model.dto.ClienteDTO;
import com.grupo6.biblioteca_digital.model.dto.ClienteRegistroDTO;
import com.grupo6.biblioteca_digital.model.embeddable.Contacto;
import com.grupo6.biblioteca_digital.model.entity.ClienteEntity;
import com.grupo6.biblioteca_digital.repository.ClienteRepository;
import com.grupo6.biblioteca_digital.service.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    private final ClienteRepository repository;
    private final PasswordEncoder passwordEncoder;

    public ClienteServiceImpl(ClienteRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public ClienteDTO registrar(ClienteRegistroDTO dto) {
        if (dto.getPassword() == null || dto.getPassword().isBlank()) {
            throw new BadRequestException("La contraseña es obligatoria");
        }

        ClienteEntity entidad = new ClienteEntity();
        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setTipoIdentidad(dto.getTipoIdentidad());
        entidad.setNumeroIdentidad(dto.getNumeroIdentidad());

        Contacto con = new Contacto();
        con.setEmail(dto.getEmail());
        con.setTelefono(dto.getTelefono());
        con.setDireccion(dto.getDireccion());
        entidad.setContacto(con);

        entidad.setPassword(passwordEncoder.encode(dto.getPassword()));
        entidad.setRol(dto.getRol() != null ? dto.getRol() : Rol.CLIENTE);
        entidad.setActivo(true);

        ClienteEntity guardado = repository.save(entidad);
        return mapearDTO(guardado);
    }

    @Override
    public ClienteDTO guardar(ClienteDTO dto) {
        ClienteEntity cliente = mapearEntidad(dto);
        cliente.setRol(Rol.CLIENTE);
        cliente.setActivo(true);
        ClienteEntity guardado = repository.save(cliente);
        return mapearDTO(guardado);
    }

    @Override
    public List<ClienteDTO> obtenerTodos() {
        return repository.findAll().stream()
                .map(this::mapearDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ClienteDTO obtenerPorId(Long id) {
        return repository.findById(id)
                .map(this::mapearDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con id: " + id));
    }

    @Override
    public ClienteDTO actualizar(Long id, ClienteDTO dto) {
        ClienteEntity clienteExistente = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("No se puede actualizar, id no encontrado: " + id));

        clienteExistente.setNombre(dto.getNombre());
        clienteExistente.setApellido(dto.getApellido());
        clienteExistente.setTipoIdentidad(dto.getTipoIdentidad());
        clienteExistente.setNumeroIdentidad(dto.getNumeroIdentidad());

        Contacto con = new Contacto();
        con.setEmail(dto.getEmail());
        con.setTelefono(dto.getTelefono());
        con.setDireccion(dto.getDireccion());
        clienteExistente.setContacto(con);

        ClienteEntity actualizado = repository.save(clienteExistente);
        return mapearDTO(actualizado);
    }

    @Override
    public void eliminar(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("No se puede eliminar, id no existe: " + id);
        }
        repository.deleteById(id);
    }

    private ClienteDTO mapearDTO(ClienteEntity entidad) {
        ClienteDTO dto = new ClienteDTO();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setApellido(entidad.getApellido());
        dto.setTipoIdentidad(entidad.getTipoIdentidad());
        dto.setNumeroIdentidad(entidad.getNumeroIdentidad());
        if (entidad.getContacto() != null) {
            dto.setEmail(entidad.getContacto().getEmail());
            dto.setTelefono(entidad.getContacto().getTelefono());
            dto.setDireccion(entidad.getContacto().getDireccion());
        }
        return dto;
    }

    private ClienteEntity mapearEntidad(ClienteDTO dto) {
        ClienteEntity entidad = new ClienteEntity();
        entidad.setNombre(dto.getNombre());
        entidad.setApellido(dto.getApellido());
        entidad.setTipoIdentidad(dto.getTipoIdentidad());
        entidad.setNumeroIdentidad(dto.getNumeroIdentidad());

        Contacto con = new Contacto();
        con.setEmail(dto.getEmail());
        con.setTelefono(dto.getTelefono());
        con.setDireccion(dto.getDireccion());
        entidad.setContacto(con);
        return entidad;
    }
}
