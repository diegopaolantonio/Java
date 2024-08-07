package com.proyectoPaolantonio.proyectoPaolantonio.repositories;

import com.proyectoPaolantonio.proyectoPaolantonio.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoicesRepository extends JpaRepository<Invoice, Long> {
}
