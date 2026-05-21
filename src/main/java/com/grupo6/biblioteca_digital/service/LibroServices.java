package com.grupo6.biblioteca_digital.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.grupo6.biblioteca_digital.exception.BadRequestException;
import com.grupo6.biblioteca_digital.exception.ResourceNotFoundException;
import com.grupo6.biblioteca_digital.model.dto.LibroDTO;
import com.grupo6.biblioteca_digital.model.entity.CategoriaEntity;
import com.grupo6.biblioteca_digital.model.entity.LibroEntity;
import com.grupo6.biblioteca_digital.repository.CategoriaRepository;
import com.grupo6.biblioteca_digital.repository.LibroRepository;

@Service
public class LibroServices {

    private final LibroRepository libroRepository;
    private final CategoriaRepository categoriaRepository;

    public LibroServices(
            LibroRepository libroRepository,
            CategoriaRepository categoriaRepository) {

        this.libroRepository = libroRepository;
        this.categoriaRepository = categoriaRepository;
    }

    // =========================
    // LISTAR
    // =========================

    public List<LibroDTO> listarLibrosDTO() {

        return libroRepository.findAll()
                .stream()
                .map(this::toDTO)
                .toList();
    }

    // =========================
    // BUSCAR POR ID
    // =========================

    public Optional<LibroDTO> buscarPorId(Long id) {

        return libroRepository.findById(id)
                .map(this::toDTO);
    }

    // =========================
    // ELIMINAR
    // =========================

    public void eliminarLibro(Long id) {

        if (!libroRepository.existsById(id)) {
            throw new ResourceNotFoundException("Libro no encontrado");
        }

        libroRepository.deleteById(id);
    }

    // =========================
    // CREAR LIBRO
    // =========================

    public LibroDTO guardarLibroDTO(LibroDTO libroDTO) {

        // ========= VALIDACIONES =========

        if (libroDTO.getTitulo() == null || libroDTO.getTitulo().isBlank()) {
            throw new BadRequestException("El título es obligatorio");
        }

        if (libroDTO.getCantidad() == null || libroDTO.getCantidad() <= 0) {
            throw new BadRequestException("La cantidad debe ser mayor a 0");
        }

        if (libroDTO.getCategoria() == null || libroDTO.getCategoria().isBlank()) {
            throw new BadRequestException("La categoría es obligatoria");
        }

        // ========= BUSCAR O CREAR CATEGORIA =========

        CategoriaEntity categoria = categoriaRepository
                .findByNombre(libroDTO.getCategoria())
                .orElseGet(() -> {

                    CategoriaEntity nuevaCategoria = new CategoriaEntity();

                    nuevaCategoria.setNombre(libroDTO.getCategoria());

                    nuevaCategoria.setDescripcion("Categoría creada automáticamente");

                    return categoriaRepository.save(nuevaCategoria);
                });

        // ========= VALIDAR DUPLICADOS =========

        Optional<LibroEntity> existente =
                libroRepository.findByTitulo(libroDTO.getTitulo());

        if (existente.isPresent()) {

            LibroEntity libroExistente = existente.get();

            libroExistente.setCantidad(
                    libroExistente.getCantidad() + libroDTO.getCantidad());

            libroExistente.actualizarDisponibilidad();

            LibroEntity actualizado = libroRepository.save(libroExistente);

            return toDTO(actualizado);
        }

        // ========= CREAR NUEVO =========

        LibroEntity nuevoLibro = toEntity(libroDTO, categoria);

        nuevoLibro.actualizarDisponibilidad();

        LibroEntity guardado = libroRepository.save(nuevoLibro);

        return toDTO(guardado);
    }

    // =========================
    // ACTUALIZAR
    // =========================

    public LibroDTO actualizarLibro(Long id, LibroDTO libroDTO) {

        LibroEntity libro = libroRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Libro no encontrado"));

        libro.setTitulo(libroDTO.getTitulo());

        libro.setCantidad(libroDTO.getCantidad());

        libro.setPrecio(libroDTO.getPrecio());

        // Buscar categoría
        CategoriaEntity categoria = categoriaRepository
                .findByNombre(libroDTO.getCategoria())
                .orElseGet(() -> {

                    CategoriaEntity nueva = new CategoriaEntity();

                    nueva.setNombre(libroDTO.getCategoria());

                    nueva.setDescripcion("Categoría creada automáticamente");

                    return categoriaRepository.save(nueva);
                });

        libro.setCategoria(categoria);

        libro.actualizarDisponibilidad();

        LibroEntity actualizado = libroRepository.save(libro);

        return toDTO(actualizado);
    }

    // =========================
    // DTO -> ENTITY
    // =========================

    private LibroEntity toEntity(
            LibroDTO dto,
            CategoriaEntity categoria) {

        LibroEntity entity = new LibroEntity();

        entity.setTitulo(dto.getTitulo());

        entity.setCantidad(dto.getCantidad());

        entity.setPrecio(dto.getPrecio());

        entity.setCategoria(categoria);

        return entity;
    }

    // =========================
    // ENTITY -> DTO
    // =========================
private LibroDTO toDTO(LibroEntity entity) {

    LibroDTO dto = new LibroDTO();

    dto.setId(entity.getId());

    dto.setTitulo(entity.getTitulo());

    dto.setCantidad(entity.getCantidad());

    dto.setPrecio(entity.getPrecio());

    dto.setEstado(entity.getEstado() == com.grupo6.biblioteca_digital.Enums.EstadoLibro.DISPONIBLE);

    dto.setCategoria(entity.getCategoria().getNombre());

    return dto;
}
}