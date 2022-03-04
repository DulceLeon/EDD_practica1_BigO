/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos;

/**
 *
 * @author phily
 */
public class Error {
    private final String tipo;
    private final String error;
    private final String descripcion;
    
    public Error(String tipo, String error, String descripcion){
        this.tipo = tipo;
        this.error = error;
        this.descripcion = descripcion;
    }
    
    public String getTIpo(){
         return this.tipo;
    }
    
    public String getElError(){
        return this.error;
    }
    
    public String getDescripcion(){
        return this.descripcion;
    }
}
