package cr.ac.ucenfotec.subastas.ui;

import cr.ac.ucenfotec.subastas.utils.Utilidades;

public class Main {

    public static void main(String[] args) {
        try {
            String[] props = Utilidades.getProperties();
            System.out.println("Driver: " + props[0]);
            System.out.println("Server: " + props[1]);
            System.out.println("Database: " + props[2]);
            System.out.println("User: " + props[3]);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Menu menu = new Menu();
        menu.ejecutar();
    }
}