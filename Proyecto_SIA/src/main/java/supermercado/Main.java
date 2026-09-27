package supermercado;

import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("       SISTEMA DE INFORMACIÓN SUPERMERCADO       ");
        System.out.println("=================================================");

        Supermercado supermercado = null;
        try {
            System.out.println("Cargando datos desde CSV...");
            supermercado = LectorCSV.leerDatosCSV();
            System.out.println("¡Datos cargados exitosamente!\n");
        } catch (IOException e) {
            System.out.println("Aviso: No se encontró 'datos_supermercado.csv' o hubo un error. Iniciando vacío.");
            supermercado = new Supermercado("Supermercado Central", "76.123.456-7");
        }

        Scanner sc = new Scanner(System.in);
        int modo = -1;

        while (modo != 1 && modo != 2 && modo != 0) {
            System.out.println("Seleccione el modo de ejecución (SIA-10):");
            System.out.println("1. Modo Consola (Terminal)");
            System.out.println("2. Modo Ventana (GUI)");
            System.out.println("0. Salir");
            System.out.print("Opción: ");

            try {
                modo = Integer.parseInt(sc.nextLine());
                if (modo == 1) {
                    Terminal terminal = new Terminal(supermercado);
                    terminal.iniciar();
                } else if (modo == 2) {
                    System.out.println("Iniciando interfaz gráfica (GUI)...");
                    final Supermercado sm = supermercado;
                    java.awt.EventQueue.invokeLater(new Runnable() {
                        public void run() {
                            new supermercado.ventanas.VentanaPrincipal(sm).setVisible(true);
                        }
                    });
                
                } else if (modo == 0) {
                    System.out.println("Saliendo del sistema...");
                } else {
                    System.out.println("Opción inválida. Ingrese 1, 2 o 0.\n");
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Ingrese un número válido.\n");
            }
        }
    }
}