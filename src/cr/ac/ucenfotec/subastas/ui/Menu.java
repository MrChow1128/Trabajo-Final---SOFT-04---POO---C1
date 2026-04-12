package cr.ac.ucenfotec.subastas.ui;

import cr.ac.ucenfotec.subastas.model.Coleccionista;
import cr.ac.ucenfotec.subastas.model.Objeto;
import cr.ac.ucenfotec.subastas.model.Oferta;
import cr.ac.ucenfotec.subastas.model.Subasta;
import cr.ac.ucenfotec.subastas.model.Usuario;
import cr.ac.ucenfotec.subastas.model.Vendedor;
import cr.ac.ucenfotec.subastas.servicio.ServiciosSubastas;
import cr.ac.ucenfotec.subastas.servicio.ServiciosUsuario;

import java.time.LocalDate;
import java.util.ArrayList;

public class Menu {

    private ServiciosSubastas servSubasta;
    private ServiciosUsuario servUsuario;
    private Usuario usuarioActivo;
    private ConsoleInput input;
    private ArrayList<String> opciones;

    public Menu(ServiciosUsuario servUsuario, ServiciosSubastas servSubasta) {
        this.servUsuario = servUsuario;
        this.servSubasta = servSubasta;
        this.usuarioActivo = null;
        this.input = new ConsoleInput();
        this.opciones = new ArrayList<String>();
    }

    public void ejecutar() {
        servUsuario.registrarModeradorDefault();

        boolean salir = false;

        while (!salir) {
            System.out.println("\n========================================");
            System.out.println("   Sistema de Subastas - Menú Consola");
            System.out.println("========================================");
            System.out.println("1) Registro de usuarios");
            System.out.println("2) Listado de usuarios");
            System.out.println("3) Iniciar sesión");
            System.out.println("4) Listado de subastas");
            System.out.println("5) Salir del programa");

            int seleccion = input.leerEntero("Seleccione una opción:");

            switch (seleccion) {
                case 1:
                    registrarUsuarioUI();
                    break;
                case 2:
                    listarUsuariosUI();
                    break;
                case 3:
                    loginUsuarioUI();
                    if (usuarioActivo != null) {
                        menuUsuarios();
                    }
                    break;
                case 4:
                    listarSubastasUI();
                    break;
                case 5:
                    salir = true;
                    System.out.println("Saliendo del sistema. ¡Adiós!");
                    break;
                default:
                    opcionInvalida();
                    break;
            }
        }
    }

    private void menuUsuarios() {
        while (true) {
            mostrarOpciones();
            imprimirOpciones();

            int seleccion = input.leerEntero("Seleccione una opción:");

            if (seleccion < 1 || seleccion > opciones.size()) {
                opcionInvalida();
                continue;
            }

            String opcionElegida = opciones.get(seleccion - 1);

            System.out.println("-------------------------------------");
            System.out.println("Usted ha elegido: " + opcionElegida);

            switch (opcionElegida) {
                case "Crear subasta":
                    crearSubastaUI();
                    break;
                case "Listar subastas":
                    listarSubastasUI();
                    break;
                case "Mis intereses":
                    menuIntereses();
                    break;
                case "Mis objetos":
                    menuObjetos();
                    break;
                case "Salir":
                    System.out.println("\n¡Regresa pronto, " + usuarioActivo.getNombreCompleto() + "!");
                    System.out.println("Regresando al menú principal.");
                    usuarioActivo = null;
                    return;
                default:
                    opcionInvalida();
                    break;
            }
        }
    }

    private void mostrarOpciones() {
        opciones.clear();

        System.out.println("\nUsuario activo: " +
                usuarioActivo.getNombreCompleto() +
                " (" + usuarioActivo.getTipoUsuario() + ")");

        opciones.add("Crear subasta");
        opciones.add("Listar subastas");

        if (usuarioActivo instanceof Coleccionista) {
            opciones.add("Mis intereses");
            opciones.add("Mis objetos");
        }

        opciones.add("Salir");
    }

