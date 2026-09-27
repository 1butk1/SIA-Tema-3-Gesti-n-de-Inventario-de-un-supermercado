package supermercado;

import Excepciones.ProductoNoEncontradoException;
import Excepciones.StockInsuficienteException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;

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
            System.out.println("=================================================");
            System.out.println("          SIA - SUPERMERCADO QUINTA              ");
            System.out.println("=================================================");
            System.out.println("----- GESTIÓN DE SECCIONES (COLECCIÓN A) -----");
            System.out.println("1.  Agregar Sección");
            System.out.println("2.  Mostrar Secciones");
            System.out.println("3.  Editar Sección");
            System.out.println("4.  Eliminar Sección");
            System.out.println("5.  Buscar Sección");
            System.out.println("\n----- GESTIÓN DE PRODUCTOS (COLECCIÓN B) -----");
            System.out.println("6.  Agregar Producto");
            System.out.println("7.  Mostrar Productos");
            System.out.println("8.  Editar Producto");
            System.out.println("9.  Eliminar Producto");
            System.out.println("10. Buscar Producto");
            System.out.println("\n----- OPERACIONES DE NEGOCIO Y REPORTES -----");
            System.out.println("11. Registrar Venta (Carrito de Compras)");
            System.out.println("12. Reabastecer Stock (Compra/Ingreso)");
            System.out.println("13. Verificar Stock Crítico (Alertas)");
            System.out.println("14. Generar Reporte de Reabastecimiento por Sección");
            System.out.println("-------------------------------------------------");
            System.out.println("0.  Salir y Guardar Datos");
            System.out.println("=================================================");
            System.out.print("Seleccione una opción: ");

            try {
                opcion = Integer.parseInt(sc.nextLine());
                if (opcion != 0) {
                    limpiarPantalla();
                    ejecutarOpcion(opcion);
                    pausar();
                } else {
                    guardarYSalir();
                }
            } catch (NumberFormatException e) {
                limpiarPantalla();
                System.out.println("Error: Ingrese un número válido.");
                pausar();
            }
        }
    }

    private void limpiarPantalla() {
        for (int i = 0; i < 40; i++) {
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
            case 11: registrarVenta(); break;
            case 12: reabastecerStock(); break;
            case 13: verificarStockCritico(); break;
            case 14: reportePorSeccion(); break;
            default: System.out.println("Opción no válida.");
        }
    }

    private void guardarYSalir() {
        System.out.println("\nGuardando cambios en 'datos_supermercado.csv'...");
        try {
            LectorCSV.guardarDatosCSV(supermercado);
            System.out.println("¡Datos guardados exitosamente!");
        } catch (IOException e) {
            System.out.println("Error al guardar los datos: " + e.getMessage());
        }
        System.out.println("¡Gracias por usar el sistema!");
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
            System.out.println("Código: [" + s.getCodigo() + "] | Nombre: " + s.getNombre() + " | Cant. Productos: " + s.getProductos().size());
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
        if (!nuevoNombre.trim().isEmpty()) {
            s.setNombre(nuevoNombre);
            System.out.println(">>> ¡Sección actualizada con éxito!");
        }
    }

