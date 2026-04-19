package cr.ac.ucenfotec.subastas.bl.model;

import java.time.LocalDate;

/**
 * Usuario de tipo moderador.
 */
public class Moderador extends Usuario {

    public Moderador() {
        super();
    }

    public Moderador(String nombreCompleto, String identificacion, LocalDate fechaNacimiento,
                     String contrasena, String correoElectronico) {
        super(nombreCompleto, identificacion, fechaNacimiento, contrasena, correoElectronico);
    }

    public String getTipoUsuario() {
        return "Moderador";
    }

    public String toString() {
        return "Moderador\n" + super.toString();
    }
}