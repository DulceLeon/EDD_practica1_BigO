/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Apuesta.Apuesta;
import Backend.Objetos.Apuesta.ApuestaErronea;
import Backend.Objetos.EDD.ListaEnlazada;

/**
 *
 * @author phily
 */
public class ManejadorCargaArchivo {
    private Receptor receptor;
    private Clasificador clasificador;
    
    public ManejadorCargaArchivo(String[] nombreParticipantes){
        this.receptor = new Receptor();
        this.clasificador = new Clasificador(nombreParticipantes);
    }
    
    public void revisarApuestasRecibidas(String path){
        //aquí haces lo que debas hacer con el JFileChooser
        
        this.receptor.leerArchivoEntrada(path);
        this.clasificador.estudiarApuestasRecibidas(this.receptor.getApuestasRecibidas());        
    }
    
    
    //estos me van a ser útiles hasta después que los jinetes hallan corrido [Es decir luego de mostrar la tabla de posiciones o junto con ella xD]
    public ListaEnlazada<Apuesta>  getApuestasAceptadas(){
        return this.clasificador.getApuestasAceptadas();
    }
    
    public ListaEnlazada<ApuestaErronea>  getApuestasErroneas(){
        return this.clasificador.getApuestasRechazadas();
    }
    
    
}
