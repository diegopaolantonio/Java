package managers;

import entities.Cart;
import entities.Client;
import entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CartsManager {

    public void create(Integer amount, Client client, Product product) {
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = Manager.get();
            transaction = manager.getTransaction();
            transaction.begin();
            Cart cart = new Cart();
            if (client != null && product != null) {
                cart.setAmount(amount);
                cart.setPrice(product.getPrice());
                cart.setClient_id(client);
                cart.setProduct_id(product);
                manager.persist(cart);
                transaction.commit();
            } else {
                System.out.println("Producto o Client no encontrado");
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public List<Cart> readAll() {
        EntityManager manager = null;
        List<Cart> carts = null;
        try {
            manager = Manager.getEntityManager();
            carts = manager.createQuery("From Cart", Cart.class).getResultList();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
            return carts;
        }
    }

    public Cart readOne(Integer id) {
        EntityManager manager = null;
        Cart cart = null;
        try {
            manager = Manager.get();
            cart = manager.find(Cart.class, id);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
            return cart;
        }
    }

    public List<Cart> readOneByClient(Client client) {
        EntityManager manager = null;
        List<Cart> carts = null;
        List<Cart> carts_client = new ArrayList<>();
        try {
            manager = Manager.getEntityManager();
            carts = manager.createQuery("From Cart", Cart.class).getResultList();
            for(Cart cart : carts) {
                if (cart.getClient_id().getId() == client.getId()) {
                    carts_client.add(cart);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
            return carts_client;
        }
    }

    public void delete(Integer cart_id) {
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = Manager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Query query = manager.createQuery("DELETE FROM Cart WHERE id = " + cart_id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public void update(Integer cart_id, Integer amount) {
        EntityManager manager = null;
        Cart cart_filter = this.readOne(cart_id);
        double price;
        Query query;
        EntityTransaction transaction;
        try {
            manager = Manager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            if (amount != null) {
                query = manager.createQuery("UPDATE Cart set amount = " + amount + " WHERE id = " + cart_id);
                query.executeUpdate();
            }
            price = cart_filter.getProduct_id().getPrice();
            query = manager.createQuery("UPDATE Cart set price = " + price + " WHERE id = " + cart_id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

}
