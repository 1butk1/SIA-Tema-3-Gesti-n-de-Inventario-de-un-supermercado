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
public class Proveedor {
    private  String rut;
    private  String razonSocial;
    private  String telefono;
    
    public Proveedor(String rut, String razonSocial, String telefono){
        this.rut = rut;
        this.razonSocial = razonSocial;
        this.telefono = telefono;
    }
    
    public String getRut(){ return rut;}
    public void setRut(String rut){
        this.rut = rut;
    }
    public String getRazonSocial(){return razonSocial;}
    public void setRazonSocial(String razonSocial){
        this.razonSocial = razonSocial;
    }
    public String getTelefono(){return telefono;}
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    
}
