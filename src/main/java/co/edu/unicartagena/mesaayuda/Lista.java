package co.edu.unicartagena.mesaayuda;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

/**
 * Lista doblemente enlazada con acceso por índice y recorridos en ambos sentidos.
 *
 * @param <T> tipo de los elementos; no se admiten valores nulos
 */
public class Lista<T> {
    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int cantidad;

    /**
     * Agrega un elemento al final de la lista.
     *
     * @param dato elemento que se agregará
     */
    public void agregar(T dato) {
        validarDato(dato);
        Nodo<T> nuevo = new Nodo<>(dato);
        if (esVacia()) {
            primero = nuevo;
        } else {
            ultimo.setSiguiente(nuevo);
            nuevo.setAnterior(ultimo);
        }
        ultimo = nuevo;
        cantidad++;
    }

    /**
     * Agrega un elemento al inicio de la lista.
     *
     * @param dato elemento que se agregará
     */
    public void agregarAlInicio(T dato) {
        validarDato(dato);
        Nodo<T> nuevo = new Nodo<>(dato);
        if (esVacia()) {
            ultimo = nuevo;
        } else {
            nuevo.setSiguiente(primero);
            primero.setAnterior(nuevo);
        }
        primero = nuevo;
        cantidad++;
    }

    /**
     * Inserta un elemento en la posición indicada.
     *
     * @param indice posición entre cero y el tamaño actual, inclusive
     * @param dato elemento que se insertará
     * @throws IndexOutOfBoundsException si el índice no es una posición válida de inserción
     */
    public void agregarEnPosicion(int indice, T dato) {
        validarDato(dato);
        validarIndiceInsercion(indice);
        if (indice == 0) {
            agregarAlInicio(dato);
            return;
        }
        if (indice == cantidad) {
            agregar(dato);
            return;
        }

        Nodo<T> actual = nodoEn(indice);
        Nodo<T> anterior = actual.getAnterior();
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setAnterior(anterior);
        nuevo.setSiguiente(actual);
        anterior.setSiguiente(nuevo);
        actual.setAnterior(nuevo);
        cantidad++;
    }

    /**
     * Elimina y devuelve el primer elemento.
     *
     * @return elemento que estaba al inicio
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarPrimero() {
        asegurarNoVacia();
        T dato = primero.getDato();
        primero = primero.getSiguiente();
        cantidad--;
        if (primero == null) {
            ultimo = null;
        } else {
            primero.setAnterior(null);
        }
        return dato;
    }

    /**
     * Elimina y devuelve el último elemento.
     *
     * @return elemento que estaba al final
     * @throws NoSuchElementException si la lista está vacía
     */
    public T eliminarUltimo() {
        asegurarNoVacia();
        T dato = ultimo.getDato();
        ultimo = ultimo.getAnterior();
        cantidad--;
        if (ultimo == null) {
            primero = null;
        } else {
            ultimo.setSiguiente(null);
        }
        return dato;
    }

    /**
     * Elimina y devuelve el elemento en la posición indicada.
     *
     * @param indice índice del elemento que se eliminará
     * @return elemento eliminado
     * @throws IndexOutOfBoundsException si el índice no corresponde a un elemento existente
     */
    public T eliminarEnPosicion(int indice) {
        validarIndiceExistente(indice);
        if (indice == 0) {
            return eliminarPrimero();
        }
        if (indice == cantidad - 1) {
            return eliminarUltimo();
        }

        Nodo<T> actual = nodoEn(indice);
        actual.getAnterior().setSiguiente(actual.getSiguiente());
        actual.getSiguiente().setAnterior(actual.getAnterior());
        cantidad--;
        return actual.getDato();
    }

    /**
     * Devuelve el elemento en la posición indicada.
     *
     * @param indice índice del elemento que se consultará
     * @return elemento en el índice indicado
     * @throws IndexOutOfBoundsException si el índice no corresponde a un elemento existente
     */
    public T buscarDato(int indice) {
        validarIndiceExistente(indice);
        return nodoEn(indice).getDato();
    }

    /**
     * Devuelve el primer elemento igual al dato según {@link Objects#equals(Object, Object)}.
     *
     * @param dato valor que se buscará
     * @return elemento encontrado o {@code null} si no existe
     */
    public T buscarDato(Object dato) {
        Nodo<T> actual = primero;
        while (actual != null) {
            if (Objects.equals(actual.getDato(), dato)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    /**
     * Indica si la lista contiene un elemento igual al dato recibido.
     *
     * @param dato valor que se buscará
     * @return {@code true} si existe un elemento igual al dato
     */
    public boolean contiene(Object dato) {
        return buscarDato(dato) != null;
    }

    /**
     * Devuelve la cantidad de elementos almacenados.
     *
     * @return número de elementos
     */
    public int cuentaElementos() {
        return cantidad;
    }

    /**
     * Indica si la lista no contiene elementos.
     *
     * @return {@code true} si está vacía
     */
    public boolean esVacia() {
        return cantidad == 0;
    }

    /** Elimina todos los elementos de la lista. */
    public void limpiar() {
        primero = null;
        ultimo = null;
        cantidad = 0;
    }

    /**
     * Devuelve los elementos en orden desde el primero hasta el último, separados por líneas.
     *
     * @return representación de los elementos de la lista
     */
    public String mostrarAdelante() {
        StringJoiner resultado = new StringJoiner(System.lineSeparator());
        Nodo<T> actual = primero;
        while (actual != null) {
            resultado.add(String.valueOf(actual.getDato()));
            actual = actual.getSiguiente();
        }
        return resultado.toString();
    }

    /**
     * Devuelve los elementos en orden desde el último hasta el primero, separados por líneas.
     *
     * @return representación inversa de los elementos de la lista
     */
    public String mostrarAtras() {
        StringJoiner resultado = new StringJoiner(System.lineSeparator());
        Nodo<T> actual = ultimo;
        while (actual != null) {
            resultado.add(String.valueOf(actual.getDato()));
            actual = actual.getAnterior();
        }
        return resultado.toString();
    }

    private Nodo<T> nodoEn(int indice) {
        if (indice < cantidad / 2) {
            Nodo<T> actual = primero;
            for (int posicion = 0; posicion < indice; posicion++) {
                actual = actual.getSiguiente();
            }
            return actual;
        }
        Nodo<T> actual = ultimo;
        for (int posicion = cantidad - 1; posicion > indice; posicion--) {
            actual = actual.getAnterior();
        }
        return actual;
    }

    private void validarDato(T dato) {
        Objects.requireNonNull(dato, "El dato no puede ser nulo.");
    }

    private void validarIndiceInsercion(int indice) {
        if (indice < 0 || indice > cantidad) {
            throw new IndexOutOfBoundsException("Indice de insercion fuera de rango: " + indice);
        }
    }

    private void validarIndiceExistente(int indice) {
        if (indice < 0 || indice >= cantidad) {
            throw new IndexOutOfBoundsException("Indice fuera de rango: " + indice);
        }
    }

    private void asegurarNoVacia() {
        if (esVacia()) {
            throw new NoSuchElementException("La lista esta vacia.");
        }
    }
}
