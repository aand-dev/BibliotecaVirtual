package com.bibliotecav.bibliotecav.models.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.bibliotecav.bibliotecav.models.entity.Usuario;


public interface UsuarioRepository extends CrudRepository<Usuario, Long>{
    Usuario findByCorreo(String correo);

    List<Usuario> findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(String query, String query2);

}
