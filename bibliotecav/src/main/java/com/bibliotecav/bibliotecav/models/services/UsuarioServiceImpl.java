package com.bibliotecav.bibliotecav.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bibliotecav.bibliotecav.models.entity.Usuario;
import com.bibliotecav.bibliotecav.models.repository.UsuarioRepository;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    
    @Autowired
	private UsuarioRepository usuarioRepository;

    @Override
	@Transactional(readOnly = true)
	public List<Usuario> findAll() {
		return (List<Usuario>) usuarioRepository.findAll();
	}

    @Override
	@Transactional
	public void save(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

    @Override
	@Transactional
	public void delete(Long id) {
		usuarioRepository.deleteById(id);
	}
}
