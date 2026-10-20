# Instrucciones del proyecto

## Arquitectura

Aplicación de consola Java 17, en el paquete `co.edu.unicartagena.mesaayuda`. `Main` gestiona el menú y la entrada/salida; `SistemaDelCaso` aplica las reglas del dominio y coordina `TicketSoporte` y `EstadoTicket`.

El sistema conserva cada ticket en `Lista<TicketSoporte>`, mantiene los pendientes en `Cola<TicketSoporte>` (FIFO) y guarda los procesados en `Pila<TicketSoporte>` (LIFO). Las tres estructuras son implementaciones enlazadas propias mediante `Nodo<T>`; no las reemplaces por colecciones estándar de Java. Al cancelar, `SistemaDelCaso` usa una cola auxiliar para conservar el orden de los pendientes restantes. Al deshacer, devuelve el ticket al final de la cola, no a su posición original.

## Compilación y pruebas

Se requiere JDK 17 o superior y Maven 3.8 o superior. Desde la raíz:

```powershell
mvn clean test
mvn package
java -cp target\classes co.edu.unicartagena.mesaayuda.Main
```

Para ejecutar una clase de prueba o un método concreto con JUnit 5 / Surefire:

```powershell
mvn -Dtest=ListaTest test
mvn -Dtest=ListaTest#agregaInsertaEliminaYRecorreEnAmbasDirecciones test
```

## Convenciones del dominio

- Mantén los nombres de clases, métodos, atributos y mensajes del dominio en español, siguiendo las clases existentes.
- Los códigos de ticket se recortan y se comparan sin distinguir mayúsculas/minúsculas. Mantén esa regla consistente entre búsqueda, duplicados y `TicketSoporte.equals`/`hashCode`.
- `TicketSoporte` rechaza campos nulos o vacíos y comienza como `PENDIENTE`; los cambios de estado se coordinan en `SistemaDelCaso`.
- Las estructuras propias rechazan datos nulos y lanzan `NoSuchElementException` al retirar o consultar elementos de una estructura vacía. Conserva estas reglas y los enlaces/contadores al modificar sus operaciones.
- Las pruebas están en `src/test/java` con JUnit Jupiter. Los nombres de selectores de prueba de Maven deben coincidir exactamente con la clase o método de prueba.
