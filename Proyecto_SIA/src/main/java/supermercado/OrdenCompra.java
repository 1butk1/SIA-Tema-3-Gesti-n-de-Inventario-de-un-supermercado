package supermercado;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa una orden de compra realizada a un proveedor para el reabastecimiento
 * de productos en el supermercado.
 */
public class OrdenCompra {
    private String idOrden;
    private String fecha;
    private Proveedor proveedor;
    private ArrayList<DetalleOrdenCompra> detalles;

    /**
     * Constructor principal de la orden de compra.
     * 
     * @param idOrden   Identificador único de la orden.
     * @param fecha     Fecha de emisión de la orden.
     * @param proveedor Proveedor al cual se solicita la compra.
     */
    public OrdenCompra(String idOrden, String fecha, Proveedor proveedor) {
        this.idOrden = idOrden;
        this.fecha = fecha;
        this.proveedor = proveedor;
        this.detalles = new ArrayList<>();
    }

    /**
     * Agrega un detalle individual a la orden de compra.
     * 
     * @param detalle Objeto DetalleOrdenCompra a agregar.
     */
    public void agregarDetalle(DetalleOrdenCompra detalle) {
        if (detalle != null) {
            this.detalles.add(detalle);
        }
    }

    /**
     * Genera un reporte formateado en cadena de texto con la información general
     * de la orden y el desglose de sus productos solicitados.
     * 
     * @return Cadena con el reporte estructurado de la orden.
     */
    public String generarReporte() {
        String reporte = "** REPORTE DE ORDEN DE COMPRA **\n";
        reporte += "ID Orden: " + idOrden + "\n";
        reporte += "Fecha: " + fecha + "\n";
        String nombreProv = (proveedor != null) ? proveedor.getRazonSocial() : "Sin proveedor";
        reporte += "Proveedor: " + nombreProv + "\n";
        reporte += "* DETALLES *\n";
        for (DetalleOrdenCompra d : detalles) {
            Producto p = d.getProducto();
            String nombreProd = (p != null) ? p.getNombre() : "Producto desconocido";
            reporte += "* Producto " + nombreProd + " | Cantidad: " + d.getCantidad() + "\n";
        }
        return reporte;
    }

    // Getters y Setters
    public String getIdOrden() { 
        return idOrden; 
    }

    public String getFecha() { 
        return fecha; 
    }

    public Proveedor getProveedor() { 
        return proveedor; 
    }

    /**
     * Obtiene una vista no modificable de la lista de detalles.
     * Protege el encapsulamiento evitando modificaciones externas no autorizadas.
     * 
     * @return Lista no modificable de detalles de la orden.
     */
    public List<DetalleOrdenCompra> getDetalles() {
        return Collections.unmodifiableList(this.detalles);
    }

    public void setIdOrden(String idOrden) { 
        this.idOrden = idOrden; 
    }

    public void setFecha(String fecha) { 
        this.fecha = fecha; 
    }

    public void setProveedor(Proveedor proveedor) { 
        this.proveedor = proveedor; 
    }

    public void setDetalles(ArrayList<DetalleOrdenCompra> detalles) { 
        this.detalles = detalles; 
    }

    @Override
    public String toString() {
        return "OrdenCompra{" +
               "idOrden='" + idOrden + '\'' +
               ", fecha='" + fecha + '\'' +
               ", proveedor=" + (proveedor != null ? proveedor.getRazonSocial() : "null") +
               ", cantidadDetalles=" + detalles.size() +
               '}';
    }
}