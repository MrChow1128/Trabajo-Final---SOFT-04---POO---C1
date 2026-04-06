package cr.ac.ucenfotec.subastas.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Vendedor extends Usuario {

    // Atributos propios

    private String direccion;
    private ArrayList<Objeto> objetosPropios;

    // Constructores

    public Vendedor() {
        super();
        this.objetosPropios = new ArrayList<>();
    }

    public Vendedor(String nombreCompleto, String identificacion,
                     LocalDate fechaNacimiento, String contrasena,
                     String correoElectronico, String direccion) {

        super(nombreCompleto, identificacion, fechaNacimiento, contrasena, correoElectronico);
        this.direccion = direccion;
        this.objetosPropios = new ArrayList<>();
    }

    // Método polimórfico

    public String getTipoUsuario() {
        return "Vendedor";
    }

    // Métodos propios

    public void agregarObjeto(Objeto objeto) {
        if (objeto != null) {
            objetosPropios.add(objeto);
        }
    }

    // Getters y Setters

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Objeto> getObjetosPropios() {
        return objetosPropios;
    }

    public void setObjetosPropios(ArrayList<Objeto> objetosPropios) {
        this.objetosPropios = objetosPropios;
    }

    // toString

    public String toString() {
        return "Vendedor {" + super.toString() +
                ", Direccion='" + direccion + '\'' +
                ", CantidadObjetos=" + objetosPropios.size() +
                "}";
    }
}