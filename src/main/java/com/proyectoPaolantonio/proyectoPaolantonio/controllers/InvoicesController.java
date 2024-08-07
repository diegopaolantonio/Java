package com.proyectoPaolantonio.proyectoPaolantonio.controllers;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Cart;
import com.proyectoPaolantonio.proyectoPaolantonio.entities.Client;
import com.proyectoPaolantonio.proyectoPaolantonio.entities.Invoice;
import com.proyectoPaolantonio.proyectoPaolantonio.services.CartsService;
import com.proyectoPaolantonio.proyectoPaolantonio.services.ClientsService;
import com.proyectoPaolantonio.proyectoPaolantonio.services.InvoicesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/invoices")
@Tag(name = "Paths for invoices", description = "CRUD of invoices")
public class InvoicesController {

    @Autowired private InvoicesService service;
    @Autowired private CartsService cartsService;
    @Autowired private ClientsService clientsService;

    // Metodo para crear un invoice de un carts pertenecientes a un client especificado por el id
    @Operation(summary = "Create a new invoice", description = "Create a new invoice for the client indicated in the 'body', 'id' is autogenerated for which it will search for all the carts belonging to this client, calculate the 'total' of all the carts and save the date and time in 'created_at', it will only be created if there are unexecuted 'carts'")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Invoice created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: typing error", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "409", description = "Conflict: Carts not found for this Client or all are already executed", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<Invoice> create(@RequestBody Client client) {
        try {
            boolean existCarts = false;
            Invoice invoice = new Invoice();
            Optional<Client> foundClient = clientsService.readById(client.getId());
            if (foundClient.isPresent()) {
                double total = 0;
                List<Cart> carts = cartsService.readAll();
                for (Cart cart : carts) {
                    if (cart.getClient().getId() == client.getId() && !cart.isExecuted()) {
                        total += cart.getAmount() * cart.getPrice();
                        cart.setExecuted(true);
                        cartsService.save(cart);
                        existCarts = true;
                    }
                }
                if (!existCarts) {
                    System.out.println("Carts not found for this Client");
                    return ResponseEntity.status(HttpStatus.CONFLICT).build();
                }
                invoice.setClient(foundClient.get());
                invoice.setTotal(total);
                invoice.setCreated_at(LocalDateTime.now());
                return new ResponseEntity<>(service.save(invoice), HttpStatus.CREATED);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataIntegrityViolationException e) {
            Throwable cause = e.getCause();
            String errorMessage = "An error occurred";

            if (cause.getMessage().contains("not-null property")) {
                errorMessage = "Total cannot be null";
            } else {
                errorMessage = "Data integrity violation";
            }
            System.out.println(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get all invoices", description = "Retrives a list of all invoices")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Invoice retrives successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
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

    @Operation(summary = "Get the last invoice of a client", description = "Retrives the last invoice of a client by its 'id'")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Invoice retrives successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "409", description = "Conflict: no 'Invoice' found for this 'Client'", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
    @GetMapping("/{clid}")
    public ResponseEntity<Object> readByClientId(@PathVariable Long clid) {
        try {
            Optional<Client> client = clientsService.readById(clid);
            if (!client.isPresent()) {
                return ResponseEntity.notFound().build();
            }

            Invoice clientInvoice = new Invoice();
            List<Invoice> invoices = service.readAll();
            for (Invoice invoice : invoices) {
                if(invoice.getClient().getId() == clid) {
                    clientInvoice = invoice;
                }
            }
            if (clientInvoice.getId() != null) {
                return ResponseEntity.ok(clientInvoice);
            } else {
                return ResponseEntity.status(HttpStatus.CONFLICT).build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get all invoices of a client", description = "Retrives all invoices of a client by its 'id'")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Invoice retrives successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))),
            @ApiResponse(responseCode = "404", description = "Client not found", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
    @GetMapping("/{clid}/all")
    public ResponseEntity<Object> readAllByClientId(@PathVariable Long clid) {
        try {
            Optional<Client> client = clientsService.readById(clid);
            if (client.isPresent()) {
                return ResponseEntity.ok(client.get().getInvoices());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get one invoice", description = "Retrives one invoice by its 'id'")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Invoice retrives successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))),
            @ApiResponse(responseCode = "404", description = "Invoice not found", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
    @GetMapping("/{iid}/invoice")
    public ResponseEntity<Object> readById(@PathVariable Long iid) {
        try {
            Optional<Invoice> invoice = service.readById(iid);
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

    @Operation(summary = "Update one invoice", description = "Updates one invoice by its 'id' with the 'total' sent in the 'body', 'id' cannot be modified, 'total' will be updated to '0' if nothing is sent in the 'body' or if it is 'null', and the time and date will be saved automatically")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Invoice updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Invoice.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: typing error", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Invoice not found", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
    @PatchMapping("/{iid}")
    public ResponseEntity<Invoice> update(@PathVariable Long iid, @RequestBody Invoice invoice) {
        try {
            Optional<Invoice> invoiceToUpdate = service.readById(iid);
            if (invoiceToUpdate.isPresent()) {
                Invoice updatedInvoice = invoiceToUpdate.get();
                updatedInvoice.setTotal(invoice.getTotal());
                updatedInvoice.setCreated_at(LocalDateTime.now());
                return ResponseEntity.ok(service.save(updatedInvoice));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (DataIntegrityViolationException e) {
            Throwable cause = e.getCause();
            String errorMessage = "An error occurred";

            if (cause.getMessage().contains("not-null property")) {
                errorMessage = "Total cannot be null";
            } else {
                errorMessage = "Data integrity violation";
            }
            System.out.println(errorMessage);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Remove one invoice", description = "Remove one invoice by its 'id'")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Invoice deleted successfully", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Invoice not found", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
    @DeleteMapping("/{iid}")
    public ResponseEntity<Map<String, Boolean>> remove(@PathVariable Long iid) {
        try {
            Optional<Invoice> invoiceToDelete = service.readById(iid);
            if (invoiceToDelete.isPresent()) {
                service.delete(iid);
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
