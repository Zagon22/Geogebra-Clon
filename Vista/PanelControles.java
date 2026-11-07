package Vista;

import Modelo.GestorObjetos;
import Modelo.ModeloListener;

import javax.swing.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class PanelControles extends JPanel implements ModeloListener {

    private final GestorObjetos modelo;
    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private final JList<String> lista = new JList<>(modeloLista);
    private final List<String> seleccionEnOrden = new ArrayList<>();

    private final JTextField campoLabel = new JTextField(6);
    private final JTextField campoX = new JTextField(4);
    private final JTextField campoY = new JTextField(4);
    private final JTextField campoXf = new JTextField(4);
    private final JTextField campoYf = new JTextField(4);
    private final JTextField norma = new JTextField(4);
    private final JComboBox<String> tipo = new JComboBox<>(new String[]{"Punto", "Vector", "Recta"});
    private final JComboBox<String> reflexion = new JComboBox<>(new String[]{"Eje-x", "Eje-y"});
    private final JComboBox<String> proyeccion = new JComboBox<>(new String[]{"Escalar", "Vectorial"});
    private final JButton botonAgregar = new JButton("Agregar");
    private final JButton botonInterseccion = new JButton("Intersección");
    private final JButton botonEliminar = new JButton("Eliminar");
    private final JButton botonDistancia = new JButton("Distancia");
    private final JButton botonReflexion = new JButton("Reflejar");
    private final JButton botonCaptura = new JButton("Capturar");
    private final JButton botonLimpiar = new JButton("Limpiar");
    private final JButton botonProyectar = new JButton("Proyectar");
    private final JButton botonCambiarBase = new JButton("Cambiar Base");

    private boolean refrescando = false;
    private Runnable alSeleccionar;

    public PanelControles(GestorObjetos modelo) {
        this.modelo = modelo;
        modelo.addModeloListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(new JScrollPane(lista));
        add(fila("Label", campoLabel));
        add(fila("X", campoX));
        add(fila("Y", campoY));
        add(Box.createVerticalStrut(4));
        add(fila("Xf", campoXf));
        add(fila("Yf", campoYf));
        add(Box.createVerticalStrut(4));
        add(fila("Norma", norma));
        add(Box.createVerticalStrut(4));
        add(fila("Tipo", tipo));
        add(Box.createVerticalStrut(8));
        add(fila(botonAgregar, botonEliminar));
        add(Box.createVerticalStrut(8));
        add(fila(botonInterseccion, botonDistancia, botonCambiarBase));
        add(Box.createVerticalStrut(8));
        add(fila("Reflexion", reflexion));
        add(Box.createVerticalStrut(4));
        add(botonReflexion);
        add(Box.createVerticalStrut(8));
        add(fila("Proyeccion", proyeccion));
        add(Box.createVerticalStrut(4));
        add(botonProyectar);
        add(Box.createVerticalStrut(8));
        add(fila(botonCaptura, botonLimpiar));
        
        norma.setEditable(false);
        botonReflexion.setEnabled(false);
        reflexion.setEnabled(false);

        tipo.addItemListener(e -> actualizarEstadoCamposRecta());
        actualizarEstadoCamposRecta();

        tipo.addItemListener(e -> actualizarEstadoCamposVector());
        actualizarEstadoCamposVector();
        
        tipo.addItemListener(e -> {
            boolean permiteReflexion = "Vector".equals(tipo.getSelectedItem()) || "Recta".equals(tipo.getSelectedItem());
            reflexion.setEnabled(permiteReflexion);
            botonReflexion.setEnabled(permiteReflexion);
        });

        lista.addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            List<String> actuales = lista.getSelectedValuesList();
            List<String> previos  = new ArrayList<>(seleccionEnOrden);
            previos.retainAll(actuales); // mantiene solo los elementos que coinciden con los elementos seleccionados

            for (String v : actuales) {
                if (!previos.contains(v)) previos.add(v);
            }
            seleccionEnOrden.clear();
            seleccionEnOrden.addAll(previos);

            if (e.getValueIsAdjusting() || refrescando) return;
            if (lista.getSelectedValue() == null) return;
            if (alSeleccionar != null) alSeleccionar.run();
        });
        
        refrescarLista();
    }

    private JPanel fila(String etiqueta, JComponent c) {
        JPanel p = new JPanel(new BorderLayout());
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        JLabel lbl = new JLabel(etiqueta);
        lbl.setPreferredSize(new Dimension(80, 24));
        lbl.setMaximumSize(new Dimension(80, 24));
        p.add(lbl);
        p.add(Box.createRigidArea(new Dimension(6, 0)));

        c.setPreferredSize(new Dimension(100, 24));
        c.setMaximumSize(new Dimension(100, 24));
        p.add(c);

        p.add(Box.createHorizontalGlue());
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        return p;
    }

    private JPanel fila(JComponent... c) {
        JPanel p = new JPanel(new BorderLayout());
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));

        for(int i = 0; i < c.length; i++) {
            c[i].setPreferredSize(new Dimension(90, 24));
            c[i].setMaximumSize(new Dimension(90, 24));
            if (c[i] instanceof AbstractButton) {
                ((AbstractButton) c[i]).setMargin(new Insets(2, 2, 2, 2));
            }
            p.add(c[i]);
            if(i != c.length - 1) p.add(Box.createHorizontalStrut(12));
        }

        p.add(Box.createHorizontalGlue());
        p.setMaximumSize(new Dimension(Integer.MAX_VALUE, 28));
        p.setAlignmentX(Component.LEFT_ALIGNMENT);

        return p;
    }

    private void actualizarEstadoCamposRecta() {
        boolean esRecta = "Recta".equals(tipo.getSelectedItem());
        campoXf.setEnabled(esRecta);
        campoYf.setEnabled(esRecta);
        botonInterseccion.setEnabled(esRecta);
    }

    private void actualizarEstadoCamposVector() {
        boolean esVector = "Vector".equals(tipo.getSelectedItem());
        norma.setEnabled(esVector);
        proyeccion.setEnabled(esVector);
        botonProyectar.setEnabled(esVector);
        botonCambiarBase.setEnabled(esVector);
    }

    // Escucha al modelo, y cada vez que cambia, refresca la lista de objetos.
    @Override public void modeloCambiado() { refrescarLista(); }

    // Obtiene solo las keys del modelo (primero lo hace cuando se incializa, y eventualmente por cada push from below)
    private void refrescarLista() {
        try {
            refrescando = true; // flagea que esta actualizandose la lista
            lista.clearSelection();
            modeloLista.clear();

            modelo.getPuntos().forEach((k, v)   -> modeloLista.addElement("P:" + k));
            modelo.getVectores().forEach((k, v) -> modeloLista.addElement("V:" + k));
            modelo.getRectas().forEach((k, v)   -> modeloLista.addElement("R:" + k));
            modelo.getSegmentos().forEach((k, v)   -> modeloLista.addElement("S:" + k));
        } finally {
            refrescando = false;
        }
    }

    public JButton getBotonAgregar()      { return botonAgregar; }
    public JButton getBotonInterseccion() { return botonInterseccion; }
    public JButton getBotonDistancia()    { return botonDistancia; }
    public JButton getBotonEliminar()     { return botonEliminar; }
    public JButton getBotonCaptura()      { return botonCaptura; }
    public JButton getBotonReflexion()    { return botonReflexion; }
    public JButton getBotonLimpiar()      { return botonLimpiar; }
    public JList<String> getLista()       { return lista; }
    public List<String> getSeleccionEnOrden() {return seleccionEnOrden; }

    public String getLabel() {
        String s = campoLabel.getText().trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Falta la etiqueta");
        return s;
    }
    public double getCoordX()   { return Double.parseDouble(campoX.getText().trim()); }
    public double getCoordY()   { return Double.parseDouble(campoY.getText().trim()); }
    public double getCoordXf()  { return Double.parseDouble(campoXf.getText().trim()); }
    public double getCoordYf()  { return Double.parseDouble(campoYf.getText().trim()); }
    public double getNorma()    { return Double.parseDouble(norma.getText().trim()); }
    public String getTipo() { return (String) tipo.getSelectedItem(); }
    public String getReflexion() { return (String) reflexion.getSelectedItem(); }

    public void setCoordX(String valor) { campoX.setText(valor); }
    public void setCoordY(String valor) { campoY.setText(valor); }
    public void setCoordXf(String valor) { campoXf.setText(valor); }
    public void setCoordYf(String valor) { campoYf.setText(valor); }
    public void setNorma(String valor) { norma.setText(valor); }
    public void setLabel(String etiqueta) { campoLabel.setText(etiqueta); }
    public void setTipo(String obj) { tipo.setSelectedItem(obj); }
    public void setAlSeleccionar(Runnable r) { this.alSeleccionar = r; }

}