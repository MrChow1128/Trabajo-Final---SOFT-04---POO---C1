package cr.ac.ucenfotec.subastas.bl.dao;

import cr.ac.ucenfotec.subastas.dl.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DAOInteres {

    private static String statement;
    private static String query;

    public static void insertarInteres(String usuarioId, String interes)
            throws SQLException, ClassNotFoundException {

        statement = "INSERT INTO t_intereses(usuario_id, interes) VALUES (?, ?)";

        Connector.getConexion().ejecutarStatement(statement, usuarioId, interes);
    }

    public static ArrayList<String> listarIntereses(String usuarioId)
            throws SQLException, ClassNotFoundException {

        ArrayList<String> lista = new ArrayList<>();

        query = "SELECT interes FROM t_intereses WHERE usuario_id = ?";
        ResultSet rs = Connector.getConexion().ejecutarQuery(query, usuarioId);

        while (rs.next()) {
            lista.add(rs.getString("interes"));
        }

        return lista;
    }
}