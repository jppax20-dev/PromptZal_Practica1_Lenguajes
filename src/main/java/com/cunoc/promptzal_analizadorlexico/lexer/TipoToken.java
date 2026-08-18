/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.lexer;

/**
 *
 * @author jppax
 */
public enum TipoToken {
 
    // Directivas: @modelo, @rol, @formato
    DIRECTIVA,
 
    // Palabras reservadas de estructura: AGENTE, contexto, variable, EJECUTAR, EXPORTAR
    PALABRA_RESERVADA,
 
    // Comandos de IA: PREGUNTAR, GENERAR, RESUMIR, ANALIZAR, TRADUCIR, CLASIFICAR, EXTRAER
    COMANDO_IA,
 
    // Funcion especial: CARGAR
    FUNCION,
 
    // Conectores: SOBRE, DESDE, EN, COMO, ->
    CONECTOR,
 
    // Identificadores definidos por el usuario (nombres de agentes y variables)
    // Letra o guion bajo, seguido de letras, digitos o guion bajo
    IDENTIFICADOR,
 
    // Literal de cadena: texto entre comillas dobles
    CADENA,
 
    // Literal numerico entero
    ENTERO,
 
    // Literal numerico decimal
    DECIMAL,
 
    // Operador de asignacion: =
    OP_ASIGNACION,
 
    // Operador de concatenacion: +
    OP_CONCATENACION,
 
    // Delimitadores: { } ( ) ,
    DELIMITADOR,
 
    // Fin de archivo cuando el lexer se detiene 
    EOF
}
