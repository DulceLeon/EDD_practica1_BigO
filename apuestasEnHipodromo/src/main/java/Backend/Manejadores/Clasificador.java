/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Apuesta.Apuesta;
import Backend.Objetos.Apuesta.ApuestaErronea;
import Backend.Objetos.Error;
import Backend.Objetos.EDD.ListaEnlazada;

/**
 *
 * @author phily
 */
public class Clasificador {
    private String[] participantes;//si logras saber cuál es, entonces solo lo reemplazas por los datos exactos..
    
    private ListaEnlazada<ApuestaErronea> apuestasRechazadas = new ListaEnlazada<>();//se add un elemento por cada postor
    private ListaEnlazada<Apuesta> apuestasAceptadas = new ListaEnlazada<>();
    private ProcessManager processManager_aceptadas;
    private ProcessManager processManager_rechazadas;
    
    public Clasificador(String[] participantes){//esta clase se va a emplear directametne en el JFrame, puesto que al inicio se especificarán los jinetes...
        this.participantes = participantes;
        this.processManager_aceptadas = new ProcessManager();
        this.processManager_rechazadas = new ProcessManager();
    }//si ya vas a saber los nombres de los jinetes, entonces solo lo obtienes del manejador de carrera, o deja ese string como variable local del JFrame... o especifica los nombres directamente aquí xD
    
    public void estudiarApuestasRecibidas(ListaEnlazada<String[]> apuestasRecibidas){
        while(!apuestasRecibidas.isEmpty()){//puesto que la inicializo para evitar que sea null...
            inicializarAnalisisComplejidad();
            
            String[] apuesta = apuestasRecibidas.removeFirstElement().getContent();
            incrementarPasosComunesApuestas();
            ListaEnlazada<Error> listadoErrores = new ListaEnlazada<>();//puesto que aunque no se hagan las demaś revisiones, de todos modos no será aceptada, por lo cual es nec add al listado de errores el único error xD
            incrementarPasosComunesApuestas();
            
            if(apuesta.length > 2){//sino, quiere decir que solo tiene el monto y el nombre del postor
                int posicionActual = 0;//será útil para recorrer cada apuesta xD                
                incrementarPasosComunesApuestas();
                
                String[] listadoJinetes  = new String [apuesta.length-2];                
                incrementarPasosComunesApuestas();
                
                System.arraycopy(apuesta, 2, listadoJinetes, 0, listadoJinetes.length);                
                incrementarPasosComunesApuestas();
                
                String jinetesEspecificados = "";
                incrementarPasosComunesApuestas();
                
                int[] repeticiones = new int[10];
                incrementarPasosComunesApuestas();
                
                while(posicionActual < listadoJinetes.length){
                    if(listadoJinetes.length > 1){
                        if(jinetesEspecificados.contains(listadoJinetes[posicionActual])){                    
                               repeticiones[((listadoJinetes[posicionActual].equals(participantes[0])?0:(listadoJinetes[posicionActual].equals(participantes[1])?1
                                :(listadoJinetes[posicionActual].equals(participantes[2])?2:(listadoJinetes[posicionActual].equals(participantes[3])?3:
                                (listadoJinetes[posicionActual].equals(participantes[4])?4:(listadoJinetes[posicionActual].equals(participantes[5])?5
                                :(listadoJinetes[posicionActual].equals(participantes[6])?6:(listadoJinetes[posicionActual].equals(participantes[7])?7
                                :(listadoJinetes[posicionActual].equals(participantes[8])?8:9))))))))))] ++;//y así sigues con cada comparación...
                               this.processManager_rechazadas.incrementarPasosDeApuesta();
                         }else{                              
                              jinetesEspecificados+= " " + listadoJinetes[posicionActual];//esto no lo vayas a quitar, sino no habrá con qué comparar xD
                              this.processManager_aceptadas.incrementarPasosDeApuesta();                            
                         }
                    }                                     
                    
                    if(posicionActual == (listadoJinetes.length-1)){
                        revisarUltimosPosiblesErrores(apuesta[0], apuesta[1], listadoJinetes, repeticiones, listadoErrores);
                    }                                
                    posicionActual++;
                }
            }else{
                listadoErrores.add(new Error("0 posiciones apostadas", "No especificaste jinete alguno", "Debes indicar un orden de posiciones por el cual apostar"));
                apuestasRechazadas.add(new ApuestaErronea(apuesta[0], apuesta[1], listadoErrores));                  
                finalizarAnalisisComplejidad_rechazadas();
            }
        }

        mostrarAnalisisComplejidadCompleto();
    }//Servicio crítico 2 [LISTO] -> O(n) quitándole las ctes xD uwu
    
