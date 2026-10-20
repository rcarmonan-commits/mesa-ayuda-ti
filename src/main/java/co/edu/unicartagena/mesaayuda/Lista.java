package co.edu.unicartagena.mesaayuda;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class Lista<T> {
    private Nodo<T> primero;
    private Nodo<T> ultimo;
    private int cantidad;

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

    public T buscarDato(int indice) {
        validarIndiceExistente(indice);
        return nodoEn(indice).getDato();
    }

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

    public boolean contiene(Object dato) {
        return buscarDato(dato) != null;
    }

    public int cuentaElementos() {
        return cantidad;
    }

    public boolean esVacia() {
        return cantidad == 0;
    }

    public void limpiar() {
        primero = null;
        ultimo = null;
        cantidad = 0;
    }

    public String mostrarAdelante() {
        StringJoiner resultado = new StringJoiner(System.lineSeparator());
        Nodo<T> actual = primero;
        while (actual != null) {
            resultado.add(String.valueOf(actual.getDato()));
            actual = actual.getSiguiente();
        }
        return resultado.toString();
    }

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
