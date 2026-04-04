package cr.ac.ucenfotec.subastas.ui;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class ConsoleInput {

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
            }
        }
    }

    public int leerEntero() {
        while (true) {
            try {
                String s = sc.nextLine();
                return Integer.parseInt(s.trim());
            } catch (NumberFormatException e) {
                System.out.print("Ingrese un número válido: ");
            }
        }
    }

    public double leerDouble(String p) {
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
