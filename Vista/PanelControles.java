package Vista;

import Modelo.GestorObjetos;
import Modelo.ModeloListener;

import javax.swing.*;
import java.awt.*;

public class PanelControles extends JPanel implements ModeloListener {

    private final GestorObjetos modelo;
    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private final JList<String> lista = new JList<>(modeloLista);

    private final JTextField campoLabel = new JTextField(6);
    private final JTextField campoX = new JTextField(4);
    private final JTextField campoY = new JTextField(4);
    private final JTextField campoXf = new JTextField(4);
    private final JTextField campoYf = new JTextField(4);
    private final JComboBox<String> tipo = new JComboBox<>(new String[]{"Punto", "Vector", "Recta"});
    private final JButton botonAgregar = new JButton("Agregar");
    private final JButton botonInterseccion = new JButton("Intersección");
    private final JButton botonEliminar = new JButton("Eliminar");
    private final JButton botonDistancia = new JButton("Distancia");
    private final JButton botonCaptura = new JButton("Capturar");

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
        add(fila("Tipo", tipo));
        add(Box.createVerticalStrut(8));
        add(botonAgregar);
        add(Box.createVerticalStrut(8));
        add(botonEliminar);
        add(Box.createVerticalStrut(8));
        add(botonInterseccion);
        add(Box.createVerticalStrut(8));
        add(botonCaptura);
        // add(Box.createVerticalStrut(8));
        // add(botonDistancia);

        tipo.addItemListener(e -> actualizarEstadoCamposRecta());
        actualizarEstadoCamposRecta();

        lista.addListSelectionListener(e -> {
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
        lbl.setPreferredSize(new Dimension(60, 24));
        lbl.setMaximumSize(new Dimension(60, 24));
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

    private void actualizarEstadoCamposRecta() {
        boolean esRecta = "Recta".equals(tipo.getSelectedItem());
        campoXf.setEnabled(esRecta);
        campoYf.setEnabled(esRecta);
        botonInterseccion.setEnabled(esRecta);
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
        } finally {
            refrescando = false;
        }
    }

    public JButton getBotonAgregar()      { return botonAgregar; }
    public JButton getBotonInterseccion() { return botonInterseccion; }
    public JButton getBotonDistancia()    { return botonDistancia; }
    public JButton getBotonEliminar()     { return botonEliminar; }
    public JButton getBotonCaptura()      { return botonCaptura; }
    public JList<String> getLista()       { return lista; }

    public String getLabel() {
        String s = campoLabel.getText().trim();
        if (s.isEmpty()) throw new IllegalArgumentException("Falta la etiqueta");
        return s;
    }
    public double getCoordX()   { return Double.parseDouble(campoX.getText().trim()); }
    public double getCoordY()   { return Double.parseDouble(campoY.getText().trim()); }
    public double getCoordXf()  { return Double.parseDouble(campoXf.getText().trim()); }
    public double getCoordYf()  { return Double.parseDouble(campoYf.getText().trim()); }
    public String getTipo() { return (String) tipo.getSelectedItem(); }

    public void setCoordX(String valor) { campoX.setText(valor); }
    public void setCoordY(String valor) { campoY.setText(valor); }
    public void setCoordXf(String valor) { campoXf.setText(valor); }
    public void setCoordYf(String valor) { campoYf.setText(valor); }
    public void setLabel(String etiqueta) { campoLabel.setText(etiqueta); }
    public void setTipo(String obj) { tipo.setSelectedItem(obj); }
    public void setAlSeleccionar(Runnable r) { this.alSeleccionar = r; }

}