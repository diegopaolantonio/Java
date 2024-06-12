import java.util.ArrayList;
import java.util.Collections;

public class Test {

    public static void main(String[] args) {
        // Creo una instancia de la clase Familia
        Familia familia = new Familia();

        // Cargo 5 personas en familia
        familia.agregarPersona("Diego", "Paolantonio");
        familia.agregarPersona("Juan", "Gonzalez");
        familia.agregarPersona("Alberto", "Perez");
        familia.agregarPersona("Nicolas", "Martinez");
        familia.agregarPersona("Martin", "Gomez");

        // Imprimo en consola el ArrayList Familia
        System.out.println("Lista Original:");
        familia.mostrarFamilia();

        // Ordeno la lista por nombre e imprimo en consola
        System.out.println("Lista ordenada por nombre:");
        familia.ordenarNombre();
        familia.mostrarFamilia();

        // Ordeno la lista por apellido e imprimo en consola
        System.out.println("Lisa ordenada por apellido:");
        familia.ordenarApellido();
        familia.mostrarFamilia();

        // Ordeno inverso de la lista por apellido e imprimo en consola
        System.out.println("Lista ordenada por apellido invertida:");
        familia.ordenarInvApellido();
        familia.mostrarFamilia();

    }
}
