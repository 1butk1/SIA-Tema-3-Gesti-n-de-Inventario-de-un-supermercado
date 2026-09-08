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
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("datos_supermercado.csv"), StandardCharsets.UTF_8));
        String linea;
        boolean primeraLinea = true;
        while ((linea = br.readLine()) != null) {
            if (linea.trim().isEmpty()) {
                continue;
            }
            if (primeraLinea) {
                primeraLinea = false;
                continue;
            }
            String[] datos = linea.split(",", -1);
            String tipoRegistro = datos[0]; 

            switch (tipoRegistro) {
                case "SECCION":
                    String codSec = datos[1];
                    String nomSec = datos[2];
                    if (supermercado.buscarSeccion(codSec) == null) {
                        supermercado.agregarSeccion(new Seccion(codSec, nomSec));
                    }
                    break;
                case "PRODUCTO":
                    String codProd = datos[1];
                    String nomProd = datos[2];
                    double precio = Double.parseDouble(datos[3]);
                    int stock = Integer.parseInt(datos[4]);
                    int stockMinimo = Integer.parseInt(datos[5]);
                    int puntoReorden = Integer.parseInt(datos[6]);
                    String codigoSeccionPadre = datos[7];
                    Producto prod = new Producto(codProd, nomProd, precio, stock, stockMinimo, puntoReorden);
                    Seccion seccionDestino = supermercado.buscarSeccion(codigoSeccionPadre);
                    if (seccionDestino != null) {
                        seccionDestino.agregarProducto(prod);
                    }
                    break;
            }
        }
        br.close();
        return supermercado;
    }
    public static void guardarDatosCSV(Supermercado supermercado) throws IOException {
        BufferedWriter writer = Files.newBufferedWriter(Paths.get("datos_supermercado.csv"), StandardCharsets.UTF_8);
        writer.write("TipoRegistro,Codigo,Nombre,Precio,Stock,StockMinimo,PuntoReorden,CodigoSeccionPadre\n");

        for (Seccion sec : supermercado.getSecciones().values()) {
            writer.write("SECCION," + sec.getCodigo() + "," + sec.getNombre() + ",,,,\n");
     
            for (Producto prod : sec.getProductos().values()) {
                String lineaProd = "PRODUCTO," +
                        prod.getCodigo() + "," +
                        prod.getNombre() + "," +
                        prod.getPrecio() + "," +
                        prod.getStock() + "," +
                        prod.getStockMinimo() + "," +
                        prod.getPuntoReOrden() + "," +
                        sec.getCodigo();
                writer.write(lineaProd + "\n");
            }
        }

        writer.close();
    }
}
