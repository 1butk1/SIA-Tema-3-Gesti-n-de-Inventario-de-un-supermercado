/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermercado;

/**
 *
 * @author ignac
 */

import java.util.HashMap;

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
    
    public Producto buscarProducto(String codigoProducto) {
        for (Seccion s : secciones.values()) {
            Producto p = s.buscarProducto(codigoProducto);
            if (p != null) {
                return p;
            }
        }
        return null; 
    }
    
    public Producto buscarProducto(String nombreProducto, String codigoSeccion) {
        Seccion s = this.buscarSeccion(codigoSeccion);
        if (s != null) {
            for (Producto p : s.getProductos().values()) {
                if (p.getNombre().equalsIgnoreCase(nombreProducto)) {
                    return p;
                }
            }
        }
        return null; 
    }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public HashMap<String, Seccion> getSecciones() { return secciones; }
}
