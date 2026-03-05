import java.time.LocalDate;

public class Moderador extends Usuario {


    // Constructores

    public Moderador() {
        super();
    }

    public Moderador(String nombreCompleto, String identificacion,
                     LocalDate fechaNacimiento, String contrasena,
                     String correoElectronico) {

        super(nombreCompleto, identificacion, fechaNacimiento,
                contrasena, correoElectronico);
    }

    // toString

    public String toString() {
        return "Moderador {" + super.toString() + "}";
    }
}