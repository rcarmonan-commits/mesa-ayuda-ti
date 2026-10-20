package co.edu.unicartagena.mesaayuda;

import java.util.Locale;
import java.util.Objects;

public class TicketSoporte {
    private final String codigo;
    private String solicitante;
    private String categoria;
    private String descripcion;
    private EstadoTicket estado;

    public TicketSoporte(String codigo, String solicitante, String categoria, String descripcion) {
        this.codigo = validarTexto(codigo, "El codigo");
        this.solicitante = validarTexto(solicitante, "El solicitante");
        this.categoria = validarTexto(categoria, "La categoria");
        this.descripcion = validarTexto(descripcion, "La descripcion");
        estado = EstadoTicket.PENDIENTE;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getSolicitante() {
        return solicitante;
    }

    public void setSolicitante(String solicitante) {
        this.solicitante = validarTexto(solicitante, "El solicitante");
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = validarTexto(categoria, "La categoria");
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = validarTexto(descripcion, "La descripcion");
    }

    public EstadoTicket getEstado() {
        return estado;
    }

    public void setEstado(EstadoTicket estado) {
        this.estado = Objects.requireNonNull(estado, "El estado no puede ser nulo.");
    }

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

    @Override
    public int hashCode() {
        return codigo.toLowerCase(Locale.ROOT).hashCode();
    }

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
