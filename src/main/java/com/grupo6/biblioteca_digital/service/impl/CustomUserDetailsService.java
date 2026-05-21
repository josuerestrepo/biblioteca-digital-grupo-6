package com.grupo6.biblioteca_digital.service.impl;

import com.grupo6.biblioteca_digital.model.entity.ClienteEntity;
import com.grupo6.biblioteca_digital.repository.ClienteRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    public CustomUserDetailsService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ClienteEntity cliente = clienteRepository.findByNumeroIdentidad(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + username));

        return User.withUsername(cliente.getNumeroIdentidad())
                .password(cliente.getPassword())
                .roles(cliente.getRol().name())
                .disabled(!Boolean.TRUE.equals(cliente.getActivo()))
                .build();
    }
}
