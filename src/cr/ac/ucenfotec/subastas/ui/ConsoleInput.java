package cr.ac.ucenfotec.subastas.ui;

import java.time.LocalDate;
import java.util.Scanner;

public class ConsoleInput {

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
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número entero.");
            }
        }
    }

    public double leerDouble(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + " ");
                return Double.parseDouble(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Entrada inválida. Debe ingresar un número decimal válido.");
            }
        }
    }

    public LocalDate leerFecha(String mensaje) {
        while (true) {
            try {
                System.out.print(mensaje + " ");
                return LocalDate.parse(scanner.nextLine());
            } catch (Exception e) {
                System.out.println("Fecha inválida. Use el formato yyyy-MM-dd.");
            }
        }
    }

    public boolean confirmar(String mensaje) {
        while (true) {
            System.out.print(mensaje + " ");
            String respuesta = scanner.nextLine();

            if (respuesta.equalsIgnoreCase("S")) {
                return true;
            }

            if (respuesta.equalsIgnoreCase("N")) {
                return false;
            }

            System.out.println("Respuesta inválida. Ingrese S o N.");
        }
    }
}