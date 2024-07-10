package com.proyectoPaolantonio.proyectoPaolantonio.controllers;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Cart;
import com.proyectoPaolantonio.proyectoPaolantonio.entities.Client;
import com.proyectoPaolantonio.proyectoPaolantonio.entities.Invoice;
import com.proyectoPaolantonio.proyectoPaolantonio.services.CartsService;
import com.proyectoPaolantonio.proyectoPaolantonio.services.ClientsService;
import com.proyectoPaolantonio.proyectoPaolantonio.services.InvoicesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/invoices")
public class InvoicesController {

    @Autowired private InvoicesService service;
    @Autowired private CartsService cartsService;
    @Autowired private ClientsService clientsService;

    // Metodo para crear un invoice de un carts pertenecientes a un client especificado por el id
    @PostMapping
    public ResponseEntity<Invoice> create(@RequestBody Invoice invoice) {
        try {
            Long client_id = invoice.getClient().getId();
            Optional<Client> client = clientsService.readById(client_id);
            if (client.isPresent()) {
                double total = 0;
                List<Cart> carts = cartsService.readAll();
                for (Cart cart : carts) {
                    if (cart.getClient().getId() == client_id) {
                        total += cart.getAmount() * cart.getPrice();
                    }
                }
                // Respuesta para cuando nos da que no hay un monto para facturar
                if (total == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
                invoice.setClient(client.get());
                invoice.setTotal(total);
                invoice.setCreated_at(LocalDateTime.now());
                return new ResponseEntity<>(service.save(invoice), HttpStatus.CREATED);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping
    public ResponseEntity<List<Invoice>> readAll() {
        try {
            List<Invoice> invoices = service.readAll();
            return ResponseEntity.ok(invoices);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> readById(@PathVariable Long id) {
        try {
            Optional<Invoice> invoice = service.readById(id);
            if (invoice.isPresent()) {
                return ResponseEntity.ok(invoice.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Invoice> update(@PathVariable Long id, @RequestBody Invoice invoice) {
        try {
            Optional<Invoice> invoiceToUpdate = service.readById(id);
            if (invoiceToUpdate.isPresent()) {
                Invoice updatedInvoice = invoiceToUpdate.get();
                double total = 0;
                List<Cart> carts = cartsService.readAll();
                if (invoice.getClient() != null) {
                    if (invoice.getClient().getId() != null) {
                        Optional<Client> foundClient = clientsService.readById(invoice.getClient().getId());
                        if (foundClient.isPresent()) {
                            updatedInvoice.setClient(foundClient.get());
                        } else {
                            return ResponseEntity.notFound().build();
                        }
                    }
                }
                for (Cart cart : carts) {
                    if (cart.getClient().getId() == updatedInvoice.getClient().getId()) {
                        total += cart.getAmount() * cart.getPrice();
                    }
                }
                if (total == 0) {
                    return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
                }
                updatedInvoice.setTotal(total);
                updatedInvoice.setCreated_at(LocalDateTime.now());
                return ResponseEntity.ok(service.save(updatedInvoice));
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
            Optional<Invoice> invoiceTodelete = service.readById(id);
            if (invoiceTodelete.isPresent()) {
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
