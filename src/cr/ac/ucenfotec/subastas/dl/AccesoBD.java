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

    public void ejecutarStatement(String statement, String s1, String s2, String s3, String s4,
                                  String s5, String s6, String s7, double d1) throws SQLException {
        preparedStatement = conexion.prepareStatement(statement);
        preparedStatement.setString(1, s1);
        preparedStatement.setString(2, s2);
        preparedStatement.setString(3, s3);
        preparedStatement.setString(4, s4);
        preparedStatement.setString(5, s5);
        preparedStatement.setString(6, s6);
        preparedStatement.setString(7, s7);
        preparedStatement.setDouble(8, d1);
        preparedStatement.executeUpdate();
    }

    public void ejecutarStatement(String statement, String s1, String s2, Object o1, boolean b1) throws SQLException {
        preparedStatement = conexion.prepareStatement(statement);
        preparedStatement.setString(1, s1);
        preparedStatement.setString(2, s2);

        if (o1 instanceof java.sql.Timestamp) {
            preparedStatement.setTimestamp(3, (java.sql.Timestamp) o1);
        } else {
            preparedStatement.setObject(3, o1);
        }

        preparedStatement.setBoolean(4, b1);
        preparedStatement.executeUpdate();
    }

    public void ejecutarStatement(String statement, int i1, String s1, String s2, String s3, String s4) throws SQLException {
        preparedStatement = conexion.prepareStatement(statement);
        preparedStatement.setInt(1, i1);
        preparedStatement.setString(2, s1);
        preparedStatement.setString(3, s2);
        preparedStatement.setString(4, s3);
        preparedStatement.setString(5, s4);
        preparedStatement.executeUpdate();
    }

    public void ejecutarStatement(String statement, int i1, String s1, double d1, Object o1) throws SQLException {
        preparedStatement = conexion.prepareStatement(statement);
        preparedStatement.setInt(1, i1);
        preparedStatement.setString(2, s1);
        preparedStatement.setDouble(3, d1);

        if (o1 instanceof java.sql.Timestamp) {
            preparedStatement.setTimestamp(4, (java.sql.Timestamp) o1);
        } else {
            preparedStatement.setObject(4, o1);
        }

        preparedStatement.executeUpdate();
    }

    public void ejecutarStatement(String statement, String s1, String s2, double d1, Object o1) throws SQLException {
        preparedStatement = conexion.prepareStatement(statement);
        preparedStatement.setString(1, s1);
        preparedStatement.setString(2, s2);
        preparedStatement.setDouble(3, d1);

        if (o1 instanceof java.sql.Timestamp) {
            preparedStatement.setTimestamp(4, (java.sql.Timestamp) o1);
        } else {
            preparedStatement.setObject(4, o1);
        }

        preparedStatement.executeUpdate();
    }

    public void ejecutarStatement(String statement, boolean b1, int i1) throws SQLException {
        preparedStatement = conexion.prepareStatement(statement);
        preparedStatement.setBoolean(1, b1);
        preparedStatement.setInt(2, i1);
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