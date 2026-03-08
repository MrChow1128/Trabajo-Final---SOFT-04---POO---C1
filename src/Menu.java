import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

public class Menu {
    private ServiciosSubastas servsubasta;
    private ServiciosUsuario servuser;
    private Scanner sc;
    private List<String> opciones = new ArrayList<>();
    private Usuario usuarioActivo;

    public Menu(ServiciosUsuario servuser, ServiciosSubastas servsubasta) {
        this.sc = new Scanner(System.in);
        this.servsubasta = servsubasta;
        this.servuser = servuser;
    }

    public void ejecturar(){
        limpiarPantallaFake();
        boolean salir = false;

        while (!salir){

            if (!servuser.adminExiste()){
                //el programa incia y solicita crear al moderador
                crearModeradorUI();
            }

            System.out.println("========================================");
            System.out.println("   Sistema de Subastas - Menú Consola");
            System.out.println("========================================");
            System.out.println("1) Registro de usuarios");
            System.out.println("2) Listado de usuarios");
            System.out.println("3) Inciar sesión");
            System.out.println("4) Listado de subastas");
            System.out.println("5) Salir del programa");

            int seleccion = leerEntero();

            switch (seleccion){
                case 1 -> registrarUsuarioUI();
                case 2 -> listarUsuariosUI();
                case 3 -> loginUsuarioUI();
                case 4 -> listarSubastasUI();
                case 5 -> {
                    salir = true;
                    System.out.println("Saliendo del sistema. ¡Adiós!");
                }
                default -> opcionInvalida();
            }
        }
    }

    /*
    ========================================================================
    Métodos de Menús
    ========================================================================
    */

    private void menuUsuarios() {
        mostrarOpciones();
        printOpciones();

        int seleccion = leerEntero();
        String opcionElegida = opciones.get(seleccion - 1);

        switch (opcionElegida) {
            case "Crear subasta" -> System.out.println("Crear subasta");
            case "Listar subastas" -> System.out.println("Listar subastas");
            case "Mis intereses" -> menuIntereses();
            case "Mis objetos" -> menuObjetos();
            case "Salir" -> {
                    System.out.println("Regresando al menú principal.");
                    System.out.println("¡Regresa pronto, " + usuarioActivo.getNombreCompleto());
                    return;}
            default -> opcionInvalida();
            }
        }

    //interactúa con método printOpciones para desplegar opciones dinámicamente
    //respondiendo al tipo de usuario activo
    private void mostrarOpciones(){
        opciones.clear();
        System.out.println("\nUsuario activo: " +
                usuarioActivo.getNombreCompleto() +
                " (" + usuarioActivo.getClass().getSimpleName() + ")");
        opciones.add("Crear subasta");
        opciones.add("Listar subastas");
        if (usuarioActivo instanceof Coleccionista){
            opciones.add("Mis intereses");
            opciones.add("Mis objetos");
        }
        opciones.add("Salir");
    }

    //método genérico para listar y/o agregar intereses u objetos
    //utiliza runnables y es llamado desde menuIntereses y menuObjetos
    private void menuColeccion(String titulo, Runnable listar,Runnable agregar){
        boolean salir = false;
        while(!salir){
            System.out.println("Mis " + titulo);

            listar.run();

            System.out.println("1) Registrar nuevo.");
            System.out.println("2) Salir");

            int seleccion = leerEntero();

            switch (seleccion){
                case 1 -> agregar.run();
                case 2 -> {
                    System.out.println("Volviendo a menú de coleccionista.");
                    salir = true;
                }
                default -> opcionInvalida();
            }

        }
    }

    //Estos dos métodos llaman a menuColeccion para listar y/o agregar intereses u objetos
    private void menuIntereses(){menuColeccion("intereses",this::listarInteresesUI,this::agregarInteresUI);}
    private void menuObjetos(){menuColeccion("objetos",this::listarObjetosUI,this::crearObjetoCollecionistaUI);}

    /*
    ========================================================================
    Métodos de servicios de Usuario
    ========================================================================
    */

    public void crearModeradorUI(){
        System.out.println("Para iniciar, por favor ingrese sus datos de moderador:");
        LocalDate adminDOB = dateInput("Fecha de nacimiento (dd/MM/yyyy):");
        if (!servuser.esMayordeEdad(adminDOB)){
            System.out.println("El moderador debe ser mayor de edad.");
            return;
        }
        String adminName = textInput("Nombre completo:");
        String adminId = textInput("Identificación:");
        String adminEmail = textInput("E-mail:");
        String adminPw = textInput("Contraseña:");

        servuser.registrarModerador(adminName,adminEmail,adminPw,adminId,adminDOB);
    }

    public void registrarUsuarioUI(){

        System.out.println("\n\nSeleccione el tipo de usuario que desea registrar:");
        System.out.println("1) Vendedor");
        System.out.println("2) Coleccionista");

        int userType = leerEntero();

        System.out.println("Por favor ingrese la información que " +
                "se le solicita para registrarle.");
        LocalDate userDOB = dateInput("Fecha de nacimiento (dd/MM/yyyy):");
        if (!servuser.esMayordeEdad(userDOB)){
            System.out.println("El usuario debe ser mayor de edad.");
            return;
        }
        String userName = textInput("Nombre completo:");
        String userId = textInput("Identificación:");
        String userEmail = textInput("E-mail:");
        String userPw = textInput("Contraseña:");
        String userDir = textInput("Dirección:");

        Usuario user = servuser.registrarUsuario(userName,userEmail,userPw,userId,userDir,userDOB,userType);
        System.out.println("Registrado: " + user);
    }

