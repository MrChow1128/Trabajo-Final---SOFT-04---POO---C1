package cr.ac.ucenfotec.subastas.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Utilidades {

    public static String[] getProperties() throws IOException {
        String[] propiedades = new String[5];
        Properties lectura = new Properties();

        try (InputStream input = Utilidades.class.getResourceAsStream("bd.properties")) {

            if (input == null) {
                throw new IOException("No se encontró bd.properties junto a Utilidades.java");
            }

            lectura.load(input);
            propiedades[0] = lectura.getProperty("driver");
            propiedades[1] = lectura.getProperty("server");
            propiedades[2] = lectura.getProperty("database");
            propiedades[3] = lectura.getProperty("user");
            propiedades[4] = lectura.getProperty("password");

            return propiedades;
        } catch (Exception e) {
            System.out.println("Sucedió un error al cargar las credenciales de acceso a la BD.");
            throw new IOException("No se pudo leer bd.properties");
        }
    }
}