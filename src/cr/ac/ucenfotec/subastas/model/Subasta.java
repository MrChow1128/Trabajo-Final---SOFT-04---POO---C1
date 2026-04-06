package cr.ac.ucenfotec.subastas.model;

import java.util.ArrayList;

public class Subasta {

    // Atributos

    private String titulo;
    private Usuario creador;
    private ArrayList<Objeto> objetos;
    private ArrayList<Oferta> ofertas;
    private boolean activa;

    // Constructores

    public Subasta() {
        this.objetos = new ArrayList<>();
        this.ofertas = new ArrayList<>();
        this.activa = true;
    }

    public Subasta(String titulo, Usuario creador) {
        this.titulo = titulo;
        this.creador = creador;
        this.objetos = new ArrayList<>();
        this.ofertas = new ArrayList<>();
        this.activa = true;
    }

    // Métodos

    public void agregarObjeto(Objeto objeto) {
        if (objeto != null) {
            objetos.add(objeto);
        }
    }

    public void agregarOferta(Oferta oferta) {
        if (oferta != null) {
            ofertas.add(oferta);
        }
    }

    public Oferta obtenerMejorOferta() {
        if (ofertas.isEmpty()) {
            return null;
        }

        Oferta mejor = ofertas.get(0);

        for (Oferta oferta : ofertas) {
            if (oferta.getMonto() > mejor.getMonto()) {
                mejor = oferta;
            }
        }

        return mejor;
    }

    public void cerrarSubasta() {
        this.activa = false;
    }

    // Getters y Setters

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

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    // toString

    public String toString() {
        String texto = "Subasta" +
                "\nTítulo: " + titulo +
                "\nCreador: " + creador.getNombreCompleto() +
                "\nTipo de creador: " + creador.getTipoUsuario() +
                "\nActiva: " + (activa ? "Sí" : "No") +
                "\nObjetos:";

        if (objetos.isEmpty()) {
            texto += "\n\tNo hay objetos registrados.";
        } else {
            for (Objeto objeto : objetos) {
                texto += "\n\t- " + objeto.getNombre();
            }
        }

        Oferta mejorOferta = obtenerMejorOferta();
        if (mejorOferta != null) {
            texto += "\nMejor oferta: " + mejorOferta.getMonto() +
                    " por " + mejorOferta.getOferente().getNombreCompleto();
        } else {
            texto += "\nMejor oferta: No hay ofertas todavía.";
        }

        return texto;
    }
}