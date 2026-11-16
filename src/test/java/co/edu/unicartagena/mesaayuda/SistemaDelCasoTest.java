package co.edu.unicartagena.mesaayuda;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;

class SistemaDelCasoTest {
    @Test
    void registraBuscaProcesaYDeshaceEnCola() {
        SistemaDelCaso sistema = new SistemaDelCaso();
        TicketSoporte primero = ticket("TI-001");
        TicketSoporte segundo = ticket("TI-002");
        sistema.registrar(primero);
        sistema.registrar(segundo);

        assertNotNull(sistema.buscar("ti-001"));
        assertEquals(primero, sistema.procesarSiguiente());
        assertEquals(EstadoTicket.PROCESADO, primero.getEstado());
        assertEquals(primero, sistema.deshacerUltimoProcesamiento());
        assertEquals(EstadoTicket.PENDIENTE, primero.getEstado());
        assertEquals(segundo, sistema.procesarSiguiente());
        assertEquals(primero, sistema.procesarSiguiente());
        assertEquals(2, sistema.cantidadProcesados());
    }

    @Test
    void cancelaConColaAuxiliarYConservaOrdenDeLosDemas() {
        SistemaDelCaso sistema = new SistemaDelCaso();
        sistema.registrar(ticket("TI-001"));
        sistema.registrar(ticket("TI-002"));
        sistema.registrar(ticket("TI-003"));

        TicketSoporte cancelado = sistema.cancelarPendiente("ti-002");
        assertEquals(EstadoTicket.CANCELADO, cancelado.getEstado());
        assertEquals(2, sistema.cantidadPendientes());
        assertEquals("TI-001", sistema.procesarSiguiente().getCodigo());
        assertEquals("TI-003", sistema.procesarSiguiente().getCodigo());
        assertNull(sistema.buscar("TI-999"));
    }

    @Test
    void rechazaCodigosDuplicadosYOperacionesInvalidas() {
        SistemaDelCaso sistema = new SistemaDelCaso();
        sistema.registrar(ticket("TI-001"));

        assertThrows(IllegalArgumentException.class, () -> sistema.registrar(ticket("ti-001")));
        assertThrows(IllegalArgumentException.class, () -> sistema.cancelarPendiente("TI-404"));
        SistemaDelCaso vacio = new SistemaDelCaso();
        assertThrows(NoSuchElementException.class, vacio::procesarSiguiente);
        assertThrows(NoSuchElementException.class, vacio::deshacerUltimoProcesamiento);
    }

    private TicketSoporte ticket(String codigo) {
        return new TicketSoporte(codigo, "Ana", "Software", "No inicia la aplicacion");
    }
}
