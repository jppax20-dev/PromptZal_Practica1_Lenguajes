/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico;
 
import com.cunoc.promptzal_analizadorlexico.lexer.Token;
import com.cunoc.promptzal_analizadorlexico.reportes.ErrorLexico;
 
import java.util.List;

/**
 *
 * @author jppax
 */
public class MostrarConsola {
 
    public static void mostrarTabla(List<Token> tokens, List<ErrorLexico> errores) {
        System.out.println("=== TOKENS ===");
        for (Token t : tokens) {
            System.out.println(t);
        }
 
        System.out.println("=== ERRORES LEXICOS ===");
        if (errores.isEmpty()) {
            System.out.println("No se encontraron errores.");
        } else {
            for (ErrorLexico e : errores) {
                System.out.println(e);
            }
        }
    }
}