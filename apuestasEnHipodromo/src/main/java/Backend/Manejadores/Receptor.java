/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Herramientas.Herramientas;
import Backend.Objetos.EDD.ListaEnlazada;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

/**
 *
 * @author phily
 */
public class Receptor {
    private Herramientas herramienta;
    private ListaEnlazada<String[]> apuestasRecibidas;    
    
    public Receptor(){
        herramienta = new Herramientas();
        apuestasRecibidas = new ListaEnlazada<>();
    }
    
    public void leerArchivoEntrada(String path){
         if(path!=null){
            try(BufferedReader buffer = new BufferedReader(new FileReader(path))){//ya que es un path con recursos no es nec el finally xD
                String lineaAEstudiar;                
                
                while((lineaAEstudiar=buffer.readLine())!=null){                                                            
                    addApuesta(herramienta.eliminarEspaciosDeElementos(lineaAEstudiar.split(","))); 
                }
                
            }catch(FileNotFoundException exc){                                
                JOptionPane.showMessageDialog(null, "No existe archivo alguno\nen el path provisto", "", JOptionPane.ERROR_MESSAGE);                                
            } catch (IOException ex) {
               JOptionPane.showMessageDialog(null, "Ocurrio un error al\nintentar obtener los datos", "", JOptionPane.ERROR_MESSAGE);               
            }finally{
                
            }
        }
   }
    
    /*SERVICIO CRÍTICO 1 -> O = O(1)*/
    private void addApuesta(String[] datosApuesta){
          apuestasRecibidas.add(datosApuesta);
    }
    
    public ListaEnlazada<String[]> getApuestasRecibidas(){
        return this.apuestasRecibidas;
    }
}

  //Vamos a trabajar así
    /*En primer lugar al leer el archivo formaremos una cola de APuestas, quue se revisarán posteriormente
    en la revisión, si se encuentra un error, se creará un objeto error (o apuesta erronea) que será
    similar al objeto apuesta, solo que en este caso el arreglo contendrá los errores, de corrido, es decir
    no tendrá espacios vacíos para que cada uno esté en la ubicación en que se encontró, sino que a partir
    de la info, se creaá un objeto error corresp al elemento revisado del arreglo del objeto apuesta actual de la 
    cola, este contendrá la info nece para especificar el tipo. Entonces los objetos apuesta que queden en la
    cola corresp a apuestas aprobadas, y se add a la tabla, yo diría que después de finalizar todo, pero no sé
    aunque quizá si por el hecho que todo debe ser paso a pasito y si add esta add a la tabla, la complex
    de tu algoritmo aumentaría y no sería por procesos específicos de revisión... luego de eso ya estás xD
    solo tendrías que hacer las revisiones y formar un objeto resultado que tendría practicamente lo mismo 
    que apuesta, solo que con el arreglo lleno con la remuneración correspondiente, aunque estaba pensando
    que quizá ese arr podría ir en el objeto apuesta, para no tener que crear otro objeto y pasar la info de un
    lado a otro, y simplemente tenerlo inicializado como null y cuando se haga la revisión de los resultados,
    se cree una instancia par él xD    
    */    