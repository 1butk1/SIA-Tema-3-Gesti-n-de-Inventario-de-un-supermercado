package supermercado;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LectorCSV {

    public static Supermercado leerDatosCSV() throws IOException {
        Supermercado supermercado = new Supermercado("Supermercado Central", "76.123.456-7");
        File archivo = new File("datos_supermercado.csv");

        if (!archivo.exists()) {
            System.out.println(">> Aviso: 'datos_supermercado.csv' no encontrado. Se creará un supermercado vacío.");
            return supermercado;
        }

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {
            String linea;
            boolean primeraLinea = true;
            int numeroLinea = 0;

            while ((linea = br.readLine()) != null) {
                numeroLinea++;
                if (linea.trim().isEmpty()) {
                    continue;
                }
                if (primeraLinea) {
                    primeraLinea = false;
                    continue;
                }

                String[] datos = linea.split(",", -1);
                if (datos.length < 3) {
                    continue;
                }

                String tipoRegistro = datos[0].trim();

                try {
                    if (tipoRegistro.equalsIgnoreCase("SECCION")) {
                        String codSec = datos[1].trim();
                        String nomSec = datos[2].trim();

                        if (supermercado.buscarSeccion(codSec) == null) {
                            supermercado.agregarSeccion(new Seccion(codSec, nomSec));
                        }
                    } else if (tipoRegistro.equalsIgnoreCase("PRODUCTO")) {
                        if (datos.length < 8) {
                            System.out.println(">> Advertencia: Faltan columnas en la línea " + numeroLinea);
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
                        } else {
                            System.out.println(">> Error línea " + numeroLinea + ": La sección '" + codigoSeccionPadre + "' no existe. Producto '" + nomProd + "' ignorado.");
                        }
                    }
                } catch (NumberFormatException e) {
                    System.out.println(">> Error de formato numérico en la línea " + numeroLinea + ". Revise que no haya letras en precios o stock.");
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