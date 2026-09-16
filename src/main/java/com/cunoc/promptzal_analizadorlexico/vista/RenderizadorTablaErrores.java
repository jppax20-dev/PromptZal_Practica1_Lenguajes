/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.vista;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JTable;
import java.awt.Component;
import java.awt.Color;

public class RenderizadorTablaErrores extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, 
            boolean isSelected, boolean hasFocus, int row, int column) {
        
        Component celda = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

        if (isSelected) {
            return celda;
        }

        celda.setBackground(new Color(255, 204, 204)); 
        celda.setForeground(new Color(153, 0, 0));

        return celda;
    }
}