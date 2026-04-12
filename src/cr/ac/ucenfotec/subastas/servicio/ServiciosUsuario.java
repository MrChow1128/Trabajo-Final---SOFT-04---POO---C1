package cr.ac.ucenfotec.subastas.servicio;

import cr.ac.ucenfotec.subastas.model.Coleccionista;
import cr.ac.ucenfotec.subastas.model.Moderador;
import cr.ac.ucenfotec.subastas.model.Usuario;
import cr.ac.ucenfotec.subastas.model.Vendedor;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

public class ServiciosUsuario {

    // Atributos

    private ArrayList<Usuario> usuarios;

    // Constructor

    public ServiciosUsuario() {
        usuarios = new ArrayList<>();
    }

    // Registrar moderador por defecto

    public void registrarModeradorDefault() {
        if (!existeIdentificacion("MOD001")) {
            Moderador moderador = new Moderador(
                    "Moderador General",
                    "MOD001",
                    LocalDate.of(1990, 1, 1),
                    "admin123",
                    "moderador@subastas.com"
            );
            usuarios.add(moderador);
        }
    }

    // Registrar usuario

    public Usuario registrarUsuario(String nombre, String email, String pw, String id,
                                    String direccion, LocalDate fechaNacimiento, String tipoUsuario) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre es obligatorio.");
        }

        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo es obligatorio.");
        }

        if (pw == null || pw.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        if (id == null || id.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria.");
        }

        if (direccion == null || direccion.trim().isEmpty()) {
            throw new IllegalArgumentException("La dirección es obligatoria.");
        }

        if (fechaNacimiento == null) {
            throw new IllegalArgumentException("La fecha de nacimiento es obligatoria.");
        }

        if (!esMayorDeEdad(fechaNacimiento)) {
            throw new IllegalArgumentException("El usuario debe ser mayor de edad.");
        }

        if (existeIdentificacion(id)) {
            throw new IllegalArgumentException("Ya existe un usuario con esa identificación.");
        }

        Usuario usuario;

        if (tipoUsuario.equalsIgnoreCase("Vendedor")) {
            usuario = new Vendedor(nombre, id, fechaNacimiento, pw, email, direccion);
        } else if (tipoUsuario.equalsIgnoreCase("Coleccionista")) {
            usuario = new Coleccionista(nombre, id, fechaNacimiento, pw, email, 0.0, direccion);
        } else {
            throw new IllegalArgumentException("Tipo de usuario no válido.");
        }

        usuarios.add(usuario);
        return usuario;
    }

    // Login

<<<<<<< HEAD
    public Usuario iniciarSesion(String correo, String contrasena) {
        for (Usuario usuario : usuarios) {
            if (usuario.getCorreoElectronico().equals(correo) &&
                    usuario.getContrasena().equals(contrasena)) {
                return usuario;
=======
    public Usuario buscarUsuario(String id){
        for (Usuario u : users){
            if (u.getIdentificacion().equals(id)){
                return u;
>>>>>>> ddd2f518b5784fe4eec853b63805707c3e118d1a
            }
        }
        return null;
    }

<<<<<<< HEAD
    // Validar mayoría de edad

    public boolean esMayorDeEdad(LocalDate fechaNacimiento) {
        Period edad = Period.between(fechaNacimiento, LocalDate.now());
        return edad.getYears() >= 18;
    }

    // Validar identificación existente

    public boolean existeIdentificacion(String identificacion) {
        for (Usuario usuario : usuarios) {
            if (usuario.getIdentificacion().equals(identificacion)) {
=======
    public boolean existeIdentificacion(String id){
        for (Usuario u : users){
            if(u.getIdentificacion().equals(id)){
>>>>>>> ddd2f518b5784fe4eec853b63805707c3e118d1a
                return true;
            }
        }
        return false;
    }

    // Listar usuarios

<<<<<<< HEAD
    public ArrayList<Usuario> listarUsuarios() {
        return usuarios;
    }
}
=======
    //métodos para atributos de usuarios
    public void agregarInteres(Coleccionista coleccionista, String interes) {
        coleccionista.agregarInteres(interes);
    }

    public List<Objeto> listarColeccionObjetos(Coleccionista coleccionista) {
        return List.copyOf(coleccionista.getObjetosPropios());
    }

    public void agregarObjetoAColeccion(Coleccionista coleccionista, Objeto objeto) {
        coleccionista.agregarObjeto(objeto);
    }

    public List<String> listarIntereses(Coleccionista col) {
        return List.copyOf(col.getListaIntereses());
    }
}
>>>>>>> ddd2f518b5784fe4eec853b63805707c3e118d1a
