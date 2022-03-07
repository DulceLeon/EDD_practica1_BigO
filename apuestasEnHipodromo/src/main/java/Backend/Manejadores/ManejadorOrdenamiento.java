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
    
    private ProcessManager processManager;
    
    public ManejadorOrdenamiento(){
        this.processManager = new ProcessManager();
    }
    
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
        this.processManager.setTiempoInicial();
        
        Nodo<Apuesta> anterior;
        this.processManager.incrementarPasosDeApuesta();
        Nodo<Apuesta> nodoSiguiente;    
        this.processManager.incrementarPasosDeApuesta();
        
        //Burbuja
         for (int rondaActual = 0; rondaActual < (listadoApuestas.getSize()-1); rondaActual++) {//puesto que solo se req n-1 vueltas para ordenar n elementos
             anterior = listadoApuestas.getFirst();
             this.processManager.incrementarPasosDeApuesta();
             
             while((nodoSiguiente = anterior.getNext()) != null){//esto garantiza que el nodo "anterior" llegue a la posición n-1 del listado, lo cual es lo que debe suceder... xD
                 if(anterior.getContent().getNombrePostor().compareTo(nodoSiguiente.getContent().getNombrePostor()) > 0){//es decir: ant < sig = TRUE
                     this.processManager.incrementarPasosDeApuesta();
                     
                     Apuesta temporal = anterior.getContent();
                     this.processManager.incrementarPasosDeApuesta();
                     
                     anterior.resetContent(nodoSiguiente.getContent());
                     this.processManager.incrementarPasosDeApuesta();
                     
                     nodoSiguiente.resetContent(temporal);
                     this.processManager.incrementarPasosDeApuesta();
                 }
                 
                 anterior = nodoSiguiente;
                 this.processManager.incrementarPasosDeApuesta();
             }           
             finalizarSubprocesoAnalisis();
         }
         
         this.mostrarAnalisisComplejidada("por Nombre", listadoApuestas.getSize());
    }//Servicio crítico 4.1 -> O(n) uwu menor de lo pedido xD xD
       
    private void orderByGains(ListaEnlazada<Apuesta> listadoApuestas){
        this.processManager.setTiempoInicial();
        
        Nodo<Apuesta> anterior;
        this.processManager.incrementarPasosDeApuesta();
        Nodo<Apuesta> nodoSiguiente;    
        this.processManager.incrementarPasosDeApuesta();
        
        //Burbuja
         for (int rondaActual = 0; rondaActual < (listadoApuestas.getSize()-1); rondaActual++) {//puesto que solo se req n-1 vueltas para ordenar n elementos
             anterior = listadoApuestas.getFirst();
             this.processManager.incrementarPasosDeApuesta();
             
             while((nodoSiguiente = anterior.getNext()) != null){//esto garantiza que el nodo "anterior" llegue a la posición n-1 del listado, lo cual es lo que debe suceder... xD
                 if(anterior.getContent().getTotalGanado() < nodoSiguiente.getContent().getTotalGanado()){//es decir: ant < sig = TRUE
                     this.processManager.incrementarPasosDeApuesta();
                     
                     Apuesta temporal = anterior.getContent();
                     this.processManager.incrementarPasosDeApuesta();
                     
                     anterior.resetContent(nodoSiguiente.getContent());
                     this.processManager.incrementarPasosDeApuesta();
                     
                     nodoSiguiente.resetContent(temporal);
                     this.processManager.incrementarPasosDeApuesta();
                 }
                 
                 anterior = nodoSiguiente;
                 this.processManager.incrementarPasosDeApuesta();
             }
             finalizarSubprocesoAnalisis();
         }
         
         this.mostrarAnalisisComplejidada("por Total Ganado", listadoApuestas.getSize());
    }//Servicio crítico 4.2 -> O(n) uwu xD xD
    
     private void finalizarSubprocesoAnalisis(){
        this.processManager.setTiempoFinal();
        this.processManager.addTiempoParcial();             
             
        this.processManager.addPasosTotalesDeApuesta();
       
        //este sería por el siguiente ciclo
        this.processManager.setTiempoInicial();
        this.processManager.inicializarPasosApuesta();
    }
    
    private void mostrarAnalisisComplejidada(String tipoBusqueda, int numeroApuestas){
        System.out.println("\nANÁLISIS DE COMPLEJIDAD");
        System.out.println("> método de ordenamiento "+ tipoBusqueda);
        if(numeroApuestas > 0){
            System.out.println("Resultados de tiempo");
            System.out.println("Tiempo primedio: "+ this.processManager.getTiempoPromedioTotal(numeroApuestas)+ "\n");
            System.out.println("Resultados de pasos");
            System.out.println("Mayor cantidad de pasos: "+ this.processManager.getMayorNumeroPasos());
            System.out.println("Menor cantidad de pasos: "+ this.processManager.getMenorNumeroPasos());
            System.out.println("Cantidad de pasos promedio: "+ this.processManager.getTiempoPromedioTotal(numeroApuestas));
        }else{
            System.out.println("No hay apuestas que ordenar");
        }        
    }
    
}
