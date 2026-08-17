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
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author jppax
 */
public class PromptZAL_AnalizadorLexico {
    //busca la ruta del archivo desde el almacenamiento 
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean continuar = true;

        while (continuar) {
            System.out.print("\nPor favor ingrese la ruta del archivo .pz para analizar: ");
            String ruta = sc.nextLine().trim();

            String codigo = LectorArchivo.leer(ruta);
            if (codigo == null) {
                System.out.println("No se pudo leer el archivo. Verifique la ruta.");
                continuar = preguntarSiContinuar(sc);
                continue; 
            }

            Lexer lexer = new Lexer(codigo);
            lexer.analizar();

            List<Token> tokens = lexer.getTokens();
            List<ErrorLexico> errores = lexer.getErrores();

            MostrarConsola.mostrarTabla(tokens, errores);

            // Generar nombres dinámicos basados en el archivo de entrada
            String nombreBase = obtenerNombreBase(ruta);
            String rutaTokens = nombreBase + "_reporte_tokens.html";
            String rutaErrores = nombreBase + "_reporte_errores.html";

            try {
                GenerarReporte generador = new GenerarReporte();
                generador.generarReporteTokens(tokens, rutaTokens);
                generador.generarReporteErrores(errores, rutaErrores);
                System.out.println("\nReportes generados exitosamente: ");
                System.out.println("1. " + rutaTokens);
                System.out.println("2. " + rutaErrores);
            } catch (IOException e) {
                System.out.println("Error al generar los reportes HTML: " + e.getMessage());
            }

            continuar = preguntarSiContinuar(sc);
        }

        System.out.println("\nPrograma finalizado.");
    }
    //extrae solo el nombre del archivo sin ruta para usarlo en los nombres 
    private static String obtenerNombreBase(String ruta) {
        File archivo = new File(ruta);
        String nombre = archivo.getName();
        int posPunto = nombre.lastIndexOf(".");
        if (posPunto > 0) {
            nombre = nombre.substring(0, posPunto);
        }
        return nombre;
    }  
    
    //Preguntar si se desean analizar mas archivos 
    private static boolean preguntarSiContinuar(Scanner sc) {
    while (true) {
        System.out.print("¿Desea analizar otro archivo? (s/n): ");
        String respuesta = sc.nextLine().trim().toLowerCase();
        
        if (respuesta.equals("s") || respuesta.equals("si")) {
            return true;
        } else if (respuesta.equals("n") || respuesta.equals("no")) {
            return false;
        } else {
            System.out.println("Entrada no valida. Por favor, ingrese 's' para si, o 'n' para no.");
        }
    }
}
} // final 
