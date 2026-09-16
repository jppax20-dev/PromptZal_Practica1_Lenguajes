/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cunoc.promptzal_analizadorlexico.vista;

import com.cunoc.promptzal_analizadorlexico.LectorArchivo;
import com.cunoc.promptzal_analizadorlexico.EscritorArchivo;
import com.cunoc.promptzal_analizadorlexico.lexer.Lexer;
import com.cunoc.promptzal_analizadorlexico.lexer.Token;
import com.cunoc.promptzal_analizadorlexico.reportes.ErrorLexico;
import com.cunoc.promptzal_analizadorlexico.reportes.GenerarReporte;
import com.cunoc.promptzal_analizadorlexico.MostrarConsola;


import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.io.File;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VentanaPrincipal extends JFrame {

    private JTextArea areaEditor;
    private JButton botonAbrir;
    private JButton botonGuardar;
    private JButton botonAnalizar;
    private JButton botonSalir;
    private JButton botonGenerarReportes;
    private JButton botonVerAFD;

    private JTable tablaTokens;
    private JTable tablaErrores;
    private DefaultTableModel modeloTokens;
    private DefaultTableModel modeloErrores;
    private JTable tablaEstadisticas;
    private DefaultTableModel modeloEstadisticas;
    private List<Token> ultimosTokens;
    private List<ErrorLexico> ultimosErrores;
    private int totalLineas;

    private String rutaActual;

    public VentanaPrincipal() {
        configurarVentana();
        construirEditor();
        construirBarraBotones();
        construirPanelResultados();
    }

    private void configurarVentana() {
        setTitle("PromptZal - Analizador Lexico");
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
    }

    private void construirEditor() {
        areaEditor = new JTextArea();
        JScrollPane scroll = new JScrollPane(areaEditor);
        scroll.setPreferredSize(new java.awt.Dimension(900, 300));
        add(scroll, BorderLayout.CENTER);
    }

    private void construirBarraBotones() {
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.LEFT));

        botonAbrir = new JButton("Abrir");
        botonGuardar = new JButton("Guardar");
        botonAnalizar = new JButton("Analizar");
        botonGenerarReportes = new JButton("Generar Reportes HTML");
        botonVerAFD = new JButton("Ver AFD");
        botonSalir = new JButton("Salir");
        

        botonAbrir.addActionListener(e -> abrirArchivo());
        botonGuardar.addActionListener(e -> guardarArchivo());
        botonAnalizar.addActionListener(e -> analizarCodigo());
        botonGenerarReportes.addActionListener(e -> generarReportes());
        botonVerAFD.addActionListener(e -> verAFD());
        botonSalir.addActionListener(e -> System.exit(0));

        panelBotones.add(botonAbrir);
        panelBotones.add(botonGuardar);
        panelBotones.add(botonAnalizar);
        panelBotones.add(botonGenerarReportes);
        panelBotones.add(botonVerAFD);
        panelBotones.add(botonSalir);

        add(panelBotones, BorderLayout.NORTH);
    }

    private void construirPanelResultados() {
        modeloTokens = new DefaultTableModel(
        new Object[]{"No.", "Lexema", "Tipo", "Fila", "Columna"}, 0
        );
        tablaTokens = new JTable(modeloTokens);

        modeloErrores = new DefaultTableModel(
            new Object[]{"No.", "Lexema/Caracter", "Descripcion", "Fila", "Columna"}, 0
        );
        tablaErrores = new JTable(modeloErrores);

        // modelo para estadisticas (metrica y valor, mismo formato para totales y frecuencias)
        modeloEstadisticas = new DefaultTableModel(
            new Object[]{"Metrica", "Valor"}, 0
        );
        tablaEstadisticas = new JTable(modeloEstadisticas);

        JTabbedPane pestañas = new JTabbedPane();
        pestañas.addTab("Tokens", new JScrollPane(tablaTokens));
        pestañas.addTab("Errores", new JScrollPane(tablaErrores));
        pestañas.addTab("Estadisticas", new JScrollPane(tablaEstadisticas)); // NUEVO
        pestañas.setPreferredSize(new java.awt.Dimension(900, 300));

        add(pestañas, BorderLayout.SOUTH);
    }

    private void analizarCodigo() {
        String codigo = areaEditor.getText();

        Lexer lexer = new Lexer(codigo);
        lexer.analizar();
        
        this.ultimosTokens = lexer.getTokens();
        this.ultimosErrores = lexer.getErrores();
        this.totalLineas = lexer.getTotalLineas();
        
        List<Token> tokens = lexer.getTokens();
        List<ErrorLexico> errores = lexer.getErrores();
        
        MostrarConsola.mostrarTabla(tokens, errores);


        modeloTokens.setRowCount(0);
        modeloErrores.setRowCount(0);
        modeloEstadisticas.setRowCount(0); 
        
        

        for (Token t : tokens) {
            modeloTokens.addRow(new Object[]{
                t.getNumero(), t.getLexema(), t.getTipo(), t.getFila(), t.getColumna()
            });
        }

        for (ErrorLexico er : errores) {
            modeloErrores.addRow(new Object[]{
                modeloErrores.getRowCount() + 1, er.getLexema(), er.getDescripcion(), er.getFila(), er.getColumna()
            });
        }

        modeloEstadisticas.addRow(new Object[]{"Total de tokens", tokens.size()});
        modeloEstadisticas.addRow(new Object[]{"Total de lineas", lexer.getTotalLineas()});
        modeloEstadisticas.addRow(new Object[]{"Total de errores", errores.size()});

        // frecuencia por tipo de token
        java.util.Map<String, Integer> frecuencia = new java.util.LinkedHashMap<>();
        for (Token t : tokens) {
            String tipo = t.getTipo().toString();
            frecuencia.put(tipo, frecuencia.getOrDefault(tipo, 0) + 1);
        }
        for (java.util.Map.Entry<String, Integer> entrada : frecuencia.entrySet()) {
            modeloEstadisticas.addRow(new Object[]{entrada.getKey(), entrada.getValue()});
        }

        JOptionPane.showMessageDialog(this,
            "Analisis completo: " + tokens.size() + " tokens, " + errores.size() + " errores.");
    }

    private void abrirArchivo() {
        JFileChooser selector = new JFileChooser();
        int resultado = selector.showOpenDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            String contenido = LectorArchivo.leer(archivo.getAbsolutePath());

            if (contenido != null) {
                areaEditor.setText(contenido);
                rutaActual = archivo.getAbsolutePath();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo abrir el archivo.");
            }
        }
    }

    private void guardarArchivo() {
        JFileChooser selector = new JFileChooser();

        if (rutaActual != null) {
            selector.setSelectedFile(new File(rutaActual));
        }

        int resultado = selector.showSaveDialog(this);

        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivo = selector.getSelectedFile();
            boolean exito = EscritorArchivo.guardar(archivo.getAbsolutePath(), areaEditor.getText());

            if (exito) {
                rutaActual = archivo.getAbsolutePath();
                JOptionPane.showMessageDialog(this, "Archivo guardado correctamente.");
            } else {
                JOptionPane.showMessageDialog(this, "Error al guardar el archivo.");
            }
        }
    }
    
    private void generarReportes() {
        if (ultimosTokens == null || ultimosErrores == null) {
            JOptionPane.showMessageDialog(this, "Primero debe analizar el código antes de generar reportes.", "Advertencia", JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Selector configurado para elegir carpetas, no archivos individuales
        JFileChooser selectorCarpeta = new JFileChooser();
        selectorCarpeta.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        selectorCarpeta.setDialogTitle("Selecciona la carpeta donde guardar los reportes");

        int resultado = selectorCarpeta.showSaveDialog(this);

        if (resultado != JFileChooser.APPROVE_OPTION) {
            return; // el usuario cancelo
        }

        File carpetaDestino = selectorCarpeta.getSelectedFile();

        try {
            GenerarReporte generador = new GenerarReporte();

            LocalDateTime fechaActual = LocalDateTime.now();
            DateTimeFormatter formatoFecha = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
            String marcaTiempo = fechaActual.format(formatoFecha);

            String nombreArchivo = "SinNombre";
            if (rutaActual != null) {
                File archivoActual = new File(rutaActual);
                nombreArchivo = archivoActual.getName();
                if (nombreArchivo.endsWith(".pz")) {
                    nombreArchivo = nombreArchivo.substring(0, nombreArchivo.length() - 3);
                }
            }

            // Las rutas ahora se arman dentro de la carpeta que se eligio
            String rutaTokens = new File(carpetaDestino, "Reporte_Tokens_" + nombreArchivo + "_" + marcaTiempo + ".html").getAbsolutePath();
            String rutaErrores = new File(carpetaDestino, "Reporte_Errores_" + nombreArchivo + "_" + marcaTiempo + ".html").getAbsolutePath();
            String rutaEstadisticas = new File(carpetaDestino, "Reporte_Estadisticas_" + nombreArchivo + "_" + marcaTiempo + ".html").getAbsolutePath();

            generador.generarReporteTokens(ultimosTokens, rutaTokens);
            generador.generarReporteErrores(ultimosErrores, rutaErrores);
            generador.generarReporteEstadisticas(ultimosTokens, ultimosErrores, totalLineas, rutaEstadisticas);

            JOptionPane.showMessageDialog(this,
                "Reportes generados con éxito en:\n" + carpetaDestino.getAbsolutePath(),
                "Éxito",
                JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar los reportes: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    private void verAFD() {
    try {
            com.cunoc.promptzal_analizadorlexico.automata.TablaTransiciones tabla =
                new com.cunoc.promptzal_analizadorlexico.automata.TablaTransiciones();
            com.cunoc.promptzal_analizadorlexico.automata.GeneradorDot generador =
                new com.cunoc.promptzal_analizadorlexico.automata.GeneradorDot(tabla);

            generador.generarImagen("afd_promptzal");

            // Abre el PNG con el visor de imagenes predeterminado del sistema operativo
            File imagen = new File("afd_promptzal.png");
            java.awt.Desktop.getDesktop().open(imagen);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error al generar o abrir el AFD: " + ex.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VentanaPrincipal ventana = new VentanaPrincipal();
            ventana.setVisible(true);
        });
    }
}