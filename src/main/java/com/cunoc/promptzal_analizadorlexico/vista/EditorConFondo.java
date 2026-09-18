/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.vista;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
/**
 *
 * @author jppax
 */
public class EditorConFondo extends JTextArea {
    private Image imagenFondo;

    public EditorConFondo(String nombreImagen) {
        java.net.URL urlImagen = getClass().getResource("/" + nombreImagen);
        
        if (urlImagen != null) {
            ImageIcon icono = new ImageIcon(urlImagen);
            this.imagenFondo = icono.getImage();
        } else {
            System.err.println("Error: No se encontró la imagen " + nombreImagen);
        }

        // Configuraciones viausles 
        setOpaque(false);
        setForeground(Color.BLACK);
        setFont(new java.awt.Font("Monospaced", java.awt.Font.BOLD, 16)); 
        setCaretColor(Color.BLACK);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g.create();

        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // imagen ajustada y centrada
        if (imagenFondo != null) {
            g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.08f));

            // Obtener el área visible actual 
            java.awt.Rectangle areaVisible = getVisibleRect();

            // Dimensiones originales de la imagen
            int anchoImagen = imagenFondo.getWidth(null);
            int altoImagen = imagenFondo.getHeight(null);

            double escalaX = (double) areaVisible.width * 0.7 / anchoImagen;
            double escalaY = (double) areaVisible.height * 0.7 / altoImagen;
            double escala = Math.min(escalaX, escalaY);

            int nuevoAncho = (int) (anchoImagen * escala);
            int nuevoAlto = (int) (altoImagen * escala);

            int x = areaVisible.x + (areaVisible.width - nuevoAncho) / 2;
            int y = areaVisible.y + (areaVisible.height - nuevoAlto) / 2;

            // Dibujar la imagen escalada
            g2d.drawImage(imagenFondo, x, y, nuevoAncho, nuevoAlto, this);
        }

        g2d.dispose();
        super.paintComponent(g);
    }

}
