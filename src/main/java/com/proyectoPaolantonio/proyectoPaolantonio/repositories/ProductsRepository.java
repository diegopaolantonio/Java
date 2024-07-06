package com.proyectoPaolantonio.proyectoPaolantonio.repositories;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Long> {
}
