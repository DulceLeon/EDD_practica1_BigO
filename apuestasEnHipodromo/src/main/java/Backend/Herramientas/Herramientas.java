/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Herramientas;

/**
 *
 * @author phily
 */
public class Herramientas {
    
    public String[] eliminarEspaciosDeElementos(String[] arreglo){
        for(int indice = 0; indice < arreglo.length; indice++){
            arreglo[indice] = arreglo[indice].trim();
        }
        
        return arreglo;
    }
    
}
