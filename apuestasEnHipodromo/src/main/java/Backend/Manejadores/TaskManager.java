/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Apuesta.Apuesta;
import Backend.Objetos.EDD.ListaEnlazada;
import Backend.Objetos.Interfaz.Jinete;
import Frontend.CareerAdvice;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.Timer;

/**
 *
 * @author phily
 */
public class TaskManager {
    private int minutos = 0;//Equivaldrán a horas y los seg a mins xD
    private int segundos = 5;        
    private final JButton btn_cargarAPuestas;
    private final  JButton btn_agregarApuestas;

    private final ManejadorCarrera manejadorCarrera;
    private  final ManejadorInterfaz manejadorInterfaz;
    private final  ManejadorResultados manejadorResultados;//mejor se hará en la interfaz, bueno en caso decidas hacer una tabla en lugar del Jasper, aunque yo diría es mejor el Jasper xD pues de todos modos tendrías que pasar los datos de la tabla a un archivo, entonces para mayor presentabiliadd el Jasper de una vez xD
    
    CareerAdvice careerAdvice = new CareerAdvice(null, true);
    
    Timer timerBets;            
    
    public TaskManager(JButton btn_cargarAPuestas, JButton btn_agregarAPuestas, JPanel pista, 
            ManejadorInterfaz manejadorInterfaz, String[] nombreJinetesParticipantes){
        this.manejadorCarrera = new ManejadorCarrera(pista, nombreJinetesParticipantes);        
        
        this.btn_agregarApuestas = btn_agregarAPuestas;
        this.btn_cargarAPuestas = btn_cargarAPuestas;        
        
        this.manejadorInterfaz = manejadorInterfaz;                
        this.manejadorResultados = new ManejadorResultados();// lo único que debe recibir es el arreglo de resultados y de apuestas, por cierto debo ver donde instanciaré el obj de Clasificador, deplano igual que el revisor, puesto que el método se invocará justo debajo de donde se invoque el del revisor xD
    }    
    
    public void setApuestasAceptadas(ListaEnlazada<Apuesta> apuestasAceptadas){
         this.manejadorResultados.setApuestasAceptadas(apuestasAceptadas);         
    }
    
    public void startCountDown_toBets(JLabel labelMIns, JLabel labelSegs){        
     JOptionPane.showMessageDialog(null, "El tiempo para apostar ha iniciado\nal terminar las dos horas\n"
         + "no será posible apostar más.\n\nsuerte en tu apuesta ;)", "Es hora de apostar!!!", 
         JOptionPane.INFORMATION_MESSAGE);
        
      timerBets = new Timer(1000, (ActionEvent e) -> {
          if(segundos>0){
              segundos--;
          }else{
              segundos=60;
              
              if(minutos>0){
                  minutos--;
              }
          }
          
          labelMIns.setText(String.valueOf(minutos));
          labelSegs.setText(String.valueOf(((segundos<10)?"0"+segundos:segundos)));//si pasa algo raro con los segundos puedes echarle la culpa al operador ternario xD xD
          
          if(minutos==0 && segundos==0){
              timerBets.stop();
              informarTiempoRestante();
          }
     } );//fin del actionListener     
        
        timerBets.start();
    }   
  
    
    private void informarTiempoRestante(){
       // CarrerAdvice carrerAdvice = new CarrerAdvice(null, true);
          this.manejadorCarrera.posicionarJinetes();         
       
         careerAdvice.iniciarCuentaRegresiva(manejadorCarrera, manejadorInterfaz, manejadorResultados);
         careerAdvice.setVisible(true);          
                
         //   startCountDouwn_Carrer(new CarrerAdvice(null, true));        
             
         this.btn_agregarApuestas.setEnabled(false);
         this.btn_cargarAPuestas.setEnabled(false);
    }  
      
    public void manejarCierreApp(JRootPane rootPane, JLabel labelMinutos, JLabel labelSegundos){
        if(this.manejadorInterfaz.confirmarCierreApp(rootPane)){            
            stopTimer(labelMinutos, labelSegundos);
            this.manejadorInterfaz.mostrarMsjeAgradecimiento();//aunque bien podría mostrar este msje antes de parar los hilos, puesto que en la vida real,de irse una persona, los evt no pararan por haberse ido xD, la persona los dejará de ver xD
            System.exit(0);
        }        
    }
    
    /**
     * se empleará para revisar que cuando el user
     * presione salir, el hilo se haya detenido, para
     * evitar que sceda algo por no haberlo parado xD
     * [entonces no es necesario cb el estado de los btn
     * porque en caso de que salga ya no habrá más btn xD]
     * @param labelMinutos
     * @param labelSegundos
     */
    private  void stopTimer(JLabel labelMinutos, JLabel labelSegundos){
        if(!labelMinutos.getText().equals("0") && !labelSegundos.getText().equals("00")){//o también podría revisar si el timer isRunning xD, como con los demás xD
            timerBets.stop();
        }        
        
        this.careerAdvice.stopTimerToCareer();
        detenerTimerJinetes(this.manejadorCarrera.getJinetes());
        this.manejadorCarrera.stopTimerResult();
    }
    
    private void detenerTimerJinetes(Jinete[] jinetes){
        for (int jineteActual = 0; jineteActual < jinetes.length; jineteActual++) {
            if(jinetes[jineteActual] != null){
                jinetes[jineteActual].stopTimerCareer();
            }
        }    
    }
    
    public void comenzarDeNuevo(){//en todo caso sería la de las apuestas, puest el de la carrera depende de este xD
        timerBets.restart();
    }
    
}


//Además de lo que había dicho de los hilos, estos solo pueden ejecutar un proceso por vez
//creo que esto sucede así con las ventanas, porque son un hilo en sí, entonces es como si 
//Se pausara la exe del siguiente paso del hilo actual hasta que se acabe el otro, entonces
//cuando sea así axn que se exe al invocar métodos de algun tipo de ventana de Swing como
//JDialog, verifica que no se exe nada más antes de este método que implique otro proceso o
//hilo, porque de ser así, no se exe nada... ni siquiera el setVisible puede estar antes xD
//lo digo porque antes de invocar al metodo del hilo aparecia la instanciación del JDialog 
//y el método para setVIsibility y por eso no se exe ese método hasta que esos dos acababan...
//eso y creo que si quieres que un diálogo refleje el cb de una axn que modificará dinámicamente
//el contenido de este, debes hacerlo todo antes de hacerlo visible, sino aunque se exe, este
//permanecerá en el estado en el que se encontraba al invocar al setVisible xD

