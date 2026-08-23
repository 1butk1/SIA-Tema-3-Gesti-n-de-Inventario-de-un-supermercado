/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package supermercado;
import java.util.ArrayList;
/**
 *
 * @author ignac
 */
public class OrdenCompra {
    private String idOrden;
    private String fecha;
    Proveedor proveedor;
    private ArrayList<DetalleOrdenCompra> detalles;
   //falta completar ordenCompra//
    public OrdenCompra(String idOrden, String fecha, Proveedor proveedor){
        this.idOrden = idOrden;
        this.fecha = fecha;
    }
    // falta agregarDetalle//
    //falta generarReporte//
  
    public String getIdOrden(){ return idOrden;}
    public String getFecha(){ return fecha;}
    public Proveedor getProveedor(){return proveedor;}
    public ArrayList getDetalles(){return detalles;}
    
    
    // falta toString//
    
    
}
