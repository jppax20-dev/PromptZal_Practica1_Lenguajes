/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.automata;

public class GeneradorGrafico {

    public static void main(String[] args) throws Exception {
        TablaTransiciones tabla = new TablaTransiciones();
        GeneradorDot generador = new GeneradorDot(tabla);
        generador.generarImagen("grafo_afd_completo");
        System.out.println("Imagen del AFD completo generada.");
    }
}