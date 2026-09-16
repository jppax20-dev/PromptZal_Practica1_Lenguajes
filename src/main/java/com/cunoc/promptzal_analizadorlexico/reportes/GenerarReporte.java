/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.reportes;

import com.cunoc.promptzal_analizadorlexico.lexer.Token;
import java.io.FileWriter;
import java.io.IOException; 
import java.util.List; 
import java.util.LinkedHashMap;
import java.util.Map;
/**
 *
 * @author jppax
 */
public class GenerarReporte {
    
    // Se usa append para crear las celdas individuales 
    public void generarReporteTokens(List<Token> tokens, String ruta) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html lang=\"es\">\n<head>\n");
        html.append("<meta charset=\"UTF-8\">\n<title>Reporte de Tokens - PromptZal</title>\n");
        html.append(estilosHTML());
        html.append("</head>\n<body>\n");
        
        // Contenedor principal para centrar el contenido
        html.append("<div class=\"container\">\n");
        html.append("<h1>Reporte de Tokens</h1>\n");
        html.append("<p>Total de tokens reconocidos: <span>").append(tokens.size()).append("</span></p>\n");

        html.append("<div class=\"table-wrapper\">\n");
        html.append("<table>\n<tr>");
        html.append("<th>No.</th><th>Lexema</th><th>Tipo</th><th>Fila</th><th>Columna</th>");
        html.append("</tr>\n");

        for (Token t : tokens) {
            String colorFondo = "#ffffff"; // Blanco por defecto
            
            // Asignación de colores para cada tipo de token
            if (t.getTipo() != null) {
                switch (t.getTipo().toString()) {
                    case "DIRECTIVA": 
                        colorFondo = "#ADD8E6"; 
                        break;
                    case "PALABRA_RESERVADA": 
                        colorFondo = "#DDA0DD";
                        break;
                    case "COMANDO_IA": 
                        colorFondo = "#FFCC99"; 
                        break;
                    case "CADENA": 
                        colorFondo = "#AAFFAA"; 
                        break;
                    case "IDENTIFICADOR": 
                        colorFondo = "#FFFFCC"; 
                        break;
                    case "ENTERO":
                    case "DECIMAL": 
                        colorFondo = "#E0FFFF"; 
                        break;
                }
            }

            html.append("<tr style=\"background-color: ").append(colorFondo).append(";\">");
            html.append("<td>").append(t.getNumero()).append("</td>");
            html.append("<td>").append(escaparHTML(t.getLexema())).append("</td>");
            html.append("<td><span class=\"badge badge-tipo\">").append(t.getTipo()).append("</span></td>");
            html.append("<td>").append(t.getFila()).append("</td>");
            html.append("<td>").append(t.getColumna()).append("</td>");
            html.append("</tr>\n");
        }

        html.append("</table>\n");
        html.append("</div>\n"); 
        html.append("</div>\n"); 
        html.append("</body>\n</html>");

        escribirArchivo(ruta, html.toString());
    }

    // genera una tabla de errores 
    // Crea la descripcion, fila y columna. Si no hay errores, lo indica explicitamente.
    public void generarReporteErrores(List<ErrorLexico> errores, String ruta) throws IOException {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html lang=\"es\">\n<head>\n");
        html.append("<meta charset=\"UTF-8\">\n<title>Reporte de Errores Léxicos - PromptZal</title>\n");
        html.append(estilosHTML());
        html.append("</head>\n<body>\n");
        
        html.append("<div class=\"container\">\n");
        html.append("<h1>Reporte de Errores Léxicos</h1>\n");
        
        // ver si la lista esta vacia 
        if (errores.isEmpty()) {
            html.append("<div class=\"sin-errores\"> No se encontraron errores léxicos en el archivo analizado.</div>\n");
        } else {
            html.append("<p>Total de errores encontrados: <span class=\"error-count\">").append(errores.size()).append("</span></p>\n");
            html.append("<div class=\"table-wrapper\">\n");
            html.append("<table>\n<tr>");
            html.append("<th>No.</th><th>Lexema / Caracter</th><th>Descripción del error</th><th>Fila</th><th>Columna</th>");
            html.append("</tr>\n");
            int contadorErrores = 1;
            // recorre los errores uno por uno, va creando filas y columnas conforme avanza;
            for (ErrorLexico e : errores) {
                html.append("<tr class=\"fila-error\">"); 
                html.append("<td>").append(contadorErrores).append("</td>");
                html.append("<td class=\"lexema-error\">").append(escaparHTML(e.getLexema())).append("</td>");
                html.append("<td>").append(escaparHTML(e.getDescripcion())).append("</td>");
                html.append("<td>").append(e.getFila()).append("</td>");
                html.append("<td>").append(e.getColumna()).append("</td>");
                html.append("</tr>\n");
                contadorErrores++;
            }
            html.append("</table>\n");
            html.append("</div>\n");
        }

        html.append("</div>\n");
        html.append("</body>\n</html>");

        escribirArchivo(ruta, html.toString());
    }

    // Reemplaza caracteres especiales de HTML para que el contenido de los
    // lexemas (comillas, simbolos, etc.) no rompa la estructura de la tabla.
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
                + "@import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap');\n"
                + "body { font-family: 'Inter', system-ui, -apple-system, sans-serif; background-color: #f8fafc; color: #1e293b; margin: 0; padding: 40px 20px; display: flex; justify-content: center; }\n"
                + ".container { max-width: 1100px; width: 100%; }\n"
                + "h1 { color: #0f172a; font-size: 2.25rem; font-weight: 700; margin-bottom: 0.5rem; letter-spacing: -0.025em; }\n"
                + "p { color: #64748b; font-size: 1.1rem; margin-top: 0; margin-bottom: 2rem; }\n"
                + "p span { background-color: #eff6ff; color: #2563eb; font-weight: 600; padding: 4px 12px; border-radius: 9999px; font-size: 0.95rem; }\n"
                + "p span.error-count { background-color: #fef2f2; color: #dc2626; }\n"
                + ".table-wrapper { background: #ffffff; border-radius: 12px; box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1); overflow: hidden; border: 2px solid #000000; }\n"
                + "table { width: 100%; border-collapse: collapse; text-align: left; }\n"
                + "th { background-color: #f8fafc; color: #475569; font-weight: 600; font-size: 0.85rem; text-transform: uppercase; letter-spacing: 0.05em; padding: 16px 24px; border-bottom: 1px solid #e2e8f0; }\n"
                + "td { padding: 16px 24px; border-bottom: 1px solid #f1f5f9; color: #334155; font-size: 0.95rem; }\n"
                + "tr:last-child td { border-bottom: none; }\n"
                + "tr:hover { background-color: #f8fafc; transition: all 0.2s ease; }\n"
                + "td:nth-child(2) { font-family: ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, monospace; color: #0369a1; font-weight: 600; }\n"
                + ".badge-tipo { background-color: #f1f5f9; color: #475569; padding: 4px 10px; border-radius: 6px; font-size: 0.85rem; font-weight: 500; border: 1px solid #e2e8f0; }\n"
                + ".sin-errores { display: inline-block; background-color: #dcfce7; color: #166534; padding: 16px 24px; border-radius: 8px; font-weight: 500; border: 1px solid #bbf7d0; box-shadow: 0 1px 2px rgba(0,0,0,0.05); }\n"
                + ".fila-error td { background-color: #fef2f2; color: #991b1b; border-bottom: 1px solid #fee2e2; }\n"
                + ".fila-error:hover td { background-color: #fee2e2; }\n"
                + ".fila-error .lexema-error { color: #b91c1c; background-color: #fee2e2; border-radius: 4px; padding: 2px 6px; }\n"
                + "</style>\n";
    }
    
    public void generarReporteEstadisticas(List<Token> tokens, List<ErrorLexico> errores,
                                        int totalLineas, String ruta) throws IOException {
    // Conteo de frecuencia por tipo de token, en el orden en que aparece cada tipo por primera vez
        Map<String, Integer> frecuencia = new LinkedHashMap<>();
        for (Token t : tokens) {
            String tipo = t.getTipo().toString();
            frecuencia.put(tipo, frecuencia.getOrDefault(tipo, 0) + 1);
        }

        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html lang=\"es\">\n<head>\n");
        html.append("<meta charset=\"UTF-8\">\n<title>Reporte de Estadísticas - PromptZal</title>\n");
        html.append(estilosHTML());
        html.append("</head>\n<body>\n");

        html.append("<div class=\"container\">\n");
        html.append("<h1>Reporte de Estadísticas</h1>\n");
        html.append("<p>Resumen general del análisis léxico</p>\n");

        // Resumen de totales
        html.append("<div class=\"table-wrapper\">\n");
        html.append("<table>\n<tr><th style=\"background-color: #DDA0DD; color: #1e293b;\">Métrica</th><th style=\"background-color: #DDA0DD; color: #1e293b;\">Valor</th></tr>\n");//html.append("<table>\n<tr><th>Métrica</th><th>Valor</th></tr>\n");
        html.append("<tr><td>Total de tokens reconocidos</td><td><span>")
            .append(tokens.size()).append("</span></td></tr>\n");
        html.append("<tr><td>Total de líneas analizadas</td><td><span>")
            .append(totalLineas).append("</span></td></tr>\n");
        html.append("<tr><td>Total de errores léxicos</td><td><span class=\"error-count\">")
            .append(errores.size()).append("</span></td></tr>\n");
        html.append("</table>\n</div>\n");

        // Frecuencia por tipo de token
        html.append("<h1 style=\"font-size:1.5rem; margin-top:2.5rem;\">Frecuencia por tipo de token</h1>\n");
        html.append("<div class=\"table-wrapper\">\n");
        html.append("<table>\n<tr><th style=\"background-color: #ADD8E6; color: #1e293b;\">Tipo de token</th><th style=\"background-color: #ADD8E6; color: #1e293b;\">Cantidad</th></tr>\n");//html.append("<table>\n<tr><th>Tipo de token</th><th>Cantidad</th></tr>\n");
        for (Map.Entry<String, Integer> entrada : frecuencia.entrySet()) {
            html.append("<tr>");
            html.append("<td><span class=\"badge-tipo\">").append(escaparHTML(entrada.getKey())).append("</span></td>");
            html.append("<td>").append(entrada.getValue()).append("</td>");
            html.append("</tr>\n");
        }
        html.append("</table>\n</div>\n");

        html.append("</div>\n");
        html.append("</body>\n</html>");

        escribirArchivo(ruta, html.toString());
}

    private void escribirArchivo(String ruta, String contenido) throws IOException {
        try (FileWriter writer = new FileWriter(ruta)) {
            writer.write(contenido);
        }
    }
}