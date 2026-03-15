package cr.ac.ucenfotec.subastas.model;

import java.util.ArrayList;
import java.util.List;

public class Subasta {

    private String titulo;
    private Usuario creador;
    private ArrayList<Objeto> objetos;
    private ArrayList<Oferta> ofertas;
    private boolean activa;

    public Subasta(String titulo, Usuario creador) {
        this.titulo = titulo;
        this.creador = creador;
        this.objetos = new ArrayList<>();
        this.ofertas = new ArrayList<>();
        this.activa = true;
    }

    public void agregarObjeto(Objeto objeto) {
        objetos.add(objeto);
    }

    public void agregarOferta(Oferta oferta) {
        ofertas.add(oferta);
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
        activa = false;
    }

    public String getTitulo() {
        return titulo;
    }

    public Usuario getCreador() {
        return creador;
    }

    public List<Objeto> getObjetos() {
        return List.copyOf(objetos);
    }

    public List<Oferta> getOfertas() {
        return List.copyOf(ofertas);
    }

    public boolean isActiva() {
        return activa;
    }

    public String toString() {

        StringBuilder sb = new StringBuilder();
        sb.append("Subasta:\n");
        sb.append("\tTítulo: ").append(titulo).append("\n");
        sb.append("\tCreador: ").append(creador.getNombreCompleto()).append("\n");
        sb.append("\tObjetos:\n");

        for (int i = 0; i<objetos.size();i++){
            sb.append("\t\t").append(i+1).append(". ")
                    .append(objetos.get(i).toString().replace("\n","\n\t\t"))
                    .append("\n");
        }
        sb.append("\tOfertas: ").append(ofertas.size()).append("\n");
        sb.append("\tActiva: ").append(activa);

        return sb.toString();
    }
}