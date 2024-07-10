package com.proyectoPaolantonio.proyectoPaolantonio.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name = "invoices")
@NoArgsConstructor @ToString @EqualsAndHashCode
public class Invoice {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Getter @Setter private double total;
    @Getter @Setter private LocalDateTime created_at;

    @ManyToOne @JoinColumn(name = "client_id")
    @Getter @Setter private Client client;

}
