/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.reportes;
import com.cunoc.promptzal_analizadorlexico.lexer.Token;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.List; 

/**
 *
 * @author jppax
 */
public class GenerarReporte {
    //Se usa append para crear las celdas individuales 
    //pasaar tokens a una manera de visualisar en una web 
    public void generarReporteTokens(List<Token> tokens, String ruta) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html lang=\"es\">\n<head>\n");
        html.append("<meta charset=\"UTF-8\">\n<title>Reporte de Tokens - PromptZal</title>\n");
        html.append(estilosHTML());
        html.append("</head>\n<body>\n");
        html.append("<h1>Reporte de Tokens</h1>\n");
        html.append("<p>Total de tokens reconocidos: ").append(tokens.size()).append("</p>\n");
 
        html.append("<table>\n<tr>");
        html.append("<th>No.</th><th>Lexema</th><th>Tipo</th><th>Fila</th><th>Columna</th>");
        html.append("</tr>\n");
 
        for (Token t : tokens) {
            html.append("<tr>");
            html.append("<td>").append(t.getNumero()).append("</td>");
            html.append("<td>").append(escaparHTML(t.getLexema())).append("</td>");
            html.append("<td>").append(t.getTipo()).append("</td>");
            html.append("<td>").append(t.getFila()).append("</td>");
            html.append("<td>").append(t.getColumna()).append("</td>");
            html.append("</tr>\n");
        }
 
        html.append("</table>\n</body>\n</html>");
 
        escribirArchivo(ruta, html.toString());
    }
 
    //genera una tabla de errores 
     // Crea la descripcion, fila y columna. Si no hay errores, lo indica explicitamente.
    public void generarReporteErrores(List<ErrorLexico> errores, String ruta) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html lang=\"es\">\n<head>\n");
        html.append("<meta charset=\"UTF-8\">\n<title>Reporte de Errores Lexicos - PromptZal</title>\n");
        html.append(estilosHTML());
        html.append("</head>\n<body>\n");
        html.append("<h1>Reporte de Errores Lexicos</h1>\n");
        //ver si la lista esta vacia 
        if (errores.isEmpty()) {
            html.append("<p class=\"sin-errores\">No se encontraron errores lexicos en el archivo analizado.</p>\n");
        } else {
            html.append("<p>Total de errores encontrados: ").append(errores.size()).append("</p>\n");
            html.append("<table>\n<tr>");
            html.append("<th>Lexema / Caracter</th><th>Descripcion del error</th><th>Fila</th><th>Columna</th>");
            html.append("</tr>\n");
            //recorre los errores uno por uno, va creando filas y columnas conforme avanza;
            for (ErrorLexico e : errores) {
                html.append("<tr>");
                html.append("<td>").append(escaparHTML(e.getLexema())).append("</td>");
                html.append("<td>").append(escaparHTML(e.getDescripcion())).append("</td>");
                html.append("<td>").append(e.getFila()).append("</td>");
                html.append("<td>").append(e.getColumna()).append("</td>");
                html.append("</tr>\n");
            }
            html.append("</table>\n");
        }
 
        html.append("</body>\n</html>");
 
        escribirArchivo(ruta, html.toString());
    }
 
     //Reemplaza caracteres especiales de HTML para que el contenido de los
      //lexemas (comillas, simbolos, etc.) no rompa la estructura de la tabla.
    private String escaparHTML(String texto) {
        if (texto == null) {
            return "";
        }
        return texto
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
 
    private String estilosHTML() {
        return "<style>\n"
                + "body { font-family: Arial, sans-serif; margin: 30px; background-color: #f5f5f5; }\n"
                + "h1 { color: #1a3c6e; }\n"
                + "table { border-collapse: collapse; width: 100%; background-color: white; }\n"
                + "th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }\n"
                + "th { background-color: #1a3c6e; color: white; }\n"
                + "tr:nth-child(even) { background-color: #f2f2f2; }\n"
                + ".sin-errores { color: green; font-weight: bold; }\n"
                + "</style>\n";
    }
 
    private void escribirArchivo(String ruta, String contenido) throws IOException {
        try (FileWriter writer = new FileWriter(ruta)) {
            writer.write(contenido);
        }
    }
}
