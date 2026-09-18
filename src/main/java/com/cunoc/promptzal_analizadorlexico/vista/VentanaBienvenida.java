/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.vista;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.net.URL;

public class VentanaBienvenida extends JFrame {

    private Image imagenOriginal;

    public VentanaBienvenida() {
        setTitle("PromptZal - Inicio");
        setSize(750, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.WHITE);

        JLabel etiquetaImagen = new JLabel("", SwingConstants.CENTER);
        try {
            URL url = getClass().getResource("/BIENVENIDO.png");
            if (url != null) {
                imagenOriginal = new ImageIcon(url).getImage();
            }
        } catch (Exception e) {}

        etiquetaImagen.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));
        add(etiquetaImagen, BorderLayout.CENTER);

        etiquetaImagen.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                if (imagenOriginal != null) {
                    int anchoContenedor = etiquetaImagen.getWidth() - 40;
                    int altoContenedor = etiquetaImagen.getHeight() - 40;

                    if (anchoContenedor > 0 && altoContenedor > 0) {
                        double proporcion = (double) imagenOriginal.getWidth(null) / imagenOriginal.getHeight(null);
                        int nuevoAncho = anchoContenedor;
                        int nuevoAlto = (int) (anchoContenedor / proporcion);

                        if (nuevoAlto > altoContenedor) {
                            nuevoAlto = altoContenedor;
                            nuevoAncho = (int) (altoContenedor * proporcion);
                        }

                        Image imgFinal = imagenOriginal.getScaledInstance(nuevoAncho, nuevoAlto, Image.SCALE_SMOOTH);
                        etiquetaImagen.setIcon(new ImageIcon(imgFinal));
                    }
                }
            }
        });

        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 40, 20));
        panelBotones.setBackground(Color.WHITE);

        JButton btnEntrar = new JButton("Ir al Analizador Léxico");
        JButton btnSalir = new JButton("Salir");

        Font fuenteBotones = new Font("Segoe UI", Font.BOLD, 15);
        btnEntrar.setFont(fuenteBotones);
        btnEntrar.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnSalir.setFont(fuenteBotones);
        btnSalir.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnEntrar.addActionListener(e -> {
            new VentanaPrincipal().setVisible(true);
            this.dispose();
        });

        btnSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(btnEntrar);
        panelBotones.add(btnSalir);
        add(panelBotones, BorderLayout.SOUTH);
    }
}