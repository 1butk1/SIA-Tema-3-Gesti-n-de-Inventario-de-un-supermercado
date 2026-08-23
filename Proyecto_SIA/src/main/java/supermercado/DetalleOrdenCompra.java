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
public class DetalleOrdenCompra {
    private Producto producto;
    private int cantidad;
    
    public DetalleOrdenCompra(Producto producto, int cantidad){
        this.producto = producto;
        this.cantidad = cantidad;
    }
    
    //falta detalleOrdenCompra//
    
    
    public Producto getProducto(){return producto;}
    public void setProducto(Producto producto){
        this.producto = producto;
    }
    public int getCantidad(){return cantidad;}
    public void setCantidad(int cantidad){
        this.cantidad = cantidad;
    }
   
}
