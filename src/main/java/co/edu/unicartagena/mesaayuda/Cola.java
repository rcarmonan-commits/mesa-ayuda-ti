package co.edu.unicartagena.mesaayuda;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Cola enlazada de tipo FIFO: el primer elemento en entrar es el primero en salir.
 *
 * @param <T> tipo de los elementos; no se admiten valores nulos
 */
public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int cantidad;

    /**
     * Agrega un elemento al final de la cola.
     *
     * @param dato elemento que se agregará
     */
    public void encolar(T dato) {
        Objects.requireNonNull(dato, "El dato no puede ser nulo.");
        Nodo<T> nuevo = new Nodo<>(dato);
        if (esVacia()) {
            frente = nuevo;
        } else {
            fin.setSiguiente(nuevo);
        }
        fin = nuevo;
        cantidad++;
    }

    /**
     * Retira y devuelve el elemento del frente.
     *
     * @return elemento retirado
     * @throws NoSuchElementException si la cola está vacía
     */
    public T desencolar() {
        asegurarNoVacia();
        T dato = frente.getDato();
        frente = frente.getSiguiente();
        cantidad--;
        if (frente == null) {
            fin = null;
        }
        return dato;
    }

    /**
     * Devuelve el elemento del frente sin retirarlo.
     *
     * @return elemento que está al frente
     * @throws NoSuchElementException si la cola está vacía
     */
    public T peek() {
        asegurarNoVacia();
        return frente.getDato();
    }

    /**
     * Devuelve la cantidad de elementos en la cola.
     *
     * @return número de elementos
     */
    public int tamanio() {
        return cantidad;
    }

    /**
     * Indica si la cola está vacía.
     *
     * @return {@code true} si no hay elementos
     */
    public boolean esVacia() {
        return frente == null;
    }

    /**
     * Indica si la cola contiene un elemento igual al dato recibido.
     *
     * @param dato valor que se buscará
     * @return {@code true} si existe un elemento igual al dato
     */
    public boolean contiene(Object dato) {
        Nodo<T> actual = frente;
        while (actual != null) {
            if (Objects.equals(actual.getDato(), dato)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    /** Retira todos los elementos de la cola. */
    public void limpiar() {
        frente = null;
        fin = null;
        cantidad = 0;
    }

    /**
     * Devuelve los elementos desde el frente hasta el final, separados por líneas.
     *
     * @return representación de los elementos en orden FIFO
     */
    public String mostrar() {
        StringJoiner resultado = new StringJoiner(System.lineSeparator());
        Nodo<T> actual = frente;
        while (actual != null) {
            resultado.add(String.valueOf(actual.getDato()));
            actual = actual.getSiguiente();
        }
        return resultado.toString();
    }

    private void asegurarNoVacia() {
        if (esVacia()) {
            throw new NoSuchElementException("La cola esta vacia.");
        }
    }
}
