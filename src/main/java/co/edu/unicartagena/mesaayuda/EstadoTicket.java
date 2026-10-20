package co.edu.unicartagena.mesaayuda;

/** Estados posibles durante el ciclo de vida de un ticket de soporte. */
public enum EstadoTicket {
    /** Registrado y pendiente de atención. */
    PENDIENTE,
    /** Atendido y agregado al historial de procesados. */
    PROCESADO,
    /** Retirado de la cola de pendientes. */
    CANCELADO
}
