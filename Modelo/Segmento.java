package Modelo;

/*La idea no era implementar segmentos; sin embargo, debieron implementarse para poder representar las distancias entre 
los objetos. A partir de eso, se decidio que para esta clase, por lo pronto, no se implementase logica (a futuro, puede ser) */

public class Segmento extends ObjetoLineal<Segmento, Segmento> {
    public Segmento(double x1, double x2, double y1, double y2) {
        super(x1, x2, y1, y2);
    }

    public Segmento distanciaA(Segmento s) { return null; }

    public void reflejarSobreY() {}

    public void reflejarSobreX() {}
    // ningun metodo fue implementado, por lo pronto.

    public double longitud() { return Math.hypot(this.getX() - this.getXi(), this.getY() - this.getYi()); }

}
