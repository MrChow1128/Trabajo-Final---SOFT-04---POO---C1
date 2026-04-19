package cr.ac.ucenfotec.subastas.bl.dao;

import cr.ac.ucenfotec.subastas.bl.model.Objeto;
import cr.ac.ucenfotec.subastas.dl.Connector;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOObjetoUsuario {

    private static String statement;
    private static String query;

    public static void insertarObjeto(String usuarioId, Objeto obj) throws SQLException, ClassNotFoundException, IOException {
        statement = "INSERT INTO t_objetos_usuario(usuario_id, nombre, descripcion, estado, fecha_compra) VALUES (?, ?, ?, ?, ?)";

        Connector.getConexion().ejecutarStatement(
                statement,
                usuarioId,
                obj.getNombre(),
                obj.getDescripcion(),
                obj.getEstado(),
                obj.getFechaCompra().toString()
        );
    }

    public static ArrayList<Objeto> listarObjetos(String usuarioId) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Objeto> lista = new ArrayList<Objeto>();

        query = "SELECT * FROM t_objetos_usuario WHERE usuario_id = ?";
        ResultSet rs = Connector.getConexion().ejecutarQuery(query, usuarioId);

        while (rs.next()) {
            Objeto obj = new Objeto(
                    rs.getString("nombre"),
                    rs.getString("descripcion"),
                    rs.getString("estado"),
                    LocalDate.parse(rs.getString("fecha_compra"))
            );
            lista.add(obj);
        }

        return lista;
    }
}