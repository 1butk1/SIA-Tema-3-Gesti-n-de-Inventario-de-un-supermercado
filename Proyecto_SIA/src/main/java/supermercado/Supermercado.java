/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermercado;
import java.util.HashMap;
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
    public Producto buscarProducto(String codigoProducto)throws ProductoNoEncontradoException {
    for (Seccion s : secciones.values()) {
        Producto p = s.buscarProducto(codigoProducto);
        if (p != null) {
            return p;
        }
    }
    throw new ProductoNoEncontradoException("No se encontró el producto con código: " + codigoProducto);
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
    public boolean eliminarSeccion(String codigo){
        return this.secciones.remove(codigo) != null;
    } 
    
    public void verificarStockYGenerarOrdenes(){
        for(Seccion s : secciones.values()){
            for(Producto p : s.getProductos().values()){
                if(p.getStock() <= p.getPuntoReOrden()){
                    System.out.println("!ALERTA : El producto "+ p.getNombre()+"requiere rebastecimiento. Stock Actual: "+p.getStock()+"\n");
                }
            }
        }
    }
    public String generarReportesOrdenesPorSeccion(String codigoSeccion){
        Seccion s = buscarSeccion(codigoSeccion);
        if(s == null){return "!ERROR : SECCION NO ENCONTRADA";}
        String reporte = "**** REPORTE DE ORDENES POR SECCION ****\n";
        reporte += "SECCION : "+s.getNombre()+" CODIGO: "+ s.getCodigo()+"\n";
        
        boolean hayProductos = false;
        for(Producto p : s.getProductos().values()){
            if(p.requiereRebastecimiento()){
                reporte+= "* Produco:"+p.getNombre()+"|Stock actual: "+ p.getStock()+"|Stock Minimo: "+p.getStockMinimo()+ "\n";
                hayProductos = true;
            }
        }
        if(!hayProductos){
        reporte += "*** Todos los productos de esta seccion tienen stock ***\n";
        }
        return reporte;
    }
    public String generarReporteOrdenesPorProveedor(String rutProveedor){
        String reporte = "**** REPORTE DE ORDENES POR PROVEEDOR ****\n";
        reporte += "Rut proveedor: "+ rutProveedor+"\n\n";
        
        reporte += "Consulta realizada para el proveedor"+ rutProveedor+"\n";
        return reporte;
    }

  
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getRut() { return rut; }
    public void setRut(String rut) { this.rut = rut; }
    public HashMap<String, Seccion> getSecciones() { return secciones; }
}
