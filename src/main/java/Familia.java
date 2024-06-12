import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Familia{
    private List<Persona> personas;

    public Familia() {
        this.personas = new ArrayList<>();
    }

    // Metodo para imprimir en consola la lista completa de familia
    public void mostrarFamilia() {
        for (Persona persona : this.personas) {
            System.out.println(persona.getApellido() + ", " + persona.getNombre());
        }
        System.out.print("\n");
    }

    // Metodo que agrega una persona en familia
    public void agregarPersona(String nombre, String apellido) {
        Persona persona = new Persona();
        persona.setNombre(nombre);
        persona.setApellido(apellido);
        this.personas.add(persona);
        System.out.println("La persona se agregó correctamente");
    }

    // Metodo que ordena la lista por nombre
    public void ordenarNombre() {
        Collections.sort(this.personas, new Comparator<Persona>(){
            @Override
            public int compare(Persona o1, Persona o2) {
                return o1.getNombre().compareToIgnoreCase(o2.getNombre());
            }
        });
    }

    // Metodo que ordena la lista por apellido
    public void ordenarApellido() {
        Collections.sort(this.personas, new Comparator<Persona>(){
            @Override
            public int compare(Persona o1, Persona o2) {
                return o1.getApellido().compareToIgnoreCase(o2.getApellido());
            }
        });
    }

    // Metodo que ordena la lista por apellido pero invertida
    public void ordenarInvApellido() {
        Collections.sort(this.personas, new Comparator<Persona>(){
            @Override
            public int compare(Persona o1, Persona o2) {
                return o2.getApellido().compareToIgnoreCase(o1.getApellido());
            }
        });
    }

}
