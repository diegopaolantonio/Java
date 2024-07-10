package com.proyectoPaolantonio.proyectoPaolantonio.repositories;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartsRepository extends JpaRepository<Cart, Long> {
}
