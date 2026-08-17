/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.lexer;
import com.cunoc.promptzal_analizadorlexico.reportes.ErrorLexico;

//Usamos las herramientas de lista 
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jppax
 */
public class Lexer {
    private final String codigo;
    private int posicion;
    private int fila;
    private int columna;
    private int contadorTokens;
 
    private final List<Token> tokens;
    private final List<ErrorLexico> errores;
 
    public Lexer(String codigo) {
        this.codigo = codigo;
        this.posicion = 0;
        this.fila = 1;
        this.columna = 1;
        this.contadorTokens = 1;
        this.tokens = new ArrayList<>();
        this.errores = new ArrayList<>();
    }
 
    //Se recorre todo el codigo y no se detiene anete errores 
    public void analizar() {
        while (!esFinal()) {
            char c = actual();
 
            if (c == ' ' || c == '\t' || c == '\r' || c == '\n') {
                avanzar();
            } else if (c == '/' && siguiente() == '/') {
                saltarComentarioLinea();
            } else if (c == '/' && siguiente() == '*') {
                saltarComentarioBloque();
            } else if (c == '@') {
                leerDirectiva();
            } else if (esLetra(c) || c == '_') {
                leerPalabra();
            } else if (esDigito(c)) {
                leerNumero();
            } else if (c == '"') {
                leerCadena();
            } else {
                leerSimboloOError();
            }
        }
    }
 
    // Reconocimiento de cada categoria
 
    private void leerDirectiva() {
        int filaInicio = fila;
        int colInicio = columna;
        StringBuilder sb = new StringBuilder();
        sb.append(actual()); // el '@'
        avanzar();
 
        while (!esFinal() && (esLetra(actual()) || esDigito(actual()) || actual() == '_')) {
            sb.append(actual());
            avanzar();
        }
 
        String lexema = sb.toString();
 
        // Clasificacion con switch 
        switch (lexema) {
            case "@modelo", "@rol", "@formato" ->
                agregarToken(lexema, TipoToken.DIRECTIVA, filaInicio, colInicio);
            default ->
                errores.add(new ErrorLexico(lexema, "Directiva no reconocida", filaInicio, colInicio));
        }
    }
 
    private void leerPalabra() {
        int filaInicio = fila;
        int colInicio = columna;
        StringBuilder sb = new StringBuilder();
 
        while (!esFinal() && (esLetra(actual()) || esDigito(actual()) || actual() == '_')) {
            sb.append(actual());
            avanzar();
        }
 
        String lexema = sb.toString();
        agregarToken(lexema, clasificarPalabra(lexema), filaInicio, colInicio);
    }
 
    //Reconome el tipo de token y compara los lexemas con las categorias del lenguale con un switch
    private TipoToken clasificarPalabra(String lexema) {
        switch (lexema) {
            case "AGENTE", "contexto", "variable", "EJECUTAR", "EXPORTAR" -> {
                return TipoToken.PALABRA_RESERVADA;
            }
            case "PREGUNTAR", "GENERAR", "RESUMIR", "ANALIZAR",
                 "TRADUCIR", "CLASIFICAR", "EXTRAER" -> {
                return TipoToken.COMANDO_IA;
            }
            case "CARGAR" -> {
                return TipoToken.FUNCION;
            }
            case "SOBRE", "DESDE", "EN", "COMO" -> {
                return TipoToken.CONECTOR;
            }
            default -> {
                return TipoToken.IDENTIFICADOR;
            }
        }
    }
 
    private void leerNumero() {
        int filaInicio = fila;
        int colInicio = columna;
        StringBuilder sb = new StringBuilder();
        boolean esDecimal = false;
 
        while (!esFinal() && esDigito(actual())) {
            sb.append(actual());
            avanzar();
        }
 
        // Decimal un punto seguido de al menos un digito
        if (!esFinal() && actual() == '.' && siguiente() != '\0' && esDigito(siguiente())) {
            esDecimal = true;
            sb.append(actual());
            avanzar();
            while (!esFinal() && esDigito(actual())) {
                sb.append(actual());
                avanzar();
            }
        }
 
        String lexema = sb.toString();
        agregarToken(lexema, esDecimal ? TipoToken.DECIMAL : TipoToken.ENTERO, filaInicio, colInicio);
    }
    
