/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermercado;
import java.util.HashMap;
/**
 *
 * @author ignac
 */
public class Venta {
    
    private String idVenta;
    private String fecha;
    private HashMap<String, Integer> productosVendidos;
    
    public Venta(String idVenta, String fecha){
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.productosVendidos = new HashMap<>();
    }
    public void AgregarProductos(Producto p, int cantidad){
        if(p == null || cantidad <=  0){return;}
        String codigo = p.getCodigo();
        int cantidadActual = productosVendidos.getOrDefault(codigo, 0);
        productosVendidos.put(codigo,cantidadActual + cantidad);        
    }
    public double calcularTotal(HashMap<String, Producto> inventario){
        double total = 0.0;
        for(String codigo : productosVendidos.keySet()){
            Producto prod = inventario.get(codigo);
            if(prod != null){
                int cantidad = productosVendidos.get(codigo);
                total += prod.getPrecio() * cantidad;
            }
        }
        return total;
    }
    public boolean procesarVenta(HashMap<String, Producto> inventario){
        for(String codigo : productosVendidos.keySet()){
            Producto prod = inventario.get(codigo);
            int cantidadRequerida = productosVendidos.get(codigo);
            if(prod == null || prod.getStock() < cantidadRequerida){
                return false;
            }
        }
            for(String codigo : productosVendidos.keySet()){
                Producto prod = inventario.get(codigo);
                int cantidad = productosVendidos.get(codigo);
                prod.descontarStock(cantidad);
            }
            return true;
        }
    public String getIdVenta(){return idVenta;}
    public String getFecha(){return fecha;}
    public HashMap<String,Integer> getProductos(){return productosVendidos;}
    
}
