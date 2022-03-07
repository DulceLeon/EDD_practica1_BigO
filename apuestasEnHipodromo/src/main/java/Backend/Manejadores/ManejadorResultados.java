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
 */                                                        //será instanciada este clase en el Task Manager, para que así pueda ser enviada al manejadorCarrera como se hace con el ManejadorInterfaz...
public class ManejadorResultados {//su método debe ser invocado justo después de postear los Resultados en la tabla de posiciones, es decir debe invocarse una línea abajo de ese método xD
    private final int maximoMonto = 10;
    private ListaEnlazada<Apuesta> apuestasAceptadas;//la inicializo para no tener que colocar en la condición del while que sea != null, puesto que aumentaría la complejidad y el trabajo de ese bucle por una situación que super remotanmente podría suceder [que la verificación del archivo no termine a tiempo y por lo tanto esta clase no reciba el listado de apuestasAceptadas...
    private ProcessManager processManager;
    
    private ManejadorOrdenamiento manejadorOrdenamiento;

    public ManejadorResultados(ManejadorOrdenamiento manejadorOrdenamiento) {
        this.apuestasAceptadas = new ListaEnlazada<>();
        this.processManager = new ProcessManager();
        
        this.manejadorOrdenamiento = manejadorOrdenamiento;
    }
    
    public void setApuestasAceptadas(ListaEnlazada<Apuesta> apuestasAceptadas){
        this.apuestasAceptadas = apuestasAceptadas;
    }
    
    public void calcularResultadosApuestas(String[] posicionesFinales){//se recibirá el arreglo rsultado que genera el manejador de carrera... aclaro que no hay forma de que este arreglo sea nulo...
        if(!this.apuestasAceptadas.isEmpty()){
           this.processManager.setTiempoInicial();
           this.processManager.incrementarPasosDeApuesta();
                       
          Nodo<Apuesta> nodoApuesta = apuestasAceptadas.getFirst();
          this.processManager.incrementarPasosDeApuesta();
          
          do{//puesto que se eliminará y add al final porque requiero que estos datos existan, vamos a hacer incrementar esta variable, bueno aunque lo que podría hacer es usar una lista auxiliar, para que no tenga necesidad de tener que recuperarla, puesto que la original no cambiaría...
                int apuestaActual = 0;
                this.processManager.incrementarPasosDeApuesta();
                
                Apuesta apuesta = nodoApuesta.getContent();
                this.processManager.incrementarPasosDeApuesta();
                
                String[] ordenApostado = apuesta.getOrdenApostado();
                this.processManager.incrementarPasosDeApuesta();
            
                while(apuestaActual  < posicionesFinales.length){
                    if(ordenApostado[apuestaActual].equals(posicionesFinales[apuestaActual])){
                        this.processManager.incrementarPasosDeApuesta();
                        
                        int montoGanado = this.maximoMonto-apuestaActual;                    
                        this.processManager.incrementarPasosDeApuesta();
                        
                        apuesta.setResultados(apuestaActual, String.valueOf(montoGanado));
                        this.processManager.incrementarPasosDeApuesta();                        
                        
                        apuesta.incrementarTotalGanado(montoGanado);
                        this.processManager.incrementarPasosDeApuesta();
                    }else{
                        apuesta.setResultados(apuestaActual, "-");
                        this.processManager.incrementarPasosDeApuesta();
                    }            
                    apuestaActual++;                   
                }//se encarga de analizar cada posición apostada en base a los resultados
                
                finalizarAnalisisComplejidad();
            }while((nodoApuesta = nodoApuesta.getNext()) != null);
        } 
        
        mostrarAnalisisComplejidad(apuestasAceptadas.getSize());
        ordenarListaFormatoPorDefecto();
    }//Servicio crítico 3 [LISTO] -> O(n) uwu xD
    //no requiere devolver algo, puesto que los datos se actualizan indirectamente en la lista de las apuestasAceptadas, que posee el clasificador...
     
    private void ordenarListaFormatoPorDefecto(){
        this.manejadorOrdenamiento.ordenar(this.manejadorOrdenamiento.DESC_POR_MONTO, apuestasAceptadas);
    }    
    
    private void finalizarAnalisisComplejidad(){
        this.processManager.setTiempoFinal();
        this.processManager.addTiempoParcial();
                
        this.processManager.addPasosTotalesDeApuesta();
        
        //para preparar todo para la siguiente apuesta de la lista
        this.processManager.setTiempoInicial();
        this.processManager.inicializarPasosApuesta();        
    }
    
    private void mostrarAnalisisComplejidad(int numeroApuestas){
        System.out.println("\nANÁLISIS DE COMPLEJIDAD");
        System.out.println("> cálculo de resultados de apuestas");
        if(numeroApuestas > 0){
            System.out.println("Resultados de tiempo");
            System.out.println("Tiempo primedio: "+ this.processManager.getTiempoPromedioTotal(numeroApuestas)+ "\n");
            System.out.println("Resultados de pasos");
            System.out.println("Mayor cantidad de pasos: "+ this.processManager.getMayorNumeroPasos());
            System.out.println("Menor cantidad de pasos: "+ this.processManager.getMenorNumeroPasos());
            System.out.println("Cantidad de pasos promedio: "+ this.processManager.getTiempoPromedioTotal(numeroApuestas));
        }else{
            System.out.println("No hay apuestas a las que calcular resultados");
        }
        
    }
}
