package cr.ac.ucenfotec.subastas.ui;

import cr.ac.ucenfotec.subastas.servicio.ServiciosSubastas;
import cr.ac.ucenfotec.subastas.servicio.ServiciosUsuario;

public class Main {

    public static void main(String[] args) {
        ServiciosUsuario servUsuario = new ServiciosUsuario();
        ServiciosSubastas servSubasta = new ServiciosSubastas();

        Menu menu = new Menu(servUsuario, servSubasta);
        menu.ejecutar();
    }
}