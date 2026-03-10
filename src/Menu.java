import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

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

    public void ejecutar(){
        limpiarPantallaFake();

        //crear al moderador antes de iniciar
        while (!servuser.adminExiste()){crearModeradorUI();}

        boolean salir = false;

        while (!salir){

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
                case 3 -> {
                    loginUsuarioUI();
                    if (usuarioActivo != null){
                        menuUsuarios();
                    }
                }
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

        while(true) {

            mostrarOpciones();
            printOpciones();

            int seleccion = leerEntero();
            String opcionElegida = opciones.get(seleccion - 1);

            System.out.println("-------------------------------------");
            System.out.println("Usted ha elegido: " + opcionElegida);

            switch (opcionElegida) {
                case "Crear subasta" -> crearSubastaUI();
                case "Listar subastas" -> {
                    listarSubastasUI();
                }
                case "Mis intereses" -> menuIntereses();
                case "Mis objetos" -> menuObjetos();
                case "Salir" -> {
                    System.out.println("\n¡Regresa pronto, " + usuarioActivo.getNombreCompleto() + "!");
                    System.out.println("\n\nRegresando al menú principal.");
                    usuarioActivo = null;
                    return;
                }
                default -> opcionInvalida();
            }
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
    private void menuIntereses(){menuColeccion("intereses",this::listarInteresesUI,
            this::agregarInteresUI);}

    private void menuObjetos(){menuColeccion("objetos",this::listarObjetosColeccionUI,
            this::crearObjetoCollecionistaUI);}

    /*
        Despliega la información de la subasta
        y ofrece al usuario a ver las ofertas
        y si cumple los requisitos: ofrece agregar su propia oferta
     */
    public void menuDetallesSubastaUI(Subasta subasta){
        System.out.println(subasta);

        opciones.clear();
        opciones.add("Ver ofertas");
        if (usuarioActivo instanceof Coleccionista){
            if (!subasta.getCreador().equals(usuarioActivo)){
                opciones.add("Hacer una oferta");
            }
        }
        opciones.add("Salir");
        printOpciones();

        int seleccion = leerEntero();
        String opcionElegida = opciones.get(seleccion - 1);

        switch (opcionElegida){
            case "Ver ofertas" -> listarOfertasUI(subasta);
            case "Hacer una oferta" -> {
                double monto = leerDouble("Ingrese el monto de la oferta:");
                Coleccionista coleccionista = (Coleccionista) usuarioActivo;
                Oferta oferta = servsubasta.hacerOferta(subasta,coleccionista,monto);
                System.out.println("¡Oferta hecha exitosamente!");
                System.out.println(oferta);
            }
            case "Salir" -> {
                System.out.println("Regresando a menú.");
            }
            default -> opcionInvalida();
        }

    }

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
        System.out.println("\n\n------Registro de usuario------");
        System.out.println("\nSeleccione el tipo de usuario que desea registrar:");
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
            /*
                entra un loop para verificar que la identificación ingresada
                no sea repetida
             */
            String userId;
            do{
                userId = textInput("Identificación (0 para cancelar):");

                if (userId.equals("0")){
                    System.out.println("¡Proceso cancelado!");
                    System.out.println("Regresando al menú");
                    return;
                }

                if (servuser.idExist(userId)){
                    System.out.println("Ya existe un usuario con esta identificación.");
                }
            } while(servuser.idExist(userId));

        String userName = textInput("Nombre completo:");
        String userEmail = textInput("E-mail:");
        String userPw = textInput("Contraseña:");
        String userDir = textInput("Dirección:");

        Usuario user = servuser.registrarUsuario(userName,userEmail,userPw,userId,userDir,userDOB,userType);
        System.out.println("Registrado: " + user);
    }

    private void listarUsuariosUI(){

        System.out.println("\n\n------Lista de usuarios------");
        List<Usuario> users = servuser.listarUsuarios();
        if (users.isEmpty()){
            System.out.println("No hay usuarios registrados aún.");
            return;
        }
        System.out.println("\nUsuario(s) en el sistema:");
        for (Usuario u : users){
            System.out.println(u.getIdentificacion() + " - " + u.getNombreCompleto());
        }
    }

    //permite iniciar sesión como un usuario
    private void loginUsuarioUI() {

        System.out.println("\n\n------Iniciar sesión como usuario------");
        //primero verifica que haya usuarios para login
        if (servuser.listarUsuarios().isEmpty()){
            System.out.println("No hay usuarios registrados aún.");
            return;
        }
        /*
        Entra un loop para validar que la id ingresada
        exista para poder habilitar el inicio de sesión
         */
        Usuario u = null;
        while (u == null) {
            String id = textInput("Identificación (0 para cancelar):");

            if (id.equals("0")){
                System.out.println("¡Proceso cancelado!");
                System.out.println("Regresando al menú");
                return;
            } //permite al usuario salir

            u = servuser.lookupUser(id);
            if (u == null) {
                System.out.println("Usuario no encontrado.");
            }
        }

        /*
        Entra en loop para autenticación.
        Limita intentos a 3
         */
        int intentos = 0;
        while (intentos < 3){
            String pw = textInput("Contraseña:");
            if (servuser.autenticarUsuario(u, pw)) {
                usuarioActivo = u;
                System.out.println("\n\n------¡Iniciar de sesión exitoso!------");
                System.out.println("¡Bienvenido " + u.getNombreCompleto() + "!");
                return;
            }
            System.out.println("Credenciales inválidas.");
            intentos++;
            System.out.println("Quedan " + (3 - intentos) + " intentos.");
        }

        System.out.println("Autenticación fallida.");
        System.out.println("Regresando al menú principal.");
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
    //sigue un flow u otro dependiendo del tipo de usuario activo
    public void crearSubastaUI(){
        if (usuarioActivo instanceof Vendedor){
            crearSubastaVendedorUI();
        } else if (usuarioActivo instanceof Coleccionista) {
            crearSubastaColeccionista();
        }
    }

    public void listarSubastasUI(){
        System.out.println("\n\n------Lista de Subastas------");

        List<Subasta> subastas = servsubasta.listarSubastas();
        if (subastas.isEmpty()){
            System.out.println("No hay subastas registradas aún.");
            return;
        }
        for (int i = 0; i < subastas.size();i++){
            System.out.println((i+1) + " - " +
                    subastas.get(i).getTitulo() +
                    " - Objetos subastados: " + subastas.get(i).getObjetos().size() +
                    " - creada por " + subastas.get(i).getCreador().getNombreCompleto());
        }
        System.out.println("0 - Volver");

        System.out.println("\nPuede elegir una subasta por su índice o puede elegir volver.\n");
        int seleccion;

        while (true){
            seleccion = leerEnteroMensj("Ingrese su elección:");
            if (seleccion < 0 || seleccion > subastas.size()){
                opcionInvalida();
                continue;
            }
            break;
        }

        if (seleccion == 0){return;}

        Subasta seleccionada = subastas.get(seleccion - 1);
        menuDetallesSubastaUI(seleccionada);

    }
    
    public void listarObjetosColeccionUI(){
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

    public void crearSubastaVendedorUI(){

        System.out.println("\n\n------Creación de Subastas------");
        System.out.println("Agreguemos tu primer objeto para la subasta:");

        List<Objeto> objetos = new ArrayList<>();

        while (true) {
            Objeto objeto = crearObjetoUI();
            objetos.add(objeto);

            if(!confirmar("¿Agregar otro objeto? [S/N]")){
                break;
            };
        }

        if (objetos.isEmpty()){
            System.out.println("Debe agregar al menos un objeto");
            return;
        }

        System.out.println("\n------Objeto(s) agregado(s) exitosamente------");
        String titulo = textInput("\nIngrese un título para la subasta:");
        Subasta subasta = servsubasta.crearSubasta(titulo,usuarioActivo);
        servsubasta.agregarObjetosSubasta(subasta,objetos);
        System.out.println("¡Subasta creada exitosamente!\n");
        System.out.println(subasta);
    }

    //Crear subasta para coleccionista
    public void crearSubastaColeccionista(){
        System.out.println("\n\n------Creación de Subastas------");
        System.out.println("Escoja al menos un objeto de su colección:");
        Coleccionista col = (Coleccionista) usuarioActivo;
        List<Objeto> objetosColeccionista = servuser.listarColeccionObjetos(col);

        //verifica si el coleccionista ya tiene una colección con objetos
        if (objetosColeccionista.isEmpty()){
            System.out.println("No tiene objetos en su colección.");
            return;
        }

        List<Objeto> objetosSeleccionados = new ArrayList<>();

        while (true){

            //listar objetos del coleccionista
            for (int i = 0; i < objetosColeccionista.size();i++){
                System.out.println((i+1) + ". " + objetosColeccionista.get(i));
            }

            int opcion = leerEnteroMensj("Seleccione un objeto por su índice:");

            //validar que sea una opción válida
            if (opcion < 1 || opcion > objetosColeccionista.size()){
                System.out.println("Selección inválida.");
                continue;
            }
            Objeto elegido = objetosColeccionista.get(opcion-1);

            //validar que no haya objetos repetidos
            if (objetosSeleccionados.contains(elegido)){
                System.out.println("Este objeto ya fue agregado.");
                continue;
            }

            objetosSeleccionados.add(elegido);

            if (objetosSeleccionados.size() == objetosColeccionista.size()){
                System.out.println("Ya ha agregado todos los objetos de su colección.");
                break;
            }

            if (!confirmar("¿Agregar otro objeto? [S/N]")){break;}
        }

        System.out.println("\n------Objeto(s) agregado(s) exitosamente");
        String titulo = textInput("\nIngrese un título para la subasta:");
        Subasta subasta = servsubasta.crearSubasta(titulo,col);
        servsubasta.agregarObjetosSubasta(subasta,objetosSeleccionados);
        System.out.println("¡Subasta creada exitosamente!\n");
        System.out.println(subasta);
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

    public void listarOfertasUI(Subasta subasta){
        System.out.println("------Lista de ofertas para subasta: " +
                subasta.getTitulo() + "------");

        List<Oferta> ofertas = servsubasta.listarOfertas(subasta);

        if (ofertas.isEmpty()){
            System.out.println("No hay ofertas aún.");
        }

        for (int i = 0; i < ofertas.size();i++){
            System.out.println((i+1) + ". Oferente: " +
                    ofertas.get(i).getOferente().getNombreCompleto() + " | Monto: " +
                    ofertas.get(i).getMonto() +"\n");
        }
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

    private int leerEnteroMensj (String p){
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

    private double leerDouble (String p){
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

    private boolean confirmar(String p){
        while (true){
            String resp = textInput(p);
            if (resp.equalsIgnoreCase("s")){
                return true;
            } else if (resp.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Por favor ingrese 's' ó 'n'.");
        }
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
