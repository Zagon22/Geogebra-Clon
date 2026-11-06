package Modelo;

import Modelo.Excepcion.ExcepcionDistanciaCero;
import PaqueteMatriz.MatrizN;

public class Recta extends ObjetoLineal<Recta, Segmento> implements Curva<MatrizN<Double>, Punto, Recta>{

    public Recta(double x1, double x2, double y1, double y2) {
        if(x1 == x2 && y1 == y2) {
            throw new IllegalArgumentException("Se paso el mismo punto! Los puntos deben ser diferentes...");
        }

        super(x1, x2, y1, y2);
    }

    @Override
    public Segmento distanciaA(Recta a) {
        Double tercerCoord = this.productoCruzHomogeneo(a).getElemento(0, 2);
        if(tercerCoord != 0) { 
            throw new ExcepcionDistanciaCero("Las rectas tienen interseccion: su distancia es 0"); 
        } // si existe interseccion, y esta es un punto, la distancia es igual a 0.
        if(a.intercepto() == this.intercepto() && a.pendiente() == this.pendiente()) {
            throw new ExcepcionDistanciaCero("Las rectas son coincidentes: su distancia es 0");
        }

        Recta rPer = new Recta(-this.getY(), -this.getYi(), this.getX(), this.getXi());

        Punto p1 = this.interseccion(rPer);
        Punto p2 = a.interseccion(rPer);

        Segmento s = p1.distanciaA(p2);

        return s;
    }

    @Override 
    public MatrizN<Double> parametrizar()
    {
        if(this.direccion().getX() == 0 && this.direccion().getY() == 0) {
            throw new IllegalArgumentException("La direccion no puede ser igual a cero...");
        }
        MatrizN<Double> pLinea = new MatrizN<>(2, 2);
        // Direccion
        pLinea.setElemento(0, 0, direccion().getX());
        pLinea.setElemento(0, 1, direccion().getY());
        // Punto de paso
        pLinea.setElemento(1, 0, this.getXi());
        pLinea.setElemento(1, 1, this.getYi());

        return pLinea;
    }

    @Override
    public Punto interseccion(Recta r) {

        MatrizN<Double> crossP = this.productoCruzHomogeneo(r);

        if(crossP.getElemento(0, 2) == 0) { 
            throw new IllegalArgumentException("La interseccion es vacia o la recta dada!"); 
        }

        double c1 = crossP.getElemento(0, 0) / crossP.getElemento(0, 2);
        double c2 = crossP.getElemento(0, 1) / crossP.getElemento(0, 2);

        Punto interseccion = new Punto(c1, c2);

        return interseccion;
    }

    protected MatrizN<Double> productoCruzHomogeneo(Recta r) {
        MatrizN<Double> tripla1 = new MatrizN<>(1, 3);

        tripla1.setElemento(0, 0, this.pendiente());
        tripla1.setElemento(0, 1, this.coordY());
        tripla1.setElemento(0, 2, this.intercepto());
        
        MatrizN<Double> tripla2 = new MatrizN<>(1, 3);

        tripla2.setElemento(0, 0, r.pendiente());
        tripla2.setElemento(0, 1, r.coordY());
        tripla2.setElemento(0, 2, r.intercepto());

        MatrizN<Double> crossP = tripla1.productoCruz(tripla2);

        return crossP;
    }

    @Override
    public void reflejarSobreY() {
        this.setXi(-getXi());
        this.setX(-getX());
    }

    @Override 
    public void reflejarSobreX() {
        this.setYi(-getYi());
        this.setY(-getY());
    }

    public double intercepto() {
        if(this.getXi() != this.getX()) {
            return ((this.getX()*this.getYi()) - (this.getXi()*this.getY())) / 
            (this.getX() - this.getXi());
        }
        return -this.getXi();
    }

    public double pendiente() {
        if(this.getXi() != this.getX()) {
            return (this.getY() - this.getYi()) / (this.getX() - this.getXi());
        }
        return 1.0;
    }

    public double coordY() {
        if(this.getXi() != this.getX()) { return -1.0; }
        return 0.0;
    }

    public VectorGeo direccion() {
        return new VectorGeo(getX() - getXi(), getY() - getYi());
    }
   
}
