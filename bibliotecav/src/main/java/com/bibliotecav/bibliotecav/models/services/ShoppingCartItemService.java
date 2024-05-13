package com.bibliotecav.bibliotecav.models.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bibliotecav.bibliotecav.models.entity.Libro;
import com.bibliotecav.bibliotecav.models.entity.ShoppingCartItem;
import com.bibliotecav.bibliotecav.models.entity.Usuario;

@Service
public interface ShoppingCartItemService {
    void addToCart(Usuario usuario, Libro libro, int cantidad);
    void removeFromCart(Long itemId);
    List<ShoppingCartItem> getCartItemsByUser(Usuario usuario);
    void updateCartItemQuantity(Long itemId, int cantidad);
    void clearCart(Usuario usuario);
}
