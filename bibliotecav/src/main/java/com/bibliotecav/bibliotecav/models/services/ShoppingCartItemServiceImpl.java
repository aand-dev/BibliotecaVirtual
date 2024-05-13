package com.bibliotecav.bibliotecav.models.services;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import com.bibliotecav.bibliotecav.models.entity.Libro;
import com.bibliotecav.bibliotecav.models.entity.ShoppingCartItem;
import com.bibliotecav.bibliotecav.models.entity.Usuario;
import com.bibliotecav.bibliotecav.models.repository.ShoppingCartItemRepository;

public class ShoppingCartItemServiceImpl implements ShoppingCartItemService {
    private ShoppingCartItemRepository shoppingCartItemRepository;

    public ShoppingCartItemServiceImpl(ShoppingCartItemRepository shoppingCartItemRepository) {
        this.shoppingCartItemRepository = shoppingCartItemRepository;
    }

    @Override
    public void addToCart(Usuario usuario, Libro libro, int cantidad) {
        ShoppingCartItem shoppingCartItem = new ShoppingCartItem(usuario, libro, cantidad);
        shoppingCartItemRepository.save(shoppingCartItem);
    }

    @Override
    public void removeFromCart(Long itemId) {
        shoppingCartItemRepository.deleteById(itemId);
    }

    @Override
    public List<ShoppingCartItem> getCartItemsByUser(Usuario usuario) {
        return shoppingCartItemRepository.findByUsuario(usuario);
    }

    @Override
    public void updateCartItemQuantity(Long itemId, int cantidad) {
        Optional<ShoppingCartItem> optionalItem = shoppingCartItemRepository.findById(itemId);
        if (optionalItem.isPresent()) {
            ShoppingCartItem shoppingCartItem = optionalItem.get();
            shoppingCartItem.setCantidad(cantidad);
            shoppingCartItemRepository.save(shoppingCartItem);
        } else {
            throw new NoSuchElementException("El ítem del carrito no existe");
        }
    }

    @Override
    public void clearCart(Usuario usuario) {
        List<ShoppingCartItem> cartItems = shoppingCartItemRepository.findByUsuario(usuario);
        shoppingCartItemRepository.deleteAll(cartItems);
    }
}
