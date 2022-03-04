/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Interfaz;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;


/**
 *
 * @author phily
 */
public class Jinete extends JLabel implements ActionListener{
    ImageIcon jinete;    
    private int posicionY;//para ser asignado a una pista...
    private int metrosFaltantes;
    private boolean heTerminadoRecorrido;
    
    Timer timerCarrer;
    private int espacioARecorrer;    
    
    /**
     * Creates new form mensajeFelicitacion     
     * @param contenedor
     * @param jinete
     * @param puntoPartidaX
     * @param puntoPartidaY
     */
    public Jinete(JPanel contenedor,ImageIcon jinete, int puntoPartidaX, int puntoPartidaY){           
        this.jinete = jinete;
        this.initComponents();               
        
        posicionarEnPista(contenedor, puntoPartidaX, puntoPartidaY);
    }
    
    private void initComponents(){
         setIcon(jinete); 
        
        setMaximumSize(new java.awt.Dimension(100, 100));
        setMinimumSize(new java.awt.Dimension(100, 100));        
    }    
    
    private void posicionarEnPista(JPanel contenedor, int puntoPartidaX, int puntoPartidaY){
        this.posicionY = puntoPartidaY;
        //no igualo los metrosFaltantes a esta posicionX, puesto que si hago eso, el caballo se hubicaría fuera de los límites de cada metro xD
        setBounds(puntoPartidaX, posicionY, 100, 100);
        
        contenedor.add(this);        
    }
    
    public void recorrerPista(JPanel pista, int metrosARecorrer, int metrosTotales){
           int tamañoPista = metrosTotales;
           metrosFaltantes = tamañoPista;
        
          timerCarrer = new Timer(1000, new ActionListener(){//al parecer al usar variables locales [sea que se emplee lambda para indicar esto o no] deben estar marcadas como final o al menos no cambiar...
            @Override
            public void actionPerformed(ActionEvent e){
                if(metrosFaltantes > (tamañoPista-metrosARecorrer)){//se hace la diferencia, puesto que a la meta se logra llegar, disminuyendo valores en X...
                    metrosFaltantes -=  85;
                    setLocation(metrosFaltantes, posicionY);
                    //con eso bastaría a menos que no se mueva, enonces actualizaremos la ui del panel xD :p, para eso es que puse el parámetro xD
                }else{               
                    heTerminadoRecorrido = true;//podría colocarlo en actionPerformed xD, debería seguir actuando igual, digo yo xD
                    timerCarrer.stop();
                }
            }        
           });
            
            timerCarrer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //no los lleno porque si llego a parar el hilo, creo que tomaría como si "realizó" la tarea
        //y exe este bloque... aunque tb si quisiera usarlo podría colocarle un if y con eso 
        //se solucionaría todo
    }
    
    public void stopTimerCareer(){
        if(this.timerCarrer!= null && this.timerCarrer.isRunning() ){
            this.timerCarrer.stop();
        }
    }
    
    public boolean getEstadoFinalizacion(){
        return heTerminadoRecorrido;
    }
    
}
