package cr.ac.ucenfotec.subastas.bl.model;

import java.time.LocalDateTime;

/**
 * Representa una oferta realizada por un coleccionista.
 */
public class Oferta {

    private Coleccionista oferente;
    private double monto;
    private LocalDateTime fecha;

    public Oferta() {
        this.fecha = LocalDateTime.now();
    }

    public Oferta(Coleccionista oferente, double monto) {
        this.oferente = oferente;
        this.monto = monto;
        this.fecha = LocalDateTime.now();
    }

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

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public boolean equals(Oferta otraOferta) {
        if (otraOferta == null) {
            return false;
        }

        if (oferente == null || otraOferta.getOferente() == null) {
            return false;
        }

        return oferente.equals(otraOferta.getOferente()) &&
                monto == otraOferta.getMonto();
    }

    public String toString() {
        return "Oferta" +
                "\n\tOferente: " + (oferente != null ? oferente.getNombreCompleto() : "Sin oferente") +
                "\n\tMonto ofertado: " + monto +
                "\n\tFecha: " + fecha;
    }
}