// Clase que define la persona por 2 atributos (nombre y apellido) y con los getter y setters de estos
public class Persona {
    private String nombre;
    private String apellido;


    public String getApellido() {
        return apellido;
    }

    public String getNombre() {
        return nombre;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}
