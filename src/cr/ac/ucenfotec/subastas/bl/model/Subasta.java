package cr.ac.ucenfotec.subastas.bl.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Representa una subasta dentro del sistema.
 */
public class Subasta {

    private int id;
    private String titulo;
    private Usuario creador;
    private ArrayList<Objeto> objetos;
    private ArrayList<Oferta> ofertas;
    private LocalDateTime fechaCreacion;
    private boolean activa;

    public Subasta() {
        this.objetos = new ArrayList<Objeto>();
        this.ofertas = new ArrayList<Oferta>();
        this.fechaCreacion = LocalDateTime.now();
        this.activa = true;
    }

    public Subasta(String titulo, Usuario creador) {
        this.titulo = titulo;
        this.creador = creador;
        this.objetos = new ArrayList<Objeto>();
        this.ofertas = new ArrayList<Oferta>();
        this.fechaCreacion = LocalDateTime.now();
        this.activa = true;
    }

    public Subasta(int id, String titulo, Usuario creador, LocalDateTime fechaCreacion, boolean activa) {
        this.id = id;
        this.titulo = titulo;
        this.creador = creador;
        this.objetos = new ArrayList<Objeto>();
        this.ofertas = new ArrayList<Oferta>();
        this.fechaCreacion = fechaCreacion;
        this.activa = activa;
    }

    public void agregarObjeto(Objeto objeto) {
        if (objeto != null) {
            objetos.add(objeto);
        }
    }

    public void agregarOferta(Oferta oferta) {
        if (oferta != null && activa) {
            ofertas.add(oferta);
        }
    }

    public Oferta obtenerMejorOferta() {
        if (ofertas.isEmpty()) {
            return null;
        }

        Oferta mejor = ofertas.get(0);

        for (int i = 1; i < ofertas.size(); i++) {
            if (ofertas.get(i).getMonto() > mejor.getMonto()) {
                mejor = ofertas.get(i);
            }
        }

        return mejor;
    }

    public double obtenerMontoActual() {
        Oferta mejor = obtenerMejorOferta();

        if (mejor == null) {
            return 0;
        }

        return mejor.getMonto();
    }

    public void cerrarSubasta() {
        activa = false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Usuario getCreador() {
        return creador;
    }

    public void setCreador(Usuario creador) {
        this.creador = creador;
    }

    public ArrayList<Objeto> getObjetos() {
        return objetos;
    }

    public void setObjetos(ArrayList<Objeto> objetos) {
        this.objetos = objetos;
    }

    public ArrayList<Oferta> getOfertas() {
        return ofertas;
    }

    public void setOfertas(ArrayList<Oferta> ofertas) {
        this.ofertas = ofertas;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(LocalDateTime fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public String toString() {
        String nombreCreador = "Sin creador";
        if (creador != null) {
            nombreCreador = creador.getNombreCompleto();
        }

        String mejorOfertaTexto = "Sin ofertas";
        Oferta mejorOferta = obtenerMejorOferta();
        if (mejorOferta != null) {
            mejorOfertaTexto = "₡" + mejorOferta.getMonto() +
                    " por " + mejorOferta.getOferente().getNombreCompleto();
        }

        return "Subasta" +
                "\nID: " + id +
                "\nTítulo: " + titulo +
                "\nCreador: " + nombreCreador +
                "\nFecha de creación: " + fechaCreacion +
                "\nEstado: " + (activa ? "Activa" : "Cerrada") +
                "\nCantidad de objetos: " + objetos.size() +
                "\nCantidad de ofertas: " + ofertas.size() +
                "\nMejor oferta: " + mejorOfertaTexto;
    }
}