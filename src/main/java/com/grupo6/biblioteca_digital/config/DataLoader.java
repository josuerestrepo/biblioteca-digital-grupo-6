package com.grupo6.biblioteca_digital.config;

import com.grupo6.biblioteca_digital.Enums.Rol;
import com.grupo6.biblioteca_digital.Enums.TipoIdentidad;
import com.grupo6.biblioteca_digital.model.embeddable.Contacto;
import com.grupo6.biblioteca_digital.model.entity.ClienteEntity;
import com.grupo6.biblioteca_digital.repository.ClienteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataLoader implements CommandLineRunner {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) {
        if (clienteRepository.count() == 0) {
            ClienteEntity admin = new ClienteEntity();
            admin.setNombre("Administrador");
            admin.setApellido("Sistema");
            admin.setTipoIdentidad(TipoIdentidad.CEDULA);
            admin.setNumeroIdentidad("admin");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setRol(Rol.ADMIN);
            admin.setActivo(true);
            Contacto adminContacto = new Contacto();
            adminContacto.setEmail("admin@biblioteca.com");
            adminContacto.setTelefono("+573001112233");
            adminContacto.setDireccion("Calle Principal 1");
            admin.setContacto(adminContacto);

            ClienteEntity user = new ClienteEntity();
            user.setNombre("Usuario");
            user.setApellido("Cliente");
            user.setTipoIdentidad(TipoIdentidad.CEDULA);
            user.setNumeroIdentidad("user");
            user.setPassword(passwordEncoder.encode("user123"));
            user.setRol(Rol.CLIENTE);
            user.setActivo(true);
            Contacto userContacto = new Contacto();
            userContacto.setEmail("user@biblioteca.com");
            userContacto.setTelefono("+573009998877");
            userContacto.setDireccion("Calle Secundaria 2");
            user.setContacto(userContacto);

            clienteRepository.save(admin);
            clienteRepository.save(user);
        }
    }
}
