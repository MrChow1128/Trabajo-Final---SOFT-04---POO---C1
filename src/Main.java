public class Main {

    public static void main(String[] args){

        Menu menu = new Menu(new ServiciosUsuario(),new ServiciosSubastas());
        menu.ejecutar();

    }

}