    private void revisarUltimosPosiblesErrores(String nombrePostor, String montoApostado, String[] listadoJinetes, 
        int[] repeticiones, ListaEnlazada<Error> listadoErrores){        
        
        if(listadoJinetes.length > 1){//pueso que de ser tamaño 1, no habrían posibilidada de repeticiones xD
                 addErroresRepeticion(repeticiones, listadoErrores);
        } 
        if(listadoJinetes.length <10){
                  listadoErrores.add(new Error("#elementos insuficientes", "Solo especificaste "+(listadoJinetes.length) +" jinetes", "El orden apostado, debe incluir todos los jinetes"));
                  this.processManager_rechazadas.incrementarPasosDeApuesta();
        }else if(listadoJinetes.length > 10){//puesto tb podría ser mayor y eso estaría mal xD
                  listadoErrores.add(new Error("demasiados elementos", "Haz especificado más de 10 apuestas", "No existe más de 10 jinetes por los cuales apostar"));
                  this.processManager_rechazadas.incrementarPasosDeApuesta();
        }
                        
        if(!listadoErrores.isEmpty()){//se setean los datos de esta apuesta, al listado que le corresp...                
                apuestasRechazadas.add(new ApuestaErronea(nombrePostor, montoApostado, listadoErrores));
                finalizarAnalisisComplejidad_rechazadas();
        }else{                                        
                apuestasAceptadas.add(new Apuesta(nombrePostor, montoApostado, listadoJinetes));
                finalizarAnalisisComplejidad_aceptadas();               
        }        
    }
    
    private void addErroresRepeticion(int[] repeticiones, ListaEnlazada<Error> errores){//ahí miras si lo incluyes o no porque deberías revisar cada parte...
        int indice = 0;
        incrementarPasosComunesApuestas();
        
        while(indice<repeticiones.length){
            if(repeticiones[indice] > 0){
                errores.add(new Error("elemento repetido", "el jinete "+ participantes[indice] + " se utiliza "+ repeticiones[indice], "A un jinete unicamente puedes apostarle una posición"));//"msje: No es posible incluir un mismo jinete más de una vez"                
                this.processManager_rechazadas.incrementarPasosDeApuesta();
            }
            
            indice++;
            incrementarPasosComunesApuestas();
        }//no se add a las apuestas rechazadas aún, puesto que podría haber un error más xD        
    }
    
    private void inicializarAnalisisComplejidad(){
        this.processManager_aceptadas.setTiempoInicial();
        this.processManager_rechazadas.setTiempoInicial();            
        this.processManager_aceptadas.inicializarPasosApuesta();//por el paso del while...
        this.processManager_rechazadas.inicializarPasosApuesta();
    }
    
    private void incrementarPasosComunesApuestas(){
        this.processManager_aceptadas.incrementarPasosDeApuesta();
        this.processManager_rechazadas.incrementarPasosDeApuesta();
    }
    
    private void finalizarAnalisisComplejidad_aceptadas(){
        this.processManager_aceptadas.setTiempoFinal();
        this.processManager_aceptadas.addTiempoParcial();            
        
        this.processManager_aceptadas.incrementarPasosDeApuesta();//por la add de la apuesta a la lista de aceptadas                 
        this.processManager_aceptadas.addPasosTotalesDeApuesta();
    }
    
    private void finalizarAnalisisComplejidad_rechazadas(){
        this.processManager_rechazadas.setTiempoFinal();
        this.processManager_rechazadas.addTiempoParcial();
        
        this.processManager_rechazadas.incrementarPasosDeApuesta();//por la add de la apuesta a la lista de rechazadas
        this.processManager_rechazadas.addPasosTotalesDeApuesta();
    }
    
    public ListaEnlazada<ApuestaErronea> getApuestasRechazadas(){
        return this.apuestasRechazadas;
    }//empleado por el manejador de archivos, específicamente por el método que escribe el arch de apuestas rechazadas [jasper xD]
        
    public ListaEnlazada<Apuesta> getApuestasAceptadas(){
        return this.apuestasAceptadas;
    }//empleado por el manejador de resultados, quien se encargará de add los resultados xD corresp al orden apostado y lo sucedido
     
    private void mostrarAnalisisComplejidadCompleto(){
          this.mostrarAnalisisComplejidad("aceptadas", this.apuestasAceptadas.getSize());
          this.mostrarAnalisisComplejidad("rechazadas", this.apuestasRechazadas.getSize());
    }
 
     private void mostrarAnalisisComplejidad(String tipoAPuesta, int numeroApuestas){
        System.out.println("\nANÁLISIS DE COMPLEJIDAD");
        System.out.println("> cálculo de resultados de apuestas "+ tipoAPuesta);
        if(numeroApuestas > 0){            
            System.out.println("Resultados de tiempo");
            System.out.println("Tiempo promedio: "+ ((tipoAPuesta.equals("aceptadas")
                ?this.processManager_aceptadas.getTiempoPromedioTotal(numeroApuestas)
                :this.processManager_rechazadas.getTiempoPromedioTotal(numeroApuestas)))+ "\n");
            System.out.println("Resultados de pasos");
            System.out.println("Mayor cantidad de pasos: "+ (tipoAPuesta.equals("aceptadas")
                ?this.processManager_aceptadas.getMayorNumeroPasos()
                :this.processManager_rechazadas.getMayorNumeroPasos()));
            System.out.println("Menor cantidad de pasos: "+ (tipoAPuesta.equals("aceptadas")
                ?this.processManager_aceptadas.getMenorNumeroPasos()
                :this.processManager_rechazadas.getMenorNumeroPasos()));
            System.out.println("Cantidad de pasos promedio: "+ (tipoAPuesta.equals("aceptadas")
                ?this.processManager_aceptadas.getTiempoPromedioTotal(numeroApuestas)
                :this.processManager_rechazadas.getTiempoPromedioTotal(numeroApuestas)));
        }else{
            System.out.println("No hubieron apuestas "+tipoAPuesta);
        }        
    }
    
}

//estamos trabajando suponiendo que el archivo siempre tiene los datos necesarios para al menos crear un string[] con los datos que ahí residen
