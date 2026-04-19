package cr.ac.ucenfotec.subastas.bl.dao;

import cr.ac.ucenfotec.subastas.bl.model.Subasta;
import cr.ac.ucenfotec.subastas.bl.model.Usuario;
import cr.ac.ucenfotec.subastas.dl.Connector;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class DAOSubasta {

    private static String statement;
    private static String query;

    public static String insertarSubasta(Subasta subasta) throws SQLException, ClassNotFoundException {
        statement = "INSERT INTO t_subastas(titulo, creador_id, fecha_creacion, activa) VALUES (?, ?, ?, ?)";

        Connector.getConexion().ejecutarStatement(statement,
                subasta.getTitulo(),
                subasta.getCreador().getIdentificacion(),
                Timestamp.valueOf(subasta.getFechaCreacion()),
                subasta.isActiva());

        int idSubasta = obtenerUltimoIdSubasta();
        subasta.setId(idSubasta);

        for (int i = 0; i < subasta.getObjetos().size(); i++) {
            DAOObjeto.insertarObjeto(idSubasta, subasta.getObjetos().get(i));
        }

        return "Subasta registrada correctamente.";
    }

    public static ArrayList<Subasta> listarSubastas() throws SQLException, ClassNotFoundException {
        ArrayList<Subasta> subastas = new ArrayList<Subasta>();

        query = "SELECT * FROM t_subastas ORDER BY id";
        ResultSet resultado = Connector.getConexion().ejecutarQueryGeneral(query);

        while (resultado.next()) {
            int id = resultado.getInt("id");
            String titulo = resultado.getString("titulo");
            String creadorId = resultado.getString("creador_id");
            Timestamp fechaCreacionBD = resultado.getTimestamp("fecha_creacion");
            LocalDateTime fechaCreacion = fechaCreacionBD.toLocalDateTime();
            boolean activa = resultado.getBoolean("activa");

            Usuario creador = DAOUsuario.seleccionarUsuarioPorIdentificacion(creadorId);

            Subasta subasta = new Subasta(id, titulo, creador, fechaCreacion, activa);
            subasta.setObjetos(DAOObjeto.seleccionarObjetosPorSubasta(id));
            subasta.setOfertas(DAOOferta.listarOfertasPorSubasta(id));

            subastas.add(subasta);
        }

        return subastas;
    }

    public static Subasta seleccionarSubastaPorId(int idBuscado) throws SQLException, ClassNotFoundException {
        query = "SELECT * FROM t_subastas WHERE id = ?";
        ResultSet resultado = Connector.getConexion().ejecutarQuery(query, idBuscado);

        if (!resultado.next()) {
            return null;
        }

        String titulo = resultado.getString("titulo");
        String creadorId = resultado.getString("creador_id");
        Timestamp fechaCreacionBD = resultado.getTimestamp("fecha_creacion");
        LocalDateTime fechaCreacion = fechaCreacionBD.toLocalDateTime();
        boolean activa = resultado.getBoolean("activa");

        Usuario creador = DAOUsuario.seleccionarUsuarioPorIdentificacion(creadorId);

        Subasta subasta = new Subasta(idBuscado, titulo, creador, fechaCreacion, activa);
        subasta.setObjetos(DAOObjeto.seleccionarObjetosPorSubasta(idBuscado));
        subasta.setOfertas(DAOOferta.listarOfertasPorSubasta(idBuscado));

        return subasta;
    }

    public static void cerrarSubasta(int idSubasta) throws SQLException, ClassNotFoundException {
        statement = "UPDATE t_subastas SET activa = ? WHERE id = ?";
        Connector.getConexion().ejecutarStatement(statement, false, idSubasta);
    }

    public static int obtenerUltimoIdSubasta() throws SQLException, ClassNotFoundException {
        query = "SELECT MAX(id) AS ultimo_id FROM t_subastas";
        ResultSet resultado = Connector.getConexion().ejecutarQueryGeneral(query);

        if (resultado.next()) {
            return resultado.getInt("ultimo_id");
        }

        return 0;
    }
}