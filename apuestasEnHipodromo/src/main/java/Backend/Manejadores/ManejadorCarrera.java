/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Interfaz.Jinete;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author phily
 */
public class ManejadorCarrera {         
    private JPanel pista;
    private final int ubicacionXPartida = 850;
    private String[] nombreJinetes;//los puse al revés porque en la posicioónY más cercana a 0 está la etiqueta para el caballo J...
    private Jinete[] jinetes = new Jinete[10];
    String[] resultados;//ahora que lo veo, no requiere ser una var global, bastaría con que fuera una local, ya que el método retornará xD
    
    Timer timerResult = null;
    
    public ManejadorCarrera(JPanel pista, String[] nombreParticipantes){
        this.pista = pista;        
        this.nombreJinetes = nombreParticipantes;
    }
    
    public void posicionarJinetes(){     
          for (int jinete = 0; jinete < jinetes.length ; jinete++) {
              jinetes[jinete] = new Jinete(this.pista, new ImageIcon(getClass().getResource("/jinete-"+(10-jinete)+ ".png")),
                                        ubicacionXPartida, (jinete)*43+ (jinete*13));
          }
          
          pista.updateUI();
    }//invocado en el manejadorTemp
    
    public void empezarCarrera(ManejadorInterfaz manejadorInterfaz, ManejadorResultados manejadorResultados,
    ManejadorReportes manejadorReportes){//O(2n) = O(n) uwu xd         
          String[] arregloBase = nombreJinetes;
          resultados = new String[]{null, null, null, null, null, null, null, null, null, null};
          int ubicacionGenerada;
          int indiceActual = 0;                
          int ubicacionPrimerJinete = 0;
          
          while(resultados[resultados.length-1] == null){             
            Random randomValor = new Random();
            ubicacionGenerada = randomValor.nextInt(10);//el que se encarga de dar los rangos da valores entre 1 y 10, para que el valor se encuentre entre 0-9
              
            if(arregloBase[ubicacionGenerada] != null){
                resultados[indiceActual] = arregloBase[ubicacionGenerada];//de alguna manera no me da null, aunque el arreglo de nombres no haya sido inicializado :0, será porque así hace cuando se declara un arreglo? :0 si te da tiempo podrías ver realmente por qué xD, pero está bueno jajaja
                jinetes[ubicacionGenerada].recorrerPista(pista, (850-(85*indiceActual)), 850);
                
                arregloBase[ubicacionGenerada] = null;                
                if(indiceActual == 0){
                    ubicacionPrimerJinete = ubicacionGenerada;
                }
                indiceActual++;
            }    
          }
          
        postearResultados(jinetes[ubicacionPrimerJinete], manejadorInterfaz, manejadorResultados, manejadorReportes);
    }//invocado al terminar el proceso del diálogo, luego de este se invocará al método de la interfaz para setear los datos en la tabla...
    
    public void postearResultados(Jinete ultimoEnLlegar, ManejadorInterfaz manejadorInterfaz,
          ManejadorResultados manejadorResultados, ManejadorReportes manejadorReportes){        
        
         timerResult = new Timer(700, new ActionListener(){//puse 700, puesto que ya habrán empezado a avanzar, y por las búsquedas de unicidad, no puedo decir que cada uno empiece a moverse después de un tiempo fijo xD
            @Override
            public void actionPerformed(ActionEvent e){
                if(ultimoEnLlegar.getEstadoFinalizacion()){//se hace la diferencia, puesto que a la meta se logra llegar, disminuyendo valores en X...
                    manejadorInterfaz.crearTablaPosiciones(resultados);
                    
                    manejadorResultados.calcularResultadosApuestas(resultados);
                    manejadorInterfaz.addResultadoApuestas();//aquí se invocará al método de la interfaz para mostrar los resultados en la tabla...
                    manejadorInterfaz.habilitarFiltros();
                    
                    manejadorReportes.setResultados(resultados);
                    manejadorReportes.generarReportes();                    
                     timerResult.stop();
                }
            }        
           });            
            timerResult.start();            
    }
    
    public void stopTimerResult(){
        if(this.timerResult!= null && this.timerResult.isRunning()){
            this.timerResult.stop();
        }
    }
    
    public Jinete[] getJinetes(){
        return this.jinetes;
    }
   
    
    //NOTA: por el método de posicionamiento de caballos, me di cuenta de que
    //cuando un componente gráfico de swing, deba cb su texto dentro de la definición
    //del proceso de un hilo, a menos que obtengas como argumento esos componentes que
    //cambiarán por medio del hilo (un JLabel p.ej) [lo que no se es si nec debe ser
    //en el método que contiene el hilo, o basta con que tengas almacenados esos 
    //componentes en la clase donde exe ese hilo xD, imagino quees lo segundo xD]
    //deberás actualizar el componente contenedor ojo, el contenedor[bueno tampoco sé 
    //si exactametne el contenedor o el que recibirá el cb, deplano que esto segundo
    //es decir si vas a colocar unos label sobre un conenedor, deberías actualizar 
    //el contenedor, porque ese sufrirá el cb, si fueras a cb el text de un label
    //entonces deberías actualizar ese label y de no tener ese método entonces si 
    //a su contenedor] con updateUI() puesto que la renderización se cb por medio de
    //un método asíncrono, no puede hacer a actualización el componente de manera
    //automática, sino que se le debe avisar por eso era que no funcionó el cb de
    //#ritos en el dialog que formaste, porque no actualizabas la UI, puesto que
    //invocabas al método para setear el nuevo texto pero en realidad no habías
    //almacenado el componente en el que se iba a realizar el cb
}
