package co.edu.unicartagena.mesaayuda;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Pila enlazada de tipo LIFO: el último elemento en entrar es el primero en salir.
 *
 * @param <T> tipo de los elementos; no se admiten valores nulos
 */
public class Pila<T> {
    private Nodo<T> cima;
    private int cantidad;

    /**
     * Agrega un elemento en la cima de la pila.
     *
     * @param dato elemento que se agregará
     */
    public void apilar(T dato) {
        Objects.requireNonNull(dato, "El dato no puede ser nulo.");
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cima);
        cima = nuevo;
        cantidad++;
    }

    /**
     * Retira y devuelve el elemento de la cima.
     *
     * @return elemento retirado
     * @throws NoSuchElementException si la pila está vacía
     */
    public T desapilar() {
        asegurarNoVacia();
        T dato = cima.getDato();
        cima = cima.getSiguiente();
        cantidad--;
        return dato;
    }

    /**
     * Devuelve el elemento de la cima sin retirarlo.
     *
     * @return elemento que está en la cima
     * @throws NoSuchElementException si la pila está vacía
     */
    public T peek() {
        asegurarNoVacia();
        return cima.getDato();
    }

    /**
     * Devuelve la cantidad de elementos en la pila.
     *
     * @return número de elementos
     */
    public int tamanio() {
        return cantidad;
    }

    /**
     * Indica si la pila está vacía.
     *
     * @return {@code true} si no hay elementos
     */
    public boolean esVacia() {
        return cima == null;
    }

    /**
     * Indica si la pila contiene un elemento igual al dato recibido.
     *
     * @param dato valor que se buscará
     * @return {@code true} si existe un elemento igual al dato
     */
    public boolean contiene(Object dato) {
        return buscar(dato) != null;
    }

    /** Retira todos los elementos de la pila. */
    public void limpiar() {
        cima = null;
        cantidad = 0;
    }

    /**
     * Devuelve los elementos desde la cima hacia el fondo, separados por líneas.
     *
     * @return representación de los elementos en orden LIFO
     */
    public String mostrar() {
        StringJoiner resultado = new StringJoiner(System.lineSeparator());
        Nodo<T> actual = cima;
        while (actual != null) {
            resultado.add(String.valueOf(actual.getDato()));
            actual = actual.getSiguiente();
        }
        return resultado.toString();
    }

    /**
     * Devuelve un elemento igual al dato recibido.
     *
     * @param dato valor que se buscará
     * @return elemento encontrado o {@code null} si no existe
     */
    public T buscar(Object dato) {
        Nodo<T> actual = cima;
        while (actual != null) {
            if (Objects.equals(actual.getDato(), dato)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    private void asegurarNoVacia() {
        if (esVacia()) {
            throw new NoSuchElementException("La pila esta vacia.");
        }
    }
}
