package com.bibliotecav.bibliotecav.controllers;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bibliotecav.bibliotecav.models.entity.Usuario;
import com.bibliotecav.bibliotecav.models.repository.UsuarioRepository;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;


@Controller
@SessionAttributes("usuario")
public class UsuarioController {
    
    @Autowired
    private UsuarioRepository usuarioRepository;


    @RequestMapping(value = "/registro")
	public String registrarUsuario(Map<String, Object> model) {

		Usuario usuario = new Usuario();
		model.put("usuario", usuario);
		model.put("titulo", "Formulario de Usuario");
		return "registro";
	}

    @GetMapping("/login")
    public String mostrarFormularioLogin() {
        return "login";
    }


    @PostMapping("/login")
    public String iniciarSesion(
        @RequestParam("correo") String correo,
        @RequestParam("contrasena") String contrasena,
        Model model,
        HttpSession session) {

        Usuario usuario = usuarioRepository.findByCorreo(correo);
        if (usuario != null && usuario.getContrasena().equals(contrasena)) {
            // Iniciar sesión exitosa
            model.addAttribute("usuario", usuario); // Guarda el usuario en la sesión
            session.setAttribute("usuario", usuario); // También puedes usar el objeto HttpSession directamente

            return "redirect:/";
        } else {
            // Credenciales inválidas, mostrar mensaje de error
            model.addAttribute("error", "Credenciales inválidas");
            return "login";
        }
    }

    @GetMapping("/listar")
    public String listarUsuarios(Model model) {
        Iterable<Usuario> usuarios = usuarioRepository.findAll();
        model.addAttribute("usuarios", usuarios);
        return "listar";
    }

    @RequestMapping(value = "/registro/{id}")
    public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

        Optional<Usuario> usuario = usuarioRepository.findById(id);

        if (id > 0) {
            if (usuario == null) {
            flash.addFlashAttribute("error", "El ID del Usuario no existe en la BBDD!");
                return "redirect:/listar";
            }
        } else {
            flash.addFlashAttribute("error", "El ID del usuario no puede ser cero!");
            return "redirect:/listar";
        }

        model.put("usuario", usuario);
        model.put("titulo", "Editar Usuario");
        return "registro";
    }

    @RequestMapping(value = "/registro", method = RequestMethod.POST)
    public String guardar(@Valid Usuario usuario, BindingResult result, Model model,
                      RedirectAttributes flash, SessionStatus status) {

        if (result.hasErrors()) {
            model.addAttribute("titulo", "Formulario de Usuario");
            return "registro";
        }

        String mensajeFlash = (usuario.getId() != null) ? "Cliente editado con éxito!" : "Cliente creado con éxito!";

        usuarioRepository.save(usuario);
        status.setComplete();
        flash.addFlashAttribute("success", mensajeFlash);
        return "redirect:/listar";
    }



    @RequestMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable("id") Long id, RedirectAttributes flash) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario != null) {
            usuarioRepository.deleteById(id);
            flash.addFlashAttribute("success", "Usuario eliminado con éxito!");
            return "redirect:/listar";
        } else {
            // Manejar el escenario cuando el usuario no existe
            return "error";
        }
    }

    @GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {

		Usuario usuario = usuarioRepository.findById(id).orElse(null);
		if (usuario == null) {
			flash.addFlashAttribute("error", "El Usuario no existe en la base de datos");
			return "redirect:/listar";
		}

		model.put("usuario", usuario);
		model.put("titulo", "Detalle Usuario: " + usuario.getNombre());
		return "ver";
	}

    //BUSCAR------------------------------------------------------------------------
    @GetMapping("/buscar")
    public String buscarUsuarios(@RequestParam(name = "q", required = false) String query, Model model) {
        if (query != null && !query.isEmpty()) {
            List<Usuario> usuarios = usuarioRepository.findByNombreContainingIgnoreCaseOrApellidoContainingIgnoreCase(query, query);
            model.addAttribute("usuarios", usuarios);
        }
        return "listar";
    }

    //Cerrar sesión-------------------------------------------------------------------
    @GetMapping("/logout")
    public String cerrarSesion(Model model, HttpSession session) {
        session.removeAttribute("usuario"); // Elimina el atributo "usuario" de la sesión
        session.invalidate(); // Invalida la sesión

        // Agrega un mensaje de éxito
        model.addAttribute("success", "Cierre de sesión exitoso");

        return "redirect:/"; // O la página que corresponda tras cerrar sesión
    }

}
