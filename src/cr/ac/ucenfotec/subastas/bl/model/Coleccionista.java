package cr.ac.ucenfotec.subastas.bl.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Usuario coleccionista.
 */
public class Coleccionista extends Usuario {

    private double puntuacion;
    private String direccion;
    private ArrayList<String> listaIntereses;
    private ArrayList<Objeto> objetosPropios;
    private ArrayList<Oferta> ofertasRealizadas;

    public Coleccionista() {
        super();
        this.listaIntereses = new ArrayList<String>();
        this.objetosPropios = new ArrayList<Objeto>();
        this.ofertasRealizadas = new ArrayList<Oferta>();
    }

    public Coleccionista(String nombreCompleto, String identificacion,
                         LocalDate fechaNacimiento, String contrasena,
                         String correoElectronico, double puntuacion, String direccion) {
        super(nombreCompleto, identificacion, fechaNacimiento, contrasena, correoElectronico);
        this.puntuacion = puntuacion;
        this.direccion = direccion;
        this.listaIntereses = new ArrayList<String>();
        this.objetosPropios = new ArrayList<Objeto>();
        this.ofertasRealizadas = new ArrayList<Oferta>();
    }

    public String getTipoUsuario() {
        return "Coleccionista";
    }

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

    public ArrayList<Oferta> getOfertasRealizadas() {
        return ofertasRealizadas;
    }

    public void setOfertasRealizadas(ArrayList<Oferta> ofertasRealizadas) {
        this.ofertasRealizadas = ofertasRealizadas;
    }

    public void agregarInteres(String interes) {
        if (interes != null && !interes.trim().isEmpty()) {
            listaIntereses.add(interes.trim());
        }
    }

    public void agregarObjeto(Objeto objeto) {
        if (objeto != null) {
            objetosPropios.add(objeto);
        }
    }

    public void agregarOferta(Oferta oferta) {
        if (oferta != null) {
            ofertasRealizadas.add(oferta);
        }
    }

    public String toString() {
        return "Coleccionista\n" + super.toString() +
                "\nPuntuación: " + puntuacion +
                "\nDirección: " + direccion +
                "\nCantidad de intereses: " + listaIntereses.size() +
                "\nCantidad de objetos propios: " + objetosPropios.size() +
                "\nCantidad de ofertas realizadas: " + ofertasRealizadas.size();
    }
}