package com.bibliotecav.bibliotecav.models.services;

import java.util.List;

import com.bibliotecav.bibliotecav.models.entity.Usuario;

public interface UsuarioService {
    
    public List<Usuario> findAll();

    public void save(Usuario usuario);

    public void delete(Long id);
}
