package com.proyectoPaolantonio.proyectoPaolantonio.configs;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info (
                title = "Paolantonio's final project",
                version = "v1.0",
                description = "ABM of Clients, Products, Carts and Invoices for the final project for Coderhouse's Java programming course commission 57315."
        )
)
public class OpenApi {
}
