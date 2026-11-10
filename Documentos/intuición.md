# Geogebra-Clon

## Aclaración

> Téngase por sentado que el presente software únicamente maneja: rectas, vectores y puntos en $`\mathbb{R}^2`$; por lo que el alcance de las operaciones enumeradas en la siguiente sección se limita a eso, por no decir que este documento no es un reemplazo al libro, no debe esperarse que lo sea y tampoco tiene la intención de serlo.
>
> Para más información se recomienda:
> 
> i) Para proyecciones vectoriales, escalares, cambios de base y distancias: "Introduction to Linear Algebra", de Gilbert Strang (5ta Edición 2016).
> 
> ii) Para coordenadas homogéneas y proyección central (temas mencionados en la primera parte): "Multiple View Geometry in computer vision", de Richard Hartley y Andrew Zisserman (la parte cero, para background, es más que suficiente para lo que se aborda en este documento).

## Trasfondo

> Este documento, por otro lado, representa una versión más prolija (no necesariamente uno a uno) del siguiente whiteboard: [Intuición - Whiteboard](https://miro.com/app/board/uXjVEd-pokg=/)
>
> Apréciese que algunos ítems de este documento no se encuentran en el whiteboard adjunto, así como algunos temas abordados y ejemplos presentes en este último fueron omitidos en la sección subsiguiente, ya que no eran del todo relevantes para generar intuición sobre el programa (por ej.: el algoritmo para invertir matrices se excluyó de este documento, ya que, si bien el programa lo precisa de forma emergente, no es un objetivo; una excepción se hizo para la parametrización, que mostró ser de utilidad para visualizar las rectas en el plano, y por eso figura en este documento).

## Intuición

### 1. Intersección

i) Dadas dos rectas:

```math
L_1: a_1x + b_1y + c_1 = 0 \quad \wedge \quad L_2: a_2x + b_2y + c_2 = 0
```

Donde: $`(a_1, b_1, c_1) \in \mathbb{R}^3`$ y $`(a_2, b_2, c_2) \in \mathbb{R}^3`$ son las coordenadas homogéneas de las rectas previamente enumeradas, respectivamente.

Sea $`D = a_1 b_2 - b_1 a_2`$ el determinante de la matriz de coeficientes:

```math
\begin{pmatrix} a_1 & b_1 \\ a_2 & b_2 \end{pmatrix}
```

ii. 1) Si: $`(a_1, b_1, c_1) = \lambda(a_2, b_2, c_2)`$ para un $`\lambda \in \mathbb{R}\setminus\{0\}`$, entonces las rectas son coincidentes y $`L_1 \cap L_2 = L_1`$.

ii. 2) Si: $`D = 0`$ pero $`(a_1, b_1, c_1)`$ y $`(a_2, b_2, c_2)`$ no son proporcionales (es decir, los vectores normales $`(a_1, b_1)`$ y $`(a_2, b_2)`$ son proporcionales, pero las triplas completas no), entonces: $`L_1 \parallel L_2`$ y $`L_1 \cap L_2 = \varnothing`$.

ii. 3) Si: $`D \neq 0`$, entonces las rectas son secantes y $`L_1 \cap L_2`$ es un único punto (ver iii y iv).

> Observación: si además $`c_1 = c_2 = 0`$, entonces $`L_1 \cap L_2 = \{(0,0)\}`$.

iii) (continúa caso ii. 3)

El producto cruz de las triplas $`\vec{t_1} = (a_1, b_1, c_1)`$, $`\vec{t_2} = (a_2, b_2, c_2)`$ es ortogonal a ambas filas de la matriz $`\mathbf{M}`$ cuyas filas son $`\vec{t_1}, \vec{t_2}`$; por lo tanto, cuando $`\vec{t_1}`$ y $`\vec{t_2}`$ son linealmente independientes, genera el null space ("ceros") de $`\mathbf{M}`$:

```math
\vec{t_1} \times \vec{t_2} =
\begin{vmatrix} \hat{i} & \hat{j} & \hat{k} \\ a_1 & b_1 & c_1 \\ a_2 & b_2 & c_2 \end{vmatrix} =
(b_1 c_2 - c_1 b_2)\,\hat{i} - (a_1 c_2 - c_1 a_2)\,\hat{j} + (a_1 b_2 - b_1 a_2)\,\hat{k}
```

