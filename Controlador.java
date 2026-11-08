import Modelo.*;
import Modelo.Excepcion.ExcepcionDistanciaCero;
import Modelo.Excepcion.InterseccionDistintaAPunto;
import Vista.*;
import Persistencia.*;

import java.util.List;
//Para manejar cifras significativas
import java.text.DecimalFormat;
//Seleccion de archivos, rutas
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
//Para capturar
import java.io.File;
import java.io.IOException;
import java.awt.image.BufferedImage;

public class Controlador {

    private final GestorObjetos modelo;
    private final VistaPrincipal vista;

    public Controlador(GestorObjetos modelo, VistaPrincipal vista) {
        this.modelo = modelo;
        this.vista = vista;
        conectar();
    }

    private void conectar() {
        PanelControles pc = vista.getPanelControles();
        pc.getBotonAgregar().addActionListener(e -> onAgregar());
        pc.getBotonInterseccion().addActionListener(e -> onInterseccion());
        pc.getBotonEliminar().addActionListener(e -> onEliminar());
        pc.getBotonCaptura().addActionListener(e -> onCaptura());
        pc.getBotonReflexion().addActionListener(e -> onReflexion());
        pc.getBotonDistancia().addActionListener(e -> onDistancia());
        pc.getBotonLimpiar().addActionListener(e -> onLimpiar());

       
        pc.setAlSeleccionar(this::recuperarDatos);
    }

    private void onAgregar() {
        PanelControles pc = vista.getPanelControles();
        try {
            String label = pc.getLabel();
            switch (pc.getTipo()) {
                case "Punto"  -> modelo.agregarPunto(label, pc.getCoordX(), pc.getCoordY());
                case "Vector" -> modelo.agregarVector(label, pc.getCoordX(), pc.getCoordY());
                case "Recta"  -> modelo.agregarRecta(label, pc.getCoordX(), 
                                    pc.getCoordXf(), 
                                    pc.getCoordY(), 
                                    pc.getCoordYf());
            }
        } catch (NumberFormatException e) {
            error("Coordenadas inválidas");
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
        }
    }

    private void onInterseccion() {
        List<String> sel = seleccionMultiple();
        if (sel == null) return;
        if (sel.size() != 2) { error("Selecciona dos objetos"); return;}

        String label1 = sel.get(0).substring(2);
        String label2 = sel.get(1).substring(2);

        try {
            Recta r1 = modelo.buscarRecta(label1);
            Recta r2 = modelo.buscarRecta(label2);
            if (r1 == null || r2 == null) { error("Selecciona dos rectas"); return; }
            Punto p = r1.interseccion(r2);
            modelo.agregarPunto(modelo.crearLabel("interseccion"), p);
        } catch (InterseccionDistintaAPunto e) {
            error(e.getMessage());
        }
    }

    private void onEliminar() {
        String sel = seleccion();
        if(sel == null) return;

        String label = sel.substring(2);
        String type = sel.substring(0, 1);
        
        switch(type) {
            case "R" -> modelo.removerRecta(label);
            case "P" -> modelo.removerPunto(label);
            case "V" -> modelo.removerVector(label);
            case "S" -> modelo.removerSegmento(label);
        }
    }
    
    private void onReflexion() {
        String sel = seleccion();
        if(sel == null) return;

        PanelControles pc = vista.getPanelControles();

        String label = sel.substring(2);
        String type = sel.substring(0, 1);

        String refleccion = pc.getReflexion();
        
        switch(type) {
            case "R" -> {
                Recta r = modelo.buscarRecta(label);
                if(refleccion == "Eje-x") r.reflejarSobreX(); modelo.agregarRecta(label, r);
                if(refleccion == "Eje-y") r.reflejarSobreY(); modelo.agregarRecta(label, r); 
            }
            case "V" -> {
                VectorGeo v = modelo.buscarVector(label);
                if(refleccion == "Eje-x") v.reflejarSobreX(); modelo.agregarVector(label, v);
                if(refleccion == "Eje-y") v.reflejarSobreY(); modelo.agregarVector(label, v);
            }
            default -> error("Al obj. seleccionado no le corresponde esta transformacion");
        }
    }

    private void onDistancia() { 
        List<String> sel = seleccionMultiple();
        if(sel == null) return;
        if(sel.size() != 2) { error("Selecciona dos objetos"); return;}

        String label = sel.get(0).substring(2);
        String type = sel.get(0).substring(0, 1);
        String label1 = sel.get(1).substring(2);
        String type1 = sel.get(1).substring(0, 1);

        if(!type.equals(type1)) {error("Combinacion invalida"); return;}

        try {
            switch(type) {
                case "R" -> {
                    Recta r = modelo.buscarRecta(label);
                    Recta r1 = modelo.buscarRecta(label1);
                    Segmento s = r.distanciaA(r1);
                    modelo.agregarSegmento(modelo.crearLabel("dist"), s);
                }
                case "P" -> {
                    Punto p = modelo.buscarPunto(label);
                    Punto p1 = modelo.buscarPunto(label1);
                    Segmento s = p.distanciaA(p1);
                    modelo.agregarSegmento(modelo.crearLabel("dist"), s);
                }
                case "V" -> {
                    VectorGeo v = modelo.buscarVector(label);
                    VectorGeo v1 = modelo.buscarVector(label1);
                    VectorGeo d = v.distanciaA(v1);
                    modelo.agregarVector(modelo.crearLabel("difV"), d);
                }
            }
        } catch (ExcepcionDistanciaCero e) {
            error(e.getMessage());
        }
    }

