/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermercado;
import java.util.Scanner;
import Excepciones.ProductoNoEncontradoException;
/**
 *
 * @author ignac
 */
public class Terminal {
    private Supermercado supermercado;
    private Scanner sc;

    public Terminal(Supermercado supermercado) {
        this.supermercado = supermercado;
        this.sc = new Scanner(System.in);
    }

    public void iniciar() {
        int opcion = -1;
        while (opcion != 0) {
            limpiarPantalla();
            System.out.println(">>>>> SUPERMERCADO QUINTA <<<<<\n");
            System.out.println("----- GESTIÓN DE SECCIONES -----");
            System.out.println("1. Agregar Sección");
            System.out.println("2. Mostrar Secciones");
            System.out.println("3. Editar Sección");
            System.out.println("4. Eliminar Sección");
            System.out.println("5. Buscar Sección");
            System.out.println("\n----- GESTIÓN DE PRODUCTOS -----");
            System.out.println("6. Agregar Producto");
            System.out.println("7. Mostrar Productos");
            System.out.println("8. Editar Producto");
            System.out.println("9. Eliminar Producto");
            System.out.println("10. Buscar Producto");
            System.out.println("\n----- NEGOCIO -----");
            System.out.println("11. Verificar Stock");
            System.out.println("0. Salir");
            System.out.print("\nSeleccione una funcionalidad: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
                if (opcion != 0) {
                    limpiarPantalla();
                    ejecutarOpcion(opcion);
                    pausar();
                }
            } catch (NumberFormatException e) {
                limpiarPantalla();
                System.out.println("Error: Ingrese un número válido.");
                pausar();
            }
        }
    }

    private void limpiarPantalla(){
        for (int i = 0; i < 60; i++){ 
            System.out.println("");
        }
    }

    private void pausar() {
        System.out.println("\n[Presione ENTER para continuar...]");
        sc.nextLine();
    }

    private void ejecutarOpcion(int opcion) {
        switch (opcion) {
            case 1: agregarSeccion(); break;
            case 2: mostrarSecciones(); break;
            case 3: editarSeccion(); break;
            case 4: eliminarSeccion(); break;
            case 5: buscarSeccion(); break;
            case 6: agregarProducto(); break;
            case 7: mostrarProductos(); break;
            case 8: editarProducto(); break;
            case 9: eliminarProducto(); break;
            case 10: buscarProducto(); break;
            case 11: supermercado.verificarStockYGenerarOrdenes(); break;
            default: System.out.println("Opción no válida.");
        }
    }
    private void agregarSeccion() {
        System.out.println("--- AGREGAR SECCIÓN ---");
        System.out.print("Ingrese código de la sección: ");
        String codigo = sc.nextLine();
        
        if (supermercado.buscarSeccion(codigo) != null) {
            System.out.println(">>> Error: Ya existe una sección con ese código.");
            return;
        }
        System.out.print("Ingrese nombre de la sección: ");
        String nombre = sc.nextLine();
        
        supermercado.agregarSeccion(new Seccion(codigo, nombre));
        System.out.println(">>> ¡Sección agregada con éxito!");
    }
    private void mostrarSecciones() {
        System.out.println("--- LISTADO DE SECCIONES ---");
        if (supermercado.getSecciones().isEmpty()) {
            System.out.println("No hay secciones registradas.");
            return;
        }
        for (Seccion s : supermercado.getSecciones().values()) {
            System.out.println("Código: " + s.getCodigo() + " | Nombre: " + s.getNombre());
        }
    }
    private void editarSeccion() {
        System.out.println("--- EDITAR SECCIÓN ---");
        System.out.print("Ingrese el código de la sección a editar: ");
        String codigo = sc.nextLine();
        Seccion s = supermercado.buscarSeccion(codigo);
        if (s == null) {
            System.out.println(">>> Error: Sección no encontrada.");
            return;
        }
        System.out.print("Ingrese el nuevo nombre (Actual: " + s.getNombre() + "): ");
        String nuevoNombre = sc.nextLine();
        s.setNombre(nuevoNombre);
        System.out.println(">>> ¡Sección actualizada con éxito!");
    }
    private void eliminarSeccion() {
        System.out.println("--- ELIMINAR SECCIÓN ---");
        System.out.print("Ingrese el código de la sección a eliminar: ");
        String codigo = sc.nextLine();
        
        if (supermercado.eliminarSeccion(codigo)) {
            System.out.println(">>> ¡Sección eliminada con éxito!");
        } else {
            System.out.println(">>> Error: Sección no encontrada.");
        }
    }
    private void buscarSeccion() {
        System.out.println("--- BUSCAR SECCIÓN ---");
        System.out.print("Ingrese el código de la sección: ");
        String codigo = sc.nextLine();
        
        Seccion s = supermercado.buscarSeccion(codigo);
        if (s != null) {
            System.out.println(">>> Sección encontrada: " + s.getNombre());
            System.out.println(">>> Cantidad de productos en esta sección: " + s.getProductos().size());
        } else {
            System.out.println(">>> Error: Sección no encontrada.");
        }
    }
    private void agregarProducto() {
        System.out.println("--- AGREGAR PRODUCTO ---");
        System.out.print("Ingrese el código de la sección donde guardará el producto: ");
        String codSeccion = sc.nextLine();
        
        Seccion s = supermercado.buscarSeccion(codSeccion);
        if (s == null) {
            System.out.println(">>> Error: La sección no existe. Cree la sección primero.");
            return;
        }
        try {
            System.out.print("Código del producto: ");
            String codProd = sc.nextLine();
            System.out.print("Nombre del producto: ");
            String nomProd = sc.nextLine();
            System.out.print("Precio: ");
            double precio = Double.parseDouble(sc.nextLine());
            System.out.print("Stock inicial: ");
            int stock = Integer.parseInt(sc.nextLine());
            System.out.print("Stock Mínimo: ");
            int stockMinimo = Integer.parseInt(sc.nextLine());
            System.out.print("Punto de Reorden: ");
            int puntoReorden = Integer.parseInt(sc.nextLine());

            Producto p = new Producto(codProd, nomProd, precio, stock, stockMinimo, puntoReorden);
            s.agregarProducto(p);
            System.out.println(">>> ¡Producto agregado con éxito a la sección " + s.getNombre() + "!");

        } catch (NumberFormatException e) {
            System.out.println(">>> Error: Ingrese valores numéricos válidos para precio y stock.");
        }
    }
    private void mostrarProductos() {
        System.out.println("--- LISTADO DE PRODUCTOS ---");
        if (supermercado.getSecciones().isEmpty()) {
            System.out.println("No hay secciones ni productos registrados.");
            return;
        }
        boolean hayProductos = false;
        for (Seccion s : supermercado.getSecciones().values()) {
            System.out.println("\nSección: " + s.getNombre() + " (Código: " + s.getCodigo() + ")");
            if (s.getProductos().isEmpty()) {
                System.out.println("  (Sin productos)");
            } else {
                for (Producto p : s.getProductos().values()) {
                    System.out.println("  - " + p.toString());
                    hayProductos = true;
                }
            }
        }
        if (!hayProductos && !supermercado.getSecciones().isEmpty()) {
            System.out.println("No hay productos cargados en ninguna sección.");
        }
    }

