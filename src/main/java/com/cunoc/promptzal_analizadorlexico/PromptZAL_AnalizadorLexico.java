/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.cunoc.promptzal_analizadorlexico;
import com.cunoc.promptzal_analizadorlexico.lexer.TipoToken;
import com.cunoc.promptzal_analizadorlexico.lexer.Token;
import com.cunoc.promptzal_analizadorlexico.lexer.Lexer;
import com.cunoc.promptzal_analizadorlexico.reportes.ErrorLexico;
import com.cunoc.promptzal_analizadorlexico.reportes.GenerarReporte;
import com.cunoc.promptzal_analizadorlexico.LectorArchivo;
//herramientas 
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jppax
 */
public class PromptZAL_AnalizadorLexico {
    //busca la ruta del archivo desde el almacenamiento 
    public static void main(String [] args ){
        Scanner sc = new Scanner(System.in);
        
        System.out.print("Porfavor ingrese el archivo .pz para analizar");
        String ruta = sc.nextLine().trim();
        
        String codigo = LectorArchivo.leer(ruta);
        if(codigo == null) {
            return; // mensaje de error de leer archivo
        }
        
        Lexer lexer = new Lexer(codigo);
        lexer.analizar();
        
        List<Token> tokens = lexer.getTokens(); 
        List<ErrorLexico> errores = lexer.getErrores();
        
        MostrarConsola.mostrarTabla(tokens, errores);
        
        try {
            GenerarReporte generador = new GenerarReporte();
            generador.generarReporteTokens(tokens, "Reporte_de_tokens.html");
            generador.generarReporteErrores(errores, "Reporte_de_errores.html");
            System.out.println("Reportes Generados: 1.Reporte_de_tokens.html y 2.Reporte_de_Errores.html");
        } catch (IOException e ){ //manejo del error 
            System.out.println("Error al generar los reportes HTML:" + e.getMessage());
        }
    }   
} // final 
