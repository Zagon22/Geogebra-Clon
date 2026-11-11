package Modelo;

import Modelo.Excepcion.ExcepcionBaseLD;
import Modelo.Excepcion.ExcepcionDistanciaCero;
import Modelo.Excepcion.ExcepcionGeometrica;
import PaqueteMatriz.MatrizN;

public class VectorGeo extends ObjetoLineal<VectorGeo, VectorGeo> {

    public VectorGeo(double x, double y) {
        super(x, y);
    }

    @Override
    public VectorGeo distanciaA(VectorGeo a) {
        if(this.getX() == a.getX() && this.getY() == a.getY()) {
            throw new ExcepcionDistanciaCero("La diferencia entre dos vectores iguales es el vector nulo");
        }
        
        VectorGeo vDist = new VectorGeo(this.getX() - a.getX(), this.getY() - a.getY());
        return vDist; // Este es el vector que representa la diferencia entre los vectores dados (el que une a los dos)
    }

    @Override
    public void reflejarSobreY() {
        this.setX(-getX());
    }

    @Override 
    public void reflejarSobreX() {
        this.setY(-getY());
    } // ambas reflexiones podrian implementarse matricialmente, pero se le agregaria complejidad innecesaria al caso

    public VectorGeo proVectorial(VectorGeo projV) {
        Segmento projE = this.proEscalar(projV); // se saca ventaja de que el proceso de calcula la proyeccion vectorial es, hasta ultima instancia, igual al de la escalar
        VectorGeo projF = new VectorGeo(projE.getX(), projE.getY());

        return projF;
    }

    public Segmento proEscalar(VectorGeo projV) {
        if(projV.longitud() == 0) {
            throw new ExcepcionGeometrica("El vector dado tiene que tener norma > 0");
        }

        if(projV.longitud() != 1) {
            double nX = projV.getX() / projV.longitud();
            double nY = projV.getY() / projV.longitud();
            projV.setX(nX);
            projV.setY(nY);
        } // Normaliza el vector sobre el que va a proyectar, de no tener norma 1

        MatrizN<Double> auxM1 = new MatrizN<>(1, 2);
        auxM1.setElemento(0, 0, projV.getX());
        auxM1.setElemento(0, 1, projV.getY());

        MatrizN<Double> auxM2 = new MatrizN<>(2, 1);
        auxM2.setElemento(0, 0, this.getX());
        auxM2.setElemento(1, 0, this.getY());

        MatrizN<Double> prodM1M2 = auxM1.multiplicarMatriz(auxM2);
        double c = prodM1M2.getElemento(0, 0);

        Segmento projE = new Segmento(c * projV.getX(), c * projV.getY());

        return projE;
    }

    public VectorGeo cambiarBase(VectorGeo b1, VectorGeo b2) {
        MatrizN<Double> m = new MatrizN<>(2,2);
        m.setElemento(0, 0, b1.getX());
        m.setElemento(1, 0, b1.getY());
        m.setElemento(0, 1, b2.getX());
        m.setElemento(1, 1, b2.getY());

        if(m.calcularDeterminante() == 0) {
            throw new ExcepcionBaseLD("Los vectores dados no forman una base de R^2");
        }

        MatrizN<Double> mI = m.calcularInversa();
        
        MatrizN<Double> vVec = new MatrizN<>(2, 1);
        vVec.setElemento(0, 0, this.getX());
        vVec.setElemento(1, 0, this.getY());

        MatrizN<Double> vF = mI.multiplicarMatriz(vVec);

        VectorGeo f = new VectorGeo(vF.getElemento(0, 0), vF.getElemento(1, 0));

        return f;
    } // Coordenadas de base estandar a una nueva base

    public double longitud() { return Math.hypot(this.getX() - this.getXi(), this.getY() - this.getYi()); }
    
}
