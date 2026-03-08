import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ServiciosUsuario {

    private List<Usuario> users = new ArrayList<>();
    private Moderador admin;

    //Métodos para usuarios
    public boolean esMayordeEdad(LocalDate DOB) {
        LocalDate hoy = LocalDate.now();
        int edad;
        edad = Period.between(DOB, hoy).getYears();
        return edad >= 18;
    }

    public void registrarModerador(String nombre, String email, String pw,
                                   String id, LocalDate DOB) {
        admin = new Moderador(nombre, id, DOB, pw, email);
    }

    public boolean adminExiste() {return admin != null;}

    public Usuario registrarUsuario(String nombre, String email, String pw,
                                    String id, String dir, LocalDate DOB, int userType) {
        Usuario user =
                switch (userType) {
                    case 1 -> new Vendedor(nombre, id, DOB, pw, email, 0, dir);
                    case 2 -> new Coleccionista(nombre, id, DOB, pw, email, 0, dir);
                    default -> throw new IllegalArgumentException("Tipo de usuario inválido");
                };
        users.add(user);
        return user;
    }

    public List<Usuario> listarUsuarios() {
        return List.copyOf(users);
    }

    public Usuario autenticarUsuario(String id, String pw) {
        for (Usuario u : users) {
            if (u.getIdentificacion().equals(id) && u.getContrasena().equals(pw)) {
                return u;
            }
        }
        return null;
    }

    //métodos para atributos de usuarios
    public void addIntereses(Coleccionista coleccionista, String interes) {
        coleccionista.agregarInteres(interes);
    }

    public void addObjeto(Coleccionista coleccionista, Objeto objeto) {
        coleccionista.agregarObjeto(objeto);
    }

    public List<Objeto> listarColeccionObjetos(Coleccionista coleccionista) {
        return List.copyOf(coleccionista.getObjetosPropios());
    }

    public void addObjetoColeccion(Coleccionista coleccionista, Objeto objeto) {
        coleccionista.agregarObjeto(objeto);
    }

    public List<String> listarIntereses(Coleccionista col) {
        return List.copyOf(col.getListaIntereses());
    }
}
