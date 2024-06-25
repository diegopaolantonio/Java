import managers.CartsManager;
import managers.ClientsManager;
import managers.InvoicesManager;
import managers.ProductsManager;

public class Main {
    public static void main(String[] args) {
        ClientsManager client = new ClientsManager();
        ProductsManager product = new ProductsManager();
        CartsManager cart = new CartsManager();
        InvoicesManager invoice = new InvoicesManager();

        try {
            System.out.println("Client");
            //client.create("Diego", "Paolantonio", 313131);
            //client.create("Yamile", "Natel", 353535);
            //client.create("Joaquin", "Paolantonio", 585858);
            //client.update(6, null, null, 55555);
            //client.delete(5);
            System.out.println(client.readAll());
            System.out.println(client.readOne(2));

            System.out.println("\nProduct");
            //product.create(45454, "Sillon", 50, 200000);
            //product.create(85214, "Mesa", 120, 11500);
            //product.create(85214, "Silla", 85, 1500);
            //product.create(85214, "Mantel", 100, 500);
            //product.create(55448, "Taza", 250, 700);
            //product.update(4,  null, null, null, "6500.75");
            //product.delete(4);
            System.out.println(product.readAll());
            System.out.println(product.readOne(4));

            System.out.println("\nCart");
            //cart.create(2, client.readOne(1), product.readOne(6));
            //cart.create(2, client.readOne(4), product.readOne(3));
            // cart.delete(2);
            cart.update(1, 2);
            System.out.println(cart.readAll());
            System.out.println(cart.readOne(1));
            System.out.println(cart.readOneByClient(client.readOne(4)));

            System.out.println("\nInvoice");
            //invoice.create(client.readOne(1));
            //invoice.create(client.readOne(4));
            //invoice.delete(2);
            invoice.update(1);
            System.out.println(invoice.readAll());
            System.out.println(invoice.readOne(1));

        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
