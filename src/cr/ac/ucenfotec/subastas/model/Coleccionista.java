package cr.ac.ucenfotec.subastas.model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Coleccionista extends Usuario {

    // Atributos propios

    private double puntuacion;
    private String direccion;
    private ArrayList<String> listaIntereses;
    private ArrayList<Objeto> objetosPropios;

    // Constructores

    public Coleccionista() {
        super();
        this.listaIntereses = new ArrayList<>();
        this.objetosPropios = new ArrayList<>();
    }

    public Coleccionista(String nombreCompleto, String identificacion,
                         LocalDate fechaNacimiento, String contrasena,
                         String correoElectronico,
                         double puntuacion, String direccion) {

        super(nombreCompleto, identificacion, fechaNacimiento, contrasena, correoElectronico);
        this.puntuacion = puntuacion;
        this.direccion = direccion;
        this.listaIntereses = new ArrayList<>();
        this.objetosPropios = new ArrayList<>();
    }

    // Método polimórfico

    public String getTipoUsuario() {
        return "Coleccionista";
    }

    // Métodos propios

    public void agregarInteres(String interes) {
        if (interes != null && !interes.trim().isEmpty()) {
            listaIntereses.add(interes);
        }
    }

    public void agregarObjeto(Objeto objeto) {
        if (objeto != null) {
            objetosPropios.add(objeto);
        }
    }

    // Getters y Setters

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<String> getListaIntereses() {
        return listaIntereses;
    }

    public void setListaIntereses(ArrayList<String> listaIntereses) {
        this.listaIntereses = listaIntereses;
    }

    public ArrayList<Objeto> getObjetosPropios() {
        return objetosPropios;
    }

    public void setObjetosPropios(ArrayList<Objeto> objetosPropios) {
        this.objetosPropios = objetosPropios;
    }

    // toString

    public String toString() {
        return "Coleccionista {" + super.toString() +
                ", Puntuacion=" + puntuacion +
                ", Direccion='" + direccion + '\'' +
                ", Intereses=" + listaIntereses +
                ", CantidadObjetos=" + objetosPropios.size() +
                "}";
    }
}