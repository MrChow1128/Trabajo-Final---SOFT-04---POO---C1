package cr.ac.ucenfotec.subastas.ui;

import java.time.LocalDate;
import java.util.*;
import cr.ac.ucenfotec.subastas.servicio.*;
import cr.ac.ucenfotec.subastas.model.*;

public class Menu {
    private ServiciosSubastas servsubasta;
    private ServiciosUsuario servuser;
    private Scanner sc;
    private List<String> opciones = new ArrayList<>();
    private Usuario usuarioActivo;
    private ConsoleInput input;

    public Menu(ServiciosUsuario servuser, ServiciosSubastas servsubasta) {
        this.sc = new Scanner(System.in);
        this.servsubasta = servsubasta;
        this.servuser = servuser;
        this.input = new ConsoleInput(sc);
    }

    public void ejecutar(){

        //crear al moderador antes de iniciar
        while (!servuser.adminExiste()){crearModeradorUI();}

        boolean salir = false;

        while (!salir){

            System.out.println("========================================");
            System.out.println("   Sistema de Subastas - Menú Consola");
            System.out.println("========================================");
            System.out.println("1) Registro de usuarios");
            System.out.println("2) Listado de usuarios");
            System.out.println("3) Iniciar sesión");
            System.out.println("4) Listado de subastas");
            System.out.println("5) Salir del programa");

            int seleccion = input.leerEntero();

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
            imprimirOpciones();

            int seleccion = input.leerOpcionMenu(opciones.size());
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

            int seleccion = input.leerEntero();

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
            this::crearObjetoColeccionistaUI);}

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
        imprimirOpciones();

        int seleccion = input.leerOpcionMenu(opciones.size());
        String opcionElegida = opciones.get(seleccion - 1);

        switch (opcionElegida){
            case "Ver ofertas" -> listarOfertasUI(subasta);
            case "Hacer una oferta" -> {
                double monto = input.leerDouble("Ingrese el monto de la oferta:");
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
        LocalDate adminDOB = input.leerFecha("Fecha de nacimiento (dd/MM/yyyy):");
        if (!servuser.esMayordeEdad(adminDOB)){
            System.out.println("El moderador debe ser mayor de edad.");
            return;
        }
        String adminName = input.leerTexto("Nombre completo:");
        String adminId = input.leerTexto("Identificación:");
        String adminEmail = input.leerTexto("E-mail:");
        String adminPw = input.leerTexto("Contraseña:");

        servuser.registrarModerador(adminName,adminEmail,adminPw,adminId,adminDOB);
    }

    public void registrarUsuarioUI(){
        System.out.println("\n\n------Registro de usuario------");
        System.out.println("\nSeleccione el tipo de usuario que desea registrar:");
        System.out.println("1) Vendedor");
        System.out.println("2) Coleccionista");

        int userType = input.leerEntero();

        System.out.println("Por favor ingrese la información que " +
                "se le solicita para registrarle.");
        LocalDate userDOB = input.leerFecha("Fecha de nacimiento (dd/MM/yyyy):");
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
                userId = input.leerTexto("Identificación (0 para cancelar):");

                if (userId.equals("0")){
                    System.out.println("¡Proceso cancelado!");
                    System.out.println("Regresando al menú");
                    return;
                }

                if (servuser.existeIdentificacion(userId)){
                    System.out.println("Ya existe un usuario con esta identificación.");
                }
            } while(servuser.existeIdentificacion(userId));

        String userName = input.leerTexto("Nombre completo:");
        String userEmail = input.leerTexto("E-mail:");
        String userPw = input.leerTexto("Contraseña:");
        String userDir = input.leerTexto("Dirección:");

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
            System.out.println(u.getIdentificacion() + " - " + u.getNombreCompleto() +
                    " - " + u.getClass().getSimpleName());
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
            String id = input.leerTexto("Identificación (0 para cancelar):");

            if (id.equals("0")){
                System.out.println("¡Proceso cancelado!");
                System.out.println("Regresando al menú");
                return;
            } //permite al usuario salir

            u = servuser.buscarUsuario(id);
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
            String pw = input.leerTexto("Contraseña:");
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
        String interes = input.leerTexto("Ingresa tu interés:");
        servuser.agregarInteres(col,interes);
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
            crearSubastaColeccionistaUI();
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
            seleccion = input.leerEnteroMensj("Ingrese su elección:");
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

    public void crearObjetoColeccionistaUI(){
        Coleccionista col = (Coleccionista) usuarioActivo;
        Objeto objeto = crearObjetoUI();
        servuser.agregarObjetoAColeccion(col,objeto);
    }

    public void crearSubastaVendedorUI(){

        System.out.println("\n\n------Creación de Subastas------");
        System.out.println("Agreguemos tu primer objeto para la subasta:");

        List<Objeto> objetos = new ArrayList<>();

        while (true) {
            Objeto objeto = crearObjetoUI();
            objetos.add(objeto);

            if(!input.confirmar("¿Agregar otro objeto? [S/N]")){
                break;
            };
        }

        if (objetos.isEmpty()){
            System.out.println("Debe agregar al menos un objeto");
            return;
        }

        System.out.println("\n------Objeto(s) agregado(s) exitosamente------");
        String titulo = input.leerTexto("\nIngrese un título para la subasta:");
        Subasta subasta = servsubasta.registrarSubasta(titulo,usuarioActivo,objetos);
        System.out.println("¡Subasta creada exitosamente!\n");
        System.out.println(subasta);
    }

    //Crear subasta para coleccionista
    public void crearSubastaColeccionistaUI(){
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

            int opcion = input.leerEnteroMensj("Seleccione un objeto por su índice:");

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

            if (!input.confirmar("¿Agregar otro objeto? [S/N]")){break;}
        }

        System.out.println("\n------Objeto(s) agregado(s) exitosamente");
        String titulo = input.leerTexto("\nIngrese un título para la subasta:");
        Subasta subasta = servsubasta.registrarSubasta(titulo,col,objetosSeleccionados);
        System.out.println("¡Subasta creada exitosamente!\n");
        System.out.println(subasta);
    }

    public Objeto crearObjetoUI(){
        System.out.println("Ingresa los datos para crear tu objeto:");
        String objNombre = input.leerTexto("Nombre del Objeto:");
        String objDescripcion = input.leerTexto("Descripción:");
        String objEstado = input.leerTexto("Condición del objeto:");
        LocalDate objFecha = input.leerFecha("Fecha original de compra:");

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

    /*========================================================================
    Otros métodos
    ========================================================================
    */

    //imprime el array de opciones para desplegar un menú más dinámico
    private void imprimirOpciones(){
        for (int i = 0; i < opciones.size();i++){
            System.out.println((i+1) + ") " + opciones.get(i));
        }
    }

    private void opcionInvalida() {System.out.println("Opción inválida.");
    }

    }
