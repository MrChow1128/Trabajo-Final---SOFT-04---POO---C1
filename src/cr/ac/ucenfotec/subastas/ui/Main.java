package cr.ac.ucenfotec.subastas.ui;

import cr.ac.ucenfotec.subastas.bl.logic.GestorSubasta;
import cr.ac.ucenfotec.subastas.bl.logic.GestorUsuario;

public class Main {

    public static void main(String[] args) {
        GestorUsuario servUsuario = new GestorUsuario();
        GestorSubasta servSubasta = new GestorSubasta();

        Menu menu = new Menu(servUsuario, servSubasta);
        menu.ejecutar();
    }
}