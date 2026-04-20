package cr.ac.ucenfotec.subastas.bl.logic;

import cr.ac.ucenfotec.subastas.bl.dao.DAOUsuario;
import cr.ac.ucenfotec.subastas.bl.model.*;
import cr.ac.ucenfotec.subastas.bl.dao.DAOInteres;
import cr.ac.ucenfotec.subastas.bl.dao.DAOObjetoUsuario;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;

/**
 * Gestiona la lógica relacionada con los usuarios del sistema.
 */
public class GestorUsuario {

    public GestorUsuario() {
    }

    public void registrarModeradorDefault() {
        try {
            if (!DAOUsuario.existeIdentificacion("MOD001")) {
                Moderador moderador = new Moderador(
                        "Moderador General",
                        "MOD001",
                        LocalDate.of(1990, 1, 1),
                        "admin123",
                        "moderador@subastas.com"
                );
                DAOUsuario.insertarUsuario(moderador);
            }
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error al registrar el moderador por defecto.");
        }
    }

    public Usuario registrarUsuario(String nombre, String correo, String contrasena, String identificacion,
                                    String direccion, LocalDate fechaNacimiento, String tipoUsuario) {

        validarDatosRegistro(nombre, correo, contrasena, identificacion, direccion, fechaNacimiento, tipoUsuario);

        Usuario usuario;

        if (tipoUsuario.equalsIgnoreCase("Vendedor")) {
            usuario = new Vendedor(nombre, identificacion, fechaNacimiento, contrasena, correo, direccion);
        } else {
            usuario = new Coleccionista(nombre, identificacion, fechaNacimiento, contrasena, correo, 0.0, direccion);
        }

        try {
            DAOUsuario.insertarUsuario(usuario);
            return usuario;
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error al registrar el usuario en la base de datos.");
        }
    }

    public Usuario iniciarSesion(String identificacion, String contrasena) {
        if (identificacion == null || identificacion.trim().isEmpty()) {
            throw new IllegalArgumentException("La identificación es obligatoria.");
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        try {
            return DAOUsuario.seleccionarUsuarioPorCredenciales(identificacion, contrasena);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error al validar las credenciales del usuario.");
        }
    }

    public boolean esMayorDeEdad(LocalDate fechaNacimiento) {
        Period edad = Period.between(fechaNacimiento, LocalDate.now());
        return edad.getYears() >= 18;
    }

    public boolean existeIdentificacion(String identificacion) {
        try {
            return DAOUsuario.existeIdentificacion(identificacion);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error al verificar la identificación.");
        }
    }

    public boolean existeCorreo(String correo) {
        try {
            return DAOUsuario.existeCorreo(correo);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error al verificar el correo electrónico.");
        }
    }

    public ArrayList<Usuario> listarUsuarios() {
        try {
            return DAOUsuario.listarUsuarios();
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error al listar los usuarios.");
        }
    }

    public Usuario buscarUsuarioPorIdentificacion(String identificacion) {
        try {
            return DAOUsuario.seleccionarUsuarioPorIdentificacion(identificacion);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException("Error al buscar el usuario.");
        }
    }

    private void validarDatosRegistro(String nombre, String correo, String contrasena, String identificacion,
                                      String direccion, LocalDate fechaNacimiento, String tipoUsuario) {

        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre completo es obligatorio.");
        }

        if (correo == null || correo.trim().isEmpty()) {
            throw new IllegalArgumentException("El correo electrónico es obligatorio.");
        }

        if (!correo.contains("@") || !correo.contains(".")) {
            throw new IllegalArgumentException("El correo electrónico no tiene un formato válido.");
        }

        if (contrasena == null || contrasena.trim().isEmpty()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }

        if (contrasena.trim().length() < 4) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 4 caracteres.");
        }

        if (identificacion == null || identificacion.trim().isEmpty()) {
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

        if (tipoUsuario == null || tipoUsuario.trim().isEmpty()) {
            throw new IllegalArgumentException("El tipo de usuario es obligatorio.");
        }

        if (!tipoUsuario.equalsIgnoreCase("Vendedor") &&
                !tipoUsuario.equalsIgnoreCase("Coleccionista")) {
            throw new IllegalArgumentException("Tipo de usuario no válido.");
        }

        if (existeIdentificacion(identificacion)) {
            throw new IllegalArgumentException("Ya existe un usuario con esa identificación.");
        }

        if (existeCorreo(correo)) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo electrónico.");
        }
    }

    public void agregarInteres(Coleccionista col, String interes) {
    if (interes == null || interes.trim().isEmpty()) {
        throw new IllegalArgumentException("El interés no puede estar vacío.");
    }

    try {
        DAOInteres.insertarInteres(col.getIdentificacion(), interes);
        col.agregarInteres(interes);
    } catch (Exception e) {
        throw new RuntimeException("Error al guardar el interés.");
    }
}
public ArrayList<String> obtenerIntereses(Coleccionista col) {
    try {
        return DAOInteres.listarIntereses(col.getIdentificacion());
    } catch (Exception e) {
        throw new RuntimeException("Error al cargar intereses.");
    }
}
public void agregarObjeto(Coleccionista col, Objeto obj) {
    try {
        DAOObjetoUsuario.insertarObjeto(col.getIdentificacion(), obj);
        col.agregarObjeto(obj);
    } catch (Exception e) {
        throw new RuntimeException("Error al guardar objeto.");
    }
}
public ArrayList<Objeto> obtenerObjetos(Coleccionista col) {
    try {
        return DAOObjetoUsuario.listarObjetos(col.getIdentificacion());
    } catch (Exception e) {
        throw new RuntimeException("Error al cargar objetos.");
    }
}
}