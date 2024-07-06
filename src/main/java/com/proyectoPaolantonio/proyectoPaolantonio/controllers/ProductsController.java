package com.proyectoPaolantonio.proyectoPaolantonio.controllers;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Product;
import com.proyectoPaolantonio.proyectoPaolantonio.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
public class ProductsController {

    @Autowired private ProductsService service;

    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        try {
            return new ResponseEntity<>(service.save(product), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Product>> readAll() {
        try {
            List<Product> products = service.readAll();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readById(@PathVariable Long id) {
        try {
            Optional<Product> client = service.readById(id);
            if (client.isPresent()) {
                return ResponseEntity.ok(client.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product product) {
        try {
            Optional<Product> productToUpdate = service.readById(id);
            if (productToUpdate.isPresent()) {
                Product updatedProduct = productToUpdate.get();
                if (product.getCode() != null) {
                    updatedProduct.setCode(product.getCode());
                }
                if (product.getDescription() != null) {
                    updatedProduct.setDescription(product.getDescription());
                }
                if (product.getStock() != null) {
                    updatedProduct.setStock(product.getStock());
                }
                if (product.getPrice() != 0 ) {
                    updatedProduct.setPrice(product.getPrice());
                }
                return ResponseEntity.ok(service.save(updatedProduct));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        try {
            Optional<Product> productToDelete = service.readById(id);
            if (productToDelete.isPresent()) {
                service.delete(id);
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
