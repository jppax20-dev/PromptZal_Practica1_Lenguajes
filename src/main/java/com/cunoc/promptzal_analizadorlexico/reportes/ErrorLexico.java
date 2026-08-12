/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.reportes;

/**
 *
 * @author jppax
 */
public class ErrorLexico {
   private final String lexema;
    private final String descripcion;
    private final int fila;
    private final int columna;
 
    public ErrorLexico(String lexema, String descripcion, int fila, int columna) {
        this.lexema = lexema;
        this.descripcion = descripcion;
        this.fila = fila;
        this.columna = columna;
    }
 
    public String getLexema() {
        return lexema;
    }
 
    public String getDescripcion() {
        return descripcion;
    }
 
    public int getFila() {
        return fila;
    }
 
    public int getColumna() {
        return columna;
    }
 
    @Override
    public String toString() {
        return String.format("Error: '%s'  %s  (fila %d, col %d)",
                lexema, descripcion, fila, columna);
    } 
}
