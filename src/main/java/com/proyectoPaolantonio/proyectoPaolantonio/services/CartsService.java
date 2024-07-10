package com.proyectoPaolantonio.proyectoPaolantonio.services;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Cart;
import com.proyectoPaolantonio.proyectoPaolantonio.repositories.CartsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartsService {

    @Autowired CartsRepository repository;

    public Cart save(Cart cart) {
        return repository.save(cart);
    }

    public List<Cart> readAll() {
        return repository.findAll();
    }

    public Optional<Cart> readById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
