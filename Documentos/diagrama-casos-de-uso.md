# Diagrama de Casos de Uso — «Geogebra-Clon»

> Observaciones
> - Como se había indicado en el README, todos los CUs tienen al caso no. 1 («Crear el lienzo») como precondición; sin embargo, en el diagrama adjunto se omitió la relación por motivos de legibilidad.
> - Parecido al caso anterior, para que algunos casos de uso tengan sentido (selección, modifcar, [...], eliminar) tiene que haber, previamente, objetos en el plano; en este sentido, el CU no. 6 es también una precondición para otros CUs, no obstante, por cuestiones también de legibilidad ésta relación fue obviada del diagrama (en el README figura como precondición de algunos CUs).

---

```mermaid
flowchart LR
    U["«actor»
    Usuario<br/>"]

    subgraph SIS["Geogebra-Clon"]
        subgraph LIENZO["Lienzo"]
            CU1(["CU1<br/>Crear el lienzo"])
            CU2(["CU2<br/>Agrandar o achicar<br/>el lienzo (zoom)"])
            CU3(["CU3<br/>Mover el lienzo<br/>(panning)"])
            CU4(["CU4<br/>Capturar el lienzo"])
            CU5(["CU5<br/>Limpiar el lienzo"])
        end

        subgraph OBJETOS["Objetos"]
            CU6(["CU6<br/>Dibujar objetos"])
            CU7(["CU7<br/>Seleccionar objetos"])
            CU8(["CU8<br/>Eliminar objetos"])
            CU9(["CU9<br/>Modificar objetos"])
            CU10(["CU10<br/>Recuperar la información<br/>del objeto"])
        end

        subgraph OPERACIONES["Operaciones"]
            CU11(["CU11<br/>Dar la distancia<br/>entre objetos"])
            CU12(["CU12<br/>Transformar objetos<br/>(solo reflexión)"])
            CU13(["CU13<br/>Intersectar curvas<br/>(sólo rectas)"])
            CU14(["CU14<br/>Proyectar vectores"])
            CU15(["CU15<br/>Cambiar la base<br/>de un vector"])
        end
    end

    %% Asociaciones del actor principal
    U --- CU1
    U --- CU2
    U --- CU3
    U --- CU4
    U --- CU5
    U --- CU6
    U --- CU7
    U --- CU8
    U --- CU9
    U --- CU10
    U --- CU11
    U --- CU12
    U --- CU13
    U --- CU14
    U --- CU15

    %% Inclusiones (las "ref. caso no. X" del README)
    CU8 -.->|"«include»"| CU7
    CU9 -.->|"«include»"| CU7
    CU10 -.->|"«include»"| CU7
    CU11 -.->|"«include»"| CU7
    CU12 -.->|"«include»"| CU7
    CU13 -.->|"«include»"| CU7
    CU14 -.->|"«include»"| CU7
    CU15 -.->|"«include»"| CU7
    CU14 -.->|"«include»"| CU9
    CU12 -.->|"«include»"| CU9
    CU9 -.->|"«include»"| CU10
    CU13 -.->|"«include»"| CU6
    CU11 -.->|"«include»"| CU6
    CU14 -.->|"«include»"| CU6
    CU15 -.->|"«include»"| CU6
```
