package com.bibliotecav.bibliotecav.models.initialization;

import org.springframework.boot.CommandLineRunner;

import com.bibliotecav.bibliotecav.models.entity.Rol;
import com.bibliotecav.bibliotecav.models.repository.RolRepository;

public class RolesInitializer implements CommandLineRunner {
    
    private final RolRepository rolRepository;

    public RolesInitializer(RolRepository rolRepository) {
        this.rolRepository = rolRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Rol cliente = new Rol();
        cliente.setNombre("cliente");
        rolRepository.save(cliente);

        Rol administrador = new Rol();
        administrador.setNombre("administrador");
        rolRepository.save(administrador);
    }

}
