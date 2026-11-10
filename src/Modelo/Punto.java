package Modelo;

import Modelo.Excepcion.ExcepcionDistanciaCero;

public class Punto extends ObjetoGeo<Punto, Segmento>{
    private double x;
    private double y;

    public Punto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public Segmento distanciaA(Punto a) {
        if(this.getX() == a.getX() && this.getY() == a.getY()) {
            throw new ExcepcionDistanciaCero("Los puntos son coincidentes: la distancia es 0");
        }
        Segmento s = new Segmento(this.x, a.x, this.y, a.y);
        //Math.sqrt(Math.pow(this.getX() - a.getX(), 2) + Math.pow(this.getY() - a.getY(), 2));

        return s;
    } // Solo distancias a otros puntos

    public double getX() { return this.x; }

    public double getY() { return this.y; }

    public void setX(double x) { this.x = x; }

    public void setY(double y) { this.y = y; }
    
}
