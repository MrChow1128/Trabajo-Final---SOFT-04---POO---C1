package cr.ac.ucenfotec.subastas.bl.dao;

import cr.ac.ucenfotec.subastas.bl.model.Coleccionista;
import cr.ac.ucenfotec.subastas.bl.model.Objeto;
import cr.ac.ucenfotec.subastas.bl.model.Orden;
import cr.ac.ucenfotec.subastas.bl.model.Usuario;
import cr.ac.ucenfotec.subastas.dl.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DAOOrden {

    private static String statement;
    private static String query;

    public static String insertarOrden(Orden orden) throws SQLException, ClassNotFoundException {
        statement = "INSERT INTO t_ordenes(comprador_id, vendedor_id, precio_total, fecha) VALUES (?, ?, ?, ?)";

        Connector.getConexion().ejecutarStatement(statement,
                orden.getComprador().getIdentificacion(),
                orden.getVendedor().getIdentificacion(),
                orden.getPrecioTotal(),
                Timestamp.valueOf(orden.getFecha()));

        return "Orden registrada correctamente.";
    }

    public static ArrayList<Orden> listarOrdenes() throws SQLException, ClassNotFoundException {
        ArrayList<Orden> ordenes = new ArrayList<Orden>();

        query = "SELECT * FROM t_ordenes ORDER BY id";
        ResultSet resultado = Connector.getConexion().ejecutarQueryGeneral(query);

        while (resultado.next()) {
            int id = resultado.getInt("id");
            String compradorId = resultado.getString("comprador_id");
            String vendedorId = resultado.getString("vendedor_id");
            double precioTotal = resultado.getDouble("precio_total");
            Timestamp fechaBD = resultado.getTimestamp("fecha");

            Coleccionista comprador = (Coleccionista) DAOUsuario.seleccionarUsuarioPorIdentificacion(compradorId);
            Usuario vendedor = DAOUsuario.seleccionarUsuarioPorIdentificacion(vendedorId);

            ArrayList<Objeto> objetos = new ArrayList<Objeto>();

            Orden orden = new Orden(id, comprador, vendedor, objetos, precioTotal, fechaBD.toLocalDateTime());
            ordenes.add(orden);
        }

        return ordenes;
    }
}