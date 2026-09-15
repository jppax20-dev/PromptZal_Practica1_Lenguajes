/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.automata;

/**
 *
 * @author jppax
 */
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class GeneradorDot {

    private final TablaTransiciones tabla;

    public GeneradorDot(TablaTransiciones tabla) {
        this.tabla = tabla;
    }

    public String generarDot() {
        StringBuilder sb = new StringBuilder();
        sb.append("digraph AFD_PromptZal {\n");
        sb.append("    rankdir=LR;\n");
        sb.append("    node [shape=circle, fontname=\"Helvetica\"];\n\n");

        // Nodo invisible para marcar el estado inicial
        sb.append("    inicio [shape=point];\n");
        sb.append("    inicio -> ").append(tabla.getEstadoInicial()).append(";\n\n");

        // Estados de aceptacion como doble circulo, con el token como etiqueta
        for (String estado : tabla.getAceptacion().keySet()) {
            String token = tabla.getAceptacion().get(estado);
            sb.append("    ").append(estado)
              .append(" [shape=doublecircle, label=\"").append(estado)
              .append("\\n").append(token).append("\"];\n");
        }

        // Estado de error, resaltado aparte
        sb.append("    ").append(tabla.getEstadoError())
          .append(" [shape=circle, style=filled, fillcolor=lightcoral];\n\n");

        // Transiciones
        List<TablaTransiciones.Transicion> transiciones = tabla.getTransiciones();
        for (TablaTransiciones.Transicion t : transiciones) {
            sb.append("    ").append(t.origen)
              .append(" -> ").append(t.destino)
              .append(" [label=\"").append(t.simbolo).append("\"];\n");
        }

        sb.append("}\n");
        return sb.toString();
    }

    // Escribe el archivo .dot y ejecuta Graphviz para generar la imagen PNG.
     
    public void generarImagen(String rutaSalidaSinExtension) throws IOException, InterruptedException {
        String dot = generarDot();
        Path archivoDot = Path.of(rutaSalidaSinExtension + ".dot");
        Files.writeString(archivoDot, dot);

        ProcessBuilder pb = new ProcessBuilder(
            "dot", "-Tpng", archivoDot.toString(),
            "-o", rutaSalidaSinExtension + ".png"
        );
        pb.redirectErrorStream(true);
        Process proceso = pb.start();

        // Leer la salida para evitar que el proceso se bloquee, y para depurar si falla
        String salida = new String(proceso.getInputStream().readAllBytes());
        int codigo = proceso.waitFor();

        if (codigo != 0) {
            throw new IOException("Graphviz fallo al generar la imagen. Salida: " + salida);
        }
    }
}
