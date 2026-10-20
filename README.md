# Sistema de Tickets para Mesa de Ayuda TI

[Repositorio en GitHub](https://github.com/rcarmonan-commits/mesa-ayuda-ti)

Actividad: **Sistema de gestión con Lista, Cola y Pila**

**Asignatura:** Estructuras de Datos  
**Programa:** Ingeniería de Software  
**IES:** Universidad de Cartagena  
**Docente Tutor:** John Carlos Arrieta Arrieta  
**Estudiante:** Rosary Eilyn Carmona Navarro  
**Ejercicio Asignado:** Ejercicio 6 - Sistema de tickets para mesa de ayuda TI

Es una aplicación de consola en Java 17 para registrar tickets de una mesa de ayuda, atenderlos en orden de llegada, guardar los procesados y permitir deshacer el último procesamiento.

Las tres estructuras son implementaciones propias enlazadas; el proyecto no sustituye la lista, la cola ni la pila por colecciones de Java.

## Requisitos

- JDK 17 o superior
- Apache Maven 3.8 o superior

## Compilar, probar y ejecutar

Desde la raiz del proyecto:

```powershell
mvn clean test
mvn package
java -cp target\classes co.edu.unicartagena.mesaayuda.Main
```

## Menu

1. Registrar elemento
2. Ver todos los elementos registrados
3. Ver elementos pendientes
4. Procesar siguiente elemento
5. Ver historial de elementos procesados
6. Buscar elemento por codigo
7. Cancelar elemento pendiente
8. Deshacer ultimo procesamiento
9. Ver cantidad de elementos
10. Salir

Cada ticket contiene codigo, solicitante, categoria, descripcion y estado. El codigo identifica el ticket: `equals` y `hashCode` lo comparan sin distinguir mayusculas y minusculas. Los campos de texto obligatorios se validan y no pueden estar vacios.

## Estructura y responsabilidades

| Clase | Responsabilidad |
| --- | --- |
| `Nodo<T>` | Guarda un dato y referencias enlazadas a izquierda y derecha. |
| `Lista<T>` | Conserva el registro completo en una lista doblemente enlazada. Incluye inserciones y eliminaciones por índice, búsqueda, recorrido adelante/atrás y limpieza. |
| `Cola<T>` | Administra tickets pendientes en una cola enlazada FIFO: el primero que llega es el primero en atenderse. |
| `Pila<T>` | Conserva los tickets procesados en una pila enlazada LIFO y permite deshacer el procesamiento más reciente. |
| `TicketSoporte` | Modelo del caso: codigo, solicitante, categoria, descripcion y estado. |
| `EstadoTicket` | Define los estados `PENDIENTE`, `PROCESADO` y `CANCELADO`. |
| `SistemaDelCaso` | Coordina las estructuras y aplica las reglas de registro, búsqueda, procesamiento, cancelación y deshacer. |
| `Main` | Lee las opciones del menú, solicita los datos y presenta resultados y errores de validación. |

## Como se conectan las estructuras

```text
Registro --> Lista general (conserva todos los tickets)
         \-> Cola de pendientes (FIFO)

Procesar --> desencolar frente --> marcar PROCESADO --> apilar en historial
Deshacer --> desapilar historial --> marcar PENDIENTE --> encolar al final
Cancelar --> desencolar a cola auxiliar, omitir el codigo elegido y restaurar el resto
```

La lista enlaza cada nodo con sus vecinos, de modo que se puede recorrer desde el inicio o el final. La cola enlaza el último nodo con el nuevo y retira desde el frente. La pila enlaza cada nuevo nodo con la cima y tanto agrega como retira desde esa cima.

Al cancelar, el sistema no elimina desde el centro de la cola: mueve los tickets a una cola auxiliar, omite el ticket elegido y devuelve los demas a la cola original conservando su orden. El ticket cancelado permanece en el registro general con estado `CANCELADO`.

Al deshacer un procesamiento, el ticket se agrega al final de la cola de pendientes. Esta política aplica literalmente la regla de devolverlo mediante `encolar`; por eso no recupera una posición anterior a tickets que ya estaban esperando.

## Pruebas

`mvn test` ejecuta siete pruebas de:

- Insercion, busqueda, recorridos en ambos sentidos y eliminacion en la lista.
- Orden FIFO de la cola y LIFO de la pila.
- Registro y busqueda de tickets, duplicados, procesamiento, deshacer, cancelacion con cola auxiliar y operaciones invalidas.

## Paso 5: Implementación del control de versiones con Git

El código de la actividad se encuentra en el repositorio público [mesa-ayuda-ti](https://github.com/rcarmonan-commits/mesa-ayuda-ti). El desarrollo se organizó en la rama `desarrollo-ejercicio`; sus avances se guardaron en commits separados y, al terminar, se integraron en `main`.

El historial muestra los avances del proyecto:

1. `chore: crear la base Maven y configurar compilacion para Java 17`
2. `feat: implementar tickets, estructuras enlazadas y flujo de atencion por consola`
3. `test: cubrir operaciones de lista, orden FIFO y LIFO y reglas de tickets`
4. `docs: explicar arquitectura, menu, reglas del flujo y ejecucion del sistema`
5. `docs: agregar caratula academica y datos de la actividad al README`
6. `docs: documentar las clases y operaciones publicas con Javadoc en espanol`
Para consultar el historial de cambios y sus integraciones:

```powershell
git log --oneline --graph --decorate --all
```

El flujo de trabajo para publicar nuevos avances en la rama de desarrollo e integrarlos en la rama principal es:

```powershell
git switch desarrollo-ejercicio
git pull --ff-only origin desarrollo-ejercicio
git status
git add <archivos-modificados>
git commit -m "tipo: describir el cambio realizado"
git push origin desarrollo-ejercicio

git switch main
git pull --ff-only origin main
git merge --no-ff desarrollo-ejercicio
git push origin main
```

La rama `main` contiene la versión integrada y publicada del ejercicio. La URL del video de sustentación se agregará cuando esté disponible.
