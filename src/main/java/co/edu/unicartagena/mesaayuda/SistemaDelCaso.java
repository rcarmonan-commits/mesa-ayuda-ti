package co.edu.unicartagena.mesaayuda;

import java.util.Locale;
import java.util.Objects;

public class SistemaDelCaso {
    private final Lista<TicketSoporte> listaGeneral = new Lista<>();
    private final Cola<TicketSoporte> colaPendientes = new Cola<>();
    private final Pila<TicketSoporte> historialProcesados = new Pila<>();

    public void registrar(TicketSoporte ticket) {
        Objects.requireNonNull(ticket, "El ticket no puede ser nulo.");
        if (buscar(ticket.getCodigo()) != null) {
            throw new IllegalArgumentException("Ya existe un ticket con codigo " + ticket.getCodigo() + ".");
        }
        listaGeneral.agregar(ticket);
        colaPendientes.encolar(ticket);
    }

    public TicketSoporte procesarSiguiente() {
        TicketSoporte ticket = colaPendientes.desencolar();
        ticket.setEstado(EstadoTicket.PROCESADO);
        historialProcesados.apilar(ticket);
        return ticket;
    }

    public TicketSoporte deshacerUltimoProcesamiento() {
        TicketSoporte ticket = historialProcesados.desapilar();
        ticket.setEstado(EstadoTicket.PENDIENTE);
        colaPendientes.encolar(ticket);
        return ticket;
    }

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

    public String mostrarTodos() {
        return listaGeneral.mostrarAdelante();
    }

    public String mostrarPendientes() {
        return colaPendientes.mostrar();
    }

    public String mostrarHistorial() {
        return historialProcesados.mostrar();
    }

    public int cantidadRegistrados() {
        return listaGeneral.cuentaElementos();
    }

    public int cantidadPendientes() {
        return colaPendientes.tamanio();
    }

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
