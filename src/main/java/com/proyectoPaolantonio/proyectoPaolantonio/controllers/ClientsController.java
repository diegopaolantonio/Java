package com.proyectoPaolantonio.proyectoPaolantonio.controllers;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Client;
import com.proyectoPaolantonio.proyectoPaolantonio.services.ClientsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/clients")
public class ClientsController {

    @Autowired private ClientsService service;

    @PostMapping
    public ResponseEntity<Client> createClient(@RequestBody Client client) {
        try {
            return new ResponseEntity<>(service.save(client), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Client>> readAll() {
        try {
            List<Client> clients = service.readAll();
            return ResponseEntity.ok(clients);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readById(@PathVariable Long id) {
        try {
            Optional<Client> client = service.readById(id);
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
    public ResponseEntity<Client> update(@PathVariable Long id, @RequestBody Client client) {
        try {
            Optional<Client> clientToUpdate = service.readById(id);
            if(clientToUpdate.isPresent()) {
                Client updatedClient = clientToUpdate.get();
                if (client.getName() != null) {
                    updatedClient.setName(client.getName());
                }
                if (client.getLastname() != null) {
                    updatedClient.setLastname(client.getLastname());
                }
                if (client.getDocnumber() != null) {
                    updatedClient.setDocnumber(client.getDocnumber());
                }
                return ResponseEntity.ok(service.save(updatedClient));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch(Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        try {
            Optional<Client> clientToDelete = service.readById(id);
            if(clientToDelete.isPresent()) {
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
