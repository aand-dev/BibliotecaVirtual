package com.bibliotecav.bibliotecav.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.bibliotecav.bibliotecav.models.entity.Libro;
import com.bibliotecav.bibliotecav.models.repository.LibroRepository;

@Controller
public class LibroController {
    
    @Autowired
    private LibroRepository libroRepository;

    @GetMapping("/")
    public String mostrarPaginaInicio(Model model) {
        Pageable pageable = PageRequest.of(0, 4); // Mostrar solo los primeros 4 libros
        Page<Libro> libros = libroRepository.findAll(pageable);
        model.addAttribute("libros", libros);
        return "pagina_inicio";
    }

    @GetMapping("/libros")
    public String listarLibros(Model model) {
        Iterable<Libro> libros = libroRepository.findAll();
        model.addAttribute("libros", libros);
        return "lista_libros";
    }

    @GetMapping("/libros/{id}")
    public String verLibro(@PathVariable("id") Long id, Model model) {
        Libro libro = libroRepository.findById(id);
        model.addAttribute("libro", libro);
        return "detalle_libro";
    }

    @GetMapping("/libros/nuevo")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("libro", new Libro());
        return "formulario_creacion";
    }

    @PostMapping("/libros")
    public String guardarLibro(@ModelAttribute("libro") Libro libro) {
        libroRepository.save(libro);
        return "redirect:/libros";
    }

    @GetMapping("/libros/{id}/editar")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model) {
        Libro libro = libroRepository.findById(id); /*
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con el ID proporcionado: " + id)); */
        model.addAttribute("libro", libro);
        return "formulario_edicion";
    }

    @PostMapping("/libros/{id}/editar")
    public String guardarEdicion(@PathVariable("id") Long id, @ModelAttribute("libro") Libro libroActualizado) {
        Libro libro = libroRepository.findById(id); /* 
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con el ID proporcionado: " + id)); */

        libro.setTitulo(libroActualizado.getTitulo());
        libro.setAutor(libroActualizado.getAutor());
        libro.setGenero(libroActualizado.getGenero());
        libro.setPrecio(libroActualizado.getPrecio());
        libro.setStock(libroActualizado.getStock());
        libro.setImagen(libroActualizado.getImagen());

        libroRepository.save(libro);

        return "redirect:/libros";
    }

    @GetMapping("/libros/{id}/eliminar")
    public String eliminarLibro(@PathVariable("id") Long id) {
        Libro libro = libroRepository.findById(id); /*
                .orElseThrow(() -> new IllegalArgumentException("Libro no encontrado con el ID proporcionado: " + id)); */

        libroRepository.delete(libro);

        return "redirect:/libros";
    }

    //BUSCAR------------------------------------------------------------------------
    @GetMapping("/buscarLibros")
    public String buscarLibros(@RequestParam(name = "q", required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            List<Libro> libros = libroRepository.findByTituloContainingIgnoreCaseOrAutorContainingIgnoreCase(query, query);
            model.addAttribute("libros", libros);
        }
        return "lista_libros";
    }

}
