package co.edu.unicartagena.mesaayuda;

import java.util.Locale;
import java.util.Objects;

/**
 * Coordina el registro de tickets y sus estructuras de atención, pendientes e historial.
 *
 * <p>El registro general conserva todos los tickets; la cola y la pila reflejan el estado
 * operativo de los tickets pendientes y procesados.</p>
 */
public class SistemaDelCaso {
    private final Lista<TicketSoporte> listaGeneral = new Lista<>();
    private final Cola<TicketSoporte> colaPendientes = new Cola<>();
    private final Pila<TicketSoporte> historialProcesados = new Pila<>();

    /**
     * Registra un ticket en el registro general y al final de la cola de pendientes.
     *
     * @throws NullPointerException si el ticket es nulo
     * @throws IllegalArgumentException si ya existe un ticket con el mismo código
     */
    public void registrar(TicketSoporte ticket) {
        Objects.requireNonNull(ticket, "El ticket no puede ser nulo.");
        if (buscar(ticket.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un ticket con codigo " + ticket.getCodigo() + ".");
        }
        listaGeneral.agregar(ticket);
        colaPendientes.encolar(ticket);
    }

    /**
     * Procesa el siguiente ticket pendiente y lo agrega al historial.
     *
     * @return ticket que estaba al frente de la cola
     * @throws java.util.NoSuchElementException si no hay tickets pendientes
     */
    public TicketSoporte procesarSiguiente() {
        TicketSoporte ticket = colaPendientes.desencolar();
        ticket.setEstado(EstadoTicket.PROCESADO);
        historialProcesados.apilar(ticket);
        return ticket;
    }

    /**
     * Deshace el procesamiento más reciente y devuelve el ticket al final de la cola.
     *
     * @return ticket que se devolvió a pendientes
     * @throws java.util.NoSuchElementException si no hay tickets procesados
     */
    public TicketSoporte deshacerUltimoProcesamiento() {
        TicketSoporte ticket = historialProcesados.desapilar();
        ticket.setEstado(EstadoTicket.PENDIENTE);
        colaPendientes.encolar(ticket);
        return ticket;
    }

    /**
     * Cancela un ticket pendiente y conserva el orden de los demás tickets de la cola.
     *
     * @param codigo código del ticket que se desea cancelar
     * @return ticket cancelado
     * @throws NullPointerException si el código es nulo
     * @throws IllegalArgumentException si el código está vacío o no identifica un pendiente
     */
    public TicketSoporte cancelarPendiente(String codigo) {
        String codigoNormalizado = normalizarCodigo(codigo);
        Cola<TicketSoporte> auxiliar = new Cola<>();
        TicketSoporte cancelado = null;

        while (!colaPendientes.esVacia()) {
            TicketSoporte actual = colaPendientes.desencolar();
            if (cancelado == null && actual.getCodigo().equalsIgnoreCase(codigoNormalizado)) {
                cancelado = actual;
            } else {
                auxiliar.encolar(actual);
            }
        }

        while (!auxiliar.esVacia()) {
            colaPendientes.encolar(auxiliar.desencolar());
        }

        if (cancelado == null) {
            throw new IllegalArgumentException("No existe un ticket pendiente con codigo " + codigoNormalizado + ".");
        }
        cancelado.setEstado(EstadoTicket.CANCELADO);
        return cancelado;
    }

    /**
     * Busca un ticket por su código sin distinguir mayúsculas de minúsculas.
     *
     * @param codigo código que se buscará
     * @return ticket coincidente o {@code null} si no está registrado
     */
    public TicketSoporte buscar(String codigo) {
        String codigoNormalizado = normalizarCodigo(codigo);
        for (int i = 0; i < listaGeneral.cuentaElementos(); i++) {
            TicketSoporte ticket = listaGeneral.buscarDato(i);
            if (ticket.getCodigo().equalsIgnoreCase(codigoNormalizado)) {
                return ticket;
            }
        }
        return null;
    }

    /**
     * Devuelve la representación de todos los tickets en orden de registro.
     *
     * @return tickets registrados separados por líneas
     */
    public String mostrarTodos() {
        return listaGeneral.mostrarAdelante();
    }

    /**
     * Devuelve la representación de los tickets pendientes en orden FIFO.
     *
     * @return tickets pendientes separados por líneas
     */
    public String mostrarPendientes() {
        return colaPendientes.mostrar();
    }

    /**
     * Devuelve la representación del historial con el ticket más recientemente procesado primero.
     *
     * @return historial de tickets procesados, separado por líneas
     */
    public String mostrarHistorial() {
        return historialProcesados.mostrar();
    }

    /**
     * Devuelve la cantidad total de tickets registrados, incluidos los cancelados.
     *
     * @return total de tickets registrados
     */
    public int cantidadRegistrados() {
        return listaGeneral.cuentaElementos();
    }

    /**
     * Devuelve la cantidad de tickets pendientes.
     *
     * @return total de tickets en espera de atención
     */
    public int cantidadPendientes() {
        return colaPendientes.tamanio();
    }

    /**
     * Devuelve la cantidad de tickets actualmente en el historial de procesados.
     *
     * @return total de tickets procesados
     */
    public int cantidadProcesados() {
        return historialProcesados.tamanio();
    }

    private String normalizarCodigo(String codigo) {
        Objects.requireNonNull(codigo, "El codigo no puede ser nulo.");
        String normalizado = codigo.trim();
        if (normalizado.isEmpty()) {
            throw new IllegalArgumentException("El codigo no puede estar vacio.");
        }
        return normalizado.toUpperCase(Locale.ROOT);
    }
}
