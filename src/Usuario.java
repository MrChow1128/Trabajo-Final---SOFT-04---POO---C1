import java.time.LocalDate;
import java.time.Period;

    public abstract class Usuario {

        // Atributos

        private String nombreCompleto;
        private String identificacion;
        private LocalDate fechaNacimiento;
        private String contrasena;
        private String correoElectronico;

        // Constructores
        //Constructor por defecto
        public Usuario() {
        }

        //Constructor completo
        public Usuario(String nombreCompleto, String identificacion,
                       LocalDate fechaNacimiento, String contrasena,
                       String correoElectronico) {
            this.nombreCompleto = nombreCompleto;
            this.identificacion = identificacion;
            this.fechaNacimiento = fechaNacimiento;
            this.contrasena = contrasena;
            this.correoElectronico = correoElectronico;
        }

        // Métodos de negocio

        //Calcula la edad del usuario a partir de la fecha de nacimiento.

        public int calcularEdad() {
            if (fechaNacimiento == null) {
                return 0;
            }
            return Period.between(fechaNacimiento, LocalDate.now()).getYears();
        }

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

        // equals

        public boolean equals(Usuario otroUsuario) {
            if (this.identificacion.equals(otroUsuario.getIdentificacion())) {
                return true;
            } else {
                return false;
            }
        }

        // toString

        public String toString() {
            return "Nombre='" + nombreCompleto + '\'' +
                    ", Identificación='" + identificacion + '\'' +
                    ", FechaNacimiento=" + fechaNacimiento +
                    ", Edad=" + calcularEdad() +
                    ", Correo='" + correoElectronico + '\'';
        }
    }
