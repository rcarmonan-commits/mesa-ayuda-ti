package co.edu.unicartagena.mesaayuda;

/**
 * Nodo enlazado que almacena un dato y referencias a sus vecinos.
 *
 * @param <T> tipo del dato almacenado
 */
public class Nodo<T> {
    private T dato;
    private Nodo<T> izquierda;
    private Nodo<T> derecha;

    /**
     * Crea un nodo con el dato indicado.
     *
     * @param dato valor que se almacenará
     */
    public Nodo(T dato) {
        this.dato = dato;
    }

    /**
     * Devuelve el dato almacenado.
     *
     * @return dato del nodo
     */
    public T getDato() {
        return dato;
    }

    /**
     * Reemplaza el dato almacenado.
     *
     * @param dato nuevo valor del nodo
     */
    public void setDato(T dato) {
        this.dato = dato;
    }

    /**
     * Devuelve el nodo enlazado a la izquierda.
     *
     * @return nodo izquierdo, o {@code null} si no está enlazado
     */
    public Nodo<T> getIzquierda() {
        return izquierda;
    }

    /**
     * Establece el nodo enlazado a la izquierda.
     *
     * @param izquierda nodo que se enlazará a la izquierda
     */
    public void setIzquierda(Nodo<T> izquierda) {
        this.izquierda = izquierda;
    }

    /**
     * Devuelve el nodo enlazado a la derecha.
     *
     * @return nodo derecho, o {@code null} si no está enlazado
     */
    public Nodo<T> getDerecha() {
        return derecha;
    }

    /**
     * Establece el nodo enlazado a la derecha.
     *
     * @param derecha nodo que se enlazará a la derecha
     */
    public void setDerecha(Nodo<T> derecha) {
        this.derecha = derecha;
    }

    /**
     * Devuelve el nodo anterior; es un alias de {@link #getIzquierda()}.
     *
     * @return nodo anterior, o {@code null} si no está enlazado
     */
    public Nodo<T> getAnterior() {
        return izquierda;
    }

    /**
     * Establece el nodo anterior; es un alias de {@link #setIzquierda(Nodo)}.
     *
     * @param anterior nodo que precede a este
     */
    public void setAnterior(Nodo<T> anterior) {
        izquierda = anterior;
    }

    /**
     * Devuelve el nodo siguiente; es un alias de {@link #getDerecha()}.
     *
     * @return nodo siguiente, o {@code null} si no está enlazado
     */
    public Nodo<T> getSiguiente() {
        return derecha;
    }

    /**
     * Establece el nodo siguiente; es un alias de {@link #setDerecha(Nodo)}.
     *
     * @param siguiente nodo que sigue a este
     */
    public void setSiguiente(Nodo<T> siguiente) {
        derecha = siguiente;
    }
}
