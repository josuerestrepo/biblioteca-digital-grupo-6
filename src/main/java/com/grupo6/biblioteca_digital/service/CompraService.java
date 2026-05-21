package com.grupo6.biblioteca_digital.service;

import com.grupo6.biblioteca_digital.exception.BadRequestException;
import com.grupo6.biblioteca_digital.exception.ResourceNotFoundException;
import com.grupo6.biblioteca_digital.model.dto.CompraCreateDTO;
import com.grupo6.biblioteca_digital.model.dto.CompraDTO;
import com.grupo6.biblioteca_digital.model.entity.ClienteEntity;
import com.grupo6.biblioteca_digital.model.entity.Compra;
import com.grupo6.biblioteca_digital.model.entity.LibroEntity;
import com.grupo6.biblioteca_digital.repository.ClienteRepository;
import com.grupo6.biblioteca_digital.repository.CompraRepository;
import com.grupo6.biblioteca_digital.repository.LibroRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private LibroRepository libroRepository;

    // Obtener todas las compras (Retorna lista de DTOs)
    public List<CompraDTO> listarCompras() {
        return compraRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // Buscar una compra por su ID (Retorna DTO o null)
    public CompraDTO buscarPorId(Long id) {
        return compraRepository.findById(id)
                .map(this::toDTO)
                .orElse(null);
    }

    // Registrar una nueva compra (Recibe CreateDTO y retorna DTO)
    @Transactional
    public CompraDTO registrarCompra(CompraCreateDTO dto) {

        //primero revisar si hay un cliente con el ID y un libro con el ID,
        ClienteEntity cliente = clienteRepository.findById(dto.clienteId())
        .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + dto.clienteId()));

        LibroEntity libro = libroRepository.findById(dto.libroId())
        .orElseThrow(() -> new ResourceNotFoundException("Libro no encontrado con ID: " + dto.libroId()));

        // lógica de negocio: ¿hay suficientes libros en stock?
        if (libro.getCantidad() == null || libro.getCantidad() < dto.cantidad()) {
            throw new BadRequestException("Ya no hay suficientes libros en stock para esta compra (stock actual: " + libro.getCantidad() + ")");
        }

        //Actualizar el stock del libro resteando la cantidad comprada
        libro.setCantidad(libro.getCantidad() - dto.cantidad());
        libroRepository.save(libro);

        //crear la compra y guardarla
        Compra compra = new Compra();
        compra.setCliente(cliente); //uniendo la compra con el cliente encontrado
        compra.setLibro(libro); //uniendo la compra con el libro encontrado
        compra.setProveedor(dto.proveedor());
        compra.setMonto(dto.monto());
        compra.setCantidad(dto.cantidad());

        // Aquí podrías luego sumar la 'cantidad' al stock global si fuera necesario
        Compra compraGuardada = compraRepository.save(compra);
        return toDTO(compraGuardada);
    }

    // Actualizar una compra existente (Recibe id, CreateDTO y retorna DTO)
    @Transactional
    public CompraDTO actualizarCompra(Long id, CompraCreateDTO detallesDto) {
        return compraRepository.findById(id).map(compra -> {
            compra.setProveedor(detallesDto.proveedor());
            compra.setMonto(detallesDto.monto());
            compra.setCantidad(detallesDto.cantidad());
            // Nota: No actualizamos 'fechaRegistro' para mantener el registro original
            Compra compraActualizada = compraRepository.save(compra);
            return toDTO(compraActualizada);
        }).orElse(null);
    }

    // Eliminar una compra (Se mantiene igual ya que usa el ID)
    @Transactional
    public boolean eliminarCompra(Long id) {
        return compraRepository.findById(id).map(compra -> {
            compraRepository.delete(compra);
            return true;
        }).orElse(false);
    }

    // --- MÉTODOS DE MAPEO MANUAL ---

    // Convierte de Entidad a DTO de respuesta
    private CompraDTO toDTO(Compra entidad) {
        return new CompraDTO(
            entidad.getId(),
            entidad.getCliente().getId(),
            entidad.getLibro().getId(),
            entidad.getLibro().getTitulo(), //para que react pueda mostrar el titulo del libro en vez del ID
            entidad.getProveedor(),
            entidad.getMonto(),
            entidad.getCantidad(),
            entidad.getFechaRegistro()
        );
    }
}