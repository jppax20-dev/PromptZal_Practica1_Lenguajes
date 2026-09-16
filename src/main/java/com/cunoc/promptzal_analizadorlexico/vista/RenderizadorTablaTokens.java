/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.vista;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.Color;

public class RenderizadorTablaTokens extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        // Java dibuje la celda original primero
        Component celda = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            return celda;
        }

        // Obtener el texto que está en la columna "Tipo" 
        // Convertimos a String para poder compararlo
        String tipoToken = table.getModel().getValueAt(row, 2).toString();

        // Elegir el color de fondo dependiendo del tipo exacto
        switch (tipoToken) {
            case "DIRECTIVA":
                celda.setBackground(new Color(173, 216, 230)); 
                break;
            case "PALABRA_RESERVADA":
                celda.setBackground(new Color(221, 160, 221)); 
                break;
            case "COMANDO_IA":
                celda.setBackground(new Color(255, 204, 153)); 
                break;
            case "CADENA":
                celda.setBackground(new Color(170, 240, 170)); 
                break;
            case "IDENTIFICADOR":
                celda.setBackground(new Color(255, 255, 204)); 
                break;
            case "ENTERO":
            case "DECIMAL":
                celda.setBackground(new Color(224, 255, 255)); 
                break;
            default:
                // Para símbolos, llaves, paréntesis, conectores, etc.
                celda.setBackground(Color.WHITE); 
                break;
        }
        
        // Asegurar que el texto siempre sea negro
        celda.setForeground(Color.BLACK);

        return celda;
    }
}