    private void onCambioBase() {
        // para hacer, ok?
    }

    private void onProyeccion() {
        // para hacer, ok?
    }

    private void onLimpiar() { 
        if(!confirmarLimpieza()) return;
        modelo.limpiar();
    }

    private void onCaptura() {
        JFileChooser seleccionar = new JFileChooser();
        seleccionar.setDialogTitle("Guardar captura");
        seleccionar.setSelectedFile(new File("captura.png"));
        seleccionar.setFileFilter(new FileNameExtensionFilter("Imagen PNG (*.png)", "png"));

        if (seleccionar.showSaveDialog(vista) != JFileChooser.APPROVE_OPTION) return;

        File archivo = seleccionar.getSelectedFile();
        if (archivo == null) return;

        File destino = ExportadorImagen.rutaConExtension(archivo, "png");
        if (archivo.exists() && !confirmarReemplazo(archivo.getName())) return;

        try {
            BufferedImage img = vista.getLienzo().capturar();
            ExportadorImagen.exportarPNG(img, destino);
        } catch (IllegalStateException e) {
            error(e.getMessage());
        } catch (IOException e) {
            error("No se pudo guardar" + e.getMessage());
        } 
    } 

    private boolean confirmarReemplazo(String nombre) {
        return JOptionPane.showConfirmDialog(vista,
            "Reemplazar " + nombre + "?", "Confirmar",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private boolean confirmarLimpieza() {
        return JOptionPane.showConfirmDialog(vista,
            "Desea limpiar el lienzo?", "Confirmar",
            JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    private void recuperarDatos() {
        String sel = seleccion();
        if(sel == null) return;

        PanelControles pc = vista.getPanelControles();
        String label = sel.substring(2);
        String type = sel.substring(0, 1);

        DecimalFormat df = new DecimalFormat("0.###"); // 3 decimales, como mucho

        switch(type) {
            case "R" -> {
                Recta r = modelo.buscarRecta(label);
                if(r == null) {error("La recta ya no existe"); return;}

                pc.setTipo("Recta");
                pc.setLabel(label);
                pc.setCoordX(df.format(r.getXi()).toString());
                pc.setCoordXf(df.format(r.getX()).toString());
                pc.setCoordY(df.format(r.getYi()).toString());
                pc.setCoordYf(df.format(r.getY()).toString());
            }   
            case "P" -> {
                Punto p = modelo.buscarPunto(label);
                if (p == null) {error("El punto ya no existe"); return;}

                pc.setTipo("Punto");
                pc.setLabel(label);
                pc.setCoordX(df.format(p.getX()).toString());
                pc.setCoordY(df.format(p.getY()).toString());
            }
            case "V" -> {
                VectorGeo v = modelo.buscarVector(label);
                if (v == null) {error("El vector ya no existe"); return;}

                pc.setTipo("Vector");
                pc.setLabel(label);
                pc.setCoordX(df.format(v.getX()).toString());
                pc.setCoordY(df.format(v.getY()).toString());
                pc.setNorma(df.format(v.longitud()).toString());
            }
            case "S" -> {
                Segmento s = modelo.buscarSegmento(label);
                if (s == null) {error("El segmento ya no existe"); return;}

                pc.setLabel(label);
                pc.setCoordX(df.format(s.getXi()).toString());
                pc.setCoordXf(df.format(s.getX()).toString());
                pc.setCoordY(df.format(s.getYi()).toString());
                pc.setCoordYf(df.format(s.getY()).toString());
                pc.setNorma(df.format(s.longitud()).toString());
            }
        }
    }

    // Devuelve las etiquetas del par de objetos seleccionadas + el prefijo
    private List<String> seleccionMultiple() {
        List<String> sel = vista.getPanelControles().getSeleccionEnOrden();
        if (sel.size() < 2) { error("Selecciona mas de un objeto"); return null; }
        return sel;
    } // Seleccion Multiple ordenada (antes solo seleccionaba pares)

    // Devuelve la etiqueta + el prefijo
    private String seleccion() {
        int idx = vista.getPanelControles().getLista().getSelectedIndex();
        if (idx < 0) { error("Selecciona un objeto"); return null; }
        String a = vista.getPanelControles().getLista().getModel().getElementAt(idx);
        return a;
    }

    private void error(String msg) {
        JOptionPane.showMessageDialog(vista, msg, "Error", JOptionPane.WARNING_MESSAGE);
    }
}