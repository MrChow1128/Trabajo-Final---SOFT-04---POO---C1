package cr.ac.ucenfotec.subastas.servicio;

import cr.ac.ucenfotec.subastas.model.*;

import java.util.ArrayList;
import java.util.List;

public class ServiciosSubastas {

    private List<Subasta> subastas;
    //private ArrayList<Orden> ordenes;

    public ServiciosSubastas(){
        subastas = new ArrayList<>();
        //ordenes = new ArrayList<>();
    }

    //métodos para subastas
    public Subasta registrarSubasta(String titulo, Usuario creador, List<Objeto> objetos){
        if (objetos == null || objetos.isEmpty()){
            throw new IllegalArgumentException("La subasta debe tener al menos un objeto.");
        }

        Subasta subasta = new Subasta(titulo,creador);

        for (Objeto objeto : objetos){
            subasta.agregarObjeto(objeto);
        }

        subastas.add(subasta);
        return subasta;
    }

    public List<Subasta> listarSubastas() { return List.copyOf(subastas); }

    //objetos
    public List<Objeto> listarObjetos(Subasta subasta){return subasta.getObjetos();}

    //ofertas
    public List<Oferta> listarOfertas(Subasta subasta) {return subasta.getOfertas(); }

    public Oferta hacerOferta(Subasta subasta, Coleccionista coleccionista, double monto){
        Oferta oferta = new Oferta(coleccionista,monto);
        subasta.agregarOferta(oferta);
        return oferta;
    }

}
