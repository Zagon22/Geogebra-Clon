package PaqueteMatriz;

public class MatrizB extends Matriz<Boolean> {

    public MatrizB(int filas, int columnas) {
        super(filas, columnas);
    }

    public MatrizB() {
        super(2, 2);
    }

    public Boolean calcularTraza() {
        this.esCuadrada();
        this.noNull();

        for(int i = 0; i < this.getFilas(); i++) {
            if (this.getElemento(i, i).booleanValue() == true) { return true; }
        } // Con saber que un valor de la diagonal es verdadero es suficiente para afirmar que la traza es igual a 1 o "true" (la traza booleana es una operacion de tipo "OR" con todos los elementos de la diagonal)

        return false;

    }
}
