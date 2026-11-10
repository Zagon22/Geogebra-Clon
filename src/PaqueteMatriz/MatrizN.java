package PaqueteMatriz;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MatrizN<T extends Number> extends Matriz<T>{

    public MatrizN(int filas, int columnas) {
       super(filas, columnas);
    }

    public MatrizN() {
        super(2, 2);
    }

    public double calcularTraza() {

        this.esCuadrada();
        this.noNull();
        double traza = 0.0;

        for(int i = 0; i < this.getFilas(); i++) {
            traza += this.getElemento(i, i).doubleValue();
        }
        
        return traza;
    }

    public double calcularDeterminante() {
        this.esCuadrada();
        this.noNull();
        return determinanteRecursivo(this);
    }

    private double determinanteRecursivo(MatrizN<T> m) {
        int n = m.getFilas();

        if (n == 1) {
            return m.getElemento(0, 0).doubleValue();
        }
        if (n == 2) {
            return m.getElemento(0, 0).doubleValue() * m.getElemento(1, 1).doubleValue()
                - m.getElemento(0, 1).doubleValue() * m.getElemento(1, 0).doubleValue();
        }

        double det = 0.0;
        int signo = 1;
        for (int j = 0; j < n; j++) {
            MatrizN<T> menor = obtenerMenor(m, 0, j);
            det += signo * m.getElemento(0, j).doubleValue() * determinanteRecursivo(menor);
            signo = -signo;
        }
        return det;
    }

    protected MatrizN<T> obtenerMenor(MatrizN<T> m, int filaExcluir, int columnaExcluir) {
        MatrizN<T> menor = new MatrizN<>(m.getFilas() - 1, m.getColumnas() - 1);
        int fi = 0;
        for (int i = 0; i < m.getFilas(); i++) {
            if (i == filaExcluir) continue;
            int fj = 0;
            for (int j = 0; j < m.getColumnas(); j++) {
                if (j == columnaExcluir) continue;
                menor.setElemento(fi, fj, m.getElemento(i, j));
                fj++;
            }
            fi++;
        }
        return menor;
    }

    @SuppressWarnings("unchecked")
    public MatrizN<T> productoCruz(MatrizN<T> v1) {

        if(!Arrays.equals(this.getDimensiones(), v1.getDimensiones())) {
            throw new IllegalArgumentException("Las matrices no tienen las mismas dimensiones");
        } // Chequea que los vectores tengan las mismas dimensiones
        if(this.getColumnas() != 3 || v1.getColumnas() != 3) {
            throw new IllegalArgumentException("No admite vectores de otro tipo mas que de 1x3");
        } // El producto cruz es una operacion que solo tiene sentido en R3, por lo que no se pueden admitir otro tipo de vectores

        MatrizN<T> m = new MatrizN<>(v1.getColumnas(), v1.getColumnas());

        MatrizN<T> coordMatriz = new MatrizN<>(v1.getFilas(), v1.getColumnas());
        List<Double> coordLista = new ArrayList<>();

        ((Matriz<Double>) m).rellenarMatriz(1.0); // Se completa la primera fila de unos (cada uno representaria i, j, k)

        for(int i = 0; i < m.getFilas(); i++) {
            for(int j = 0; j < m.getColumnas(); j++) {
                if(i == 1) { ((MatrizN<Double>) m).setElemento(1, j, this.getElemento(0, j).doubleValue()); }
                if(i == 2) { ((MatrizN<Double>) m).setElemento(2, j, v1.getElemento(0, j).doubleValue()); }
            }
        } // Para completar la matriz con los valores de los vectores dados

        List<MatrizN<T>> menores = new ArrayList<>(); // Se arma una lista que contiene cada minor

        for(int i = 0; i < m.getFilas(); i++) {
            MatrizN<T> menor = obtenerMenor(m, 0, i);
            menores.add(menor);
        }

        int signo = 1;
        for(MatrizN<T> menor : menores) {
            double c = menor.calcularDeterminante();
            c *= signo;
            coordLista.add(c);
            
            signo = -signo;
        }

        for(int i = 0; i < coordLista.size(); i++) { ((MatrizN<Double>) coordMatriz).setElemento(0, i, coordLista.get(i).doubleValue()); }

        coordLista.clear();
        menores.clear();

        coordLista = null;
        menores = null;

        return coordMatriz;
    }

    public MatrizN<Double> multiplicarMatriz(MatrizN<T> m) {
        if (this.getColumnas() != m.getFilas()) {
            throw new IllegalArgumentException("No se puede hacer el producto de las matrices, ya que sus dimensiones son incompatibles");
        }
        this.noNull();
        m.noNull();

        MatrizN<Double> prodM = new MatrizN<>(this.getFilas(), m.getColumnas());

        for(int i = 0; i < this.getFilas(); i++) {
            for(int j = 0; j < m.getColumnas(); j++) {
                double valor = 0.0;
                for(int k = 0; k < this.getColumnas(); k++) {
                    valor += this.getElemento(i, k).doubleValue() * m.getElemento(k, j).doubleValue();
                }
                prodM.setElemento(i, j, valor);
            }
        }

        return prodM;
    }

    private void esInvertible() {
        if(this.calcularDeterminante() == 0) {
            throw new ArithmeticException("Las matrices singulares no tienen inversa!");
        }
    }

    public MatrizN<Double> calcularInversa() {
        esCuadrada();
        noNull();
        esInvertible();

        MatrizN<Double> mI = new MatrizN<>(this.getFilas(), this.getColumnas());
        int n = this.getFilas();

        double[][] m = new double[n][2*n]; // Matriz aumentada. por ej.: si la matriz dada es de 2x2, la aumentada es de 2x4 [m1 | m2]
        for(int i = 0; i < n; i++) {
            for(int j = 0; j < n; j++) {
                m[i][j] = this.getElemento(i, j).doubleValue();
            }
            m[i][n + i] = 1.0; // la diagonal de m2 es completada con 1s
        }

        for(int col = 0; col < n; col++) {
            int piv = col;
            for(int i = col + 1; i < n; i++) {
                if(Math.abs(m[i][col]) > Math.abs(m[piv][col])) {piv = i;} 
            } // busqueda de la columna pivote
            double[] tmp = m[col]; m[col] = m[piv]; m[piv] = tmp; // cambio de filas

            double p = m[col][col];
            for(int j = 0; j < 2*n; j++) {m[col][j] /= p;} // para normalizar

            for(int i = 0; i < n; i++) {
                if (i == col) continue;
                double f = m[i][col];
                if (f == 0) continue;
                for (int j = 0; j < 2*n; j++) {
                    m[i][j] -= f*m[col][j];
                }
            }
        }

        for(int i = 0; i < n; i++) {
            for(int j=0; j < n; j++) {
                mI.setElemento(i, j, m[i][n + j]);
            }
        }


        return mI;
    }
}
