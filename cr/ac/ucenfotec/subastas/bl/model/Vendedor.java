package cr.ac.ucenfotec.subastas.bl.model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Representa a un usuario vendedor dentro del sistema.
 */
public class Vendedor extends Usuario {

    private String direccion;
    private ArrayList<Subasta> subastasCreadas;

    public Vendedor() {
        super();
        this.direccion = "";
        this.subastasCreadas = new ArrayList<Subasta>();
    }

    public Vendedor(String nombreCompleto, String identificacion, LocalDate fechaNacimiento,
                    String contrasena, String correoElectronico, String direccion) {
        super(nombreCompleto, identificacion, fechaNacimiento, contrasena, correoElectronico);
        this.direccion = direccion;
        this.subastasCreadas = new ArrayList<Subasta>();
    }

    public String getTipoUsuario() {
        return "Vendedor";
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public ArrayList<Subasta> getSubastasCreadas() {
        return subastasCreadas;
    }

    public void setSubastasCreadas(ArrayList<Subasta> subastasCreadas) {
        this.subastasCreadas = subastasCreadas;
    }

    public void agregarSubasta(Subasta subasta) {
        if (subasta != null) {
            subastasCreadas.add(subasta);
        }
    }

    public String toString() {
        return "Vendedor\n" + super.toString() +
                "\nDirección: " + direccion +
                "\nCantidad de subastas creadas: " + subastasCreadas.size();
    }
}