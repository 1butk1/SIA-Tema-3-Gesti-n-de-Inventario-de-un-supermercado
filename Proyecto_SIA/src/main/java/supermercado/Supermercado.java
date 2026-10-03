/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermercado;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.time.LocalDate;
import Excepciones.ProductoNoEncontradoException;

/**
 *
 * @author ignac
 */
public class Supermercado {
    
    private String nombre;
    private String rut;
    
    private HashMap<String, Seccion> secciones;
    
    public Supermercado(String nombre, String rut) {
        this.nombre = nombre;
        this.rut = rut;
        this.secciones = new HashMap<>(); 
    }
    
    public void agregarSeccion(Seccion s) {
        this.secciones.put(s.getCodigo(), s);
    }

    public Seccion buscarSeccion(String codigo) {
        return this.secciones.get(codigo);
    }

    public Producto buscarProducto(String codigoProducto) throws ProductoNoEncontradoException {
        for (Seccion s : secciones.values()) {
            Producto p = s.buscarProducto(codigoProducto);
            if (p != null) {
                return p;
            }
        }
        throw new ProductoNoEncontradoException("No se encontró el producto con código: " + codigoProducto);
    }
    
    public void eliminarProducto(String codigoProducto) throws ProductoNoEncontradoException {
        for (Seccion s : secciones.values()) {
            if (s.buscarProducto(codigoProducto) != null) {
                s.eliminarProducto(codigoProducto);
                return;
            }
        }
        throw new ProductoNoEncontradoException("No se encontró el producto con código: " + codigoProducto);
    }

    public boolean eliminarSeccion(String codigo) {
        return this.secciones.remove(codigo) != null;
    } 
    
    public OrdenCompra verificarStockYGenerarOrdenes(Proveedor proveedor) {

        String idOrden = "OC-" + System.currentTimeMillis();
        String fecha = LocalDate.now().toString();

        OrdenCompra orden = new OrdenCompra(idOrden, fecha, proveedor);

        boolean hayProductos = false;

        for (Seccion s : secciones.values()) {

            for (Producto p : s.getProductos().values()) {

                if (p.getStock() <= p.getPuntoReOrden()) {

                    int cantidad = p.getStockMinimo() - p.getStock();

                    if (cantidad > 0) {

                        DetalleOrdenCompra detalle =
                                new DetalleOrdenCompra(p, cantidad);

                        orden.agregarDetalle(detalle);
                        hayProductos = true;
                    }
                }
            }
        }

        if (!hayProductos) {
            return null;
        }

        return orden;
    }

    public String generarReportesOrdenesPorSeccion(String codigoSeccion) {
        Seccion s = buscarSeccion(codigoSeccion);
        if (s == null) { return "!ERROR : SECCION NO ENCONTRADA"; }
        String reporte = "**** REPORTE DE ORDENES POR SECCION ****\n";
        reporte += "SECCION : " + s.getNombre() + " CODIGO: " + s.getCodigo() + "\n";
        
        boolean hayProductos = false;
        for (Producto p : s.getProductos().values()) {
            if (p.requiereRebastecimiento()) {
                reporte += "* Produco:" + p.getNombre() + "|Stock actual: " + p.getStock() + "|Stock Minimo: " + p.getStockMinimo() + "\n";
                hayProductos = true;
            }
        }
        if (!hayProductos) {
            reporte += "*** Todos los productos de esta seccion tienen stock ***\n";
        }
        return reporte;
    }

    public String generarReporteOrdenesPorProveedor(String rutProveedor) {
        String reporte = "**** REPORTE DE ORDENES POR PROVEEDOR ****\n";
        reporte += "Rut proveedor: " + rutProveedor + "\n\n";
        
        reporte += "Consulta realizada para el proveedor" + rutProveedor + "\n";
        return reporte;
    }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public Map<String, Seccion> getSecciones() { return Collections.unmodifiableMap(this.secciones); }
}