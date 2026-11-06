package Vista;

import Modelo.*;
import PaqueteMatriz.MatrizN;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public class Lienzo extends JPanel implements ModeloListener {

    private final GestorObjetos modelo;
    private final Transformacion tr = new Transformacion();
    private Point ultimoArrastre;

    public Lienzo(GestorObjetos modelo) {
        this.modelo = modelo;
        modelo.addModeloListener(this);
        setBackground(Color.WHITE);
        setPreferredSize(new Dimension(900, 650));

        MouseAdapter ma = new MouseAdapter() {
            @Override public void mousePressed(MouseEvent e)  { ultimoArrastre = e.getPoint(); }
            @Override public void mouseReleased(MouseEvent e) { ultimoArrastre = null; }
            @Override public void mouseDragged(MouseEvent e) {
                if (ultimoArrastre != null) {
                    tr.pan(e.getX() - ultimoArrastre.x, e.getY() - ultimoArrastre.y);
                    ultimoArrastre = e.getPoint();
                    repaint();
                }
            }
        };
        addMouseListener(ma);
        addMouseMotionListener(ma);
        addMouseWheelListener(e -> {
            tr.zoomEn(e.getX(), e.getY(), e.getWheelRotation() < 0 ? 1.1 : 1 / 1.1);
            repaint();
        });
        addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) {
                tr.setViewport(getWidth(), getHeight());
            }
        });
    }

    // Dibuja nuevamente el lienzo al darse un cambio en algun objeto.
    @Override public void modeloCambiado() { repaint(); }

    @Override protected void paintComponent(Graphics g0) {
        super.paintComponent(g0);
        Graphics2D g = (Graphics2D) g0;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        tr.setViewport(getWidth(), getHeight());

        dibujarEjes(g);

        for (Map.Entry<String, Recta> e : modelo.getRectas().entrySet()) dibujarRecta(g, e.getValue(), e.getKey());
        for (Map.Entry<String, VectorGeo> e : modelo.getVectores().entrySet()) dibujarVector(g, e.getValue(), e.getKey());
        for (Map.Entry<String, Punto> e : modelo.getPuntos().entrySet()) dibujarPunto(g, e.getValue(), e.getKey());
        for (Map.Entry<String, Segmento> e : modelo.getSegmentos().entrySet()) dibujarSegmento(g, e.getValue(), e.getKey());

    }

    private void dibujarEjes(Graphics2D g) {
        Point2D o = tr.aPantalla(0, 0);
        g.setColor(new Color(0, 0, 0));
        g.draw(new Line2D.Double(0, o.getY(), getWidth(), o.getY()));
        g.draw(new Line2D.Double(o.getX(), 0, o.getX(), getHeight()));
    }

    private void dibujarRecta(Graphics2D g, Recta r, String label) {
        MatrizN<Double> p = r.parametrizar();
        double dx = p.getElemento(0, 0), dy = p.getElemento(0, 1);
        double px = p.getElemento(1, 0), py = p.getElemento(1, 1);
        
        double norm = Math.hypot(dx, dy);
        if(norm < 1e-12) return;
        double ux = dx / norm, uy = dy / norm; // Normaliza la direccion
        

        double t = (tr.getPanX() - px) * ux + (tr.getPanY() - py) * uy;
        double cx = px + t * ux;
        double cy = py + t * uy;

        double L = Math.hypot(getWidth(), getHeight()) / (2.0 * tr.getZoom()) * 1.5;
        
        Point2D a = tr.aPantalla(cx - L * ux, cy - L * uy);
        Point2D b = tr.aPantalla(cx + L * ux, cy + L * uy);

        g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
        g.setColor(new Color(0, 0, 0));
        g.setStroke(new BasicStroke(2.0f));
        g.draw(new Line2D.Double(a, b));

        dibujarEtiqueta(g, label, tr.aPantalla(px, py));
    }

    private void dibujarVector(Graphics2D g, VectorGeo v, String label) {
        Point2D a = tr.aPantalla(v.getXi(), v.getYi());   // cola (0,0) para vectores de 2 args
        Point2D b = tr.aPantalla(v.getX(),  v.getY());
        g.setColor(new Color(204, 51, 0));
        g.draw(new Line2D.Double(a, b));
        dibujarPunta(g, a, b);
        dibujarEtiqueta(g, label, b);
    }

    private void dibujarPunto(Graphics2D g, Punto p, String label) {
        Point2D s = tr.aPantalla(p.getX(), p.getY());
        g.setColor(Color.BLUE);
        g.fill(new Ellipse2D.Double(s.getX() - 3, s.getY() - 3, 6, 6));
        dibujarEtiqueta(g, label, s);
    }

    private void dibujarPunta(Graphics2D g, Point2D a, Point2D b) {
        double ang = Math.atan2(b.getY() - a.getY(), b.getX() - a.getX());
        double s = 10;
        double ax = b.getX() - s * Math.cos(ang - 0.4), ay = b.getY() - s * Math.sin(ang - 0.4);
        double bx = b.getX() - s * Math.cos(ang + 0.4), by = b.getY() - s * Math.sin(ang + 0.4);
        g.fill(new Polygon(new int[]{(int) b.getX(), (int) ax, (int) bx},
                           new int[]{(int) b.getY(), (int) ay, (int) by}, 3));
    }

    private void dibujarSegmento(Graphics2D g, Segmento s, String label) {
        Point2D a = tr.aPantalla(s.getXi(), s.getYi());   // cola (0,0) para vectores de 2 args
        Point2D b = tr.aPantalla(s.getX(),  s.getY());
        g.setColor(new Color(0, 128, 0));
        g.draw(new Line2D.Double(a, b));
        dibujarEtiqueta(g, label, b);
    }

    private void dibujarEtiqueta(Graphics2D g, String texto, Point2D p) {
        g.setColor(Color.DARK_GRAY);
        g.drawString(texto, (float) p.getX() + 6, (float) p.getY() - 6);
    }

    public BufferedImage capturar() {
        int w = getWidth(); int h = getHeight();
        if(w <= 0 || h <= 0) {
            throw new IllegalStateException("El lienzo no tiene tamano");
        }

        BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = img.createGraphics();

        try {
            g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            printAll(g);
        } finally {
            g.dispose();
        }
        return img;
    }
}