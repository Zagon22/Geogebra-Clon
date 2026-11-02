package Modelo;

public abstract class ObjetoLineal<T, U> extends ObjetoGeo<T, U> {
    private Punto p1;
    private Punto p2;

    public ObjetoLineal(double x1, double x2, double y1, double y2) {
        this.p1 = new Punto(x1, y1);
        this.p2 = new Punto(x2, y2);
    }

    public ObjetoLineal(double x, double y) {
        this.p1 = new Punto(0, 0);
        this.p2 = new Punto(x, y);
    }

    public Punto getPunto1() { return this.p1; }

    public Punto getPunto2() { return this.p2; }

    public double getX() { return this.p2.getX(); }

    public double getY() { return this.p2.getY(); }

    public void setX(double x) { this.p2.setX(x); }

    public void setY(double y) { this.p2.setY(y); }

    public double getXi() { return this.p1.getX(); }

    public double getYi() { return this.p1.getY(); }

    public void setXi(double x) { this.p1.setX(x); }

    public void setYi(double y) { this.p1.setY(y); }

}