Como los vectores de la base canónica son:

```math
\hat{i} = (1,0,0),\quad \hat{j} = (0,1,0),\quad \hat{k} = (0,0,1)
```

se tiene que:

```math
\vec{t_1} \times \vec{t_2} = (b_1 c_2 - c_1 b_2,\ -(a_1 c_2 - c_1 a_2),\ a_1 b_2 - b_1 a_2)
```
iv) Proyección central de la tripla (se divide por la tercera componente, que es $`D \neq 0`$):

```math
L_1 \cap L_2 = \left\{ \left( \frac{b_1 c_2 - c_1 b_2}{a_1 b_2 - b_1 a_2},\ \frac{-(a_1 c_2 - c_1 a_2)}{a_1 b_2 - b_1 a_2} \right) \right\}
```

> El resultado es equivalente a la Regla de Cramer aplicada al sistema $`a_i x + b_i y = -c_i`$, $`i = 1, 2`$.

### 2. Parametrización

i) Se tiene que la recta $`L_1`$ está definida por los puntos $`P_1, P_2 \in \mathbb{R}^{2}`$, que tienen la siguiente forma:

```math
P_1 = (a_1, b_1) \wedge P_2 = (a_2, b_2)
```

ii) Sucede que la forma paramétrica de $`L_1`$ está dada por el vector director (no nulo) $`\vec{v_d}`$, el punto de paso $`P = (a, b)`$ y un parámetro $`\lambda \in \mathbb{R}\setminus\{0\}`$, de forma tal que:

```math
L_1: \lambda\vec{v_d} + P
```
Donde: $`\vec{v_d} = (a_2 - a_1, b_2 - b_1)`$ y el punto de paso $`P`$ es un punto que pertenece a la recta $`L`$.

> Observación: ese punto de paso $`P`$ normalmente es alguno de los puntos dados, $`P_1 \lor P_2`$

### 3. Proyección Escalar

i) Se tiene a los vectores no nulos $`\vec{v_1}, \vec{v_2} \in \mathbb{R}^{2}`$, donde: 

```math
\vec{v_1} = (a_1, a_2) \wedge \vec{v_2} = (b_1, b_2)
```

y se desea proyectar al primero sobre el segundo.

ii. 1) Si $`\ \lVert \vec{v_2} \lVert_2 = 1`$: saltar al punto (iii)

ii. 2) Si $`\ \lVert \vec{v_2} \lVert_2 \neq 1`$, entonces $`\vec{v_2}`$ debe ser normalizado con tal de que su longitud no tenga influencia en la magnitud del vector proyectado:

```math
\vec{v_n} = \left(\frac{b_1}{\lVert \vec{v_2} \lVert_2},\ \frac{b_2}{\lVert \vec{v_2} \lVert_2} \right)
```

iii) Calcular el producto interno de $`\vec{v_1} \cdot \vec{v_n}`$

```math
\vec{v_1} \cdot \vec{v_n} = (a_1 n_1 + a_2 n_2) = c
```

La constante $`c \in \mathbb{R}`$, y es la que representa la proyección escalar de $`\vec{v_1} sobre \vec{v_2}`$

### 4. Proyección Vectorial

Ref. operación n.º3

iv) Escalar $`c`$ veces el vector $`\vec{v_n}`$:

```math
  \text{proj}_{\vec{v_2}}\vec{v_1} = c \cdot \vec{v_n}
```

### 5. Cambio de Base

i) Se tienen tres vectores: 

```math
\vec{v_1} = (a_1, b_1), \ \vec{b_1} = (x_1, y_1) \ \wedge \ \vec{b_2} = (x_2, y_2)
```
de los cuales: $`\vec{b_1} \wedge \vec{b_2}`$ son una base de $`\mathbb{R}^2`$ (es decir, ambos vectores "generan el plano"); mientras que $`\vec{v_1}`$ es un vector escrito en términos de la base elemental.

ii) Armar la matriz de cambio de base.

```math
$$P_{B \to C} = 
\begin{pmatrix} 
\vec{b_1} & \vec{b_2}
\end{pmatrix}$$
```

