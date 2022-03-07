/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Apuesta.Apuesta;
import Backend.Objetos.Apuesta.ApuestaErronea;
import Backend.Objetos.EDD.ListaEnlazada;
import Backend.Objetos.EDD.Nodo;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import Backend.Objetos.Error;

/**
 *
 * @author phily
 */
public class ManejadorReportes {//igual que el manejador dde interfaz, para que muestre los datos en la tabla, este será empleado en el diálogo y será enviado así como ellos...
    private ManejadorOrdenamiento manejadorOrdenamiento;
    
    private ListaEnlazada<Apuesta> apuestasAceptadas;
    private ListaEnlazada<ApuestaErronea> apuestasRechazadas;
    private String[] resultadosCarrera;
    
    public ManejadorReportes(ManejadorOrdenamiento manejadorOrdenamiento){
        this.manejadorOrdenamiento = manejadorOrdenamiento;
    }
    
    public void setListas(ListaEnlazada<Apuesta> apuestasAceptadas, ListaEnlazada<ApuestaErronea> apuestasRechazadas){
        this.apuestasAceptadas = apuestasAceptadas;
        this.apuestasRechazadas = apuestasRechazadas;
    }//Esto se invoca en el TaskManager y el método de este, es invocado en el cnstruct del HIpódromo, por lo cual se garantiza que al nada más ser creada esta clase, rev¿ciba las listas, al igual que el otro manejador, creo que es el de interfaz...
    
    public void setResultados(String[] resultadosCarrera){
        this.resultadosCarrera = resultadosCarrera;
    }
    
    public void generarReportes(){
        String IDCarrera = String.valueOf(System.currentTimeMillis());
        
        generarReporteResultadosCarrera(IDCarrera);
        generarReporteAPuestas(IDCarrera);
        generarReporteApuestasRechazadas(IDCarrera);
        generarReportesComplejidad(IDCarrera);
    }
    
    private void generarReporteResultadosCarrera(String IDCarrera){       
        if(resultadosCarrera != null){
            try {            
                 System.out.println("IDCarrera: "+IDCarrera);
                String ruta = "src/main/resources/ResultadosCarreras/"+"RC_"+ IDCarrera+".txt";//se supone que no debería repetirse nunca este valor...
                String contenido = "\t\t\t\tRESULTADOS CARRERA\n";
            
                 for (int resultadoActual = 0; resultadoActual < resultadosCarrera.length; resultadoActual++) {
                    contenido += "\t\t\t"+(resultadoActual+1)+".   "+ resultadosCarrera[resultadoActual]+ "\n";
                 }

                File file = new File(ruta);

                // Si el archivo no existe es creado
                if (!file.exists()) {//aunque no debería xD
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(contenido);
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }  
    
    private void generarReporteAPuestas(String IDCarrera){
        if(!apuestasAceptadas.isEmpty()){//sé que el try evita que se muera, pero para qué exe algo si de todos modos no tiene nada xD
            try {         
                this.manejadorOrdenamiento.ordenar(this.manejadorOrdenamiento.DESC_POR_MONTO, apuestasAceptadas);
                String ruta = "src/main/resources/ResultadosApuestas/"+"RA_"+ IDCarrera+".txt";//, ApuestasRechazadas
                String contenido = "\t\t\t\tRESULTADOS APUESTAS\n";
                Nodo<Apuesta> nodoApuesta = apuestasAceptadas.getFirst();
                Apuesta apuesta;
                int apuestaActual = 1;
            
                do{
                    apuesta = nodoApuesta.getContent();
                   contenido += "\n["+apuestaActual+"] "+" Nombre: "+apuesta.getNombrePostor() + ", "+ "Monto apostado: "+
                           apuesta.getMontoApostado() +", Total ganado: "+apuesta.getTotalGanado() +"\n\t " +
                           apuesta.getOrdenApostado()[0]+": " + apuesta.getResultado()[0]+", " +
                           apuesta.getOrdenApostado()[1]+": " +apuesta.getResultado()[1]+", " + 
                           apuesta.getOrdenApostado()[2]+": " +apuesta.getResultado()[2]+", " + 
                           apuesta.getOrdenApostado()[3]+": " +apuesta.getResultado()[3]+ ", " + 
                           apuesta.getOrdenApostado()[4]+": " +apuesta.getResultado()[4]+ ", " + 
                           apuesta.getOrdenApostado()[5]+": " +apuesta.getResultado()[5]+", " + 
                           apuesta.getOrdenApostado()[6]+": " +apuesta.getResultado()[6]+", " + 
                           apuesta.getOrdenApostado()[7]+": " +apuesta.getResultado()[7]+", " + 
                           apuesta.getOrdenApostado()[8]+": " +apuesta.getResultado()[8]+", " + 
                           apuesta.getOrdenApostado()[9]+": " +apuesta.getResultado()[9]+"\n";
                
                   apuestaActual++;
                }while((nodoApuesta = nodoApuesta.getNext()) != null);
            
                File file = new File(ruta);

                // Si el archivo no existe es creado
                if (!file.exists()) {//aunque no debería xD
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(contenido);
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }        
        }        
     
    }//Aquí se generan ambos
    
    private void generarReporteApuestasRechazadas(String IDCarrera){
        if(!apuestasRechazadas.isEmpty()){
            try {
                String ruta = "src/main/resources/ApuestasRechazadas/"+"RR_"+ IDCarrera+".txt";
                String contenido = "\t\t\t\tAPUESTAS RECHAZADAS\n";
                Nodo<ApuestaErronea> nodoApuesta = apuestasRechazadas.getFirst();
                ApuestaErronea apuesta;
                int apuestaActual = 1;
            
                do{
                    apuesta = nodoApuesta.getContent();
                    contenido += "\n["+apuestaActual+"] "+" Nombre: "+apuesta.getNombrePostor() + "\n";
                    Nodo<Error> nodoError = apuesta.getErrores().getFirst();
               
                    do{
                        Error error = nodoError.getContent();
                        contenido += "      "+ "Error: "+error.getElError() + ", Tipo: "+ error.getTIpo() + ", Descripcion: "+ error.getDescripcion()+ "\n";                    
                    }while((nodoError = nodoError.getNext())!= null);                
                
                   apuestaActual++;
                }while((nodoApuesta = nodoApuesta.getNext()) != null);
            
                File file = new File(ruta);
            
                // Si el archivo no existe es creado
                if (!file.exists()) {//aunque no debería xD
                    file.createNewFile();
                }

                FileWriter fw = new FileWriter(file);
                BufferedWriter bw = new BufferedWriter(fw);
                bw.write(contenido);
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }        
        }      
    }
    
  public void generarReportesComplejidad(String IDCarrera){
      
  }
    
}
