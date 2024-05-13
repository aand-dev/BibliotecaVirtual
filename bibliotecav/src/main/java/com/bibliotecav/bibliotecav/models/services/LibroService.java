package com.bibliotecav.bibliotecav.models.services;

import com.bibliotecav.bibliotecav.models.entity.Libro;

public interface LibroService {
    Libro guardarLibro(Libro libro);
    Libro obtenerLibroPorId(Long id);
    Iterable<Libro> obtenerTodosLosLibros();
    void eliminarLibro(Long id);
}
