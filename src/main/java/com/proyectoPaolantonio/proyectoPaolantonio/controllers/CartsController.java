package com.proyectoPaolantonio.proyectoPaolantonio.controllers;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Cart;
import com.proyectoPaolantonio.proyectoPaolantonio.entities.Client;
import com.proyectoPaolantonio.proyectoPaolantonio.entities.Product;
import com.proyectoPaolantonio.proyectoPaolantonio.services.CartsService;
import com.proyectoPaolantonio.proyectoPaolantonio.services.ClientsService;
import com.proyectoPaolantonio.proyectoPaolantonio.services.ProductsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/carts")
public class CartsController {

    @Autowired private CartsService service;
    @Autowired private ClientsService clientsService;
    @Autowired private ProductsService productsService;

    // Metodo para crear un cart, identificando el id del client y el del product en la ruta de la consulta
    @PostMapping
    public ResponseEntity<Cart> create(@RequestBody Cart cart) throws Exception {
        try {
            // Revisa si existe un client con el id especificado, si no existe devuelve una Exception
            if (cart.getClient() != null) {
                if (cart.getClient().getId() != null) {
                    Long client_id = cart.getClient().getId();
                    Optional<Client> foundClient = clientsService.readById(client_id);
                    if (!foundClient.isPresent()) {
                        throw new Exception("Client not found with id: " + client_id);
                    }
                    Client client = foundClient.get();
                    // Carga el client especificado en el cart
                    cart.setClient(client);
                } else {
                    throw new Exception("Client id not logged in");
                }
            } else {
                throw new Exception("Client id not logged in");
            }

            // Revisa si existe un product con el id especificado, si no existe devuelve una Exception
            if (cart.getProduct() != null) {
                if (cart.getProduct().getId() != null) {
                    Long product_id = cart.getProduct().getId();
                    Optional<Product> foundProduct = productsService.readById(product_id);
                    if (!foundProduct.isPresent()) {
                        throw new Exception("Product not found with id: " + product_id);
                    }
                    Product product = foundProduct.get();
                    // Carga el product y el price del product especificado en el cart
                    cart.setProduct(product);
                    cart.setPrice(product.getPrice());
                } else {
                    throw new Exception("Product id not logged in");
                }
            } else {
                throw new Exception("Product id not logged in");
            }

            // El amount ya viene desde el body de la consulta enviada

            return new ResponseEntity<>(service.save(cart), HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Metodo para buscar todos los carts
    @GetMapping
    public ResponseEntity<List<Cart>> readAll() {
        try {
            List<Cart> carts = service.readAll();
            return ResponseEntity.ok(carts);
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Metodo para buscar un cart especificado por el id enviado en la ruta
    @GetMapping("/{id}")
    public ResponseEntity<Object> readById(@PathVariable Long id) {
        try {
            // Busca el cart especifico, si no existe devuelve "not found"
            Optional<Cart> cart = service.readById(id);
            if (cart.isPresent()) {
                return ResponseEntity.ok(cart.get());
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Metodo para actualizar un cart ya creado, identificado por el id especificado
    @PatchMapping("/{id}")
    public ResponseEntity<Cart> update(@PathVariable Long id, @RequestBody Cart cart) throws Exception {
        try {
            // Busca el cart por el id
            Optional<Cart> cartToUpdate = service.readById(id);
            // Revisa si el cart existe, si no existe envia "not found"
            if (cartToUpdate.isPresent()) {
                // Pasa el cart encontrado a una variable tipo Cart
                Cart updatedCart = cartToUpdate.get();

                // Revisa si se envio un client en el body de la consulta
                if (cart.getClient() != null) {
                    // Revisa si el client tiene un id especificado para actualizar
                    if (cart.getClient().getId() != null) {
                        // Pasa el id de cliente enviado a una variale tipo Long y busca si exite un client con ese id
                        Long client_id = cart.getClient().getId();
                        Optional<Client> foundClient = clientsService.readById(client_id);
                        // Si el client no existe devuelve un Exception, si existe actualiza el cart
                        if (!foundClient.isPresent()) {
                            throw new Exception("Client not found with id: " + client_id);
                        } else {
                            Client client = foundClient.get();
                            updatedCart.setClient(client);
                        }
                    }
                }

                // Revisa si se envio un product en el body de la consulta
                if (cart.getProduct() != null) {
                    // Revisa si el product tiene un id especificado para actualizar
                    if (cart.getProduct().getId() != null) {
                        // Pasa el id de cliente enviado a una variale tipo Long y busca si exite un client con ese id
                        Long product_id = cart.getProduct().getId();
                        Optional<Product> foundProduct = productsService.readById(product_id);
                        // Si el product no existe devuelve un Exception, si existe actualiza el cart
                        if (!foundProduct.isPresent()) {
                            throw new Exception("Product not found with id: " + product_id);
                        } else {
                            Product product = foundProduct.get();
                            updatedCart.setProduct(product);
                        }
                    }
                }

                // Revisa si se envio un amount en el body de la consulta
                if (cart.getAmount() != null) {
                    // Si el amount existe actualiza el cart
                    updatedCart.setAmount(cart.getAmount());
                }

                // Actualiza el price con el price el product que esta en el cart
                updatedCart.setPrice(updatedCart.getProduct().getPrice());

                return ResponseEntity.ok(service.save(updatedCart));
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Metodo para eliminar un cart identificado por el id enviado en la ruta
    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Boolean>> delete(@PathVariable Long id) {
        try {
            // Busca si existe el cart con el id enviado, si existe lo elimina, si no devuelve "not found"
            Optional<Cart> cartToDelete = service.readById(id);
            if (cartToDelete.isPresent()) {
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
