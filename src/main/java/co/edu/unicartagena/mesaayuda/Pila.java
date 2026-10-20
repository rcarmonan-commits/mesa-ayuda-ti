package co.edu.unicartagena.mesaayuda;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class Pila<T> {
    private Nodo<T> cima;
    private int cantidad;

    public void apilar(T dato) {
        Objects.requireNonNull(dato, "El dato no puede ser nulo.");
        Nodo<T> nuevo = new Nodo<>(dato);
        nuevo.setSiguiente(cima);
        cima = nuevo;
        cantidad++;
    }

    public T desapilar() {
        asegurarNoVacia();
        T dato = cima.getDato();
        cima = cima.getSiguiente();
        cantidad--;
        return dato;
    }

    public T peek() {
        asegurarNoVacia();
        return cima.getDato();
    }

    public int tamanio() {
        return cantidad;
    }

    public boolean esVacia() {
        return cima == null;
    }

    public boolean contiene(Object dato) {
        return buscar(dato) != null;
    }

    public void limpiar() {
        cima = null;
        cantidad = 0;
    }

    public String mostrar() {
        StringJoiner resultado = new StringJoiner(System.lineSeparator());
        Nodo<T> actual = cima;
        while (actual != null) {
            resultado.add(String.valueOf(actual.getDato()));
            actual = actual.getSiguiente();
        }
        return resultado.toString();
    }

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
