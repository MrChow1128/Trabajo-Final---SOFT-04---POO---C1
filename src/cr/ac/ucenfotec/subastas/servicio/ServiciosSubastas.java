package cr.ac.ucenfotec.subastas.servicio;

import cr.ac.ucenfotec.subastas.model.Coleccionista;
import cr.ac.ucenfotec.subastas.model.Objeto;
import cr.ac.ucenfotec.subastas.model.Oferta;
import cr.ac.ucenfotec.subastas.model.Subasta;
import cr.ac.ucenfotec.subastas.model.Usuario;

import java.util.ArrayList;

public class ServiciosSubastas {

    // Atributos

    private ArrayList<Subasta> subastas;

    // Constructor

    public ServiciosSubastas() {
        subastas = new ArrayList<>();
    }

    // Registrar subasta

    public Subasta registrarSubasta(String titulo, Usuario creador, ArrayList<Objeto> objetos) {

        if (titulo == null || titulo.trim().isEmpty()) {
            throw new IllegalArgumentException("La subasta debe tener un título válido.");
        }

        if (creador == null) {
            throw new IllegalArgumentException("La subasta debe tener un creador válido.");
        }

        if (objetos == null || objetos.isEmpty()) {
            throw new IllegalArgumentException("La subasta debe tener al menos un objeto.");
        }

        Subasta subasta = new Subasta(titulo, creador);

        for (Objeto objeto : objetos) {
            if (objeto == null) {
                throw new IllegalArgumentException("No se permiten objetos nulos en la subasta.");
            }
            subasta.agregarObjeto(objeto);
        }

        subastas.add(subasta);
        return subasta;
    }

    // Hacer oferta

    public Oferta hacerOferta(Subasta subasta, Coleccionista coleccionista, double monto) {

        if (subasta == null) {
            throw new IllegalArgumentException("Debe seleccionar una subasta válida.");
        }

        if (coleccionista == null) {
            throw new IllegalArgumentException("El oferente no es válido.");
        }

        if (!subasta.isActiva()) {
            throw new IllegalArgumentException("No se puede ofertar en una subasta cerrada.");
        }

        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser mayor que cero.");
        }

        if (subasta.getCreador().equals(coleccionista)) {
            throw new IllegalArgumentException("No puede ofertar en su propia subasta.");
        }

        Oferta mejorOferta = subasta.obtenerMejorOferta();
        if (mejorOferta != null && monto <= mejorOferta.getMonto()) {
            throw new IllegalArgumentException("La oferta debe ser mayor a la oferta actual.");
        }

        Oferta oferta = new Oferta(coleccionista, monto);
        subasta.agregarOferta(oferta);
        return oferta;
    }

    // Listar subastas

    public ArrayList<Subasta> listarSubastas() {
        return subastas;
    }

    // Listar ofertas de una subasta

    public ArrayList<Oferta> listarOfertas(Subasta subasta) {
        if (subasta == null) {
            return new ArrayList<>();
        }
        return subasta.getOfertas();
    }
}