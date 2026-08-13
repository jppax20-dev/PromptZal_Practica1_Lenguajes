/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.cunoc.promptzal_analizadorlexico;
import com.cunoc.promptzal_analizadorlexico.lexer.TipoToken;
import com.cunoc.promptzal_analizadorlexico.lexer.Token;
import com.cunoc.promptzal_analizadorlexico.lexer.Lexer;
import com.cunoc.promptzal_analizadorlexico.reportes.ErrorLexico;

/**
 *
 * @author jppax
 */
public class PromptZAL_AnalizadorLexico {

    public static void main(String[] args) {
        // Prueba, con un error a proposito
        String codigoPrueba =
                "@modelo \"claude-sonnet-4-6\"\n" +
                "@rol \"analista de datos\"\n" +
                "// Agente que prepara el analisis\n" +
                "AGENTE analista {\n" +
                "  contexto = \"Eres un analista de datos experto\"\n" +
                "  variable ventas = CARGAR(\"ventas.csv\")\n" +
                "  PREGUNTAR \"Cuales son las 3 tendencias?\" SOBRE ventas -> tendencias\n" +
                "  RESUMIR tendencias EN 100 palabras -> resumen\n" +
                "}\n" +
                "EJECUTAR analista\n" +
                "EXPORTAR resumen #\n"; // el '#' al final es un error a proposito
 
        Lexer lexer = new Lexer(codigoPrueba);
        lexer.analizar();
 
        System.out.println("=== TOKENS RECONOCIDOS ===");
        for (Token t : lexer.getTokens()) {
            System.out.println(t);
        }
 
        System.out.println("\n=== ERRORES LEXICOS ===");
        if (lexer.getErrores().isEmpty()) {
            System.out.println("No se encontraron errores.");
        } else {
            for (ErrorLexico e : lexer.getErrores()) {
                System.out.println(e);
            }
        }
    }
}