private void eliminarSeccion() {
        System.out.println("--- ELIMINAR SECCIÓN ---");
        System.out.print("Ingrese el código de la sección a eliminar: ");
        String codigo = sc.nextLine();

        Seccion s = supermercado.buscarSeccion(codigo);
        if (s == null) {
            System.out.println(">>> Error: Sección no encontrada.");
            return;
        }

        if (!s.getProductos().isEmpty()) {
            System.out.print("Esta sección tiene " + s.getProductos().size() 
                    + " producto(s). ¿Eliminar de todas formas? (S/N): ");
            String confirmar = sc.nextLine();
            if (!confirmar.equalsIgnoreCase("S")) {
                System.out.println("Operación cancelada");
                return;
            }
        }

        supermercado.eliminarSeccion(codigo);
        System.out.println("Sección eliminada con éxito");
    }

    private void buscarSeccion() {
        System.out.println("--- BUSCAR SECCIÓN ---");
        System.out.print("Ingrese el código de la sección: ");
        String codigo = sc.nextLine();

        Seccion s = supermercado.buscarSeccion(codigo);
        if (s != null) {
            System.out.println("Sección encontrada: " + s.getNombre());
            System.out.println("Cantidad de productos: " + s.getProductos().size());
        } else {
            System.out.println("Error: Sección no encontrada.");
        }
    }

    private void agregarProducto() {
        System.out.println("--- AGREGAR PRODUCTO ---");
        System.out.print("Ingrese el código de la sección destino: ");
        String codSeccion = sc.nextLine();

        Seccion s = supermercado.buscarSeccion(codSeccion);
        if (s == null) {
            System.out.println("Error: La sección no existe.");
            return;
        }
        try {
            System.out.print("Código del producto: ");
            String codProd = sc.nextLine();
            
            if (s.buscarProducto(codProd) != null) {
                System.out.println("Error: Ya existe un producto con ese código en esta sección.");
                return;
            }
            
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
            System.out.println(">>> Error: Ingrese valores numéricos válidos.");
        }
    }

    private void mostrarProductos() {
        System.out.println("--- LISTADO DE PRODUCTOS ---");
        if (supermercado.getSecciones().isEmpty()) {
            System.out.println("No hay secciones registradas.");
            return;
        }
        boolean hayProductos = false;
        for (Seccion s : supermercado.getSecciones().values()) {
            System.out.println("\n[SECCIÓN]: " + s.getNombre() + " (Código: " + s.getCodigo() + ")");
            if (s.getProductos().isEmpty()) {
                System.out.println("  (Sin productos en esta sección)");
            } else {
                for (Producto p : s.getProductos().values()) {
                    System.out.println("  - " + p.toString());
                    hayProductos = true;
                }
            }
        }
        if (!hayProductos) {
            System.out.println("No hay productos cargados en ninguna sección.");
        }
    }

    private void editarProducto() {
        System.out.println("--- EDITAR PRODUCTO ---");
        System.out.print("Ingrese el código del producto a editar: ");
        String codigo = sc.nextLine();

        try {
            Producto p = supermercado.buscarProducto(codigo);
            System.out.println("Producto encontrado: " + p.getNombre() + " | Precio actual: $" + p.getPrecio() + " | Stock: " + p.getStock());

            System.out.print("Nuevo nombre (Enter para mantener actual): ");
            String nuevoNom = sc.nextLine();
            if (!nuevoNom.trim().isEmpty()) {
                p.setNombre(nuevoNom);
            }

            System.out.print("Nuevo precio (Enter para mantener actual): ");
            String nuevoPrecioStr = sc.nextLine();
            if (!nuevoPrecioStr.trim().isEmpty()) {
                p.setPrecio(Double.parseDouble(nuevoPrecioStr));
            }

            System.out.print("Nuevo stock (Enter para mantener actual): ");
            String nuevoStockStr = sc.nextLine();
            if (!nuevoStockStr.trim().isEmpty()) {
                p.setStock(Integer.parseInt(nuevoStockStr));
            }

            System.out.println(">>> ¡Producto modificado con éxito!");

        } catch (ProductoNoEncontradoException e) {
            System.out.println(">>> [EXCEPCIÓN]: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(">>> Error: Entrada numérica inválida.");
        }
    }

    private void eliminarProducto() {
    System.out.println("--- ELIMINAR PRODUCTO ---");
    System.out.print("Ingrese el código del producto a eliminar: ");
    String codigo = sc.nextLine();

    try {
        supermercado.eliminarProducto(codigo);
        System.out.println(">>> ¡Producto eliminado con éxito!");
    } catch (ProductoNoEncontradoException e) {
        System.out.println(">>> [EXCEPCIÓN]: " + e.getMessage());
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
            System.out.println(">>> [EXCEPCIÓN]: " + e.getMessage());
        }
    }

    private void registrarVenta() {
        System.out.println("--- REGISTRAR VENTA (CARRITO DE COMPRAS) ---");
        System.out.print("Ingrese ID de la venta (ej. V-001): ");
        String idVenta = sc.nextLine();
        System.out.print("Ingrese fecha (ej. 10/09/2026): ");
        String fecha = sc.nextLine();

        Venta venta = new Venta(idVenta, fecha);
        
        HashMap<String, Producto> inventarioGlobal = new HashMap<>();
        for (Seccion s : supermercado.getSecciones().values()) {
            inventarioGlobal.putAll(s.getProductos());
        }

        String continuar = "S";
        while (continuar.equalsIgnoreCase("S")) {
            System.out.print("\nIngrese código del producto a agregar: ");
            String codigo = sc.nextLine();

            try {
                Producto p = supermercado.buscarProducto(codigo);
                int yaEnCarrito = venta.getProductosVendidos().getOrDefault(codigo, 0);
                int disponibleReal = p.getStock() - yaEnCarrito;
                System.out.println("Producto: " + p.getNombre() + " | Stock actual: " + p.getStock() + " | Ya en carrito: " + yaEnCarrito + " | Precio: $" + p.getPrecio());

                System.out.print("Cantidad a llevar (Enter para llevar 1 unidad): ");
                String cantStr = sc.nextLine();
                
                if (cantStr.trim().isEmpty()) {
                    if (disponibleReal >= 1) {
                        venta.agregarProducto(p);
                        System.out.println(">>> 1 unidad añadida al carrito.");
                    } else {
                        System.out.println(">>> Stock insuficiente.");
                    }
                } else {
                    int cantidad = Integer.parseInt(cantStr);
                    if (cantidad <= 0) {
                        System.out.println(">>> La cantidad debe ser mayor a 0.");
                    } else if (cantidad > disponibleReal) {
                        System.out.println(">>> Stock insuficiente. Disponible: " + disponibleReal);
                    } else {
                        venta.agregarProducto(p, cantidad);
                        System.out.println(">>> Producto añadido al carrito.");
                    }
                }
            } catch (ProductoNoEncontradoException e) {
                System.out.println(">>> [EXCEPCIÓN]: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println(">>> Error: Ingrese un valor numérico válido.");
            }

            System.out.print("¿Desea agregar otro producto a esta venta? (S/N): ");
            continuar = sc.nextLine();
        }

        if (venta.getProductosVendidos().isEmpty()) {
            System.out.println(">>> No se agregaron productos. Venta cancelada.");
            return;
        }

        double total = venta.calcularTotal(inventarioGlobal);
        System.out.println("\n-------------------------------------------------");
        System.out.println("Total a pagar: $" + total);
        System.out.print("¿Confirmar compra y descontar inventario? (S/N): ");
        String confirmar = sc.nextLine();

        if (confirmar.equalsIgnoreCase("S")) {
            try {
                if (venta.procesarVenta(inventarioGlobal)) {
                    System.out.println(">>> ¡Venta " + venta.getIdVenta() + " procesada con éxito!");
                    
                    for (String cod : venta.getProductosVendidos().keySet()) {
                        Producto prod = inventarioGlobal.get(cod);
                        if (prod != null && prod.requiereRebastecimiento()) {
                            System.out.println(">>> [ALERTA STOCK]: El producto '" + prod.getNombre() + "' requiere reabastecimiento.");
                        }
                    }
                } else {
                    System.out.println(">>> Error: No se pudo completar la transacción.");
                }
            } catch (StockInsuficienteException e) {
                System.out.println(">>> [ERROR AL PROCESAR VENTA]: " + e.getMessage());
            }
        } else {
            System.out.println(">>> Venta anulada.");
        }
    }

    private void reabastecerStock() {
        System.out.println("--- REABASTECER STOCK (COMPRA) ---");
        System.out.print("Ingrese código del producto a reabastecer: ");
        String codigo = sc.nextLine();

        try {
            Producto p = supermercado.buscarProducto(codigo);
            System.out.println("Producto actual: " + p.getNombre() + " | Stock actual: " + p.getStock());
            
            System.out.print("Ingrese la cantidad a ingresar: ");
            int cantidad = Integer.parseInt(sc.nextLine());
            
            System.out.print("Ingrese motivo u orden asociada (Opcional, presione Enter para omitir): ");
            String motivo = sc.nextLine();

            if (motivo.trim().isEmpty()) {
                p.aumentarStock(cantidad);
            } else {
                p.aumentarStock(cantidad, motivo);
            }

            System.out.println(">>> Stock actualizado. Nuevo stock: " + p.getStock());

        } catch (ProductoNoEncontradoException e) {
            System.out.println(">>> [EXCEPCIÓN]: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println(">>> Error: Ingrese un número válido.");
        }
    }
    private void verificarStockCritico() {
    Proveedor proveedor = new Proveedor("76.000.000-0","Proveedor General","999999999");
    OrdenCompra orden = supermercado.verificarStockYGenerarOrdenes(proveedor);
    if (orden == null) {
        System.out.println("No hay productos que requieran reabastecimiento.");
    } else {
        System.out.println("===== ORDEN DE COMPRA GENERADA =====");
        System.out.println(orden.generarReporte());
    }
}
    private void reportePorSeccion() {
        System.out.println("--- REPORTE DE REABASTECIMIENTO POR SECCIÓN ---");
        System.out.print("Ingrese código de la sección: ");
        String codigo = sc.nextLine();
        System.out.println(supermercado.generarReportesOrdenesPorSeccion(codigo));
    }
}