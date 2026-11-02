# Geogebra-Clon
> Téngase por sentado que el presente software únicamente maneja: rectas, vectores y puntos en R²; por lo que el alcance de las operaciones enumeradas en la siguiente sección se limita a eso, por no decir que este documento no es un reemplazo al libro, no debe esperarse que lo sea y tampoco tiene la intención de serlo.
>
> Para más información, se recomienda: "Introduction to Linear Algebra", de Gilbert Strang.

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

Como $`\hat{i} = (1,0,0)`$, $`\hat{j} = (0,1,0)`$ y $`\hat{k} = (0,0,1)`$, se tiene que:

```math
\vec{t_1} \times \vec{t_2} = (b_1 c_2 - c_1 b_2,\ -(a_1 c_2 - c_1 a_2),\ a_1 b_2 - b_1 a_2)
```

iv) Proyección central de la tripla (se divide por la tercera componente, que es $`D \neq 0`$):

```math
L_1 \cap L_2 = \left\{ \left( \frac{b_1 c_2 - c_1 b_2}{a_1 b_2 - b_1 a_2},\ \frac{-(a_1 c_2 - c_1 a_2)}{a_1 b_2 - b_1 a_2} \right) \right\}
```

> El resultado es equivalente a la Regla de Cramer aplicada al sistema $`a_i x + b_i y = -c_i`$, $`i = 1, 2`$.

### 2. Parametrización

### 3. Proyección Escalar

### 4. Proyección Vectorial

### 5. Cambio de Base
