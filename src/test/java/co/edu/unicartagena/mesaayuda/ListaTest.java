package co.edu.unicartagena.mesaayuda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import org.junit.jupiter.api.Test;

class ListaTest {
    @Test
    void agregaInsertaEliminaYRecorreEnAmbasDirecciones() {
        Lista<Integer> lista = new Lista<>();
        lista.agregar(1);
        lista.agregar(3);
        lista.agregarEnPosicion(1, 2);
        lista.agregarAlInicio(0);

        assertEquals(List.of(0, 1, 2, 3), leerAdelante(lista));
        assertEquals(List.of(3, 2, 1, 0), leerAtras(lista));
        assertEquals(2, lista.eliminarEnPosicion(2));
        assertEquals(0, lista.eliminarPrimero());
        assertEquals(3, lista.eliminarUltimo());
        assertEquals(1, lista.buscarDato(0));
        assertEquals(1, lista.cuentaElementos());
    }

    @Test
    void limpiaYValidaIndices() {
        Lista<String> lista = new Lista<>();
        assertThrows(IndexOutOfBoundsException.class, () -> lista.buscarDato(0));
        lista.agregar("ticket");
        lista.limpiar();
        assertTrue(lista.esVacia());
        assertEquals(0, lista.cuentaElementos());
    }

    private List<Integer> leerAdelante(Lista<Integer> lista) {
        return java.util.Arrays.stream(lista.mostrarAdelante().split(System.lineSeparator()))
                .map(Integer::parseInt).toList();
    }

    private List<Integer> leerAtras(Lista<Integer> lista) {
        return java.util.Arrays.stream(lista.mostrarAtras().split(System.lineSeparator()))
                .map(Integer::parseInt).toList();
    }
}
