/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Apuesta.Apuesta;
import Backend.Objetos.EDD.ListaEnlazada;

/**
 *
 * @author phily
 */                                                        //será instanciada este clase en el Task Manager, para que así pueda ser enviada al manejadorCarrera como se hace con el ManejadorInterfaz...
public class ManejadorResultados {//su método debe ser invocado justo después de postear los Resultados en la tabla de posiciones, es decir debe invocarse una línea abajo de ese método xD
    private final int maximoMonto = 10;
    private ListaEnlazada<Apuesta> apuestasAceptadas = new ListaEnlazada<>();//la inicializo para no tener que colocar en la condición del while que sea != null, puesto que aumentaría la complejidad y el trabajo de ese bucle por una situación que super remotanmente podría suceder [que la verificación del archivo no termine a tiempo y por lo tanto esta clase no reciba el listado de apuestasAceptadas...
    
    public void setApuestasAceptadas(ListaEnlazada<Apuesta> apuestasAceptadas){
        this.apuestasAceptadas = apuestasAceptadas;
    }
    
    public void mostrarResultadosApuesta(String[] posicionesFinales){//se recibirá el arreglo rsultado que genera el manejador de carrera...
        ListaEnlazada<Apuesta> copiaListadoApuestas = apuestasAceptadas;
        
        while(!copiaListadoApuestas.isEmpty()){//puesto que se eliminará y add al final porque requiero que estos datos existan, vamos a hacer incrementar esta variable, bueno aunque lo que podría hacer es usar una lista auxiliar, para que no tenga necesidad de tener que recuperarla, puesto que la original no cambiaría...
            int apuestaActual = 0;
            Apuesta apuesta = copiaListadoApuestas.getFirst().getContent();
            String[] ordenApostado = apuesta.getOrdenApostado();
            
            while(apuestaActual  < posicionesFinales.length){
                if(ordenApostado[apuestaActual].equals(posicionesFinales[apuestaActual])){
                    int montoGanado = this.maximoMonto-apuestaActual;                    
                    apuesta.setResultados(apuestaActual, String.valueOf(montoGanado));
                    apuesta.incrementarTotalGanado(montoGanado);
                }else{
                    apuesta.setResultados(apuestaActual, "-");
                }            
            }
        }
        
        for (int revision = 0; revision < apuestasAceptadas.getSize(); revision++) {
               System.out.println((revision+1) + " "+ apuestasAceptadas);//solo es para ver que tenga aún los datos, aunque yo diría que sí xD
        }
    }//Servicio crítico 3 [LISTO] -> O(n) uwu xD
    //no requiere devolver algo, puesto que los datos se actualizan indirectamente en la lista de las apuestasAceptadas, que posee el clasificador...
    
}
