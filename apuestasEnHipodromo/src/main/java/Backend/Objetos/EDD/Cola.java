/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.EDD;

/**
 *
 * @author phily
 * @param <T>
 */
public class Cola<T> {
    private Object[] cola;
    private int tamañoBase = 1000;
    private int indiceInsercionActual = 0;
    private int ubicacionTurnoActual = -1;
    
    public Cola(){
        this.cola = new Object[1000];        
    }
    
    public void add(Object elementoEntrante){
       if(indiceInsercionActual<cola.length){
           cola[indiceInsercionActual] = elementoEntrante;
       }else{
           agrandarLimiteCola();
       }        
    }
    
    private void agrandarLimiteCola(){
        Object[] arregloAuxiliar = cola;        
        cola = new Object[(int) (cola.length + (tamañoBase*0.75))];
        System.arraycopy(arregloAuxiliar, 0, cola, 0, arregloAuxiliar.length);
        
        //se redimensiona el arreglo de la cola
        //debo tratar que sea coonstne esto... siino el método de add no será O(1)...
    }
    
    public T getElemento(){
        ubicacionTurnoActual++;
        
        return (T) cola[ubicacionTurnoActual];
    }   
    
    public boolean existenElementosEnCola(){
        return (ubicacionTurnoActual < (indiceInsercionActual-1));
    }
    
}

//en dado caso decidieras mejor usar una listaEnlazada, el método para obtener sería getAndRemove, porque de esa manera así siempre solo tendría que hacer referencia al primer nodo, y como eliminar un primer nodo no requiere ciclos, la complejidad sería un O(1)
//en caso de usar la Cola, lo que tendrías que hacer es tener un índice de obtención y uno para agregar, sabrías que ya no puedes obtener más si indiceObtención == indiceAdd -1 [puesto que este indice de add tiene el número referente a la ubicación vacía que puede recibir algo...

