package com.proyectoPaolantonio.proyectoPaolantonio.services;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Client;
import com.proyectoPaolantonio.proyectoPaolantonio.repositories.ClientsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientsService {

    @Autowired ClientsRepository repository;

    public Client save(Client client) {
        return repository.save(client);
    }

    public List<Client> readAll() {
        return repository.findAll();
    }

    public Optional<Client> readById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

}
