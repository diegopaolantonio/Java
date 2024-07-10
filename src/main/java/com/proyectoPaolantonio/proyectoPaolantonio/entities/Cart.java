package com.proyectoPaolantonio.proyectoPaolantonio.entities;

import jakarta.persistence.*;
import lombok.*;

@Entity @Table(name = "carts")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Cart {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Getter @Setter private Integer amount;
    @Getter @Setter private double price;

    @ManyToOne @JoinColumn(name = "client_id")
    @Getter @Setter private Client client;

    @ManyToOne @JoinColumn(name = "product_id")
    @Getter @Setter private Product product;

}
