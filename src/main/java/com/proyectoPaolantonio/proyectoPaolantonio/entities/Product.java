package com.proyectoPaolantonio.proyectoPaolantonio.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.List;

@Entity @Table(name = "products")
@NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Product entity")
public class Product {

    @Schema(description = "Unique identifier", type = "Long", example = "25")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Schema(description = "Product code, must be 'unique' and cannot be 'null'", type = "Integer", example = "254")
    @Column(nullable = false, unique = true)
    @Getter @Setter private Integer code;

    @Schema(description = "Product description", type = "String", example = "Wireless mouse")
    @Getter @Setter private String description;

    @Schema(description = "Product actual stock, cannot be 'null'", type = "Integer", example = "45")
    @Column(nullable = false)
    @Getter @Setter private Integer stock;

    @Schema(description = "Product price, cannot be 'null'", type = "double", example = "210.20")
    @Column(nullable = false)
    @Getter @Setter private double price;

    @Schema(description = "Product carts", type = "List<Cart>")
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private List<Cart> carts;

}
