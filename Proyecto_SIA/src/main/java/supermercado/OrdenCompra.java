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
    private Proveedor proveedor;
    private ArrayList<DetalleOrdenCompra> detalles;
   //falta completar ordenCompra//
    public OrdenCompra(String idOrden, String fecha, Proveedor proveedor){
        this.idOrden = idOrden;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.detalles = new ArrayList<> ();
    }
    public void agregarDetalle(DetalleOrdenCompra detalle){
        if(detalle != null){
            this.detalles.add(detalle);
        }
    }
    public String generarReporte(){
        String reporte = "***** REPORTE DE ORDEN DE COMPRA *****\n";
        reporte += "ID Orden:"+idOrden+"\n";
        reporte += "Fecha:"+fecha+"\n";
        String nombreProv = (proveedor != null) ? proveedor.getRazonSocial() :"Sin proveedor";
        reporte += "Proveedor: "+nombreProv+"\n";
        reporte += "*** DETALLES ***\n";
        for(DetalleOrdenCompra d : detalles){
            Producto p = d.getProducto();
            String nombreProd = (p!=null) ? p.getNombre() : "Producto desconocido";
            reporte += "* Producto "+nombreProd+"| Cantidad:"+d.getCantidad()+"\n";
        }
        
        return reporte;
    }  
    public String getIdOrden(){ return idOrden;}
    public String getFecha(){ return fecha;}
    public Proveedor getProveedor(){return proveedor;}
    public ArrayList getDetalles(){return detalles;}
    
    @Override 
    public String toString(){
        return "OrdenCompra{" +
                "idOrden='" + idOrden + '\'' +
                ", fecha='" + fecha + '\'' +
                ", proveedor=" + (proveedor != null ? proveedor.getRazonSocial() : "null") +
                ", cantidadDetalles=" + detalles.size() +
                '}';
    }
}
