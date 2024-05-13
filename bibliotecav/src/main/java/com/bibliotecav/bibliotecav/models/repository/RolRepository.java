package com.bibliotecav.bibliotecav.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecav.bibliotecav.models.entity.Rol;

public interface RolRepository extends JpaRepository<Rol, Long> {
    
    Rol findByNombre(String nombre);

}
