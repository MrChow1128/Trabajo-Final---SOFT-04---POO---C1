package cr.ac.ucenfotec.subastas.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Orden {

    // Atributos

    private Coleccionista comprador;
    private ArrayList<Objeto> objetos;
    private double precioTotal;
    private LocalDateTime fecha;

    // Constructor

    public Orden(Coleccionista comprador, ArrayList<Objeto> objetos, double precioTotal) {
        this.comprador = comprador;
        this.objetos = objetos;
        this.precioTotal = precioTotal;
        this.fecha = LocalDateTime.now();
    }

    // Getters

    public Coleccionista getComprador() {
        return comprador;
    }

    public ArrayList<Objeto> getObjetos() {
        return objetos;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    // toString

    public String toString() {
        return "Orden{" +
                "comprador=" + comprador.getNombreCompleto() +
                ", precioTotal=" + precioTotal +
                ", fecha=" + fecha +
                ", objetos=" + objetos +
                '}';
    }
}