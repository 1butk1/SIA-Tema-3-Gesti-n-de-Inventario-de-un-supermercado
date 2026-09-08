/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermercado;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
        
/**
 *
 * @author ignac
 */
public class LectorCSV {

    public static Supermercado leerDatosCSV() throws IOException {

        Supermercado supermercado =new Supermercado("Supermercado Central", "76.123.456-7");

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("datos_supermercado.csv"),StandardCharsets.UTF_8))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null) {
                // Ignorar líneas vacías
                if (linea.trim().isEmpty()) {
                    continue;
                }
                // Ignorar encabezado
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }
                String[] datos = linea.split(",", -1);
                if (datos.length == 0) {
                    continue;
                }
                String tipoRegistro = datos[0];
                try {
                    switch (tipoRegistro) {
                        case "SECCION":
                            
                            if (datos.length < 3) {
                                System.out.println("Registro SECCION incompleto.");
                                continue;
                            }
                            String codSec = datos[1];
                            String nomSec = datos[2];
                            if (supermercado.buscarSeccion(codSec) == null) {
                                Seccion nuevaSeccion =new Seccion(codSec, nomSec);
                                supermercado.agregarSeccion(nuevaSeccion);
                            }
                            break;
                            
                        case "PRODUCTO":
                            
                            if (datos.length < 8) {
                                System.out.println("Registro producto incompleto.");
                                continue;
                            }

                            String codProd = datos[1];
                            String nomProd = datos[2];
                            double precio = Double.parseDouble(datos[3]);

                            int stock = Integer.parseInt(datos[4]);

                            int stockMinimo = Integer.parseInt(datos[5]);

                            int puntoReorden = Integer.parseInt(datos[6]);

                            String codigoSeccionPadre = datos[7];

                            Producto prod = new Producto(codProd,nomProd,precio,stock,stockMinimo,puntoReorden);

                            Seccion seccionDestino =supermercado.buscarSeccion(codigoSeccionPadre);
                            if (seccionDestino != null) {
                                seccionDestino.agregarProducto(prod);
                            } else {System.out.println("No existe la sección "+ codigoSeccionPadre+ " para el producto "+ codProd);}break;
                        default:
                            System.out.println("Tipo de registro desconocido: "+ tipoRegistro);
                            break;
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Error en los datos numéricos del registro: "+ linea);
                }
            }
        }

        return supermercado;
    }
    public static void guardarDatosCSV(
            Supermercado supermercado) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(
                Paths.get("datos_supermercado.csv"),
                StandardCharsets.UTF_8)) {

            // Encabezado
            writer.write("TipoRegistro,Codigo,Nombre,Precio,Stock,"+ "StockMinimo,PuntoReorden,CodigoSeccionPadre\n");
            for (Seccion sec : supermercado.getSecciones().values()) {
                writer.write("SECCION,"+ sec.getCodigo()+ ","+ sec.getNombre()+ ",,,,,\n");
                for (Producto prod :sec.getProductos().values()) {
                    String lineaProd ="PRODUCTO,"+ prod.getCodigo()+ ","+ prod.getNombre()+ ","+ prod.getPrecio()+ ","+ prod.getStock()+ ","+ prod.getStockMinimo()+ ","+ prod.getPuntoReOrden()+ ","+ sec.getCodigo();
                    writer.write(lineaProd + "\n");
                }
            }
        }
    }
}