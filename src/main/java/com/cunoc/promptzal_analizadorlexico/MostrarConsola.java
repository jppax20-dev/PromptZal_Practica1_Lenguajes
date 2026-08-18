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
 
    // Constantes de colores ANSI
    private static final String RESET = "\u001B[0m";
    private static final String ROJO = "\u001B[31m";
    private static final String VERDE = "\u001B[32m";
    private static final String AMARILLO = "\u001B[33m";
    private static final String AZUL = "\u001B[34m";
    private static final String MORADO = "\u001B[35m";
    private static final String CYAN = "\u001B[36m";
    private static final String BLANCO = "\u001B[37m";

    public static void mostrarTabla(List<Token> tokens, List<ErrorLexico> errores) {
        System.out.println(CYAN + "=== TOKENS ===" + RESET);
        
        //encabezado de la tabla para los tokens
        System.out.println("---------------------------------------------------------------------------------------------------");
        System.out.printf("%-5s %-40s %-25s %-10s %-10s\n", "No.", "Lexema", "Tipo", "Fila", "Columna");
        System.out.println("---------------------------------------------------------------------------------------------------");

        for (Token t : tokens) {
            String color = BLANCO;
            
            // Asignar color según el tipo de token
            if (t.getTipo() != null) {
                switch (t.getTipo().name()) {
                    case "DIRECTIVA":
                        color = CYAN;
                        break;
                    case "PALABRA_RESERVADA":
                        color = AZUL;
                        break;
                    case "COMANDO_IA":
                        color = MORADO;
                        break;
                    case "CONECTOR":
                        color = AMARILLO;
                        break;
                    case "CADENA":
                    case "NUMERO": // Agrupa enteros y decimales 
                        color = VERDE;
                        break;
                    default:
                        color = BLANCO;
                        break;
                }
            }
            
            // Acortar lexemas muy largos (como frases enteras en cadenas) para que no rompan la tabla
            String lexemaPrint = t.getLexema();
            if (lexemaPrint.length() > 37) {
                lexemaPrint = lexemaPrint.substring(0, 37) + "...";
            }
            
            // Imprimir la fila usando printf, concatenando el color al inicio y el RESET al final
            System.out.printf(color + "%-5d %-40s %-25s %-10d %-10d" + RESET + "\n", 
                    t.getNumero(), 
                    lexemaPrint, 
                    t.getTipo(), 
                    t.getFila(), 
                    t.getColumna());
        }

        System.out.println("\n" + ROJO + "=== ERRORES LEXICOS ===" + RESET);
        if (errores.isEmpty()) {
            System.out.println(VERDE + "No se encontraron errores." + RESET);
        } else {
            System.out.println("---------------------------------------------------------------------------------------------------------");
            // encabezado errores
            System.out.printf(ROJO + "%-5s %-30s %-40s %-10s %-10s\n" + RESET, "No.", "Lexema/Caracter", "Descripcion", "Fila", "Columna");
            System.out.println("---------------------------------------------------------------------------------------------------------");
            
            int contadorError = 1; 
            
            for (ErrorLexico e : errores) {
                System.out.printf(ROJO + "%-5d %-30s %-40s %-10d %-10d\n" + RESET, 
                        contadorError,
                        e.getLexema(), 
                        e.getDescripcion(), 
                        e.getFila(), 
                        e.getColumna());
                
                contadorError++; 
            }
        }
    }
}