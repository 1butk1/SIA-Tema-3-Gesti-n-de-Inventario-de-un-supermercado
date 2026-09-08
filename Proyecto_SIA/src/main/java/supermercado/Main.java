/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermercado;

import java.io.IOException;

/**
 *
 * @author ignac
 */
public class Main {
    public static void main(String[] args) throws IOException {
        System.out.println("Iniciando sistema y leyendo archivo CSV");
        Supermercado supermercado = LectorCSV.leerDatosCSV();
        
        if (supermercado == null) return;
        System.out.println("Datos cargados exitosamente!");
        Terminal terminal = new Terminal(supermercado);
        terminal.iniciar();
    }
}
