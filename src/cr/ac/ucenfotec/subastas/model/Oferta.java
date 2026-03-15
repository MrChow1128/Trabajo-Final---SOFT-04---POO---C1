package cr.ac.ucenfotec.subastas.model;

import java.util.Date;

public class Oferta {

    private Coleccionista oferente;
    private double monto;
    private Date fecha;

    public Oferta(Coleccionista oferente, double monto) {
        this.oferente = oferente;
        this.monto = monto;
        this.fecha = new Date();
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

    public Date getFecha() {
        return fecha;
    }

    public boolean equals(Oferta otra) {
        return this.oferente.equals(otra.oferente) && this.monto == otra.monto;
    }

    public String toString() {
        return "Oferta" +
                "\n\tOferente: " + oferente.getNombreCompleto() +
                "\n\tMonto ofertado: " + monto +
                "\n\tfecha:" + fecha;
    }
}