    private void menuColeccion(String titulo, Runnable listar, Runnable agregar) {
        boolean salir = false;

        while (!salir) {
            System.out.println("\nMis " + titulo);
            listar.run();

            System.out.println("1) Registrar nuevo");
            System.out.println("2) Salir");

            int seleccion = input.leerEntero("Seleccione una opción:");

            switch (seleccion) {
                case 1:
                    agregar.run();
                    break;
                case 2:
                    System.out.println("Volviendo al menú de coleccionista.");
                    salir = true;
                    break;
                default:
                    opcionInvalida();
                    break;
            }
        }
    }

    private void menuIntereses() {
        menuColeccion("intereses", new Runnable() {
            public void run() {
                listarInteresesUI();
            }
        }, new Runnable() {
            public void run() {
                agregarInteresUI();
            }
        });
    }

    private void menuObjetos() {
        menuColeccion("objetos", new Runnable() {
            public void run() {
                listarObjetosColeccionUI();
            }
        }, new Runnable() {
            public void run() {
                crearObjetoColeccionistaUI();
            }
        });
    }

    public void menuDetallesSubastaUI(Subasta subasta) {
        System.out.println("\n" + subasta);

        opciones.clear();
        opciones.add("Ver ofertas");

        if (usuarioActivo instanceof Coleccionista) {
            if (!subasta.getCreador().equals((Usuario) usuarioActivo)) {
                opciones.add("Hacer una oferta");
            }
        }

        opciones.add("Salir");
        imprimirOpciones();

        int seleccion = input.leerEntero("Seleccione una opción:");

        if (seleccion < 1 || seleccion > opciones.size()) {
            opcionInvalida();
            return;
        }

        String opcionElegida = opciones.get(seleccion - 1);

        switch (opcionElegida) {
            case "Ver ofertas":
                listarOfertasUI(subasta);
                break;
            case "Hacer una oferta":
                double monto = input.leerDouble("Ingrese el monto de la oferta:");
                Coleccionista coleccionista = (Coleccionista) usuarioActivo;

                try {
                    Oferta oferta = servSubasta.hacerOferta(subasta, coleccionista, monto);
                    System.out.println("¡Oferta hecha exitosamente!");
                    System.out.println(oferta);
                } catch (IllegalArgumentException e) {
                    System.out.println(e.getMessage());
                }
                break;
            case "Salir":
                System.out.println("Regresando al menú.");
                break;
            default:
                opcionInvalida();
                break;
        }
    }

