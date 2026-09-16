/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author jppax
 */
// Usando FileWriter y BufferedWriter escribe el contenido del editor a un archivo .pz en disco
public class EscritorArchivo {
    // Guarda el texto recibido en la ruta indicada. Devuelve true si tuvo exito, false si fallo.
    public static boolean guardar(String ruta, String contenido) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta))) {
            bw.write(contenido);
            return true;
        } catch (IOException e) {
            System.out.println("No se pudo guardar el archivo: " + e.getMessage());
            return false;
        }
    }
}