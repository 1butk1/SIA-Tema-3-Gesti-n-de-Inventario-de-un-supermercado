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
public class Producto {
    
   private String codigo;
   private String nombre;
   private double precio;
   private int stock;
   private int stockMinimo;
   
   public Producto(String codigo, String nombre, double precio, int sotck, int stockMinimo){
       this.codigo = codigo;
       this.nombre = nombre;
       this.precio = precio;
       this.stock = stock;
       this.stockMinimo = stockMinimo;
   }
    
   public void descontarStock(int cantidad){
       this.stock -= cantidad;
   }
   
   public boolean requiereRebastecimiento(){
       return this.stock <= this.stockMinimo;
   }
   public void aumentarStock(int cantidad){
       this.stock += cantidad;
   }
   public void aumentarStock(int cantidad, String motivo){
       this.stock += cantidad;
       System.out.println("Stock de " + this.nombre + "aumentado en " + cantidad +". Motivo : " + motivo);
   }
   
   
   public String toString(){
       return "Producto [" + codigo + "] - " + nombre + " | Precio: $" + precio + " | Stock: " + stock;
   }
   
   public String getNombre(){return nombre;}
   public void setNombre(String nombre){ this.nombre = nombre;}
   public double getPrecio() { return precio; }
   public void setPrecio(double precio){this.precio = precio;}
   public int getStock(){return stock; }
   public void setStock(int stock){ this.stock = stock; }
   public int getStockMinimo(){return stock;}
   public void setStockMinimo(int stockMinimo){this.stockMinimo = stockMinimo;}
   public String getCodigo() { return codigo; }
}
   
   

