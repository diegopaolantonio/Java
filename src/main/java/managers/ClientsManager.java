package managers;

import entities.Client;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.util.List;

public class ClientsManager {

    public void create(String name, String lastname, Integer docnumber) {
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = Manager.get();
            transaction = manager.getTransaction();
            transaction.begin();
            Client client = new Client();
            client.setName(name);
            client.setLastname(lastname);
            client.setDocnumber(docnumber);
            manager.persist(client);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public List<Client> readAll() {
        EntityManager manager = null;
        List<Client> clients = null;
        try {
            manager = Manager.getEntityManager();
            clients = manager.createQuery("From Client", Client.class).getResultList();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
            return clients;
        }
    }

    public Client readOne(Integer id) {
        EntityManager manager = null;
        Client client = null;
        try {
            manager = Manager.get();
            client = manager.find(Client.class, id);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
            return client;
        }
    }

    public void delete(Integer client_id) {
        EntityManager manager = null;
        EntityTransaction transaction;
        try {
            manager = Manager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            Query query = manager.createQuery("DELETE FROM Client WHERE id = " + client_id);
            query.executeUpdate();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            manager.close();
        }
    }

    public void update(Integer client_id, String name, String lastname, Integer docnumber) {
        EntityManager manager = null;
        Query query;
        EntityTransaction transaction;
        try {
            manager = Manager.getEntityManager();
            transaction = manager.getTransaction();
            transaction.begin();
            if (name != null) {
                query = manager.createQuery("UPDATE Client set name = '" + name + "' WHERE id = " + client_id);
                query.executeUpdate();
            }
            if (lastname != null) {
                query = manager.createQuery("UPDATE Client set lastname = '" + lastname + "' WHERE id = " + client_id);
                query.executeUpdate();
            }
            if (docnumber != null) {
                query = manager.createQuery("UPDATE Client set docnumber = " + docnumber + " WHERE id = " + client_id);
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
