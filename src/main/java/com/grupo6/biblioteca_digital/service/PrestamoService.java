package com.grupo6.biblioteca_digital.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo6.biblioteca_digital.Enums.EstadoPrestamo;
import com.grupo6.biblioteca_digital.exception.BadRequestException;
import com.grupo6.biblioteca_digital.exception.ResourceNotFoundException;
import com.grupo6.biblioteca_digital.model.dto.PrestamoCreateDTO;
import com.grupo6.biblioteca_digital.model.dto.PrestamoDTO;
import com.grupo6.biblioteca_digital.model.entity.ClienteEntity;
import com.grupo6.biblioteca_digital.model.entity.LibroEntity;
import com.grupo6.biblioteca_digital.model.entity.PrestamosEntity;
import com.grupo6.biblioteca_digital.repository.ClienteRepository;
import com.grupo6.biblioteca_digital.repository.LibroRepository;
import com.grupo6.biblioteca_digital.repository.PrestamoRepository;

import jakarta.transaction.Transactional;

@Service
public class PrestamoService {
    @Autowired
    PrestamoRepository prestamoRepository;
    @Autowired
    ClienteRepository clienteRepository;
    @Autowired
    LibroRepository libroRepository;

    // 1. LISTAR TODOS LOS PRÉSTAMOS
    public List<PrestamoDTO> listarTodos() {
        return prestamoRepository.findAll()
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    // 2. BUSCAR POR ID
    public PrestamoDTO buscarPorId(Long id) {
        return prestamoRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado con ID: " + id));
    }

    // 3. REGISTRAR PRESTAMO (El corazón del Service)
    @Transactional
    public PrestamoDTO registrarPrestamo(PrestamoCreateDTO dto) {
        // A. Validar que el cliente existe
        ClienteEntity cliente = clienteRepository.findById(dto.clienteId())
                .orElseThrow(() -> new ResourceNotFoundException("Error: El cliente con ID " + dto.clienteId() + " no existe."));

        // B. Validar que el libro existe
        LibroEntity libro = libroRepository.findById(dto.libroId())
                .orElseThrow(() -> new ResourceNotFoundException("Error: El libro con ID " + dto.libroId() + " no existe."));

        // C. Lógica de negocio: Verificar stock
        if (libro.getCantidad() == null || libro.getCantidad() <= 0) {
            throw new BadRequestException("No hay ejemplares disponibles de '" + libro.getTitulo() + "' para préstamo.");
        }

        // D. Actualizar el libro (Restar 1 y refrescar estado)
        libro.setCantidad(libro.getCantidad() - 1);
        libro.actualizarDisponibilidad(); // Tu método que cambia a NO_DISPONIBLE si llega a 0
        libroRepository.save(libro);

        // E. Crear la entidad Préstamo
        PrestamosEntity prestamo = new PrestamosEntity();
        prestamo.setCliente(cliente);
        prestamo.setLibro(libro);
        prestamo.setFechaSalida(LocalDateTime.now());
        // Calculamos la devolución basada en los días que pida el DTO
        prestamo.setFechaDevolucionEsperada(LocalDateTime.now().plusDays(dto.diaPrestamo()));
        prestamo.setEstado(EstadoPrestamo.ACTIVO); // Estado inicial

        // F. Guardar y devolver como DTO
        PrestamosEntity prestamoGuardado = prestamoRepository.save(prestamo);
        return toDTO(prestamoGuardado);
    }

    // 4. FINALIZAR PRÉSTAMO (Cuando devuelven el libro)
    @Transactional
    public void devolverLibro(Long prestamoId) {
        PrestamosEntity prestamo = prestamoRepository.findById(prestamoId)
                .orElseThrow(() -> new ResourceNotFoundException("Préstamo no encontrado"));

        if (prestamo.getEstado() == EstadoPrestamo.DEVUELTO) {
            throw new BadRequestException("Este préstamo ya fue devuelto anteriormente.");
        }

        // Marcar préstamo como devuelto
        prestamo.setEstado(EstadoPrestamo.DEVUELTO);
        
        // Devolver el libro al inventario
        LibroEntity libro = prestamo.getLibro();
        libro.setCantidad(libro.getCantidad() + 1);
        libro.actualizarDisponibilidad();
        
        libroRepository.save(libro);
        prestamoRepository.save(prestamo);
    }

    // --- MAPEO DE ENTIDAD A DTO ---
    private PrestamoDTO toDTO(PrestamosEntity entidad) {
        return new PrestamoDTO(
                entidad.getId(),
                entidad.getCliente().getNombre() + " " + entidad.getCliente().getApellido(),
                entidad.getLibro().getTitulo(),
                entidad.getFechaSalida(),
                entidad.getFechaDevolucionEsperada(),
                entidad.getEstado().toString()
        );
    }
}

