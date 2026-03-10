import java.util.ArrayList;
import java.util.Date;

public class Orden {

    private Coleccionista comprador;
    private ArrayList<Objeto> objetos;
    private double precioTotal;
    private Date fecha;

    public Orden(Coleccionista comprador, ArrayList<Objeto> objetos, double precioTotal) {
        this.comprador = comprador;
        this.objetos = objetos;
        this.precioTotal = precioTotal;
        this.fecha = new Date();
    }

    public Coleccionista getComprador() {
        return comprador;
    }

    public ArrayList<Objeto> getObjetos() {
        return objetos;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public Date getFecha() {
        return fecha;
    }

    public String toString() {
        return "Orden{" +
                "comprador=" + comprador.getNombreCompleto() +
                ", precioTotal=" + precioTotal +
                ", fecha=" + fecha +
                ", objetos=" + objetos +
                '}';
    }
}