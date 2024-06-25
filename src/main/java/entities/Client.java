package entities;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String lastname;
    private Integer docnumber;

    @OneToMany(mappedBy = "client_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> carts;

    @OneToMany(mappedBy = "client_id", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Invoice> invoices;

    public Client() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public Integer getDocnumber() {
        return docnumber;
    }

    public void setDocnumber(Integer docnumber) {
        this.docnumber = docnumber;
    }

    public List<Cart> getCarts() {
        return carts;
    }

    public void setCarts(List<Cart> carts) {
        this.carts = carts;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return Objects.equals(id, client.id) && Objects.equals(name, client.name) && Objects.equals(lastname, client.lastname) && Objects.equals(docnumber, client.docnumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, lastname, docnumber);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastname='" + lastname + '\'' +
                ", docnumber=" + docnumber +
                '}';
    }
}
