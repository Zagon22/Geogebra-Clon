# Geogebra-Clon
> Téngase por sentado que el presente software únicamente maneja: rectas, vectores y puntos en $`\mathbb{R}^2`$; por lo que el alcance de las operaciones enumeradas en la siguiente sección se limita a eso, por no decir que este documento no es un reemplazo al libro, no debe esperarse que lo sea y tampoco tiene la intención de serlo.
>
> Para más información (como referencia gral.), se recomienda: "Introduction to Linear Algebra", de Gilbert Strang (5ta Edición 2016)

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

iii) Hacer el producto interno de $`\vec{v_1} \cdot \vec{v_n}`$

```math
\vec{v_1} \cdot \vec{v_n} = a_1 b_1 + a_2 b_2 = c
```

La constante $`c \in \mathbb{R}\setminus\{0\}`$, y es la que representa la proyección escalar de $`\vec{v_2} sobre \vec{v_1}`$

### 4. Proyección Vectorial

Ref. operación n.º3

iv) Escalar $`c`$ veces el vector $`\vec{v_n}`$:

```math
  \text{proj}_{\vec{v_2}}\vec{v_1} = c \cdot \vec{v_n}
```

### 5. Cambio de Base

i) Se tienen tres vectores: 

```math
\vec{v_1} = (a_1, b_!), \ \vec{b_1} = (x_1, y_1) \ \wedge \ \vec{b_2} = (x_2, y_2)
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