    public void registrarUsuarioUI() {
        System.out.println("\n------Registro de usuario------");
        System.out.println("Seleccione el tipo de usuario que desea registrar:");
        System.out.println("1) Vendedor");
        System.out.println("2) Coleccionista");

        int opcionTipo = input.leerEntero("Ingrese una opción:");

        String tipoUsuario;
        if (opcionTipo == 1) {
            tipoUsuario = "Vendedor";
        } else if (opcionTipo == 2) {
            tipoUsuario = "Coleccionista";
        } else {
            System.out.println("Tipo de usuario inválido.");
            return;
        }

        System.out.println("\nIngrese la información solicitada:");
        LocalDate fechaNacimiento = input.leerFecha("Fecha de nacimiento (yyyy-MM-dd):");

        String identificacion = input.leerTexto("Identificación (0 para cancelar):");
        if (identificacion.equals("0")) {
            System.out.println("Proceso cancelado.");
            return;
        }

        String nombre = input.leerTexto("Nombre completo:");
        String correo = input.leerTexto("E-mail:");
        String contrasena = input.leerTexto("Contraseña:");
        String direccion = input.leerTexto("Dirección:");

        try {
            Usuario user = servUsuario.registrarUsuario(
                    nombre,
                    correo,
                    contrasena,
                    identificacion,
                    direccion,
                    fechaNacimiento,
                    tipoUsuario
            );
            System.out.println("Registrado exitosamente:");
            System.out.println(user);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void listarUsuariosUI() {
        System.out.println("\n------Lista de usuarios------");
        ArrayList<Usuario> usuarios = servUsuario.listarUsuarios();

        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados aún.");
            return;
        }

        for (Usuario u : usuarios) {
            System.out.println(u.getIdentificacion() + " - " +
                    u.getNombreCompleto() + " - " +
                    u.getTipoUsuario());
        }
    }

    private void loginUsuarioUI() {
        System.out.println("\n------Iniciar sesión------");

        ArrayList<Usuario> usuarios = servUsuario.listarUsuarios();
        if (usuarios.isEmpty()) {
            System.out.println("No hay usuarios registrados aún.");
            return;
        }

        String identificacion = input.leerTexto("Identificación:");
        String contrasena = input.leerTexto("Contraseña:");

        for (Usuario usuario : usuarios) {
            if (usuario.getIdentificacion().equals(identificacion) &&
                    usuario.getContrasena().equals(contrasena)) {
                usuarioActivo = usuario;
                System.out.println("Inicio de sesión exitoso. ¡Bienvenido " +
                        usuario.getNombreCompleto() + "!");
                return;
            }
        }

        System.out.println("Credenciales inválidas.");
    }

    private void listarInteresesUI() {
        Coleccionista col = (Coleccionista) usuarioActivo;
        ArrayList<String> intereses = col.getListaIntereses();

        if (intereses.isEmpty()) {
            System.out.println("No hay intereses registrados aún.");
            return;
        }

        for (int i = 0; i < intereses.size(); i++) {
            System.out.println((i + 1) + " - " + intereses.get(i));
        }
    }

    private void agregarInteresUI() {
        Coleccionista col = (Coleccionista) usuarioActivo;
        String interes = input.leerTexto("Ingresa tu interés:");
        col.agregarInteres(interes);
        System.out.println("Interés agregado correctamente.");
    }

    public void crearSubastaUI() {
        if (usuarioActivo instanceof Vendedor) {
            crearSubastaVendedorUI();
        } else if (usuarioActivo instanceof Coleccionista) {
            crearSubastaColeccionistaUI();
        }
    }

    public void listarSubastasUI() {
        System.out.println("\n------Lista de Subastas------");

        ArrayList<Subasta> subastas = servSubasta.listarSubastas();

        if (subastas.isEmpty()) {
            System.out.println("No hay subastas registradas aún.");
            return;
        }

        for (int i = 0; i < subastas.size(); i++) {
            System.out.println((i + 1) + " - " +
                    subastas.get(i).getTitulo() +
                    " - Objetos subastados: " + subastas.get(i).getObjetos().size() +
                    " - creada por " + subastas.get(i).getCreador().getNombreCompleto());
        }

        System.out.println("0 - Volver");
        int seleccion = input.leerEntero("Ingrese su elección:");

        if (seleccion == 0) {
            return;
        }

        if (seleccion < 1 || seleccion > subastas.size()) {
            opcionInvalida();
            return;
        }

        Subasta seleccionada = subastas.get(seleccion - 1);
        menuDetallesSubastaUI(seleccionada);
    }

    public void listarObjetosColeccionUI() {
        Coleccionista coleccionista = (Coleccionista) usuarioActivo;
        ArrayList<Objeto> objetos = coleccionista.getObjetosPropios();

        if (objetos.isEmpty()) {
            System.out.println("No hay objetos registrados aún.");
            return;
        }

        for (int i = 0; i < objetos.size(); i++) {
            System.out.println((i + 1) + " - " + objetos.get(i));
        }
    }

    public void crearObjetoColeccionistaUI() {
        Coleccionista col = (Coleccionista) usuarioActivo;
        Objeto objeto = crearObjetoUI();

        if (objeto == null) {
            return;
        }

        col.agregarObjeto(objeto);
        System.out.println("Objeto agregado a la colección.");
    }

    public void crearSubastaVendedorUI() {
        System.out.println("\n------Creación de Subastas------");
        System.out.println("Agreguemos objetos para la subasta:");

        ArrayList<Objeto> objetos = new ArrayList<Objeto>();

        while (true) {
            Objeto objeto = crearObjetoUI();

            if (objeto == null) {
                System.out.println("No se pudo crear el objeto.");
                return;
            }

            boolean repetido = false;
            for (Objeto existente : objetos) {
                if (existente.equals(objeto)) {
                    repetido = true;
                    break;
                }
            }

            if (repetido) {
                System.out.println("Ese objeto ya fue agregado.");
            } else {
                objetos.add(objeto);
            }

            if (!input.confirmar("¿Agregar otro objeto? [S/N]")) {
                break;
            }
        }

        if (objetos.isEmpty()) {
            System.out.println("Debe agregar al menos un objeto.");
            return;
        }

        String titulo = input.leerTexto("Ingrese un título para la subasta:");

        try {
            Subasta subasta = servSubasta.registrarSubasta(titulo, usuarioActivo, objetos);
            System.out.println("¡Subasta creada exitosamente!");
            System.out.println(subasta);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public void crearSubastaColeccionistaUI() {
        System.out.println("\n------Creación de Subastas------");
        System.out.println("Escoja al menos un objeto de su colección:");

        Coleccionista col = (Coleccionista) usuarioActivo;
        ArrayList<Objeto> objetosColeccionista = col.getObjetosPropios();

        if (objetosColeccionista.isEmpty()) {
            System.out.println("No tiene objetos en su colección.");
            return;
        }

        ArrayList<Objeto> objetosSeleccionados = new ArrayList<Objeto>();

        while (true) {
            for (int i = 0; i < objetosColeccionista.size(); i++) {
                System.out.println((i + 1) + ". " + objetosColeccionista.get(i));
            }

            int opcion = input.leerEntero("Seleccione un objeto por su índice:");

            if (opcion < 1 || opcion > objetosColeccionista.size()) {
                System.out.println("Selección inválida.");
                continue;
            }

            Objeto elegido = objetosColeccionista.get(opcion - 1);

            boolean repetido = false;
            for (Objeto obj : objetosSeleccionados) {
                if (obj.equals(elegido)) {
                    repetido = true;
                    break;
                }
            }

            if (repetido) {
                System.out.println("Este objeto ya fue agregado.");
                continue;
            }

            objetosSeleccionados.add(elegido);

            if (objetosSeleccionados.size() == objetosColeccionista.size()) {
                System.out.println("Ya ha agregado todos los objetos de su colección.");
                break;
            }

            if (!input.confirmar("¿Agregar otro objeto? [S/N]")) {
                break;
            }
        }

        if (objetosSeleccionados.isEmpty()) {
            System.out.println("Debe seleccionar al menos un objeto.");
            return;
        }

        String titulo = input.leerTexto("Ingrese un título para la subasta:");

        try {
            Subasta subasta = servSubasta.registrarSubasta(titulo, col, objetosSeleccionados);
            System.out.println("¡Subasta creada exitosamente!");
            System.out.println(subasta);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public Objeto crearObjetoUI() {
        System.out.println("\nIngrese los datos para crear su objeto:");

        String nombre = input.leerTexto("Nombre del objeto:");
        String descripcion = input.leerTexto("Descripción del objeto:");
        String estado = input.leerTexto("Estado del objeto:");
        LocalDate fechaCompra = input.leerFecha("Fecha de compra (yyyy-MM-dd):");

        try {
            return new Objeto(nombre, descripcion, estado, fechaCompra);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void listarOfertasUI(Subasta subasta) {
        System.out.println("\n------Lista de Ofertas------");

        ArrayList<Oferta> ofertas = subasta.getOfertas();

        if (ofertas.isEmpty()) {
            System.out.println("No hay ofertas aún.");
            return;
        }

        for (int i = 0; i < ofertas.size(); i++) {
            System.out.println((i + 1) + ". Oferente: " +
                    ofertas.get(i).getOferente().getNombreCompleto() +
                    " | Monto: " + ofertas.get(i).getMonto() +
                    " | Fecha: " + ofertas.get(i).getFecha());
        }
    }

    private void imprimirOpciones() {
        for (int i = 0; i < opciones.size(); i++) {
            System.out.println((i + 1) + ") " + opciones.get(i));
        }
    }

    private void opcionInvalida() {
        System.out.println("Opción inválida. Intente nuevamente.");
    }
}