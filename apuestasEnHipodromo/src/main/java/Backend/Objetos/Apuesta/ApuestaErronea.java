/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Apuesta;

import Backend.Objetos.Apuesta.Apuesta;
import Backend.Objetos.EDD.ListaEnlazada;
import Backend.Objetos.Error;
import Backend.Objetos.Error;

/**
 *
 * @author phily
 */
public class ApuestaErronea  extends Apuesta{
    private ListaEnlazada<Error> errores;

    public ApuestaErronea(String nombrePostor, String montoApostado, ListaEnlazada<Error> errores) {
        super(nombrePostor, montoApostado, null);
        
        this.errores = errores;
    }
    
    public ListaEnlazada<Error> getErrores(){
        return errores;
    }
    
    
}