    private void listarUsuariosUI(){
        List<Usuario> users = servuser.listarUsuarios();
        if (users.isEmpty()){
            System.out.println("No hay usuarios registrados aún.");
            return;
        }
        for (Usuario u : users){
            System.out.println(u.getIdentificacion() + " - " + u.getNombreCompleto());
        }
    }

    //permite iniciar sesión como un usuario
    private void loginUsuarioUI(){
        String id = textInput("Identificación:");
        String pw = textInput("Contraseña:");
        Usuario u = servuser.autenticarUsuario(id, pw);

        if (u == null){
            System.out.println("Credenciales inválidas.");
            return;
        }
        usuarioActivo = u;
        System.out.println("Bienvenido " + u.getNombreCompleto());
    }

    private void listarInteresesUI(){
        Coleccionista col = (Coleccionista) usuarioActivo;
        List<String> intereses = servuser.listarIntereses(col);

        if (intereses.isEmpty()){
            System.out.println("No hay intereses registrados aún.");
            return;
        }
        for (int i = 0; i < intereses.size(); i++){
            System.out.println((i+1) + " - " + intereses.get(i));
        }
    }

    private void agregarInteresUI(){
        Coleccionista col = (Coleccionista) usuarioActivo;
        String interes = textInput("Ingresa tu interés:");
        servuser.addIntereses(col,interes);
    }

        /*
    ========================================================================
    Métodos de servicios de Subastas y objetos
    ========================================================================
    */

    public void crearSubastaUI(){
        System.out.println("Por favor ingrese la información para crear su subasta:");
    }

    public void listarSubastasUI(){
        List<Subasta> subastas = servsubasta.listarSubastas();
        if (subastas.isEmpty()){
            System.out.println("No hay subastas registradas aún.");
            return;
        }
        for (Subasta s : subastas){
            System.out.println(s);
        }
    }
    
    public void listarObjetosUI(){
        Coleccionista coleccionista = (Coleccionista) usuarioActivo;
        List<Objeto> objetos = servuser.listarColeccionObjetos(coleccionista);
        if (objetos.isEmpty()){
            System.out.println("No hay objetos registrados aún.");
            return;
        }
        for (int i = 0; i < objetos.size();i++){
            System.out.println((i+1) + " - " + objetos.get(i));
        }
    }

    public void crearObjetoCollecionistaUI(){
        Coleccionista col = (Coleccionista) usuarioActivo;
        Objeto objeto = crearObjetoUI();
        servuser.addObjetoColeccion(col,objeto);
    }

    public void crearObjetoSubastaUI(Subasta subasta){
        Objeto objeto = crearObjetoUI();
        servsubasta.crearObjetoSubasta(subasta,objeto);
    }

    public Objeto crearObjetoUI(){
        System.out.println("Ingresa los datos para crear tu objeto:");
        String objNombre = textInput("Nombre del Objeto:");
        String objDescripcion = textInput("Descripción:");
        String objEstado = textInput("Condición del objeto:");
        LocalDate objFecha = dateInput("Fecha original de compra:");

        Objeto objeto = new Objeto(objNombre,objDescripcion,objEstado,objFecha);
        return objeto;
    }

    /*
    ========================================================================
    Lectores
    ========================================================================
    */

    //String input
    private String textInput(String p){
        while(true){
            System.out.println(p);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Por favor ingrese un valor.");
        }
    }

    //limpia la consola
    private void limpiarPantallaFake(){ for(int i=0;i<25;i++) System.out.println(); }


    private String userInput(String p) {
        while (true) {
            System.out.println(p);
            String s = sc.nextLine();
            if (!s.isEmpty()){
                return s;
            } else
                System.out.println("Por favor, ingrese un valor.");
        }
    }

    private int userInputINT (String p){
        while (true) {
            System.out.print(p);

            try {
                String input = sc.nextLine();
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    private int leerEntero(){
        while (true) {
            try {
                String s = sc.nextLine();
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    private double userInputDouble (String p){
        while (true) {
            System.out.print(p);

            try {
                String input = sc.nextLine();
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
            }
        }
    }

    //lee un string, formatea, y devuelve una fecha
    private LocalDate dateInput(String p){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = null;

        while (date == null){
            try{
                String rawDate = userInput(p);
                date = LocalDate.parse(rawDate, formatter);
            } catch (DateTimeParseException e){
                System.out.println("Formato inválido; por favor use dd/MM/yyyy.");
            }
        }
        return date;
    }

    /*========================================================================
    Otros métodos
    ========================================================================
    */

    //imprime el array de opciones para desplegar un menú más dinámico
    private void printOpciones(){
        for (int i = 0; i < opciones.size();i++){
            System.out.println((i+1) + ") " + opciones.get(i));
        }
    }

    private void opcionInvalida() {
        System.out.println("Opción inválida.");
    }

    }
