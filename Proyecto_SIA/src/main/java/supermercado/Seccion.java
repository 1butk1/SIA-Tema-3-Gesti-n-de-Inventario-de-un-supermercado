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


public class Seccion {
    
    private String codigo;
    private String nombre;
    
    private HashMap<String, Producto> productos;
    
    public Seccion(String codigo, String nombre){
        this.codigo = codigo;
        this.nombre = nombre;
        this.productos = new HashMap<>();
    }
  
    public void agregarProducto(Producto p){
        this.productos.put(p.getCodigo(), p);
    }
    public Producto buscarProducto(String codigoProducto){
        return this.productos.get(codigoProducto);
    }
    
    
    public String getCodigo() {
        return codigo;
    }
    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public HashMap<String, Producto> getProductos() {
        return productos;
    }
    
    
}
