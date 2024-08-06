package com.proyectoPaolantonio.proyectoPaolantonio.entities;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity @Table(name = "clients")
@NoArgsConstructor @ToString @EqualsAndHashCode
@Schema(description = "Client entity")
public class Client {

    @Schema(description = "Unique identifier", type = "Long", example = "20")
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Getter @Setter private Long id;

    @Schema(description = "Client's name", type = "String", example = "Juan")
    @Getter @Setter private String name;

    @Schema(description = "Client's lastmane", type = "String", example = "Perez")
    @Getter @Setter private String lastname;

    @Schema(description = "Client document, must be 'unique' and cannot be 'null'", type = "Integer", example = "232323")
    @Column(nullable = false, unique = true)
    @Getter @Setter private Integer docnumber;

    @Schema(description = "Client carts", type = "List<Cart>")
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private List<Cart> carts;

    @Schema(description = "Client invoices", type = "List<Invoice>")
    @OneToMany(mappedBy = "client" , cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Getter @Setter private List<Invoice> invoices;

}
