package co.edu.unicartagena.mesaayuda;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class Cola<T> {
    private Nodo<T> frente;
    private Nodo<T> fin;
    private int cantidad;

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

    public T peek() {
        asegurarNoVacia();
        return frente.getDato();
    }

    public int tamanio() {
        return cantidad;
    }

    public boolean esVacia() {
        return frente == null;
    }

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

    public void limpiar() {
        frente = null;
        fin = null;
        cantidad = 0;
    }

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
