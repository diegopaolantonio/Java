package managers;

import entities.Product;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import org.w3c.dom.Node;

import java.util.List;

public class ProductsManager {

    public void create(Integer code, String description, Integer stock, double price) {
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = Manager.get();
            transaction = manager.getTransaction();
            transaction.begin();
            Product product = new Product();
            product.setCode(code);
            product.setDescription(description);
            product.setStock(stock);
            product.setPrice(price);
            manager.persist(product);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public List<Product> readAll() {
        EntityManager manager = null;
        List<Product> products = null;
        try {
            manager = Manager.getEntityManager();
            products = manager.createQuery("From Product", Product.class).getResultList();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
            return products;
        }
    }

    public Product readOne(Integer id) {
        EntityManager manager = null;
        Product product = null;
        try {
            manager = Manager.get();
            product = manager.find(Product.class, id);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
            return product;
        }
    }

    public void delete(Integer product_id) {
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = Manager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Query query = manager.createQuery("DELETE FROM Product WHERE id = " + product_id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public void update(Integer product_id, Integer code, String description, Integer stock, String price) {
        EntityManager manager = null;
        Query query;
        EntityTransaction transaction;
        try {
            manager = Manager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            if (code != null) {
                query = manager.createQuery("UPDATE Product set code = '" + code + "' WHERE id = " + product_id);
                query.executeUpdate();
            }
            if (description != null) {
                query = manager.createQuery("UPDATE Product set description = '" + description + "' WHERE id = " + product_id);
                query.executeUpdate();
            }
            if (stock != null) {
                query = manager.createQuery("UPDATE Product set stock = " + stock + " WHERE id = " + product_id);
                query.executeUpdate();
            }
            if (price != null) {
                query = manager.createQuery("UPDATE Product set price = " + price + " WHERE id = " + product_id);
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
