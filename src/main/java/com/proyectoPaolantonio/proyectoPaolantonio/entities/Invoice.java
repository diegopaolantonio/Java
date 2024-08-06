package com.proyectoPaolantonio.proyectoPaolantonio.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity @Table(name = "invoices")
@NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Invoice entity")
public class Invoice {

    @Schema(description = "Unique identifier", type = "Long", example = "20")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Schema(description = "Total to pay, cannot be 'null'", type = "double", example = "8500.54")
    @Column(nullable = false)
    @Getter @Setter private double total;

    @Schema(description = "Creation date", type = "LocalDateTime", example = "2024-08-02T03:57:51.821Z")
    @Getter @Setter private LocalDateTime created_at;

    @Schema(description = "Invoice client identifier", type = "Client")
    @ManyToOne @JoinColumn(name = "client_id")
    @JsonIgnore
    @Getter @Setter private Client client;

}
