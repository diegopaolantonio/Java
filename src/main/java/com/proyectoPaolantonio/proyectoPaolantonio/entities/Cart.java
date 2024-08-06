package com.proyectoPaolantonio.proyectoPaolantonio.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "carts")
@NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Cart entity")
public class Cart {

    @Schema(description = "Unique identifier", type = "Long", example = "185")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Schema(description = "Quantity to buy, cannot be 'null'", type = "Integer", example = "5")
    @Column(nullable = false)
    @Getter @Setter private Integer amount;

    @Schema(description = "Price of the product, cannot be 'null'", type = "double", example = "5500.50")
    @Column(nullable = false)
    @Getter @Setter private double price;

    @Schema(description = "Indicates if the product has already been sold, cannot be 'null'", type = "boolean", example = "true")
    @Column(nullable = false)
    @Getter @Setter private boolean executed;

    @Schema(description = "Cart client identifier", type = "Client")
    @ManyToOne @JoinColumn(name = "client_id")
    @JsonIgnore
    @Getter @Setter private Client client;

    @Schema(description = "Cart product identifier", type = "Product")
    @ManyToOne @JoinColumn(name = "product_id")
    @JsonIgnore
    @Getter @Setter private Product product;

}
