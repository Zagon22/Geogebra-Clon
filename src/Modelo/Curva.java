package Modelo;

public interface Curva<T, U, K> {
    
    T parametrizar();
    U interseccion(K a);
}
