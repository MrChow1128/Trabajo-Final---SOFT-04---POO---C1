package cr.ac.ucenfotec.subastas.bl.model;

import java.time.LocalDate;

/**
 * Clase base abstracta para todos los usuarios del sistema.
 */
public abstract class Usuario implements PerfilUsuario {

    private String nombreCompleto;
    private String identificacion;
    private LocalDate fechaNacimiento;
    private String contrasena;
    private String correoElectronico;

    public Usuario() {
    }

    public Usuario(String nombreCompleto, String identificacion, LocalDate fechaNacimiento,
                   String contrasena, String correoElectronico) {
        this.nombreCompleto = nombreCompleto;
        this.identificacion = identificacion;
        this.fechaNacimiento = fechaNacimiento;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public boolean equals(Usuario otroUsuario) {
        if (otroUsuario == null) {
            return false;
        }

        if (identificacion == null || otroUsuario.getIdentificacion() == null) {
            return false;
        }

        return identificacion.equals(otroUsuario.getIdentificacion());
    }

    public String toString() {
        return "Nombre completo: " + nombreCompleto +
                "\nIdentificación: " + identificacion +
                "\nFecha de nacimiento: " + fechaNacimiento +
                "\nCorreo electrónico: " + correoElectronico +
                "\nTipo de usuario: " + getTipoUsuario();
    }
}