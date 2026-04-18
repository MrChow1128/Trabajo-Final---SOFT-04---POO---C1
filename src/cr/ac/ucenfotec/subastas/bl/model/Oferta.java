package cr.ac.ucenfotec.subastas.bl.model;

import java.time.LocalDateTime;

public class Oferta {

    // Atributos

    private Coleccionista oferente;
    private double monto;
    private LocalDateTime fecha;

    // Constructor

    public Oferta(Coleccionista oferente, double monto) {
        this.oferente = oferente;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }

    // Getters y Setters

    public Coleccionista getOferente() {
        return oferente;
    }

    public void setOferente(Coleccionista oferente) {
        this.oferente = oferente;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    // equals

    public boolean equals(Oferta otra) {
        if (otra == null) {
            return false;
        }

        if (this.oferente == null || otra.getOferente() == null) {
            return false;
        }

        return this.oferente.equals(otra.getOferente()) &&
                this.monto == otra.getMonto();
    }

    // toString

    public String toString() {
        return "Oferta" +
                "\n\tOferente: " + oferente.getNombreCompleto() +
                "\n\tMonto ofertado: " + monto +
                "\n\tFecha: " + fecha;
    }
}