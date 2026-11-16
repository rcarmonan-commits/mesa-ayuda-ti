package co.edu.unicartagena.mesaayuda;

import java.util.Locale;
import java.util.Objects;

/** Representa un ticket de soporte identificado de forma única por su código. */
public class TicketSoporte {
    private String codigo;
    private String solicitante;
    private String categoria;
    private String descripcion;
    private EstadoTicket estado;

    /**
     * Crea un ticket pendiente y valida que sus campos de texto no sean nulos ni vacíos.
     *
     * @param codigo identificador del ticket
     * @param solicitante persona que reporta el problema
     * @param categoria tipo de problema
     * @param descripcion detalle del problema reportado
     */
    public TicketSoporte(String codigo, String solicitante, String categoria, String descripcion) {
        this.codigo = validarTexto(codigo, "El codigo");
        this.solicitante = validarTexto(solicitante, "El solicitante");
        this.categoria = validarTexto(categoria, "La categoria");
        this.descripcion = validarTexto(descripcion, "La descripcion");
        estado = EstadoTicket.PENDIENTE;
    }

    /**
     * Devuelve el código del ticket.
     *
     * @return código identificador
     */
    public String getCodigo() {
        return codigo;
    }

    /**
     * Actualiza el código del ticket; el valor no puede ser nulo ni estar vacío.
     *
     * @param codigo nuevo código del ticket
     */
    public void setCodigo(String codigo) {
        this.codigo = validarTexto(codigo, "El codigo");
    }

    /**
     * Devuelve el nombre del solicitante.
     *
     * @return nombre de quien reportó el problema
     */
    public String getSolicitante() {
        return solicitante;
    }

    /**
     * Actualiza el solicitante; el valor no puede ser nulo ni estar vacío.
     *
     * @param solicitante nuevo nombre del solicitante
     */
    public void setSolicitante(String solicitante) {
        this.solicitante = validarTexto(solicitante, "El solicitante");
    }

    /**
     * Devuelve la categoría del ticket.
     *
     * @return categoría del problema
     */
    public String getCategoria() {
        return categoria;
    }

    /**
     * Actualiza la categoría; el valor no puede ser nulo ni estar vacío.
     *
     * @param categoria nueva categoría del problema
     */
    public void setCategoria(String categoria) {
        this.categoria = validarTexto(categoria, "La categoria");
    }

    /**
     * Devuelve la descripción del problema.
     *
     * @return descripción del ticket
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Actualiza la descripción; el valor no puede ser nulo ni estar vacío.
     *
     * @param descripcion nueva descripción del problema
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = validarTexto(descripcion, "La descripcion");
    }

    /**
     * Devuelve el estado actual del ticket.
     *
     * @return estado del ticket
     */
    public EstadoTicket getEstado() {
        return estado;
    }

    /**
     * Actualiza el estado del ticket; no admite valores nulos.
     *
     * @param estado nuevo estado
     */
    public void setEstado(EstadoTicket estado) {
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser nulo.");
    }

    /**
     * Compara tickets por código, sin distinguir mayúsculas de minúsculas.
     *
     * @param objeto objeto que se comparará con este ticket
     * @return {@code true} si el objeto es un ticket con el mismo código
     */
    @Override
    public boolean equals(Object objeto) {
        if (this == objeto) {
            return true;
        }
        if (!(objeto instanceof TicketSoporte otro)) {
            return false;
        }
        return codigo.equalsIgnoreCase(otro.codigo);
    }

    /**
     * Calcula el hash del código normalizado, coherente con {@link #equals(Object)}.
     *
     * @return hash del código del ticket
     */
    @Override
    public int hashCode() {
        return codigo.toLowerCase(Locale.ROOT).hashCode();
    }

    /**
     * Devuelve una representación legible del ticket y sus datos.
     *
     * @return texto con el código, solicitante, categoría, estado y descripción
     */
    @Override
    public String toString() {
        return "Ticket " + codigo + " | Solicitante: " + solicitante
                + " | Categoria: " + categoria + " | Estado: " + estado
                + " | Descripcion: " + descripcion;
    }

    private static String validarTexto(String valor, String campo) {
        Objects.requireNonNull(valor, campo + " no puede ser nulo.");
        String normalizado = valor.trim();
        if (normalizado.isEmpty()) {
            throw new IllegalArgumentException(campo + " no puede estar vacio.");
        }
        return normalizado;
    }
}
