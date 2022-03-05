/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Apuesta.Apuesta;
import Backend.Objetos.EDD.ListaEnlazada;
import Backend.Objetos.EDD.Nodo;

/**
 *
 * @author phily
 */
public class ManejadorOrdenamiento {
    public final int DESC_POR_MONTO = 0;
    public final int DESC_POR_NOMBRE = 1;    
    
    public ListaEnlazada<Apuesta> ordenar(int tipoOrden, ListaEnlazada<Apuesta> listadoApuestas){
        if(!listadoApuestas.isEmpty()){
            if(tipoOrden == DESC_POR_MONTO){
                orderByGains(listadoApuestas);            
            }else{
                orderByName(listadoApuestas);
            }
        }                
        return listadoApuestas;//con tal de no add otra línea después después del llamado a este método xD, puesto que no es nec hacer return ya que estoy trabajando por referencia xD
    }
    
    private void orderByName(ListaEnlazada<Apuesta> listadoApuestas){
        Nodo<Apuesta> anterior;
        Nodo<Apuesta> nodoSiguiente;    
        
        //Burbuja
         for (int rondaActual = 0; rondaActual < (listadoApuestas.getSize()-1); rondaActual++) {//puesto que solo se req n-1 vueltas para ordenar n elementos
             anterior = listadoApuestas.getFirst();
             
             while((nodoSiguiente = anterior.getNext()) != null){//esto garantiza que el nodo "anterior" llegue a la posición n-1 del listado, lo cual es lo que debe suceder... xD
                 if(anterior.getContent().getNombrePostor().compareTo(nodoSiguiente.getContent().getNombrePostor()) > 0){//es decir: ant < sig = TRUE
                     Apuesta temporal = anterior.getContent();
                     anterior.resetContent(nodoSiguiente.getContent());
                     nodoSiguiente.resetContent(temporal);
                 }
                 
                 anterior = nodoSiguiente;
             }
         }
    }//Servicio crítico 4.1 -> O(n) uwu menor de lo pedido xD xD
    
    private void orderByGains(ListaEnlazada<Apuesta> listadoApuestas){
        Nodo<Apuesta> anterior;
        Nodo<Apuesta> nodoSiguiente;    
        
        //Burbuja
         for (int rondaActual = 0; rondaActual < (listadoApuestas.getSize()-1); rondaActual++) {//puesto que solo se req n-1 vueltas para ordenar n elementos
             anterior = listadoApuestas.getFirst();
             
             while((nodoSiguiente = anterior.getNext()) != null){//esto garantiza que el nodo "anterior" llegue a la posición n-1 del listado, lo cual es lo que debe suceder... xD
                 if(anterior.getContent().getTotalGanado() < nodoSiguiente.getContent().getTotalGanado()){//es decir: ant < sig = TRUE
                     Apuesta temporal = anterior.getContent();
                     anterior.resetContent(nodoSiguiente.getContent());
                     nodoSiguiente.resetContent(temporal);
                 }
                 
                 anterior = nodoSiguiente;
             }
         }
    }//Servicio crítico 4.2 -> O(n) uwu xD xD
    
}
