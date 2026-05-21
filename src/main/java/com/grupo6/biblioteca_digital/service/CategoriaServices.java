package com.grupo6.biblioteca_digital.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.grupo6.biblioteca_digital.model.dto.CategoriaDTO;
import com.grupo6.biblioteca_digital.model.entity.CategoriaEntity;
import com.grupo6.biblioteca_digital.repository.CategoriaRepository;

@Service
public class CategoriaServices {
    private final CategoriaRepository categoriaRepository;

    public CategoriaServices(CategoriaRepository categoriaRepository) {
        this.categoriaRepository = categoriaRepository;
    }

    public List<CategoriaDTO> listarCategorias() {
        return categoriaRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    public CategoriaDTO guardarCategoria(CategoriaDTO categoriaDTO) {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNombre(categoriaDTO.getNombre());
        categoria.setDescripcion(categoriaDTO.getDescripcion());
        CategoriaEntity guardada = categoriaRepository.save(categoria);
        return toDTO(guardada);
    }

    private CategoriaDTO toDTO(CategoriaEntity entity) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setId(entity.getId());
        dto.setNombre(entity.getNombre());
        dto.setDescripcion(entity.getDescripcion());
        return dto;
    }
}
