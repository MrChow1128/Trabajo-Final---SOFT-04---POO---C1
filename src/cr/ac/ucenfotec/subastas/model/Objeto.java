package cr.ac.ucenfotec.subastas.model;

import java.time.LocalDate;
import java.time.Period;

public class Objeto {

    // Atributos

    private String nombre;
    private String descripcion;
    private String estado;
    private LocalDate fechaCompra;

    // Constructores

    public Objeto() {
    }

    public Objeto(String nombre, String descripcion, String estado, LocalDate fechaCompra) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
        this.fechaCompra = fechaCompra;
    }

    // Método para calcular antigüedad

    public String calcularAntiguedad() {
        if (fechaCompra == null) {
            return "Fecha no definida";
        }

        Period periodo = Period.between(fechaCompra, LocalDate.now());

        return periodo.getYears() + " años, "
                + periodo.getMonths() + " meses, "
                + periodo.getDays() + " días";
    }

    // Getters y Setters

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

    // equals (sin override, estilo visto en clase)

    public boolean equals(Objeto otroObjeto) {
        if (otroObjeto == null) {
            return false;
        }

        if (this.nombre == null || otroObjeto.getNombre() == null) {
            return false;
        }

        if (this.fechaCompra == null || otroObjeto.getFechaCompra() == null) {
            return false;
        }

        return this.nombre.equals(otroObjeto.getNombre()) &&
                this.fechaCompra.equals(otroObjeto.getFechaCompra());
    }

    // toString

    public String toString() {
        return "Objeto:" +
                "\n\tNombre: " + nombre +
                "\n\tDescripción: " + descripcion +
                "\n\tEstado: " + estado +
                "\n\tFechaCompra: " + fechaCompra +
                "\n\tAntigüedad: " + calcularAntiguedad();
    }
}