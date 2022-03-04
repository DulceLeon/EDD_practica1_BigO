/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.EDD;

/**
 *
 * @author phily
 * @param <E>
 */
public class Nodo<E> {
    private E contenido;    
    private Nodo<E> siguiente;
    private Nodo<E> ultimoNodo;
    
    /**
    *ctrctor para 1er elemento es decir, cabeza
    * @param elemento
    */
    public Nodo(E elemento){
          contenido = elemento;
          this.siguiente = null;        
    }
    
    public void resetContent(E contenidoNuevo){
        contenido = contenidoNuevo;                      
    }//será útil para la ordenación...

    public void setNext(E contenido){            
        siguiente = new Nodo(contenido);
    }      

    public E getContent(){//no será necesario el índice?? para hacer ref a uno específico y obtener sus respect datos??
        return contenido;
    }

    public Nodo<E> getNext(){//Aqupi estas refiriendote al nodo, mas no al objeto que dentro de él está contenido
        return siguiente;
    }              
}
