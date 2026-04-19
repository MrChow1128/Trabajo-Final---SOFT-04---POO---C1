package cr.ac.ucenfotec.subastas.bl.dao;

import cr.ac.ucenfotec.subastas.bl.model.Objeto;
import cr.ac.ucenfotec.subastas.dl.Connector;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

public class DAOObjeto {

    private static String statement;
    private static String query;

    public static void insertarObjeto(int idSubasta, Objeto objeto) throws SQLException, ClassNotFoundException, IOException {
        statement = "INSERT INTO t_objetos_subasta(id_subasta, nombre, descripcion, estado, fecha_compra) VALUES (?, ?, ?, ?, ?)";

        Connector.getConexion().ejecutarStatement(
                statement,
                idSubasta,
                objeto.getNombre(),
                objeto.getDescripcion(),
                objeto.getEstado(),
                objeto.getFechaCompra().toString()
        );
    }

    public static ArrayList<Objeto> seleccionarObjetosPorSubasta(int idSubasta) throws SQLException, ClassNotFoundException, IOException {
        ArrayList<Objeto> objetos = new ArrayList<Objeto>();

        query = "SELECT * FROM t_objetos_subasta WHERE id_subasta = ?";
        ResultSet resultado = Connector.getConexion().ejecutarQuery(query, idSubasta);

        while (resultado.next()) {
            Objeto objeto = new Objeto(
                    resultado.getString("nombre"),
                    resultado.getString("descripcion"),
                    resultado.getString("estado"),
                    LocalDate.parse(resultado.getString("fecha_compra"))
            );
            objetos.add(objeto);
        }

        return objetos;
    }
}