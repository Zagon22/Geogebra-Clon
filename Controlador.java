import Modelo.*;
import Vista.*;

import java.text.DecimalFormat;

import javax.swing.*;

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
        /*Listeners para las otras acciones
        pc.getBotonDistancia().addActionListener(e -> onDistancia()); // por ej.

        */
       
        pc.setAlSeleccionar(this::recuperarDatos);
    }

    private void onAgregar() {
        PanelControles pc = vista.getPanelControles();
        try {
            String label = pc.getLabel();
            switch (pc.getTipo()) {
                case "Punto"  -> modelo.agregarPunto(label, pc.getCoordX(), pc.getCoordY());
                case "Vector" -> modelo.agregarVector(label, pc.getCoordX(), pc.getCoordY());
                case "Recta"  -> modelo.agregarRecta(label, pc.getCoordX(), pc.getCoordXf(), pc.getCoordY(), pc.getCoordYf());   // De momento solo grafica rectas homogeneas.
            }
        } catch (NumberFormatException ex) {
            error("Coordenadas inválidas");
        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
        }
    }

    private void onInterseccion() {
        String[] sel = seleccionDeDos();
        if (sel == null) return;

        try {
            Recta r1 = modelo.buscarRecta(sel[0]);
            Recta r2 = modelo.buscarRecta(sel[1]);
            if (r1 == null || r2 == null) { error("Selecciona dos rectas"); return; }
            Punto p = r1.interseccion(r2);
            modelo.agregarPunto(modelo.crearLabel("interseccion"), p);
        } catch (IllegalArgumentException ex) {
            error(ex.getMessage());
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
        }
    }
    
    private void onDistancia() {
        // para hacer, ok?
    }

    private void onCambioBase() {
        // para hacer, ok?
    }

    private void onProyeccion() {
        // para hacer, ok?
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
            }
        }
    }

    // Devuelve las etiquetas de las rectas seleccionadas, sin su prefijo.
    private String[] seleccionDeDos() {
        int[] idx = vista.getPanelControles().getLista().getSelectedIndices();
        if (idx.length != 2) { error("Selecciona exactamente dos objetos"); return null; }
        String a = vista.getPanelControles().getLista().getModel().getElementAt(idx[0]);
        String b = vista.getPanelControles().getLista().getModel().getElementAt(idx[1]);
        return new String[]{ a.substring(2), b.substring(2) };
    }

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