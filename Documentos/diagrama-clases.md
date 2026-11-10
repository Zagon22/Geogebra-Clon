```mermaid
classDiagram
    direction TB

    %% ==========================================
    %% MODELO
    %% ==========================================
    namespace Modelo {
        class ModeloListener {
            <<interface>>
            +modeloCambiado()
        }

        class GestorObjetos {
            -vectores Map~String, VectorGeo~
            -puntos Map~String, Punto~
            -rectas Map~String, Recta~
            -segmentos Map~String, Segmento~
            -listeners List~ModeloListener~
            +addModeloListener(l)
            +removeModeloListener(l)
            +notificar()
            +agregarPunto(label, x, y)
            +agregarVector(label, x, y)
            +agregarRecta(label, x1, x2, y1, y2)
            +agregarSegmento(label, s)
            +removerPunto(label)
            +removerVector(label)
            +removerRecta(label)
            +removerSegmento(label)
            +buscarPunto(label) Punto
            +buscarVector(label) VectorGeo
            +buscarRecta(label) Recta
            +crearLabel(operacion) String
        }

        class ObjetoGeo~T, U~ {
            <<abstract>>
            +distanciaA(T a) U
        }

        class ObjetoLineal~T, U~ {
            <<abstract>>
            -p1 Punto
            -p2 Punto
            +reflejarSobreY()
            +reflejarSobreX()
            +getX() double
            +getY() double
            +getXi() double
            +getYi() double
        }

        class Punto {
            -x double
            -y double
            +distanciaA(p) Segmento
        }

        class Recta {
            -pendiente double
            -intercepto double
            +distanciaA(r) Segmento
            +interseccion(r) Punto
            +parametrizar() MatrizN
            +productoCruzHomogeneo(r) MatrizN
        }

        class Segmento {
            +longitud() double
            +distanciaA(s) Segmento
        }

        class VectorGeo {
            -longitud double
            +distanciaA(v) VectorGeo
            +proVectorial(v) VectorGeo
            +proEscalar(v) Segmento
            +cambiarBase(b1, b2) VectorGeo
        }

        class Curva~T, U, K~ {
            <<interface>>
            +parametrizar() T
            +interseccion(K a) U
        }

        %% Excepciones
        class ExcepcionGeometrica {
            +ExcepcionGeometrica(message)
        }
        class ExcepcionBaseLD {
            +ExcepcionBaseLD(message)
        }
        class ExcepcionDistanciaCero {
            +ExcepcionDistanciaCero(message)
        }
        class InterseccionDistintaAPunto {
            +InterseccionDistintaAPunto(message)
        }
    }

    %% ==========================================
    %% PAQUETE MATRIZ
    %% ==========================================
    namespace PaqueteMatriz {
        class Matriz~T~ {
            -filas int
            -columnas int
            -elemento List~List~T~~
            +getElemento(fila, columna) T
            +setElemento(fila, columna, valor)
            +trasponerMatriz() Matriz~T~
        }

        class MatrizB {
            +calcularTraza() Boolean
        }

        class MatrizN {
            +calcularTraza() double
            +calcularDeterminante() double
            +productoCruz(v1) MatrizN
        }
    }

    %% ==========================================
    %% VISTA
    %% ==========================================
    namespace Vista {
        class VistaPrincipal {
            -lienzo Lienzo
            -panelControles PanelControles
            +getLienzo() Lienzo
            +getPanelControles() PanelControles
        }

        class Lienzo {
            -modelo GestorObjetos
            -tr Transformacion
            +capturar() BufferedImage
            +paintComponent(g)
            +modeloCambiado()
        }

        class PanelControles {
            -modelo GestorObjetos
            -lista JList~String~
            -seleccionEnOrden List~String~
            +getSeleccionActual() String
            +getSeleccionEnOrden() List~String~
            +setTipo(t)
            +setLabel(l)
            +mostrarError(msg)
            +modeloCambiado()
        }

        class Transformacion {
            -zoom double
            -panX double
            -panY double
            +aPantalla(mx, my) Point2D
            +aModelo(sx, sy) double[]
            +zoomEn(sx, sy, factor)
            +pan(dx, dy)
        }
    }

    %% ==========================================
    %% PERSISTENCIA
    %% ==========================================
    namespace Persistencia {
        class ExportadorImagen {
            +exportarPNG(img, file) File
            +rutaConExtension(f, ext) File
        }
    }

    %% ==========================================
    %% CONTROLADOR
    %% ==========================================
    namespace Controlador {
        class Controlador {
            -modelo GestorObjetos
            -vista VistaPrincipal
            +onAgregar()
            +onEliminar()
            +onInterseccion()
            +onDistancia()
            +onReflexion()
            +onProyeccion()
            +onCambioBase()
            +onCaptura()
            +onLimpiar()
            +recuperarDatos()
        }
    }

    %% ==========================================
    %% MAIN
    %% ==========================================
    namespace App {
        class Main {
            +main(args)
        }
    }

    %% ==========================================
    %% RELACIONES
    %% ==========================================

    %% Herencia y Polimorfismo
    ObjetoGeo <|-- ObjetoLineal
    ObjetoLineal <|-- Punto
    ObjetoLineal <|-- Recta
    ObjetoLineal <|-- Segmento
    ObjetoLineal <|-- VectorGeo

    Matriz <|-- MatrizB
    Matriz <|-- MatrizN

    RuntimeException <|-- ExcepcionGeometrica
    ExcepcionGeometrica <|-- ExcepcionBaseLD
    ExcepcionGeometrica <|-- ExcepcionDistanciaCero
    ExcepcionGeometrica <|-- InterseccionDistintaAPunto

    %% Implementaciones de Interfaces
    Curva <|.. Recta
    ModeloListener <|.. Lienzo
    ModeloListener <|.. PanelControles

    %% Dependencias (Uso)
    Recta --> MatrizN : usa
    VectorGeo --> MatrizN : usa
    VectorGeo --> Segmento : retorna
    Punto --> Segmento : retorna
    Recta --> Segmento : retorna
    Controlador --> GestorObjetos : manipula
    Controlador --> VistaPrincipal : actualiza
    Controlador --> ExportadorImagen : usa

    %% Agregación (Contiene)
    GestorObjetos o-- ModeloListener : notifica
    GestorObjetos o-- ObjetoGeo : almacena

    %% Composición (Posee)
    VistaPrincipal *-- Lienzo
    VistaPrincipal *-- PanelControles
    Lienzo *-- Transformacion
```
