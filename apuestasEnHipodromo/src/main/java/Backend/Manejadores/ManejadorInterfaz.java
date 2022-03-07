/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Manejadores;

import Backend.Objetos.Apuesta.Apuesta;
import Backend.Objetos.Apuesta.ApuestaErronea;
import Backend.Objetos.EDD.ListaEnlazada;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author phily
 */
public class ManejadorInterfaz {    
    public final int REGISTRO_CARRERAS = 0;
    public final int REGISTRO_APUESTAS = 1;
    public final int REGISTRO_RECHAZOS = 2;
    private String tiposDeRegistros[] = {"ResultadosCarreras", "ResultadosApuestas", "ApuestasRechazadas"};
    
    private final int widthPositionLabel = 200;
    private final int heightPositionLabel = 57;        
    private final int fieldBacgroundLabel_widht = 800;
    private final int fieldBacgroundLabel_height = 650;
    
    private JPanel contenedorPosiciones;//puesto que debe exe una axn con este entonces es razonable que esté aquí prte xD
    private JTable tablaResultadosApuestas;
    private JComboBox filtros;
    
    //para llenar la JTable correspondiente
    private ListaEnlazada<Apuesta> apuestasAceptadas;
    private ListaEnlazada<ApuestaErronea> apuestasRechazadas;
    
    private ManejadorTablas manejadorTablas = new ManejadorTablas();
    
     public void setComponentes(JPanel contenedorPosiciones, JTable tablaResultadosApuestas, JComboBox filtros){
         this.contenedorPosiciones = contenedorPosiciones;
         this.tablaResultadosApuestas = tablaResultadosApuestas;
         this.filtros = filtros;
     }    
     
     public void setListasApuestas(ListaEnlazada<Apuesta> apuestasAceptadas, ListaEnlazada<ApuestaErronea> apuestasRechazadas){
         this.apuestasAceptadas = apuestasAceptadas;
         this.apuestasRechazadas = apuestasRechazadas;
     }
    
      public void cambiarApariencia(){
        try{ 
            javax.swing.UIManager.setLookAndFeel(javax.swing.UIManager.getSystemLookAndFeelClassName()); //aunque si jalaras el GTK+ también estaría genial y no cambiaría con SO xD
        }catch ( ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException e ){ 
           System.err.println("Imposible cargar interfaz, se usará la interfaz por defecto de Java.\nError: "+e);
        }         
    }    
      
      public void addElementosAJcomoBox(JComboBox<String> comboBox, String[] items){
          for (int elemento = 0; elemento < items.length; elemento++) {
              comboBox.addItem(items[elemento]);
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
    
    public void addApuestasAceptadas(JTable tablaApuestasAceptadas){
        this.manejadorTablas.addApuestasAceptadas(tablaApuestasAceptadas, apuestasAceptadas);        
    }//LISTO
    
      public void addApuestasRechazadas(JTable tablaApuestasRechazadas){
       this.manejadorTablas.addApuestasRechazadas(tablaApuestasRechazadas, apuestasRechazadas);
    }//LISTO    
    
      public void addResultadoApuestas(){
          this.manejadorTablas.addResultadoApuestas(tablaResultadosApuestas, apuestasAceptadas);
      }//LISTO 
      
      public void habilitarFiltros(){
          this.filtros.setEnabled(true);
      }
      
      public void mostrarContenidoRegistro(int tipoRegistro, JTextArea txtA_contenidoRegistro){
          this.agregarContenidoDeRegistro(txtA_contenidoRegistro, this.mostrarChooserRegistros(tipoRegistro));
      }
      
     private  String mostrarChooserRegistros(int tipoRegistro){
       JFileChooser fileChooser = new JFileChooser();        
        
        fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Solo archivos .txt", new String[]{"txt"});//esto para evitar que pueda seleccionar cualquier otra extesión que no sea la requerida
        fileChooser.setFileFilter(filtro);        
        fileChooser.setCurrentDirectory(new File("src/main/resources/"+ this.tiposDeRegistros[tipoRegistro]));
        
        if(fileChooser.showOpenDialog(fileChooser)==(JFileChooser.APPROVE_OPTION)){
            return fileChooser.getSelectedFile().getAbsolutePath();            
        }        
        return null;
     }
     
     private void agregarContenidoDeRegistro(JTextArea txtA_contenidoRegistro, String path) {//no se si el usaurio pueda especificar el nombre, pues de ser así entonces si podría suceder un FileIOException...
         if(path != null){
             try {         
                Scanner scanner = new Scanner(new File(path));
                     txtA_contenidoRegistro.setText("");//se limpia el txtArea...
                while(scanner.hasNext()){
                     txtA_contenidoRegistro.insert(scanner.nextLine() + "\n", txtA_contenidoRegistro.getText().length());
                }
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "Archivo no encontrado", "Error", JOptionPane.ERROR_MESSAGE);
            }
         }         
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
