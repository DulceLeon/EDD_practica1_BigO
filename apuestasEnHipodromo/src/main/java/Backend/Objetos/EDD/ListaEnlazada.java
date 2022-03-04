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
public class ListaEnlazada<E> {
      private Nodo<E> primerNodo;
      private Nodo<E> ultimoNodo;
      private int size;
    
    public ListaEnlazada(){
        clear();
    }
    
    private boolean inicializarLista(E contenido){
        if(size == 0){
            primerNodo = ultimoNodo = new Nodo<>(contenido);           
            return true;
        }        
        return false;
    }
        
    public void add(E contenido){//agrega al final de la lista
        if(!inicializarLista(contenido)){                   
            ultimoNodo.setNext(contenido);
            ultimoNodo = ultimoNodo.getNext();            
        }        
        size++;
    }
    
    public void clear(){
        primerNodo = ultimoNodo = null;        
        size = 0;      
    }
      
    public Nodo<E> removeFirstElement(){
        Nodo<E> nodoAuxiliar = primerNodo;
        primerNodo = primerNodo.getNext();
        size--;
        
        return nodoAuxiliar;
    }
    
    public Nodo<E> getFirst(){
        return primerNodo;
    }
    
    public Nodo<E> getLast(){
        return ultimoNodo;
    }
    
    public boolean isEmpty(){
        return (size==0);
    }      
    
    public int getSize(){
        return size;
    }
}
