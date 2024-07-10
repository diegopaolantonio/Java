package com.proyectoPaolantonio.proyectoPaolantonio.services;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Invoice;
import com.proyectoPaolantonio.proyectoPaolantonio.repositories.InvoicesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InvoicesService {

    @Autowired InvoicesRepository repository;

    public Invoice save(Invoice invoice) {
        return repository.save(invoice);
    }

    public List<Invoice> readAll() {
        return repository.findAll();
    }

    public Optional<Invoice> readById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
