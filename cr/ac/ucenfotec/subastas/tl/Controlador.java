package cr.ac.ucenfotec.subastas.tl;

import cr.ac.ucenfotec.subastas.bl.logic.GestorSubasta;
import cr.ac.ucenfotec.subastas.bl.logic.GestorUsuario;
import cr.ac.ucenfotec.subastas.bl.model.Coleccionista;
import cr.ac.ucenfotec.subastas.bl.model.Objeto;
import cr.ac.ucenfotec.subastas.bl.model.Oferta;
import cr.ac.ucenfotec.subastas.bl.model.Subasta;
import cr.ac.ucenfotec.subastas.bl.model.Usuario;

import java.time.LocalDate;
import java.util.ArrayList;

public class Controlador {

    private GestorUsuario gestorUsuario;
    private GestorSubasta gestorSubasta;

    public Controlador() {
        gestorUsuario = new GestorUsuario();
        gestorSubasta = new GestorSubasta();
    }

    public void registrarModeradorDefault() {
        gestorUsuario.registrarModeradorDefault();
    }

    public Usuario registrarUsuario(String nombre, String correo, String contrasena, String identificacion,
                                    String direccion, LocalDate fechaNacimiento, String tipoUsuario) {
        return gestorUsuario.registrarUsuario(nombre, correo, contrasena, identificacion, direccion, fechaNacimiento, tipoUsuario);
    }

    public Usuario iniciarSesion(String identificacion, String contrasena) {
        return gestorUsuario.iniciarSesion(identificacion, contrasena);
    }

    public ArrayList<Usuario> listarUsuarios() {
        return gestorUsuario.listarUsuarios();
    }

    public Subasta registrarSubasta(String titulo, Usuario creador, ArrayList<Objeto> objetos) {
        return gestorSubasta.registrarSubasta(titulo, creador, objetos);
    }

    public ArrayList<Subasta> listarSubastas() {
        return gestorSubasta.listarSubastas();
    }

    public ArrayList<Oferta> listarOfertas(Subasta subasta) {
        return gestorSubasta.listarOfertas(subasta);
    }

    public Oferta hacerOferta(Subasta subasta, Coleccionista coleccionista, double monto) {
        return gestorSubasta.hacerOferta(subasta, coleccionista, monto);
    }

    public Subasta buscarSubastaPorId(int idSubasta) {
        return gestorSubasta.buscarSubastaPorId(idSubasta);
    }

    public void cerrarSubasta(Subasta subasta) {
        gestorSubasta.cerrarSubasta(subasta);
    }

    public void agregarInteres(Coleccionista col, String interes) {
        gestorUsuario.agregarInteres(col, interes);
    }

    public ArrayList<String> obtenerIntereses(Coleccionista col) {
        return gestorUsuario.obtenerIntereses(col);
    }

    public void agregarObjeto(Coleccionista col, Objeto obj) {
        gestorUsuario.agregarObjeto(col, obj);
    }

    public ArrayList<Objeto> obtenerObjetos(Coleccionista col) {
        return gestorUsuario.obtenerObjetos(col);
    }
}