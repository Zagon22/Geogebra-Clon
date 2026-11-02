package Vista;

import java.awt.geom.Point2D;

// Pure fabrication
public class Transformacion {
    private double zoom = 50.0;   // pixeles por unidad de modelo
    private double panX = 0, panY = 0;
    private int ancho = 800, alto = 600;

    public void setViewport(int w, int h) { ancho = w; alto = h; }

    public Point2D aPantalla(double mx, double my) {
        return new Point2D.Double((mx - panX) * zoom + ancho / 2.0,
                                   alto / 2.0 - (my - panY) * zoom);
    }

    public double[] aModelo(int sx, int sy) {
        return new double[]{ (sx - ancho / 2.0) / zoom + panX,
                             (alto / 2.0 - sy) / zoom + panY };
    }

    public void pan(double dpx, double dpy) {
        panX -= dpx / zoom;
        panY += dpy / zoom;
    }

    public void zoomEn(int sx, int sy, double factor) {
        double[] m = aModelo(sx, sy);
        zoom = Math.max(1e-3, Math.min(1e5, zoom * factor));
        panX = m[0] - (sx - ancho / 2.0) / zoom;
        panY = m[1] - (alto / 2.0 - sy) / zoom;
    }

    public double getZoom() { return zoom; }
    public double getPanX() { return panX; }
    public double getPanY() { return panY; }
}