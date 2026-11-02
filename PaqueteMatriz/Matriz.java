package PaqueteMatriz;

import java.util.List;
import java.util.ArrayList;

public class Matriz<T> {
    
    private int filas;
    private int columnas;
    private List<List<T>> elemento;

    public Matriz(int filas, int columnas) {

        if(filas == 0 || columnas == 0) {
            throw new IllegalArgumentException("La matriz no puede tener 0 dimensiones");
        }
        if(filas < 0 || columnas < 0) {
            throw new IllegalArgumentException("La matriz no puede tener dimensiones negativas");
        }

        this.elemento = new ArrayList<>();
        for(int i = 0; i < filas; i++) {
            elemento.add(new ArrayList<>());
            for(int j = 0; j < columnas; j++) {
                elemento.get(i).add(null);
            }
        } // Inicializamos la matriz con nulls, ya que desconocemos el tipo de dato que le van a pasar

        this.filas = filas;
        this.columnas = columnas;

    }

    public Matriz() {
        this(2, 2); // Llama al constructor de arriba
    } // Constructor por defecto: Matriz cuadrada de 2x2

    public int getFilas() {return this.filas;}

    public int getColumnas() {return this.columnas;}

    protected void setFilas(int filas) {
        if(filas < 0) {
            throw new IllegalArgumentException("La matriz no puede tener dimensiones negativas");
        }
        this.filas = filas;
    }

    protected void setColumnas(int columnas) {
        if(columnas < 0) {
            throw new IllegalArgumentException("La matriz no puede tener dimensiones negativas");
        }
        this.columnas = columnas;
    } // Los setters para las dimensiones de la matriz son protected porque no queremos que el usuario pueda cambiar las dimensiones de la matriz directamente, sino que lo debe hacer por medio de los métodos anadirFila, anadirColumna, eliminarFila y eliminarColumna

    public T getElemento(int fila, int columna) {
        validarIndice(fila, columna);
        return this.elemento.get(fila).get(columna);
    }

    public void setElemento(int fila, int columna, T valor) {
        validarIndice(fila, columna);
        this.elemento.get(fila).set(columna, valor);
    }

    protected void validarIndice(int fila, int columna) {
        if (fila < 0 || fila >= this.filas || columna < 0 || columna >= this.columnas) {
            throw new IndexOutOfBoundsException("Indice fuera de rango");
        }
    }

    protected void noNull() {
        for(int i = 0; i < getFilas(); i++) {
            for(int j = 0; j < getColumnas(); j++) {
                if(this.getElemento(i, j) == null) {
                    throw new NullPointerException("La celda no puede ser nula");
                }
            }
        }
    }

    public Matriz<T> trasponerMatriz() {

        Matriz<T> traspuesta = new Matriz<>(this.columnas, this.filas);
        
        for(int i = 0; i < this.filas; i++) {
            for(int j = 0; j < this.columnas; j++) {
                T valor = this.getElemento(i, j);
                traspuesta.setElemento(j, i, valor);
            }
        }

        return traspuesta;
    }

    /* Adicionales */

    protected void esCuadrada() {
        if(this.filas != this.columnas) {
            throw new IllegalArgumentException("La matriz no es cuadrada");
        }
    }

    public void rellenarMatriz(T valor) {
        for(int i = 0; i < this.filas; i++) {
            for(int j = 0; j < this.columnas; j++) {
                this.setElemento(i, j, valor);
            }
        }
    }

    public void completarDiagonal(T valor) {
        esCuadrada();
        for(int i = 0; i < this.filas; i++) {
            this.setElemento(i, i, valor);
        }
    }

    public void anadirFila() {
        List<T> nuevaFila = new ArrayList<>();
        for(int j = 0; j < this.columnas; j++) {
            nuevaFila.add(null);
        }
        this.elemento.add(nuevaFila);
        this.setFilas(this.filas + 1);
    }

    public void anadirColumna() {
        for(int i = 0; i < this.filas; i++) {
            this.elemento.get(i).add(null);
        }
        this.setColumnas(this.columnas + 1);
    }

    public void eliminarFila(int fila) {
        if(fila < 0 || fila >= this.filas) {
            throw new IllegalArgumentException("La fila a eliminar no existe");
        }
        this.elemento.remove(fila);
        this.setFilas(this.filas - 1);
    }

    public void eliminarColumna(int columna) {
        if(columna < 0 || columna >= this.columnas) {
            throw new IllegalArgumentException("La columna a eliminar no existe");
        }
        for(int i = 0; i < this.filas; i++) {
            this.elemento.get(i).remove(columna);
        }
        this.setColumnas(this.columnas - 1);
    }

    public int[] getDimensiones() {
        int[] a = {this.getFilas(), this.getColumnas()};
        return a;
    }

    public void limpiarMatriz() {
        for (int i = 0; i < this.getFilas(); i++) {
            for(int j = 0; j < this.getColumnas(); j++) {
                this.setElemento(i, j, null);
            }
        }
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < this.filas; i++) {
            for(int j = 0; j < this.columnas; j++) {
                sb.append(this.getElemento(i, j)).append(" ");
            }
            sb.append("\n");
        }
        return sb.toString();
    }
}
