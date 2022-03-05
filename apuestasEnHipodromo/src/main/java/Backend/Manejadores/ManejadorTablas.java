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
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author phily
 */
public class ManejadorTablas {
    
    public void addApuestasAceptadas(JTable tablaApuestasAceptadas, ListaEnlazada<Apuesta> apuestasAceptadas){
        if(!apuestasAceptadas.isEmpty()){            
            DefaultTableModel modelo = (DefaultTableModel) tablaApuestasAceptadas.getModel();
            eliminarTodosLosRegistros(modelo, tablaApuestasAceptadas);
        
            Nodo<Apuesta> nodoActual = apuestasAceptadas.getFirst();
            int filaActual = 1;//solo para que se muestre el número en la tabla...
            int datoActual;
         
            do{//puesto que al entrar aquí la lista al menos tendrá un dato...
                datoActual = 3;
                Apuesta apuesta =  nodoActual.getContent();
                String[] datosApuesta = new String[apuesta.getOrdenApostado().length+2+1];//2 por el nombre y el monto, 1 por el número...
                datosApuesta[0] = String.valueOf(filaActual);
                datosApuesta[1] = apuesta.getNombrePostor();
                datosApuesta[2] = apuesta.getMontoApostado();
            
                while(datoActual< datosApuesta.length){
                    datosApuesta[datoActual] = apuesta.getOrdenApostado()[datoActual-3];//puesto que está "adelantado" en 2 el valor de esa var... xD
                
                    datoActual++;
                }            
            
                modelo.addRow(datosApuesta);
                filaActual++;
            }while((nodoActual = nodoActual.getNext()) != null);        
        
            tablaApuestasAceptadas.setModel(modelo);
            tablaApuestasAceptadas.updateUI();//es un hecho que tendrás que descomentarlo xD
        }
    }//LISTO
    
      public void addApuestasRechazadas(JTable tablaApuestasRechazadas, ListaEnlazada<ApuestaErronea> apuestasRechazadas){
         if(!apuestasRechazadas.isEmpty()){
            String[] datosApuesta;//para no estar reservando un espacio para esta variable cada vez...
            DefaultTableModel modelo = (DefaultTableModel) tablaApuestasRechazadas.getModel();
            eliminarTodosLosRegistros(modelo, tablaApuestasRechazadas);
        
            Nodo<ApuestaErronea> nodoActual = apuestasRechazadas.getFirst();        
            int filaActual = 1;
         
            if(nodoActual != null){//puesto que esta lista SÍ podría estar vacía...
                do{
                    ApuestaErronea apuesta =  nodoActual.getContent();
                    Nodo<Backend.Objetos.Error> errorActual = apuesta.getErrores().getFirst();            
          
                    while(errorActual !=null){//no por el primero sino por los siguientes, pues el mínimo de elementos es 1...
                          datosApuesta = new String[4+1];//puesto que debe haber un espacio por cada atributo de la clase Error y el +1 por la columna de la numeración xD
              
                          datosApuesta[0] = String.valueOf(filaActual);
                          datosApuesta[1] = apuesta.getNombrePostor();                        
                          datosApuesta[2] = errorActual.getContent().getElError();
                          datosApuesta[3] = errorActual.getContent().getTIpo();
                          datosApuesta[4] = errorActual.getContent().getDescripcion();
            
                          modelo.addRow(datosApuesta);                          
                          errorActual = errorActual.getNext();                          
                          filaActual++;
                    }//también podría ser un doWhile xD     
                }while((nodoActual = nodoActual.getNext())!=null);
        
                tablaApuestasRechazadas.setModel(modelo);
                tablaApuestasRechazadas.updateUI();
            }         
          }        
    }//LISTO    
      
    public void addResultadoApuestas(JTable tablaResultadoApuestas, ListaEnlazada<Apuesta> apuestasAceptadas){
        if(!apuestasAceptadas.isEmpty()){
            DefaultTableModel modelo = (DefaultTableModel) tablaResultadoApuestas.getModel();
            eliminarTodosLosRegistros(modelo, tablaResultadoApuestas);
        
            Nodo<Apuesta> nodoActual = apuestasAceptadas.getFirst();
            int datoActual;
            int filaActual = 1;
         
            do{//puesto que al entrar aquí la lista al menos tendrá un dato...
                datoActual = 2;
                Apuesta apuesta =  nodoActual.getContent();
                String[] datosApuesta = new String[apuesta.getResultado().length+2+1];//2 por el nombre y el total, 1 por el No.
                datosApuesta[0] = String.valueOf(filaActual);
                datosApuesta[1] = apuesta.getNombrePostor();            
            
                while(datoActual< (datosApuesta.length-1)){//puesto que debe dejar el espacio para el totalGanado
                    datosApuesta[datoActual] = apuesta.getResultado()[datoActual-2];//puesto que está "adelantado" en 2 el valor de esa var... xD
                
                    datoActual++;
                }         
            
               datosApuesta[datosApuesta.length-1] = String.valueOf(apuesta.getTotalGanado());            
            
                modelo.addRow(datosApuesta);
                filaActual++;
            }while((nodoActual = nodoActual.getNext()) != null);        
        
            tablaResultadoApuestas.setModel(modelo);
            tablaResultadoApuestas.updateUI();//es un hecho que tendrás que descomentarlo xD
     }        
    }//LISTO
    
    private void eliminarTodosLosRegistros(DefaultTableModel modelo, JTable tabla){
        try {            
            while(tabla.getRowCount()>0){
                modelo.removeRow(0);                
            }
            
            tabla.updateUI();
        } catch (Exception e) {
            System.out.println("Error al limpiar la tabla" + e.getMessage());
        }        
    }      
    
}//ya solo falta crear la tabla y add los filechooser que se abren automáticamente en una carpeta y abren el archivo, deplano que eso útlimo con un TextArea...a menos que e fileChoose failite eso xD
//AH SI, falta implementar que al cb el filtro en el cbBox, eso cb...
