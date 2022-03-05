/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Apuesta;

import java.io.Serializable;

/**
 *
 * @author phily
 */
public class Apuesta implements Serializable{
    String nombrePostor;
    String montoApostado;
    private String[] ordenApostado;
    private String resultado[];//aquí será donde se add la remuneración corresp al orden apostado y los R// xD
    private int totalGanado = 0;
    
    
    public Apuesta(String nombrePostor, String montoApostado, String[] ordenApostado){
        this.nombrePostor = nombrePostor;
        this.montoApostado = montoApostado;
        this.ordenApostado = ordenApostado;
        if(ordenApostado != null){
            this.resultado = new String[ordenApostado.length];
        }        
    }
    
    public void setResultados(int numeroResultado, String resultado){
        this.resultado[numeroResultado] = resultado;
    }   
    
    public void incrementarTotalGanado(int incremento){
        this.totalGanado += incremento;
    }

    public String getNombrePostor() {
        return nombrePostor;
    }

    public String getMontoApostado() {
        return montoApostado;
    }

    public String[] getOrdenApostado() {
        return ordenApostado;
    }

    public String[] getResultado() {
        return resultado;
    }
    
    public int getTotalGanado(){
        return this.totalGanado;    
    }
    
}
