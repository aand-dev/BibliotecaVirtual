package com.bibliotecav.bibliotecav.models.services;

import com.bibliotecav.bibliotecav.models.entity.Libro;
import com.bibliotecav.bibliotecav.models.repository.LibroRepository;

public class LibroServiceImpl implements LibroService {
    private LibroRepository libroRepository;

    public LibroServiceImpl(LibroRepository libroRepositorio) {
        this.libroRepository = libroRepositorio;
    }

    @Override
    public Libro guardarLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    @Override
    public Libro obtenerLibroPorId(Long id) {
        return libroRepository.findById(id);
    }

    @Override
    public Iterable<Libro> obtenerTodosLosLibros() {
        return libroRepository.findAll();
    }

    @Override
    public void eliminarLibro(Long id) {
        Libro libro = obtenerLibroPorId(id);
        libroRepository.delete(libro);
    }
}
