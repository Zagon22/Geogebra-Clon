package Modelo;

public class Punto extends ObjetoGeo<Punto, Double>{
    private double x;
    private double y;

    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Double distanciaA(Punto a) {
        double dist = Math.hypot(this.getX() - a.getX(), this.getY() - a.getY());
        //Math.sqrt(Math.pow(this.getX() - a.getX(), 2) + Math.pow(this.getY() - a.getY(), 2));

        return dist;
    } // Solo distancias a otros puntos

    public double getX() { return this.x; }

    public double getY() { return this.y; }

    public void setX(double x) { this.x = x; }

    public void setY(double y) { this.y = y; }
    
}
