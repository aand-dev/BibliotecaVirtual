package com.bibliotecav.bibliotecav.models.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bibliotecav.bibliotecav.models.entity.ShoppingCartItem;
import com.bibliotecav.bibliotecav.models.entity.Usuario;

public interface ShoppingCartItemRepository extends JpaRepository<ShoppingCartItem, Long> {

    List<ShoppingCartItem> findByUsuario(Usuario usuario);
    
}
