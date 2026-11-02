package Vista;

import Modelo.GestorObjetos;

import javax.swing.*;

public class VistaPrincipal extends JFrame {

    private final Lienzo lienzo;
    private final PanelControles panelControles;

    public VistaPrincipal(GestorObjetos modelo) {
        super("Geogebra - Clon");
        this.lienzo = new Lienzo(modelo);
        this.panelControles = new PanelControles(modelo);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, lienzo, panelControles);
        split.setResizeWeight(0.82);
        setContentPane(split);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);

        setResizable(false);
    }

    public Lienzo getLienzo() { return lienzo; }
    public PanelControles getPanelControles() { return panelControles; }
}