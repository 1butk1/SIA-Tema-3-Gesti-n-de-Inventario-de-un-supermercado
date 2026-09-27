package supermercado;

import Excepciones.StockInsuficienteException;
import java.util.HashMap;

public class Venta {
    
    private String idVenta;
    private String fecha;
    private HashMap<String, Integer> productosVendidos;
    
    public Venta(String idVenta, String fecha) {
        this.idVenta = idVenta;
        this.fecha = fecha;
        this.productosVendidos = new HashMap<>();
    }
    
    public void agregarProducto(Producto p) {
        if (p == null) {
            return;
        }
        String codigo = p.getCodigo();
        int cantidadActual = productosVendidos.getOrDefault(codigo, 0);
        productosVendidos.put(codigo, cantidadActual + 1);        
    }
    
    public void agregarProducto(Producto p, int cantidad) {
        if (p == null || cantidad <= 0) {
            return;
        }
        String codigo = p.getCodigo();
        int cantidadActual = productosVendidos.getOrDefault(codigo, 0);
        productosVendidos.put(codigo, cantidadActual + cantidad);        
    }
    
    public double calcularTotal(HashMap<String, Producto> inventario) {
        double total = 0.0;
        for (String codigo : productosVendidos.keySet()) {
            Producto prod = inventario.get(codigo);
            if (prod != null) {
                int cantidad = productosVendidos.get(codigo);
                total += prod.getPrecio() * cantidad;
            }
        }
        return total;
    }
    
    public boolean procesarVenta(HashMap<String, Producto> inventario) throws StockInsuficienteException {
        for (String codigo : productosVendidos.keySet()) {
            Producto prod = inventario.get(codigo);
            int cantidadRequerida = productosVendidos.get(codigo);
            if (prod == null) {
                return false;
            }
            if (prod.getStock() < cantidadRequerida) {
                throw new StockInsuficienteException("Stock insuficiente para: " + prod.getNombre() 
                        + " (Solicitado: " + cantidadRequerida + ", Disponible: " + prod.getStock() + ")");
            }
        }
        for (String codigo : productosVendidos.keySet()) {
            Producto prod = inventario.get(codigo);
            int cantidad = productosVendidos.get(codigo);
            prod.descontarStock(cantidad);
        }
        return true;
    }
    
    public String getIdVenta() { return idVenta; }
    public void setIdVenta(String idVenta) { this.idVenta = idVenta; }
    public String getFecha() { return fecha; }
    public void setFecha(String fecha) { this.fecha = fecha; }
    public HashMap<String, Integer> getProductosVendidos() { return productosVendidos; }
    public void setProductosVendidos(HashMap<String, Integer> productosVendidos) { this.productosVendidos = productosVendidos; }
}