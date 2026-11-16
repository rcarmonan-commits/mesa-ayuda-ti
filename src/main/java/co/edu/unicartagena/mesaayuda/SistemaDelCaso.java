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
     * Devuelve la representación inversa de todos los tickets (desde el último hasta el primero).
     * Demuestra la manipulación de referencias izquierda/anterior en la lista doblemente enlazada.
     *
     * @return tickets registrados en orden inverso separados por líneas
     */
    public String mostrarAtras() {
        return listaGeneral.mostrarAtras();
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

    /**
     * Precarga un catálogo deliberado de 15 tickets de soporte técnico de infraestructura
     * para facilitar la grabación del video de sustentación y auditoría docente sin digitar datos en vivo.
     */
    public void precargarDatosPrueba() {
        registrar(new TicketSoporte("TI-101", "Ana Gomez - Finanzas", "Red", "Acceso VPN corporativo bloqueado por expiracion de certificado"));
        registrar(new TicketSoporte("TI-102", "Carlos Mendoza - TI", "Base de datos", "Falla en cluster PostgreSQL - Timeout en transacciones de nomina"));
        registrar(new TicketSoporte("TI-103", "Diana Rivas - RRHH", "Software", "Aprovisionamiento y activacion de licencia Office 365"));
        registrar(new TicketSoporte("TI-104", "Esteban Torres - Planta", "Red", "Caida total de enlace de fibra optica en sede norte"));
        registrar(new TicketSoporte("TI-105", "Fabiola Perez - Logistica", "Software", "Error de sincronizacion en modulo de inventario SAP"));
        registrar(new TicketSoporte("TI-106", "Gabriel Castro - Caja", "Hardware", "Impresora de red de facturacion sin respuesta a ping"));
        registrar(new TicketSoporte("TI-107", "Helena Morales - Web", "Servidores", "Reinicio inesperado del servicio Apache HTTP principal"));
        registrar(new TicketSoporte("TI-108", "Ignacio Ruiz - Calidad", "Seguridad", "Instalacion de parches criticos de Windows en estacion de trabajo"));
        registrar(new TicketSoporte("TI-109", "Julia Navarro - Servidores", "Servidores", "Alerta de saturacion de almacenamiento SAN mayor al 95%"));
        registrar(new TicketSoporte("TI-110", "Kevin Ortiz - Contabilidad", "Seguridad", "Desbloqueo de credenciales de usuario en Active Directory"));
        registrar(new TicketSoporte("TI-111", "Laura Sanchez - Comercial", "Hardware", "Configuracion de cliente de correo corporativo en laptop"));
        registrar(new TicketSoporte("TI-112", "Manuel Ramos - Redes", "Red", "Intermitencia y perdida de paquetes en switch del piso 3"));
        registrar(new TicketSoporte("TI-113", "Natalia Beltran - Riesgos", "Base de datos", "Exportacion y backup cifrado de base de datos para auditoria"));
        registrar(new TicketSoporte("TI-114", "Oscar Jimenez - Seguridad", "Seguridad", "Deteccion de intentos de fuerza bruta sobre puerto SSH 22"));
        registrar(new TicketSoporte("TI-115", "Patricia Duarte - Gerencia", "Servidores", "Solicitud de ampliacion de memoria RAM en servidor virtual"));
    }

    private String normalizarCodigo(String codigo) {
        Objects.requireNonNull(codigo, "El codigo no puede ser nulo.");
        String normalizado = codigo.trim();
        if (normalizado.isEmpty()) {
            throw new IllegalArgumentException("El codigo no puede estar vacio.");
        }
        return normalizado.toUpperCase(Locale.ROOT);
    }

    /**
     * Punto de entrada principal en caso de ejecución directa de SistemaDelCaso.
     *
     * @param args argumentos de línea de comandos
     */
    public static void main(String[] args) {
        Main.main(args);
    }
}