    //donde se reconoce cadenas, con comillas cuando cierran se registrar como token delimitador 
    //y el contenido entre las comillas se regristra como un token tipo cadena.
    private void leerCadena() {
        int filaApertura = fila;
        int colApertura = columna;
        agregarToken("\"", TipoToken.DELIMITADOR, filaApertura, colApertura);
        avanzar(); // comilla de inicio 
 
        int filaContenido = fila;
        int colContenido = columna;
        StringBuilder sb = new StringBuilder();
 
        while (!esFinal() && actual() != '"' && actual() != '\n') {
            sb.append(actual());
            avanzar();
        }
 
        if (!esFinal() && actual() == '"') {
            agregarToken(sb.toString(), TipoToken.CADENA, filaContenido, colContenido);
            agregarToken("\"", TipoToken.DELIMITADOR, fila, columna);
            avanzar(); // comilla de final 
        } else {
            // Se llego al fin de linea o del archivo sin cerrar la cadena
            errores.add(new ErrorLexico("\"" + sb, "Cadena sin cerrar", filaApertura, colApertura));
        }
    }
 
    private void leerSimboloOError() {
        int filaInicio = fila;
        int colInicio = columna;
        char c = actual();
 
        switch (c) {
            case '=' -> {
                avanzar();
                agregarToken("=", TipoToken.OP_ASIGNACION, filaInicio, colInicio);
            }
            case '+' -> {
                avanzar();
                agregarToken("+", TipoToken.OP_CONCATENACION, filaInicio, colInicio);
            }
            case '{', '}', '(', ')', ',' -> {
                avanzar();
                agregarToken(String.valueOf(c), TipoToken.DELIMITADOR, filaInicio, colInicio);
            }
            case '-' -> {
                if (siguiente() == '>') {
                    avanzar();
                    avanzar();
                    agregarToken("->", TipoToken.CONECTOR, filaInicio, colInicio);
                } else {
                    avanzar();
                    errores.add(new ErrorLexico("-", "Caracter no reconocido (se esperaba '->')", filaInicio, colInicio));
                }
            }
            default -> {
                avanzar();
                errores.add(new ErrorLexico(String.valueOf(c), "Caracter no reconocido", filaInicio, colInicio));
            }
        }
    }
 
    private void saltarComentarioLinea() {
        while (!esFinal() && actual() != '\n') {
            avanzar();
        }
    }
 
    private void saltarComentarioBloque() {
        int filaInicio = fila;
        int colInicio = columna;
        avanzar(); // '/'
        avanzar(); // '*'
 
        while (!esFinal() && !(actual() == '*' && siguiente() == '/')) {
            avanzar();
        }
 
        if (esFinal()) {
            errores.add(new ErrorLexico("/*", "Comentario de bloque sin cerrar", filaInicio, colInicio));
        } else {
            avanzar(); // '*'
            avanzar(); // '/'
        }
    }
 
    // Utilidades de recorrido
 
    private boolean esFinal() {
        return posicion >= codigo.length();
    }
 
    private char actual() {
        return codigo.charAt(posicion);
    }
 
    private char siguiente() {
        if (posicion + 1 >= codigo.length()) {
            return '\0';
        }
        return codigo.charAt(posicion + 1);
    }
 
    private void avanzar() {
        if (esFinal()) {
            return;
        }
        if (codigo.charAt(posicion) == '\n') {
            fila++;
            columna = 1;
        } else {
            columna++;
        }
        posicion++;
    }
 
    private boolean esLetra(char c) {
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z');
    }
 
    private boolean esDigito(char c) {
        return c >= '0' && c <= '9';
    }
 
    private void agregarToken(String lexema, TipoToken tipo, int fila, int columna) {
        tokens.add(new Token(contadorTokens, lexema, tipo, fila, columna));
        contadorTokens++;
    }
 
    //Getters
 
    public List<Token> getTokens() {
        return tokens;
    }
 
    public List<ErrorLexico> getErrores() {
        return errores;
    }
}
