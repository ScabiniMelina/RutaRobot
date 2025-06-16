# Ruta del Robot - Simulación de Planta Energética

## Integrantes
- **Mayra Rossi** (35.366.464) - Mayra.rossi03@gmail.com
- **Melina Scabini** (44.756.058) - Melina.Scabini@gmail.com

## Profesores
- Patricia Bagnes
- Ignacio Sotelo

## Tabla de Contenidos
1. [Descripción](#descripción)
2. [Características Principales](#características-principales)
3. [Estructura del Proyecto](#estructura-del-proyecto)
4. [Requisitos](#requisitos)
5. [Instalación](#instalación)
6. [Uso](#uso)
7. [Documentación Técnica](#documentación-técnica)

## Descripción
Aplicación Java que simula el recorrido de un robot a través de una grilla rectangular que representa una planta energética. El robot debe moverse desde la esquina superior izquierda (0,0) hasta la esquina inferior derecha (n-1,m-1), manteniendo un balance neutro de cargas eléctricas (+1/-1) en su ruta.

## Características Principales
- Interfaz gráfica intuitiva
- Generación de grillas dinámicas
- Visualización del recorrido del robot
- Métricas de rendimiento
- Algoritmo de búsqueda optimizado con poda
- Patrón Observer para actualizaciones en tiempo real

## Estructura del Proyecto
```
src/main/java/
├── controller/         # Controladores
│   └── RobotController.java
├── model/             # Modelo de datos
│   ├── BoardName.java
│   ├── Grid.java
│   ├── Metric.java
│   ├── Position.java
│   ├── Robot.java
│   └── util/
├── observer/          # Implementación del patrón Observer
├── view/              # Vistas
│   ├── BaseView.java
│   ├── BoardView.java
│   ├── MainView.java
│   ├── ReportView.java
│   └── util/
└── Main.java          # Punto de entrada
```

## Requisitos
- Java 11 o superior
- Maven 3.6.3 o superior

## Instalación
1. Clonar el repositorio:
   ```bash
   git clone https://github.com/ScabiniMelina/RutaRobot.git
   cd RutaRobot
   ```

2. Compilar el proyecto:
   ```bash
   mvn clean install
   ```

## Uso
1. Ejecutar la aplicación:
   ```bash
   mvn exec:java -Dexec.mainClass="Main"
   ```

2. En la interfaz gráfica:
   - Configurar los parámetros de la grilla
   - Hacer clic en "Iniciar Simulación"
   - Visualizar los resultados y métricas

## Documentación Técnica

### Introducción
El objetivo de este trabajo práctico fue desarrollar una aplicación visual que permita simular el recorrido de un robot a través de una planta energética representada como una grilla rectangular de n x m. Cada celda de la grilla contiene una carga eléctrica que puede ser positiva (+1) o negativa (−1).

El robot debe partir desde la celda superior izquierda (0,0) y llegar a la celda inferior derecha (n−1, m−1), moviéndose únicamente hacia la derecha o hacia abajo. Como restricción principal, el recorrido debe tener una suma total de cargas igual a cero, es decir, debe tener la misma cantidad de celdas con carga positiva que negativa.

### Algoritmo de Búsqueda
- Implementa búsqueda en profundidad (DFS) con poda
- Estrategias de optimización:
  - **Poda por balance inalcanzable**: Detiene el camino si la suma actual es tan alta (en positivo o negativo) que, aunque las celdas que quedan sean del signo contrario, no se puede llegar a una suma final de cero.
  - **Validación de paridad**: Verifica si la suma actual y los pasos restantes tienen la misma paridad (par o impar).
  - **Cálculo de límites de optimización**: Evalúa los límites teóricos para determinar la viabilidad de continuar la búsqueda.

### Análisis de Complejidad

#### Complejidad del Algoritmo
- **Peor Caso**: O(2^(n+m)) - Sin podas, explorando todos los caminos posibles
  - Donde n×m son las dimensiones de la matriz (filas × columnas)
- **Con Poda por Balance**: O(2^(n+m)/2) - En el mejor de los casos reduce a la mitad el espacio de búsqueda

#### Rendimiento
- En grillas pequeñas las optimizaciones no muestran mejoras significativas.
- En algunos casos, el tiempo puede ser ligeramente mayor debido al overhead de las validaciones.
- Las ventajas de la poda se vuelven más notorias en grillas más grandes.

### Decisiones de Desarrollo

#### Estrategias de Poda
#### Poda por balance inalcanzable:
- Detiene el camino si la suma actual es inalcanzable a cero con los pasos restantes.
- Ejemplo: Si quedan 4 pasos y la suma actual es +6, incluso restando -1 en cada paso, solo se puede llegar a +2.
- Implementación: `isBalanceImpossible(sum, stepsLeft)` en la clase `Robot`

#### Implementación del Patrón Observer
- Sistema de notificación que actualiza automáticamente la vista cuando cambia el estado del modelo.
- Desacopla la lógica de negocio de la presentación.
- Facilita futuras extensiones y cambios en la interfaz.

#### Cálculo del Tiempo de Ejecución
- Uso de `System.nanoTime()` para mediciones precisas.
- Permite comparaciones exactas incluso en grafos pequeños.
- Evalúa objetivamente la eficiencia de cada algoritmo.

### Buenas Prácticas

1. **Funciones auxiliares legibles**:
   - Uso de nombres descriptivos como `isBalanceImpossible(sum, stepsLeft)` en lugar de condiciones complejas.
   - Reemplazo de validaciones directas por métodos con nombres significativos.

2. **Métricas desacopladas**:
   - Clase `Metric` separada para el seguimiento de estadísticas.
   - Mantiene la clase `Robot` enfocada en la lógica de búsqueda.

3. **Pruebas Unitarias**:
   - Cobertura de casos críticos con JUnit.
   - Validación de archivos, grillas y algoritmos.

4. **Modularidad**:
   - Estructura clara en paquetes (model, view, controller, observer).
   - Separación de responsabilidades.

5. **Desacoplamiento de Estilos**:
   - Uso de `ColorPalette` para centralizar la gestión de colores.
   - Facilita modificaciones y mantiene consistencia visual.

### Patrones de Diseño
- **MVC**: Separación clara entre Modelo, Vista y Controlador.
- **Observer**: Para actualizaciones en tiempo real de la interfaz.

---
*Universidad Nacional General Sarmiento - Programación 3 - Comisión 01*
