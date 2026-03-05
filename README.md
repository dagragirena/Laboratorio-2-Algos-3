# Laboratorio 2: El Juego del Sudoku

**Universidad Simón Bolívar**  
**Departamento de Computación y Tecnología de la Información**  
**Asignatura:** Algoritmos y Estructuras de Datos III (CI-2693)  
**Trimestre:** Enero-Marzo 2026  

## Estudiante

### Nombre Completo y Carnet

Daniela Gragirena 19-10543

## Descripción del Laboratorio

Este laboratorio implementa una solución de Sudokus de **9x9** utilizando una estrategia de **Backtracking**. El programa recibe una cadena de 81 caracteres que representa el tablero (leída de izquierda a derecha y de arriba hacia abajo, donde el '0' representa una casilla vacía) y devuelve la solución completa o un mensaje de error ("NOSOLUTION") si el tablero no tiene solución válida.

## Instrucciones de Ejecución

El proyecto ha sido desarrollado en **Kotlin**. Siga estos pasos para compilar y ejecutar el programa desde la terminal:

### 1. Compilación

Ejecute el siguiente comando para compilar el código fuente y generar el archivo ejecutable `.jar`:

```bash
kotlinc Sudoku.kt -include-runtime -d Sudoku.jar

```

### 2. Ejecución

Para ejecutar el programa, debe pasar una cadena de 81 caracteres como argumento. Los ceros (0) representan las celdas a resolver:

```bash
java -jar Sudoku.jar 530070000600195000098000060800060003400803001700020006060000280000419005000080079

```

### 3. Salida

El programa imprimirá una cadena de 81 caracteres con el Sudoku resuelto. Si el tablero inicial es inválido o no tiene solución, imprimirá: NOSOLUTION

## Complejidad Computacional (Big O)

### Algoritmo Backtracking (`resolverSudoku`)

* **Complejidad:** O(9^n), donde n es el número de casillas vacías.
* **Explicación:** El algoritmo explora un árbol de decisión donde cada nodo (casilla vacía) puede tener hasta 9 hijos. Aunque las reglas del Sudoku actúan como una función de poda que reduce de forma importante el espacio de búsqueda real, el peor caso teórico sigue siendo exponencial.

### Función de Validación (`esValido`)

* **Complejidad:** O(1) (Constante).
* **Explicación:** Independientemente del estado del tablero, la función siempre realiza una cantidad fija de comprobaciones: revisa 9 celdas para la fila, 9 para la columna y 9 para el subcuadrante de 3x3 (un máximo de 27 iteraciones).

## Decisiones de Implementación

### 1. Representación del Sudoku (`IntArray`)

Se decidió utilizar un **arreglo unidimensional** de 81 posiciones en lugar de una matriz bidimensional.

* **Ventaja:** Optimiza el uso de la memoria y mejora la velocidad de acceso por localidad de datos.
* **Simplificación:** Permite manejar el progreso del algoritmo con un solo índice `pos` que avanza de 0 a 80.

### 2. Aritmética para Validación de Subtablero

Para validar los subtableros de 3x3 en un arreglo lineal, se implementó una fórmula:

```kotlin
val filaInicio = (fila / 3) * 3
val colInicio = (col / 3) * 3
val inicioSubtablero = filaInicio * 9 + colInicio

```

Esto permite identificar la esquina superior izquierda de cada bloque y recorrer sus celdas sumando los números correspondientes.

### 3. Gestión de la Recursión

El algoritmo implementa **backtracking**. Si una rama de búsqueda no llega a una solución válida, el estado de la celda se restaura a `0` antes de retornar a la llamada anterior, asegurando que no queden residuos de intentos fallidos en el tablero final.

### 4. Manejo de Errores

* **Validación de entrada:** Se incluyó un bloque de control en el `main` para detectar argumentos vacíos o de longitud incorrecta antes de iniciar el procesamiento.
* **Conversión de caracteres:** Se utiliza la resta de caracteres (`'x' - '0'`) para convertir de `Char` a `Int` de forma eficiente.

## Estrategias de Poda

El algoritmo implementado utiliza **poda** para reducir el espacio de búsqueda de manera eficiente:

### 1. Poda por Restricciones Locales (`esValido`)
Esta es la poda principal del algoritmo. Antes de realizar una llamada recursiva para la posición `pos + 1`, el programa verifica si el número `num` es válido en la posición actual según las tres reglas del Sudoku (fila, columna y subtablero 3x3). 

Si un número viola una regla, esa rama del árbol de decisión se corta inmediatamente, evitando explorar combinaciones subsecuentes que serían inválidas.

### 2. Salto de Celdas Prefijadas
El algoritmo identifica las celdas que ya contienen valores iniciales mediante la condición `if (sudoku[pos] != 0)`.

Así, en lugar de intentar probar números del 1 al 9 en esas casillas, el algoritmo salta directamente a la siguiente posición. Esto reduce la profundidad efectiva de la recursión y concentra el procesamiento únicamente en las celdas libres.

### 3. Finalización Temprana
Una vez que el algoritmo encuentra la **primera solución válida** (cuando `pos == 81`), el valor `true` se retorna hacia arriba en toda la pila de llamadas recursivas. Esto detiene cualquier búsqueda adicional, garantizando que el programa termine tan pronto como se halle una solución válida, en lugar de seguir buscando otras posibles soluciones.