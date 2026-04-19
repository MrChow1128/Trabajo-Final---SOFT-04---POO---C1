package cr.ac.ucenfotec.subastas.dl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccesoBD {

    private Connection conexion;
    private PreparedStatement preparedStatement;

    public AccesoBD(String direccion, String usuario, String contrasena) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        conexion = DriverManager.getConnection(direccion, usuario, contrasena);
    }

    public void ejecutarStatement(String statement, Object... parametros) throws SQLException {
        preparedStatement = conexion.prepareStatement(statement);

        for (int i = 0; i < parametros.length; i++) {
            Object parametro = parametros[i];

            if (parametro instanceof Integer) {
                preparedStatement.setInt(i + 1, (Integer) parametro);
            } else if (parametro instanceof Double) {
                preparedStatement.setDouble(i + 1, (Double) parametro);
            } else if (parametro instanceof Boolean) {
                preparedStatement.setBoolean(i + 1, (Boolean) parametro);
            } else if (parametro instanceof java.sql.Timestamp) {
                preparedStatement.setTimestamp(i + 1, (java.sql.Timestamp) parametro);
            } else {
                preparedStatement.setString(i + 1, String.valueOf(parametro));
            }
        }

        preparedStatement.executeUpdate();
    }

    public ResultSet ejecutarQuery(String query, String s1, String s2) throws SQLException {
        preparedStatement = conexion.prepareStatement(query);
        preparedStatement.setString(1, s1);
        preparedStatement.setString(2, s2);
        return preparedStatement.executeQuery();
    }

    public ResultSet ejecutarQuery(String query, String s1) throws SQLException {
        preparedStatement = conexion.prepareStatement(query);
        preparedStatement.setString(1, s1);
        return preparedStatement.executeQuery();
    }

    public ResultSet ejecutarQuery(String query, int i1) throws SQLException {
        preparedStatement = conexion.prepareStatement(query);
        preparedStatement.setInt(1, i1);
        return preparedStatement.executeQuery();
    }

    public ResultSet ejecutarQueryGeneral(String query) throws SQLException {
        preparedStatement = conexion.prepareStatement(query);
        return preparedStatement.executeQuery();
    }
}