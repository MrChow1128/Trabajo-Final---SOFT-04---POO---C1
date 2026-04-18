package cr.ac.ucenfotec.subastas.bl.model;

import java.time.LocalDate;

public abstract class Usuario implements PerfilUsuario {

    // Atributos

    private String nombreCompleto;
    private String identificacion;
    private LocalDate fechaNacimiento;
    private String contrasena;
    private String correoElectronico;

    // Constructores

    public Usuario() {
    }

    public Usuario(String nombreCompleto, String identificacion,
                   LocalDate fechaNacimiento, String contrasena,
                   String correoElectronico) {
        this.nombreCompleto = nombreCompleto;
        this.identificacion = identificacion;
        this.fechaNacimiento = fechaNacimiento;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
    }

    // Método abstracto para polimorfismo

    public abstract String getTipoUsuario();

    // Getters y Setters

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

    // equals (estilo visto en clase, sin override)

    public boolean equals(Usuario otroUsuario) {
        if (otroUsuario == null) {
            return false;
        }

        if (this.identificacion == null || otroUsuario.getIdentificacion() == null) {
            return false;
        }

        return this.identificacion.equals(otroUsuario.getIdentificacion());
    }

    // toString

    public String toString() {
        return "NombreCompleto='" + nombreCompleto + '\'' +
                ", Identificacion='" + identificacion + '\'' +
                ", FechaNacimiento=" + fechaNacimiento +
                ", CorreoElectronico='" + correoElectronico + '\'' +
                ", TipoUsuario='" + getTipoUsuario() + '\'';
    }
}