    private void editarProducto() {
        System.out.println("--- EDITAR PRODUCTO ---");
        System.out.print("Ingrese el código del producto que desea editar: ");
        String codigo = sc.nextLine();

        try {
            // Aprovechamos tu método ya creado en Supermercado que lanza ProductoNoEncontradoException
            Producto p = supermercado.buscarProducto(codigo);
            System.out.println("Encontrado: " + p.getNombre() + " | Precio actual: " + p.getPrecio() + " | Stock: " + p.getStock());

            System.out.print("Nuevo nombre (deje en blanco para no cambiar): ");
            String nuevoNom = sc.nextLine();
            if (!nuevoNom.trim().isEmpty()) {
                p.setNombre(nuevoNom);
            }

            System.out.print("Nuevo precio (deje en blanco para no cambiar): ");
            String nuevoPrecioStr = sc.nextLine();
            if (!nuevoPrecioStr.trim().isEmpty()) {
                p.setPrecio(Double.parseDouble(nuevoPrecioStr));
            }

            System.out.print("Nuevo stock (deje en blanco para no cambiar): ");
            String nuevoStockStr = sc.nextLine();
            if (!nuevoStockStr.trim().isEmpty()) {
                p.setStock(Integer.parseInt(nuevoStockStr));
            }

            System.out.println(">>> ¡Producto actualizado correctamente!");

        } catch (ProductoNoEncontradoException e) {
            System.out.println(">>> " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(">>> Error: Ingrese valores numéricos válidos.");
        }
    }

    private void eliminarProducto() {
        System.out.println("--- ELIMINAR PRODUCTO ---");
        System.out.print("Ingrese el código del producto a eliminar: ");
        String codigo = sc.nextLine();

        boolean eliminado = false;
        for (Seccion s : supermercado.getSecciones().values()) {
            if (s.getProductos().containsKey(codigo)) {
                s.getProductos().remove(codigo);
                eliminado = true;
                break;
            }
        }

        if (eliminado) {
            System.out.println(">>> ¡Producto eliminado con éxito de su sección!");
        } else {
            System.out.println(">>> Error: No se encontró ningún producto con ese código.");
        }
    }

    private void buscarProducto() {
        System.out.println("--- BUSCAR PRODUCTO ---");
        System.out.print("Ingrese el código del producto: ");
        String codigo = sc.nextLine();

        try {
            Producto p = supermercado.buscarProducto(codigo);
            System.out.println(">>> ¡Producto encontrado!");
            System.out.println(p.toString() + " | Stock Mínimo: " + p.getStockMinimo() + " | Punto Reorden: " + p.getPuntoReOrden());
        } catch (ProductoNoEncontradoException e) {
            System.out.println(">>> " + e.getMessage());
        }
    }
}
   
