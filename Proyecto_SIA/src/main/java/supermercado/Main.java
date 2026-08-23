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
   public static void main(String[] args) {
        
        System.out.println("=== INICIANDO PRUEBAS DEL SISTEMA ===");

        // 1. Instanciar el Supermercado
        Supermercado miSuper = new Supermercado("Supermercado Villa Alemana", "76.543.210-K");
        System.out.println("Supermercado creado : " + miSuper.getNombre() + "\nRut de la empresa : " + miSuper.getRut());

        // 2. Instanciar Secciones
        Seccion secCarniceria = new Seccion("SEC-01", "Carnes");
        Seccion secPanaderia = new Seccion("SEC-02", "Panes y Masas");
        Seccion secComida = new Seccion("SEC-03", "Comida Preparada");

        // 3. Agregar las secciones al supermercado
        miSuper.agregarSeccion(secCarniceria);
        miSuper.agregarSeccion(secPanaderia);
        miSuper.agregarSeccion(secComida);
        
        // 4. Instanciar Productos
        Producto p1 = new Producto("PROD-001", "Corte Lomo Vetado", 12500.0, 10, 5);
        Producto p2 = new Producto("PROD-002", "Pan Marraqueta 1kg", 2000.0, 50, 15);
        Producto p3 = new Producto("PROD-003", "Pollo con papas fritas ", 8500.0, 6, 3); // Cerca del stock mínimo

        // 5. Agregar productos a sus respectivas secciones
        secCarniceria.agregarProducto(p1);
        secPanaderia.agregarProducto(p2);
        secComida.agregarProducto(p3);

        System.out.println("\n--- PRUEBA DE BÚSQUEDA (SOBRECARGA) ---");
        
        // Prueba Buscar Producto (Opción 1: Solo por código en todo el súper)
        Producto encontrado1 = miSuper.buscarProducto("PROD-003");
        if(encontrado1 != null) {
            System.out.println("Encontrado por código: " + encontrado1.toString());
        }

        // Prueba Buscar Producto (Opción 2: Por nombre y código de sección)
        Producto encontrado2 = miSuper.buscarProducto("Corte Lomo Vetado", "SEC-01");
        if(encontrado2 != null) {
            System.out.println("Encontrado por nombre y sección: " + encontrado2.toString());
        }

        System.out.println("\n--- PRUEBA DE LÓGICA DE STOCK ---");
        
        // Descontar stock
        System.out.println("Stock inicial de " + p3.getNombre() + ": " + p3.getStock());
        p3.descontarStock(4);
        System.out.println("Stock después de vender 4: " + p3.getStock());
        
        // Comprobar si requiere reabastecimiento (el mínimo es 3, nos quedan 2)
        if (p3.requiereRebastecimiento()) {
            System.out.println("¡ALERTA! El producto " + p3.getNombre() + " requiere reabastecimiento.");
            
            // Aumentar stock con sobrecarga (cantidad + motivo)
            p3.aumentarStock(10, "Llegada de pedido urgente");
        }
        
        System.out.println("\nEstado final: " + p3.toString());
        System.out.println("=====================================");
    }
}
