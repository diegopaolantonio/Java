package managers;

import entities.Cart;
import entities.Client;
import entities.Invoice;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.time.LocalDateTime;
import java.util.List;

public class InvoicesManager {

    public void create(Client client) {
        CartsManager cartsManager = new CartsManager();
        EntityManager manager = null;
        EntityTransaction transaction;
        List<Cart> carts_client = null;
        double total = 0;
        try {
            manager = Manager.get();
            transaction = manager.getTransaction();
            transaction.begin();
            Invoice invoice = new Invoice();

            carts_client = cartsManager.readOneByClient(client);
            for (Cart cart : carts_client) {
                total += cart.getAmount() * cart.getPrice();
            }
            invoice.setTotal(total);
            invoice.setCreated_at(LocalDateTime.now());
            invoice.setClient_id(client);

            manager.persist(invoice);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public List<Invoice> readAll() {
        EntityManager manager = null;
        List<Invoice> invoices = null;
        try {
            manager = Manager.getEntityManager();
            invoices = manager.createQuery("From Invoice", Invoice.class).getResultList();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
            return invoices;
        }
    }

    public Invoice readOne(Integer id) {
        EntityManager manager = null;
        Invoice invoice = null;
        try {
            manager = Manager.get();
            invoice = manager.find(Invoice.class, id);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
            return invoice;
        }
    }

    public void delete(Integer invoice_id) {
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = Manager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Query query = manager.createQuery("DELETE FROM Invoice WHERE id = " + invoice_id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public void update(Integer invoice_id) {
        EntityManager manager = null;
        CartsManager cartsManager = new CartsManager();
        Invoice invoice = this.readOne(invoice_id);
        Query query;
        EntityTransaction transaction;
        List<Cart> carts_client = null;
        double total = 0;
        try {
            manager = Manager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();

            carts_client = cartsManager.readOneByClient(invoice.getClient_id());
            for (Cart cart : carts_client) {
                total += cart.getAmount() * cart.getPrice();
            }

            if (total != invoice.getTotal()) {
                query = manager.createQuery("UPDATE Invoice set total = " + total + " WHERE id = " + invoice_id);
                query.executeUpdate();
            }
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

}
