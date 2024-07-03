package entities;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private double total;
    private LocalDateTime created_at;

    @ManyToOne
    @JoinColumn(name = "client_id")
    private Client client_id;

    public Invoice() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }

    public void setCreated_at(LocalDateTime created_at) {
        this.created_at = created_at;
    }

    public Client getClient_id() {
        return client_id;
    }

    public void setClient_id(Client client_id) {
        this.client_id = client_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice invoice)) return false;
        return Double.compare(total, invoice.total) == 0 && Objects.equals(id, invoice.id) && Objects.equals(created_at, invoice.created_at) && Objects.equals(client_id, invoice.client_id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, total, created_at, client_id);
    }

    @Override
    public String toString() {
        return "Invoice{" +
                "id=" + id +
                ", total=" + total +
                ", created_at=" + created_at +
                ", client_id=" + client_id +
                '}';
    }
}
