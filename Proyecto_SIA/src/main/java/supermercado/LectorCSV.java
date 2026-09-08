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
        Supermercado supermercado = new Supermercado("Supermercado Central", "76.123.456-7");
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("datos_supermercado.csv"), StandardCharsets.UTF_8))) {
            String linea;
            boolean primeraLinea = true;
            while ((linea = br.readLine()) != null){
             if (linea.trim().isEmpty()) {
                        continue;
                    }
              if (primeraLinea){
                  primeraLinea = false;
                  continue;
              }
                String[] datos = linea.split(",", -1);
                if (datos.length < 3) {
                    continue;
                }
                String tipoRegistro = datos[0].trim();
                if (tipoRegistro.equals("SECCION")) {
                    String codSec = datos[1].trim();
                    String nomSec = datos[2].trim();
                    
                    if (supermercado.buscarSeccion(codSec) == null) {
                        Seccion nuevaSeccion = new Seccion(codSec, nomSec);
                        supermercado.agregarSeccion(nuevaSeccion);
                    }
                } else if (tipoRegistro.equals("PRODUCTO")) {
                    if (datos.length < 8) {
                        continue;
                    }

                    String codProd = datos[1].trim();
                    String nomProd = datos[2].trim();
                    double precio = Double.parseDouble(datos[3].trim());
                    int stock = Integer.parseInt(datos[4].trim());
                    int stockMinimo = Integer.parseInt(datos[5].trim());
                    int puntoReorden = Integer.parseInt(datos[6].trim());
                    String codigoSeccionPadre = datos[7].trim();

                    Producto prod = new Producto(codProd, nomProd, precio, stock, stockMinimo, puntoReorden);
                    Seccion seccionDestino = supermercado.buscarSeccion(codigoSeccionPadre);
                   
                    if (seccionDestino != null) {
                        seccionDestino.agregarProducto(prod);
                    }
                }
            }
        }
        return supermercado;
    }
    public static void guardarDatosCSV(Supermercado supermercado) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("datos_supermercado.csv"), StandardCharsets.UTF_8)) {
            writer.write("TipoRegistro,Codigo,Nombre,Precio,Stock,StockMinimo,PuntoReorden,CodigoSeccionPadre\n");
            for (Seccion sec : supermercado.getSecciones().values()) {
                writer.write("SECCION," + sec.getCodigo() + "," + sec.getNombre() + ",,,,,\n");
                for (Producto prod : sec.getProductos().values()) {
                    String lineaProd = "PRODUCTO," + prod.getCodigo() + "," + prod.getNombre() + "," + prod.getPrecio() + "," + prod.getStock() + "," + prod.getStockMinimo() + "," + prod.getPuntoReOrden() + "," + sec.getCodigo();
                    writer.write(lineaProd + "\n");
                }
            }
        }
    }
}