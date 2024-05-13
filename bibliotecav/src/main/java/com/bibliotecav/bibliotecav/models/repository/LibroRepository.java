package com.bibliotecav.bibliotecav.models.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.bibliotecav.bibliotecav.models.entity.Libro;

public interface LibroRepository extends PagingAndSortingRepository<Libro, Long>{

    void delete(Libro libro);

    Libro save(Libro libro);

    Iterable<Libro> findAll();

    Libro findById(Long id);

    List<Libro> findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(String query, String query2);
    
}
