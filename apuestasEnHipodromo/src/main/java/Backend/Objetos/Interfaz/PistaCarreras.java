/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backend.Objetos.Interfaz;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

/**
 *
 * @author phily
 */
public class PistaCarreras extends JPanel{        
    Image image = new ImageIcon(getClass().getResource("/pista_Hipodromo_redimensionada.png")).getImage();//pistaHipodromo_etiquetada

    @Override
    public void paint(Graphics graficas){
        graficas.drawImage(image, 0, 0, getWidth(), getHeight(), this);
        setOpaque(false);
        super.paint(graficas);
    }
}
