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
    //falta AgregarProductos
    //falta calcularTotal
    //falta procesarVenta
    public String getIdVenta(){return idVenta;}
    public String getFecha(){return fecha;}
    public HashMap<String,Integer> getProductos(){return productosVendidos;}
    
}
