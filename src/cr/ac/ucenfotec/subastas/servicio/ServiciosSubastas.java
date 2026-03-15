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
    public Subasta crearSubasta(String titulo, Usuario creador){
        Subasta subasta = new Subasta(titulo, creador);
        return subasta;
    }

    public void agregarObjetosSubasta(Subasta subasta, List<Objeto> objetos){
        for (Objeto o : objetos){
            subasta.agregarObjeto(o);
        }
        subastas.add(subasta);
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
