package cr.ac.ucenfotec.subastas.bl.dao;

import cr.ac.ucenfotec.subastas.bl.model.Coleccionista;
import cr.ac.ucenfotec.subastas.bl.model.Moderador;
import cr.ac.ucenfotec.subastas.bl.model.Usuario;
import cr.ac.ucenfotec.subastas.bl.model.Vendedor;
import cr.ac.ucenfotec.subastas.dl.Connector;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOUsuario {

    private static String statement;
    private static String query;

    public static String insertarUsuario(Usuario usuario) throws SQLException, ClassNotFoundException, IOException {
        statement = "INSERT INTO t_usuarios(nombre_completo, identificacion, fecha_nacimiento, contrasena, correo_electronico, tipo_usuario, direccion, puntuacion) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        String direccion = "";
        double puntuacion = 0.0;

        if (usuario instanceof Vendedor) {
            direccion = ((Vendedor) usuario).getDireccion();
        } else if (usuario instanceof Coleccionista) {
            direccion = ((Coleccionista) usuario).getDireccion();
            puntuacion = ((Coleccionista) usuario).getPuntuacion();
        }

        Connector.getConexion().ejecutarStatement(
                statement,
                usuario.getNombreCompleto(),
                usuario.getIdentificacion(),
                usuario.getFechaNacimiento().toString(),
                usuario.getContrasena(),
                usuario.getCorreoElectronico(),
                usuario.getTipoUsuario(),
                direccion,
                puntuacion
        );

        return "Usuario registrado correctamente.";
    }

    public static Usuario seleccionarUsuarioPorCredenciales(String identificacion, String contrasena) throws SQLException, ClassNotFoundException, IOException {
        query = "SELECT * FROM t_usuarios WHERE identificacion = ? AND contrasena = ?";
        ResultSet resultado = Connector.getConexion().ejecutarQuery(query, identificacion, contrasena);

        if (!resultado.next()) {
            return null;
        }

        return construirUsuario(resultado);
    }

    public static Usuario seleccionarUsuarioPorIdentificacion(String identificacion) throws SQLException, ClassNotFoundException, IOException {
        query = "SELECT * FROM t_usuarios WHERE identificacion = ?";
        ResultSet resultado = Connector.getConexion().ejecutarQuery(query, identificacion);

        if (!resultado.next()) {
            return null;
        }

        return construirUsuario(resultado);
    }

    public static boolean existeIdentificacion(String identificacion) throws SQLException, ClassNotFoundException, IOException {
        query = "SELECT identificacion FROM t_usuarios WHERE identificacion = ?";
        ResultSet resultado = Connector.getConexion().ejecutarQuery(query, identificacion);
        return resultado.next();
    }

    public static boolean existeCorreo(String correo) throws SQLException, ClassNotFoundException, IOException {
        query = "SELECT correo_electronico FROM t_usuarios WHERE correo_electronico = ?";
        ResultSet resultado = Connector.getConexion().ejecutarQuery(query, correo);
        return resultado.next();
    }

    public static ArrayList<Usuario> listarUsuarios() throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Usuario> usuarios = new ArrayList<Usuario>();

        query = "SELECT * FROM t_usuarios ORDER BY nombre_completo";
        ResultSet resultado = Connector.getConexion().ejecutarQueryGeneral(query);

        while (resultado.next()) {
            usuarios.add(construirUsuario(resultado));
        }

        return usuarios;
    }

    private static Usuario construirUsuario(ResultSet resultado) throws SQLException {
        String tipo = resultado.getString("tipo_usuario");
        String nombre = resultado.getString("nombre_completo");
        String identificacion = resultado.getString("identificacion");
        LocalDate fechaNacimiento = LocalDate.parse(resultado.getString("fecha_nacimiento"));
        String contrasena = resultado.getString("contrasena");
        String correo = resultado.getString("correo_electronico");
        String direccion = resultado.getString("direccion");
        double puntuacion = resultado.getDouble("puntuacion");

        if (tipo.equalsIgnoreCase("Vendedor")) {
            return new Vendedor(nombre, identificacion, fechaNacimiento, contrasena, correo, direccion);
        }

        if (tipo.equalsIgnoreCase("Coleccionista")) {
            return new Coleccionista(nombre, identificacion, fechaNacimiento, contrasena, correo, puntuacion, direccion);
        }

        return new Moderador(nombre, identificacion, fechaNacimiento, contrasena, correo);
    }
}