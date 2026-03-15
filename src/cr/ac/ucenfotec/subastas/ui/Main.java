package cr.ac.ucenfotec.subastas.ui;

import cr.ac.ucenfotec.subastas.servicio.ServiciosSubastas;
import cr.ac.ucenfotec.subastas.servicio.ServiciosUsuario;

public class Main {

    public static void main(String[] args){

        Menu menu = new Menu(new ServiciosUsuario(),new ServiciosSubastas());
        menu.ejecutar();

    }

}
