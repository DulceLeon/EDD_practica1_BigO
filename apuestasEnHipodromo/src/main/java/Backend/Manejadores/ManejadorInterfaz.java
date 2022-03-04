/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author phily
 */
public class ManejadorInterfaz {    
    private final int widthPositionLabel = 200;
    private final int heightPositionLabel = 57;        
    private final int fieldBacgroundLabel_widht = 800;
    private final int fieldBacgroundLabel_height = 650;
    
    private JPanel contenedorPosiciones;//puesto que debe exe una axn con este entonces es razonable que esté aquí prte xD
    
     public void setContenedorPosiciones(JPanel contenedorPosiciones){
         this.contenedorPosiciones = contenedorPosiciones;
     }    
    
      public void cambiarApariencia(){
        try{ 
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); //aunque si jalaras el GTK+ también estaría genial y no cambiaría con SO xD
        }catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ){ 
           System.err.println("Imposible cargar interfaz, se usará la interfaz por defecto de Java.\nError: "+e);
        }         
    }    
      
      public boolean elCampoEstaLleno(JTextField campo){
          return !(campo.getText().isBlank() && campo.getText().isEmpty());
      }
      
      public boolean hayDatosRepetidos(String[] datosSeleccionados){
          String[] datosUnicos = new String[] {null, null, null, null, null, null, null, null, null};//puesto que si llega al último y este no es igual, no tendría por qué estar asignando este dato al arreglo si ya no hay algo más para revisar xD
          
          for (int datoActual = 0; datoActual < datosSeleccionados.length; datoActual++) {
              if(datoActual != 0){
                  for (int elemento = 0; elemento < datosUnicos.length; elemento++) {
                      if(datosUnicos[elemento] !=null){
                           if(datosSeleccionados[datoActual].equals(datosUnicos[elemento] )){
                               System.out.println("return repetido");
                               return true;//debería para ambos for xD
                           }
                      }else{
                          datosUnicos[elemento]  = datosSeleccionados[datoActual];//par que así se tome en cta en la revisión de la prócima ronda...
                          System.out.println("break");
                          break;//debería parar el for interno únicamente
                      }
                  }
              }else{
                    datosUnicos[0] = datosSeleccionados[0];
              }              
          }
          
          return false;      
      }    
    
      public String mostrarFileChooser(){
       JFileChooser fileChooser = new JFileChooser();        
        
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Solo archivos .txt y .csv", new String[]{"txt", "csv"});//esto para evitar que pueda seleccionar cualquier otra extesión que no sea la requerida
        fileChooser.setFileFilter(filtro);        
        
        if(fileChooser.showOpenDialog(fileChooser)==(JFileChooser.APPROVE_OPTION)){
            return fileChooser.getSelectedFile().getAbsolutePath();            
        }        
        return null;
      }
            
      
    public void crearTablaPosiciones(String[]resultadosCarrera){
        contenedorPosiciones.removeAll();                
        int columna = 0;
        int fila = 0;
        
        for (int posicionActual =0; posicionActual < 10; posicionActual++) {
         //   System.out.println("posicion -> "+ posicionActual + "resultado columna-> "+ (((posicionActual+1)%3==0)?columna++:columna)+"resultado fila-> "+(((posicionActual+1)%3==0)?0:fila++));
            JLabel label = new JLabel();
            
            label.setFont(new java.awt.Font("Laksaman", 0, 18));
            label.setText((posicionActual+1) +". "+ resultadosCarrera[posicionActual]);
            System.out.println("columna"+columna);
            System.out.println("fila: "+ fila);
            label.setBounds(((columna*widthPositionLabel) + 35), ((fila*heightPositionLabel)+10)
                    ,widthPositionLabel, heightPositionLabel);
            
            columna = (((posicionActual+1)%3==0)?columna+1:columna);//por qué no funciona el operador ternario :v?
            fila = (((posicionActual+1)%3==0)?0:fila+1);
            
            if((posicionActual+1)%3 == 0){
                JSeparator separador = new JSeparator();
                separador.setOrientation(javax.swing.SwingConstants.VERTICAL);
                separador.setBounds(((columna*widthPositionLabel)), 15, 3, 155);
                contenedorPosiciones.add(separador);
            }
            contenedorPosiciones.add(label);
        }
        contenedorPosiciones.updateUI();
    }    
    
    public boolean confirmarCierreApp(JRootPane rootPane){
        
        Object [] opciones ={"Aceptar","Cancelar"};
        int eleccion = JOptionPane.showOptionDialog(rootPane,"En realidad desea realizar cerrar la aplicacion","Mensaje de Confirmacion",
        JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null,opciones,"Aceptar");
        
        return (eleccion == JOptionPane.YES_OPTION);
   }
    
    public void mostrarMsjeAgradecimiento(){
          JOptionPane.showMessageDialog(null, "Gracias por visitar nuestro Hipódromo :)\nte esperamos en una próxima", 
               "Salida", JOptionPane.INFORMATION_MESSAGE);
    } 
}
