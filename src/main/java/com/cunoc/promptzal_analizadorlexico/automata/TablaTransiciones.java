/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.automata;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
/**
 *
 * @author jppax
 */
public class TablaTransiciones {
    public static class Transicion {
        public final String origen;
        public final String simbolo;
        public final String destino;

        public Transicion(String origen, String simbolo, String destino) {
            this.origen = origen;
            this.simbolo = simbolo;
            this.destino = destino;
        }
    }

    private final List<Transicion> transiciones;
    private final Map<String, String> aceptacion; // estado -> token que emite
    private final String estadoInicial;
    private final String estadoError;

    public TablaTransiciones() {
        this.transiciones = new ArrayList<>();
        this.aceptacion = new LinkedHashMap<>();
        this.estadoInicial = "q0";
        this.estadoError = "qerr";
        construir();
    }

    private void construir() {
        // Identificadores / palabras reservadas / comandos / conectores de palabra
        agregar("q0", "L", "q1");
        agregar("q1", "L", "q1");
        agregar("q1", "D", "q1");
        aceptar("q1", "IDENTIFICADOR");

        // Directivas
        agregar("q0", "@", "q2");
        agregar("q2", "L", "q3");
        agregar("q3", "L", "q3");
        agregar("q3", "D", "q3");
        aceptar("q3", "DIRECTIVA");

        // Enteros y decimales
        agregar("q0", "D", "q4");
        agregar("q4", "D", "q4");
        aceptar("q4", "ENTERO");
        agregar("q4", ".", "q5");
        agregar("q5", "D", "q6");
        agregar("q6", "D", "q6");
        aceptar("q6", "DECIMAL");

        // Cadenas
        agregar("q0", "\\\"", "q7");
        agregar("q7", "C", "q7");
        agregar("q7", "\\\"", "q8");
        aceptar("q8", "CADENA");

        // Operadores
        agregar("q0", "=", "q9");
        aceptar("q9", "OP_ASIGNACION");
        agregar("q0", "+", "q10");
        aceptar("q10", "OP_CONCATENACION");

        // Conector flecha
        agregar("q0", "-", "q11");
        agregar("q11", ">", "q12");
        aceptar("q12", "CONECTOR");

        // Delimitadores
        agregar("q0", "{", "q17");
        aceptar("q17", "DELIMITADOR");
        agregar("q0", "}", "q18");
        aceptar("q18", "DELIMITADOR");
        agregar("q0", "(", "q19");
        aceptar("q19", "DELIMITADOR");
        agregar("q0", ")", "q20");
        aceptar("q20", "DELIMITADOR");

        // Comentarios
        agregar("q0", "/", "q13");
        agregar("q13", "/", "q14");
        agregar("q14", "N", "q14");
        agregar("q14", "\\\\n", "q0");
        agregar("q13", "*", "q15");
        agregar("q15", "B", "q15");
        agregar("q15", "*", "q16");
        agregar("q16", "*", "q16");
        agregar("q16", "B", "q15");
        agregar("q16", "/", "q0");

        // Espacios en blanco: se ignoran, q0 se mantiene
        agregar("q0", "esp", "q0");
        agregar("q0", "\\\\n", "q0");

        // Transiciones a error
        agregar("q2", "otro", estadoError);
        agregar("q5", "otro", estadoError);
        agregar("q7", "\\\\n", estadoError);
        agregar("q11", "otro", estadoError);
        agregar("q13", "otro", estadoError);
        agregar("q0", "otro", estadoError);

        // Retorno a q0 desde cada estado de aceptación
        for (String estado : new ArrayList<>(aceptacion.keySet())) {
            agregar(estado, "fin token", estadoInicial);
        }
    }

    private void agregar(String origen, String simbolo, String destino) {
        transiciones.add(new Transicion(origen, simbolo, destino));
    }

    private void aceptar(String estado, String token) {
        aceptacion.put(estado, token);
    }

    public List<Transicion> getTransiciones() {
        return transiciones;
    }

    public Map<String, String> getAceptacion() {
        return aceptacion;
    }

    public boolean esAceptacion(String estado) {
        return aceptacion.containsKey(estado);
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public String getEstadoError() {
        return estadoError;
    }

    public List<String> getEstados() {
        List<String> estados = new ArrayList<>();
        for (Transicion t : transiciones) {
            if (!estados.contains(t.origen)) estados.add(t.origen);
            if (!estados.contains(t.destino)) estados.add(t.destino);
        }
        return estados;
    }
}
