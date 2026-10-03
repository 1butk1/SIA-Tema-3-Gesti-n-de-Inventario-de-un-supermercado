package supermercado;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Seccion {
    
    private String codigo;
    private String nombre;
    private HashMap<String, Producto> productos;
    
    public Seccion(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.productos = new HashMap<>();
    }

    public void agregarProducto(Producto p) {
        this.productos.put(p.getCodigo(), p);
    }

    public Producto buscarProducto(String codigoProducto) {
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

    public Map<String, Producto> getProductos() {
        return Collections.unmodifiableMap(this.productos);
    }
}