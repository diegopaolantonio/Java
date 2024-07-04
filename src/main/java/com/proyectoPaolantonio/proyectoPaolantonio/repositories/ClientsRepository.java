package com.proyectoPaolantonio.proyectoPaolantonio.repositories;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientsRepository extends JpaRepository<Client, Long> {
}
