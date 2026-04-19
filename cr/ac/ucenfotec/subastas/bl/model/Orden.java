package cr.ac.ucenfotec.subastas.bl.model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * Representa la orden generada cuando una subasta finaliza
 * y existe un comprador ganador.
 */
public class Orden {

    private int id;
    private Coleccionista comprador;
    private Usuario vendedor;
    private ArrayList<Objeto> objetos;
    private double precioTotal;
    private LocalDateTime fecha;

    public Orden() {
        this.objetos = new ArrayList<Objeto>();
        this.fecha = LocalDateTime.now();
    }

    public Orden(Coleccionista comprador, Usuario vendedor, ArrayList<Objeto> objetos, double precioTotal) {
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.objetos = objetos;
        this.precioTotal = precioTotal;
        this.fecha = LocalDateTime.now();
    }

    public Orden(int id, Coleccionista comprador, Usuario vendedor, ArrayList<Objeto> objetos,
                 double precioTotal, LocalDateTime fecha) {
        this.id = id;
        this.comprador = comprador;
        this.vendedor = vendedor;
        this.objetos = objetos;
        this.precioTotal = precioTotal;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Coleccionista getComprador() {
        return comprador;
    }

    public void setComprador(Coleccionista comprador) {
        this.comprador = comprador;
    }

    public Usuario getVendedor() {
        return vendedor;
    }

    public void setVendedor(Usuario vendedor) {
        this.vendedor = vendedor;
    }

    public ArrayList<Objeto> getObjetos() {
        return objetos;
    }

    public void setObjetos(ArrayList<Objeto> objetos) {
        this.objetos = objetos;
    }

    public double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(double precioTotal) {
        this.precioTotal = precioTotal;
    }

    public LocalDateTime getFecha() {
        return fecha;
    }

    public void setFecha(LocalDateTime fecha) {
        this.fecha = fecha;
    }

    public String toString() {
        String nombreComprador = "Sin comprador";
        String nombreVendedor = "Sin vendedor";

        if (comprador != null) {
            nombreComprador = comprador.getNombreCompleto();
        }

        if (vendedor != null) {
            nombreVendedor = vendedor.getNombreCompleto();
        }

        return "Orden" +
                "\nID: " + id +
                "\nComprador: " + nombreComprador +
                "\nVendedor: " + nombreVendedor +
                "\nPrecio total: ₡" + precioTotal +
                "\nFecha: " + fecha +
                "\nCantidad de objetos: " + (objetos != null ? objetos.size() : 0);
    }
}