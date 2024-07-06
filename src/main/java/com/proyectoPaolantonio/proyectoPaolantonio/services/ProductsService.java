package com.proyectoPaolantonio.proyectoPaolantonio.services;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Product;
import com.proyectoPaolantonio.proyectoPaolantonio.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    @Autowired ProductsRepository repository;

    public Product save(Product product) {
        return repository.save(product);
    }

    public List<Product> readAll() {
        return repository.findAll();
    }

    public Optional<Product> readById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
