package co.edu.unicartagena.mesaayuda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class ColaPilaTest {
    @Test
    void colaRespetaElOrdenFifo() {
        Cola<String> cola = new Cola<>();
        cola.encolar("A");
        cola.encolar("B");

        assertEquals("A", cola.peek());
        assertEquals("A", cola.desencolar());
        assertEquals("B", cola.desencolar());
        assertTrue(cola.esVacia());
        assertThrows(NoSuchElementException.class, cola::desencolar);
    }

    @Test
    void pilaRespetaElOrdenLifo() {
        Pila<String> pila = new Pila<>();
        pila.apilar("A");
        pila.apilar("B");

        assertEquals("B", pila.peek());
        assertEquals("B", pila.desapilar());
        assertEquals("A", pila.buscar("A"));
        assertEquals("A", pila.desapilar());
        assertThrows(NoSuchElementException.class, pila::desapilar);
    }
}
