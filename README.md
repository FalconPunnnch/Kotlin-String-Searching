# Kotlin-String-Searching

## 3 algoritmos de Búsqueda de Cadenas: String dentro de String.

### Fuerza Bruta:
*Examina todas las posibles combinaciones de manera exhaustiva.*

**Paso a paso:**
1. Toma dos cadenas como entradas:
	- Texto objetivo (dónde se va a buscar).
	- Patrón de búsqueda (lo que se desea encontrar).
2. Desliza el patrón a lo largo del texto objetivo desde la posición inicial, es decir, desde el primer caracter.
3. Compara los caracteres del patrón con los del texto objetivo, uno a uno, correspondientemente.
4. Si los caracteres son los mismos, se han encontrado coincidencias entre ambos strings.

   > A este punto se puede elegir si solo indicar la posición de coincidencia o si contar el número de ocurrencias a lo largo del Texto Objetivo.
   
6. Si los caracteres son diferentes, se desliza el patrón de búsqueda hacia adelante en el texto objetivo y se repite el proceso de comparación.
7. Se continúa deslizando hasta encontrar todas las coinicidencias en el texto objetivo.
8. Resultado deseado según indicación elegida de Paso_4.


**Pros:**
- Sencillo de entender.
- Base para comprender conceptos más avanzados de Pattern Matching.
	
**Contras:**
- Ineficiente con cadenas muy grandes de búsqueda, por alto número de comparaciones.


### ㅤㅤ
### ㅤㅤ

### KMP: Knuth-Morris-Pratt:
*Utiliza información previa para evitar comparaciones innecesarias.*

**Paso a paso:**
1. Construcción de tabla LPS de prefijos(longest proper prefix suffix):
	- Tiene la misma longitud que el patrón de búsqueda.
	- Se inicia con ceros.
	- Se recorre patrón desde segundo caracter hasta el último y en cada posición se calcula valor de LPS.
	- Valor de LPS indica longitud de prefijo más largo, que también es sufijo propio del patrón.
2. Búsqueda usando algoritmo KMP:
	- Se inician dos índices: i para texto objetivo, j para patrón de búsqueda.
	- Se itera i mientras su valor sea menor a la longitud del texto:
		- Si T[i] = P[j], se incrementan ambos índices en 1.
  		- Si j = longitud del patrón de búsqueda, se ha encontrado una coincidencia entre ambos. (Se puede hacer una o más búsquedas, al actualizar el índice j en la tabla LPS y ajustar i en función de j, según se solicite).
		- Si T[i] ≠ P[j], se actualiza j con la tabla LPS. (Si j ≠ 0, j disminuye en 1, caso contrario se incrementa en 1).


**Pros:**
- Eficiente en tiempo al comparar las longitudes del texto objetivo y patrón de búsqueda.
- Al usar la tabla LPS reduce número total de comparaciones.
- Salta coincidencias parciales(subpatrones repetidos), gracias al LPS.

**Contras:**
- Puede ser más complejo de implementar al usar dos índices. Es propenso a errores.
- Requiere memoria adicional.


### ㅤㅤ
### ㅤㅤ

### Boyer-Moore:
*Utiliza dos heurísticas clave para obviar secciones no coincidientes con el texto objetivo.*

__Paso a paso:__
1. Preposicionamiento del patrón de búsqueda:
	- Se construyen dos estructuras de datos importantes: la tabla de saltos de caracteres mal emparejados (Bad Character Shift Table), y la tabla de saltos de sufijos (Good Suffix Shift Table).
2. Desplazamiento del patrón: hacia la derecha a lo largo del texto objetivo. Se alinea con último caracter de texto objetivo.
3. Comparación: se comparan caracteres de derecha a izquierda, si alguno no coincide, se usan tablas de salto para saber cuántos caracteres se pueden saltar.
4. Actualización de posición del patrón de búsqueda: Si hay más coincidencias, se continúa desplazando y comparando hasta encontrar una coincidencia completa o ninguna otra.
5. Según la implementación, se repiten los Pasos2-4 hasta el final del texto objetivo.

> Tabla de saltos de caracteres mal emparejados (Bad Character Shift Table): Indica cuántos caracteres podemos saltar en el texto objetivo al encontrar alguno que no coincida con el patrón de búsqueda.

> Tabla de saltos de sufijos (Good Suffix Shift Table): Indica cuántos caracteres podemos saltar si existe coincidencia parcial con el texto objetivo y el patrón de búsqueda.


**Pros:**
- Eficiente en textos objetivo grandes, ya que realiza grandes saltos de caracteres en los strings en función de los no coincidientes.
- Menos comparaciones necesarias.
- Buen rendimiento con patrones largos, gracias a las tablas de saltos.
- Admite múltiples patrones de búsqueda en un solo texto.

**Contras:**
- Requiere procesamiento previo de los patrones de búsqueda, lo que puede consumir tiempo y recursos adicionales, más si hay muchos patrones o éstos se cambian frecuentemente.
- Ineficiente con alfabetos grandes, como con caracteres Unicode, pues requieren más memoria y tiempo de ejecución.
