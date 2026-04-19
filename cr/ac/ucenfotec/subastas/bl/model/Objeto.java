package cr.ac.ucenfotec.subastas.bl.model;

import java.time.LocalDate;
import java.time.Period;

/**
 * Representa un objeto que puede pertenecer a una colección
 * o ser parte de una subasta.
 */
public class Objeto {

    private String nombre;
    private String descripcion;
    private String estado;
    private LocalDate fechaCompra;

    public Objeto() {
    }

    public Objeto(String nombre, String descripcion, String estado, LocalDate fechaCompra) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCompra = fechaCompra;
    }

    public String calcularAntiguedad() {
        if (fechaCompra == null) {
            return "Fecha no definida";
        }

        Period periodo = Period.between(fechaCompra, LocalDate.now());

        return periodo.getYears() + " años, " +
                periodo.getMonths() + " meses, " +
                periodo.getDays() + " días";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(LocalDate fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public boolean equals(Objeto otroObjeto) {
        if (otroObjeto == null) {
            return false;
        }

        if (nombre == null || otroObjeto.getNombre() == null) {
            return false;
        }

        if (fechaCompra == null || otroObjeto.getFechaCompra() == null) {
            return false;
        }

        return nombre.equals(otroObjeto.getNombre()) &&
                fechaCompra.equals(otroObjeto.getFechaCompra());
    }

    public String toString() {
        return "Objeto" +
                "\n\tNombre: " + nombre +
                "\n\tDescripción: " + descripcion +
                "\n\tEstado: " + estado +
                "\n\tFecha de compra: " + fechaCompra +
                "\n\tAntigüedad: " + calcularAntiguedad();
    }
}