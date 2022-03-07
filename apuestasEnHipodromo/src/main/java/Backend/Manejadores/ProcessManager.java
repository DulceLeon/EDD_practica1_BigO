/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;


/**
 *
 * @author phily
 */
public class ProcessManager {//este será instanciado por cada uno de los manejadores que se encargan de realizar los servicios críticos
    //variables para reportar pasos y tiempo POR APUESTA
    private long tiempoInicial;
    private long tiempoFinal;
    private long tiempoTotal = 0;
    private int numeroPasos = 0;
    private int mayorNumeroPasos = 0;
    private int menorNumeroPasos = 0;
    private int numeroTotalPasosPorAPuestas = 0;
    //private ListaEnlazada<Integer> pasosPorApuestas;
    
    public void setTiempoInicial(){
        this.tiempoInicial = System.currentTimeMillis();
    }
    
    public void setTiempoFinal(){
        this.tiempoFinal = System.currentTimeMillis();
    }
    
    public void addTiempoParcial(){
        this.tiempoTotal += (this.tiempoFinal - this.tiempoInicial);
    }
    
    //este getter se debe emplear para obtener el dato para formar el archivo, puesto que todo ya está
    //listo y se crea una instancia de esta clase para cada manejador de servicio crítico no habrá pena
    //de pensar que el promedio que se obtenga no vaya a ser el correcto, por contener datos de otros procesos
    public long getTiempoPromedioTotal(int numeroTotalApuestas){
        return (this.tiempoTotal/numeroTotalApuestas);
    }
    
    public void inicializarPasosApuesta(){
        this.numeroPasos = 1;//para que así cuando se llegue de nuevo al inicio del estudio de otra apuesta, no haya pena de incrementar a los datos anteriores y que además no se tenga que usar el método de incrementar además de este xD
    }
    
    public void incrementarPasosDeApuesta(){
        this.numeroPasos++;
    }
    
    //se debe invocar al terminar los pasos del proceso de análisis de UNA apuesta
    public void addPasosTotalesDeApuesta(){
        this.mayorNumeroPasos = (this.numeroPasos > this.mayorNumeroPasos)?this.numeroPasos:this.mayorNumeroPasos; 
        this.menorNumeroPasos = ((this.menorNumeroPasos == 0)?this.numeroPasos
                :((this.numeroPasos < this.menorNumeroPasos)?this.numeroPasos:this.menorNumeroPasos)); 
        this.numeroTotalPasosPorAPuestas += this.numeroPasos;
        
         //this.pasosPorApuestas.add(numeroPasos);no es necesario, puesto que todo lo guardo en vars xD, por medio de susti xD         
    }
    
    public int getNumeroPromedioPasosPorAPuesta(int apuestasTotales){
       double promedio = Math.floor((double) (this.numeroTotalPasosPorAPuestas/apuestasTotales));
       return (int) promedio;
    }//A ver si no me da problemas de casteo xD
    
    public int getMayorNumeroPasos(){
        return this.mayorNumeroPasos;
    }
    
    public int getMenorNumeroPasos(){
        return this.menorNumeroPasos;
    }
    
}


//ya solo es de llamar a los métodos en las partes iniciales y fianles del ciclo donde se evalúa cada apuesta
//y enviar cada objeto ProcessManager, que aparece en cada clase que se encarga de realizar un servicio de los
//3 que requieren esta recopilación de info, a la instacia de manejador de reportes, para que pueda generar
//el archivo que contendrá la info relacionada con esto de los 3 servicios

//solo hay que ver dónde se enviarán estas instancias
//porque el método en sí ya lo invocas xD