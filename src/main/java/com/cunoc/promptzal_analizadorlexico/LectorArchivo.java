/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
/**
 *
 * @author jppax
 */
//usando Filereader y Buffer reader lee el archivo, si no encuentra nada regresa null
public class LectorArchivo {
    //es el encargado de leer el archivo y guardarlo mientras tanto en la ram 
    public static String leer(String ruta){
        StringBuilder sb = new StringBuilder();
                
        try(BufferedReader br = new BufferedReader(new FileReader(ruta))){
            String linea; 
            while ((linea = br.readLine()) != null){
                sb.append(linea).append("\n");
            }
        } catch (IOException e) {
            System.out.println(" No se pudo leer el archivo" + e.getMessage());
            return null;       
        }        
        return sb.toString();
    }
    
} 
