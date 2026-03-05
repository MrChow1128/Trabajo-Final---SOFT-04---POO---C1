import java.time.LocalDate;

public class Vendedor extends Usuario {

    // Atributos propios

    private double puntuacion;
    private String direccion;

    // Constructores

    public Vendedor() {
        super();
    }

    public Vendedor(String nombreCompleto, String identificacion,
                    LocalDate fechaNacimiento, String contrasena,
                    String correoElectronico,
                    double puntuacion, String direccion) {

        super(nombreCompleto, identificacion, fechaNacimiento,
                contrasena, correoElectronico);

        this.puntuacion = puntuacion;
        this.direccion = direccion;
    }

    // Getters y Setters

    public double getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(double puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    // toString

    public String toString() {
        return "Vendedor {" + super.toString() +
                ", Puntuación=" + puntuacion +
                ", Dirección='" + direccion + '\'' +
                "}";
    }
}