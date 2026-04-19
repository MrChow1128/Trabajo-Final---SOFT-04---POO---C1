package cr.ac.ucenfotec.subastas.bl.dao;

import cr.ac.ucenfotec.subastas.bl.model.Coleccionista;
import cr.ac.ucenfotec.subastas.bl.model.Oferta;
import cr.ac.ucenfotec.subastas.dl.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class DAOOferta {

    private static String statement;
    private static String query;

    public static String insertarOferta(int idSubasta, Oferta oferta) throws SQLException, ClassNotFoundException {
        statement = "INSERT INTO t_ofertas(id_subasta, oferente_id, monto, fecha) VALUES (?, ?, ?, ?)";

        Connector.getConexion().ejecutarStatement(statement,
                idSubasta,
                oferta.getOferente().getIdentificacion(),
                oferta.getMonto(),
                Timestamp.valueOf(oferta.getFecha()));

        return "Oferta registrada correctamente.";
    }

    public static ArrayList<Oferta> listarOfertasPorSubasta(int idSubasta) throws SQLException, ClassNotFoundException {
        ArrayList<Oferta> ofertas = new ArrayList<Oferta>();

        query = "SELECT * FROM t_ofertas WHERE id_subasta = ? ORDER BY monto DESC";
        ResultSet resultado = Connector.getConexion().ejecutarQuery(query, idSubasta);

        while (resultado.next()) {
            String identificacionOferente = resultado.getString("oferente_id");
            double monto = resultado.getDouble("monto");
            Timestamp fechaBD = resultado.getTimestamp("fecha");

            Coleccionista oferente = (Coleccionista) DAOUsuario.seleccionarUsuarioPorIdentificacion(identificacionOferente);

            Oferta oferta = new Oferta(oferente, monto);
            oferta.setFecha(fechaBD.toLocalDateTime());

            ofertas.add(oferta);
        }

        return ofertas;
    }
}