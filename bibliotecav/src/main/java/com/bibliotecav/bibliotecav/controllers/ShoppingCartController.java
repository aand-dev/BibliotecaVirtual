package com.bibliotecav.bibliotecav.controllers;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.bibliotecav.bibliotecav.models.entity.Libro;
import com.bibliotecav.bibliotecav.models.entity.ShoppingCartItem;
import com.bibliotecav.bibliotecav.models.entity.Usuario;
import com.bibliotecav.bibliotecav.models.repository.LibroRepository;
import com.bibliotecav.bibliotecav.models.repository.ShoppingCartItemRepository;
import com.bibliotecav.bibliotecav.models.repository.UsuarioRepository;


@Controller
@SessionAttributes("usuario")
@RequestMapping("/cart")
public class ShoppingCartController {

    private ShoppingCartItemRepository shoppingCartItemRepository;
    private UsuarioRepository usuarioRepository;
    private LibroRepository libroRepository;

    public ShoppingCartController(ShoppingCartItemRepository shoppingCartItemRepository, UsuarioRepository usuarioRepository, LibroRepository libroRepository) {
        this.shoppingCartItemRepository = shoppingCartItemRepository;
        this.usuarioRepository = usuarioRepository;
        this.libroRepository = libroRepository;
    }

    @GetMapping
    public String viewCart(Model model, @ModelAttribute("usuario") Usuario usuario) {
        // Obtener el nombre de usuario del usuario autenticado
        String username = usuario.getCorreo();

        // Obtener el usuario a través del repositorio utilizando el nombre de usuario
        Usuario usuarioActual = usuarioRepository.findByCorreo(username);

        // Obtener los elementos del carrito de compras del usuario actual
        List<ShoppingCartItem> cartItems = shoppingCartItemRepository.findByUsuario(usuarioActual);

        Double total = calculateCartTotal(cartItems); // Calcular el total

        // Pasar los datos a la vista
        model.addAttribute("total", total);
        
        model.addAttribute("cartItems", cartItems);

        return "cart";
    }

   @PostMapping("/add")
    public String addToCart(@RequestParam("bookId") Long bookId, RedirectAttributes redirectAttributes, @ModelAttribute("usuario") Usuario usuario) {
    // Obtener el libro a agregar (por ejemplo, a través de su ID)
        Libro libro = libroRepository.findById(bookId);

    // Obtener el nombre de usuario del usuario autenticado
        String username = usuario.getCorreo();

    // Obtener el usuario a través del repositorio utilizando el nombre de usuario
        Usuario usuarioActual = usuarioRepository.findByCorreo(username);

        if (libro != null && usuarioActual != null) {
        // Crear un nuevo objeto ShoppingCartItem y guardarlo en el repositorio
            ShoppingCartItem newItem = new ShoppingCartItem(usuarioActual, libro, 1); // Se establece una cantidad inicial de 1
            shoppingCartItemRepository.save(newItem);

        // Mostrar mensaje de éxito en la vista
            redirectAttributes.addFlashAttribute("successMessage", "El libro se ha agregado al carrito de compras.");
        } else {
        // Mostrar mensaje de error en la vista
            redirectAttributes.addFlashAttribute("errorMessage", "No se pudo agregar el libro al carrito de compras.");
        }

        return "redirect:/cart";
    }



    @PostMapping("/remove/{itemId}")
    public String removeFromCart(@PathVariable("itemId") Long itemId) {
        // Lógica para eliminar un ítem del carrito de compras
        shoppingCartItemRepository.deleteById(itemId);
        return "redirect:/cart";
    }

    @PostMapping("/update/{itemId}")
    public String updateCartItemQuantity(@PathVariable("itemId") Long itemId, @RequestParam("cantidad") int cantidad) {
        // Lógica para actualizar la cantidad de un ítem en el carrito de compras
        Optional<ShoppingCartItem> optionalItem = shoppingCartItemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            ShoppingCartItem shoppingCartItem = optionalItem.get();
            shoppingCartItem.setCantidad(cantidad);
            shoppingCartItemRepository.save(shoppingCartItem);
        } else {
            throw new NoSuchElementException("El ítem del carrito no existe");
        }
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(@ModelAttribute("usuario") Usuario usuario) {
        // Obtener el nombre de usuario del usuario autenticado
        String username = usuario.getCorreo();

        // Obtener el usuario a través del repositorio utilizando el nombre de usuario
        Usuario usuarioActual = usuarioRepository.findByCorreo(username);

        // Obtener los elementos del carrito de compras del usuario actual
        List<ShoppingCartItem> cartItems = shoppingCartItemRepository.findByUsuario(usuarioActual);

        // Eliminar los elementos del carrito de compras
        shoppingCartItemRepository.deleteAll(cartItems);

    return "redirect:/cart";
    }

    // Método para calcular el total de un elemento del carrito
    private Double calculateTotal(ShoppingCartItem item) {
        Double itemTotal = item.getLibro().getPrecio() * item.getCantidad();
        return itemTotal;
    }

    // Método para calcular el total general del carrito
    private Double calculateCartTotal(List<ShoppingCartItem> cartItems) {
        Double cartTotal = 0.0;
        for (ShoppingCartItem item : cartItems) {
            Double itemTotal = calculateTotal(item);
            cartTotal += itemTotal;
        }
        return cartTotal;
    }

}
