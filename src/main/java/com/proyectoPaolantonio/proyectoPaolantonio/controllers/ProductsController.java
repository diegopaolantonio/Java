package com.proyectoPaolantonio.proyectoPaolantonio.controllers;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Product;
import com.proyectoPaolantonio.proyectoPaolantonio.services.ProductsService;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/products")
@Tag(name = "Paths for products", description = "CRUD of products")
public class ProductsController {

    @Autowired private ProductsService service;

    @Operation(summary = "Create a new product", description = "Create a new product with the data sent in the 'body', in data 'id' is autogenerated, 'code' cannot be null, 'stock and price' will be '0' by default and 'code' must be unique")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "201", description = "Product created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: typing error, 'code' cannot be null or 'code' already exists", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
    @PostMapping
    public ResponseEntity<Product> create(@RequestBody Product product) {
        try {
            if(product.getStock() == null) {
                product.setStock(0);
            }
            return new ResponseEntity<>(service.save(product), HttpStatus.CREATED);
        } catch (DataIntegrityViolationException e) {
            Throwable cause = e.getCause();
            String errorMessage = "An error occurred";

            if (cause.getMessage().contains("not-null property")) {
                errorMessage = "Code cannot be null";
            } else if (cause.getMessage().contains("Duplicate entry")) {
                errorMessage = "Code already exists";
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

    @Operation(summary = "Get all products", description = "Retrieves a list of all products")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Products retrives successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
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

    @Operation(summary = "Get one product", description = "Retrieves a product by its 'id'")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Product retrive successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
    @GetMapping("/{pid}")
    public ResponseEntity<Object> readById(@PathVariable Long pid) {
        try {
            Optional<Product> client = service.readById(pid);
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

    @Operation(summary = "Update one product", description = "Updates one product by its 'id' with the data sent in the 'body', in data 'id' cannot be modified, 'code, description, stock and price' cannot be modified to null, 'price' cannot be modified to '0' and 'code' must be unique")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Product updated successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = Product.class))),
            @ApiResponse(responseCode = "400", description = "Bad request: typing error, 'code' already exists", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
    @PatchMapping("/{pid}")
    public ResponseEntity<Product> update(@PathVariable Long pid, @RequestBody Product product) {
        try {
            Optional<Product> productToUpdate = service.readById(pid);
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
        } catch (DataIntegrityViolationException e) {
            Throwable cause = e.getCause();
            String errorMessage = "An error occurred";

            if (cause.getMessage().contains("not-null property")) {
                errorMessage = "Code/Stock/Price cannot be null";
            } else if (cause.getMessage().contains("Duplicate entry")) {
                errorMessage = "Code already exists";
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

    @Operation(summary = "Remove one product", description = "Remove one product by its 'id'")
    @ApiResponses( value = {
            @ApiResponse(responseCode = "200", description = "Product deleted successfully", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "404", description = "Product not found", content = @Content(mediaType = "", schema = @Schema())),
            @ApiResponse(responseCode = "500", description = "Internal server error", content = @Content(mediaType = "", schema = @Schema()))
    })
    @DeleteMapping("/{pid}")
    public ResponseEntity<Map<String, Boolean>> remove(@PathVariable Long pid) {
        try {
            Optional<Product> productToDelete = service.readById(pid);
            if (productToDelete.isPresent()) {
                service.delete(pid);
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
