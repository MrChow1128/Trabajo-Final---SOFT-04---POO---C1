package cr.ac.ucenfotec.subastas.ui;

import java.time.LocalDate;
<<<<<<< HEAD
=======
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
>>>>>>> ddd2f518b5784fe4eec853b63805707c3e118d1a
import java.util.Scanner;

public class ConsoleInput {

<<<<<<< HEAD
    private Scanner scanner;

    public ConsoleInput() {
        scanner = new Scanner(System.in);
    }

    public String leerTexto(String mensaje) {
        System.out.print(mensaje + " ");
        return scanner.nextLine();
    }

    public int leerEntero(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + " ");
                int valor = Integer.parseInt(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
=======
    private Scanner sc;

    public ConsoleInput(Scanner sc) {
        this.sc = sc;
    }

    public String leerTexto(String p) {
        while (true) {
            System.out.println(p);
            String s = sc.nextLine().trim();
            if (!s.isEmpty()) return s;
            System.out.println("Por favor ingrese un valor.");
        }
    }

    public int leerEnteroMensj(String p) {
        while (true) {
            System.out.print(p);

            try {
                String input = sc.nextLine();
                return Integer.parseInt(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
>>>>>>> ddd2f518b5784fe4eec853b63805707c3e118d1a
            }
        }
    }

<<<<<<< HEAD
    public double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + " ");
                double valor = Double.parseDouble(scanner.nextLine());
                return valor;
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número válido.");
=======
    public int leerEntero() {
        while (true) {
            try {
                String s = sc.nextLine();
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
>>>>>>> ddd2f518b5784fe4eec853b63805707c3e118d1a
            }
        }
    }

<<<<<<< HEAD
    public LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + " ");
                String texto = scanner.nextLine();
                return LocalDate.parse(texto);
            } catch (Exception e) {
                System.out.println("Fecha inválida. Use el formato yyyy-MM-dd.");
=======
    public double leerDouble(String p) {
        while (true) {
            System.out.print(p);
            try {
                String input = sc.nextLine();
                return Double.parseDouble(input.trim());
            } catch (NumberFormatException e) {
                System.out.println("Por favor, ingrese un número válido.");
>>>>>>> ddd2f518b5784fe4eec853b63805707c3e118d1a
            }
        }
    }

<<<<<<< HEAD
    public boolean confirmar(String mensaje) {
        while (true) {
            System.out.print(mensaje + " ");
            String respuesta = scanner.nextLine();

            if (respuesta.equalsIgnoreCase("S")) {
                return true;
            } else if (respuesta.equalsIgnoreCase("N")) {
                return false;
            } else {
                System.out.println("Respuesta inválida. Ingrese S o N.");
            }
        }
    }
}
=======
    //lee un string, formatea, y devuelve una fecha
    public LocalDate leerFecha(String p) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate date = null;

        while (date == null) {
            try {
                String rawDate = leerTexto(p);
                date = LocalDate.parse(rawDate, formatter);
            } catch (DateTimeParseException e) {
                System.out.println("Formato inválido; por favor use dd/MM/yyyy.");
            }
        }
        return date;
    }

    public boolean confirmar(String p) {
        while (true) {
            String resp = leerTexto(p);
            if (resp.equalsIgnoreCase("s")) {
                return true;
            } else if (resp.equalsIgnoreCase("n")) {
                return false;
            }
            System.out.println("Por favor ingrese 's' ó 'n'.");
        }
    }

    public int leerOpcionMenu(int max) {
        while (true) {
            int seleccion = leerEntero();
            if (seleccion >= 1 && seleccion <= max) {
                return seleccion;
            }
            System.out.println("Opción inválida.");
        }
    }
}
>>>>>>> ddd2f518b5784fe4eec853b63805707c3e118d1a