Donde: $`C`$ es la base estándar y $`B`$ la nueva base.

> Observación: i) nótese que los vectores de la "nueva base" son las coordenadas de cada uno de esos vectores en la base estándar o canónica, de modo que ésta matriz transforma vectores de la base dada a la estándar; y, ii) por lo tanto, para poder mapear vectores escritos en la base estándar a la nueva, es necesario invertir la matriz.

iii) Aplicar cambio de base.

```math
[\vec{v_1}]_B = (P_{B \to C})^{-1}[\vec{v_1}]_C
```

> Observación: a partir de las obs. anteriores, se desprende que: $`(P_{B \to C})^{-1} = P_{C \to B}`$

### 6. Distancia

(Puntos)

i) Se tienen dos puntos $`P_1, P_2 \in \mathbb{R}^2`$.

ii) La menor distancia entre ellos está dada por la longitud del segmento de la recta que las une:

```math
dist(P_1, P_2) = \lVert P_2 - P_1 \lVert_2 = \sqrt{(x_2 - x_1)^2 + (y_2 - y_1)^2}
```

(Rectas)

i) Se tiene a las rectas $`L_1, L_2 \subseteq \mathbb{R}^2`$, las cuales tienen una $`dist(L_1, L_2) \neq 0`$ si $`L_1 \cap L_2 = \varnothing`$ (véase sección n.º1 parte ii. 2)

ii) Ocurre que la menor distancia entre las rectas $`L_1, L_2`$ está dada por la longitud del segmento de: $`L'`$, que satisface las siguientes condiciones:

```math
L' \perp L_1, L' \perp L_2 \ \wedge \ L' \cap L_1 = P_1, L' \cap L_2 = P_2
```

iii) La distancia, finalmente, está dada por:

```math
dist(P_1, P_2) = \lVert P_2 - P_1 \lVert_2 = \sqrt{(x_2 - x_1)^2 + (y_2 - y_1)^2}
```

Donde: $`P_1, P_2`$ son los extremos del segmento que representa la menor distancia entre las rectas.

(Recta y Punto)

i) Se tiene al punto $`P \in \mathbb{R}^2`$ y a la recta $`L_1 \subseteq \mathbb{R}^2`$

ii) Sucede que la mínima distancia entre estos objetos está dada por aquella entre la recta $`L_1`$ y $`L'`$, que es la recta paralela a $`L_1`$ que contiene al punto $`P`$.

iii) Calcular: $`dist(L_1, L')`$ (revisar parte anterior)

> Observación: no se aborda la distancia entre vectores, ya que en el software estos se encuentran fijos en el origen, de modo que su distancia siempre es 0; sin embargo, la operación distancia fue reinterpretada como su diferencia, la cual no es una distancia ya que devuelve otro vector (siempre distinto del nulo, a no ser que los vectores sean iguales) en lugar de un escalar.

### 7. Reflexiones

> Observación: las reflexiones a tratar son sólo respecto de los ejes x e y, siendo una reflexión realizada primero sobre uno de los ejes y eventualmente sobre el otro, lo que se conoce como una reflexión respecto del origen (0, 0) que, al mismo tiempo, representa una rotación de 180º.

i) Se tiene al punto $`P_1 \in \mathbb{R}^2`$.

ii. 1) Su reflexión respecto del eje-x está dada por:

```math
\begin{pmatrix} x_1 \\ -y_1 \end{pmatrix}
= \begin{pmatrix} 1 & 0 \\ 0 & -1 \end{pmatrix}
\begin{pmatrix} x_1 \\ y_1 \end{pmatrix} 
```

ii. 2) Mientras tanto aquella respecto del eje-y, lo está por:

```math
\begin{pmatrix} -x_1 \\ y_1 \end{pmatrix}
= \begin{pmatrix} -1 & 0 \\ 0 & 1 \end{pmatrix}
\begin{pmatrix} x_1 \\ y_1 \end{pmatrix}
```

> Observación: Para las rectas el proceso es el mismo, sólo que, al estar defenidas por dos puntos, esto debe extenderse al segundo punto; lo mismo ocurre para aquellos vectores que no estén anclados al origen, si lo están, sin embargo, el proceso es igual al descrito previamente.
