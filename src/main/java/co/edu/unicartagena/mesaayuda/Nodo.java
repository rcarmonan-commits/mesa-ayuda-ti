package co.edu.unicartagena.mesaayuda;

public class Nodo<T> {
    private T dato;
    private Nodo<T> izquierda;
    private Nodo<T> derecha;

    public Nodo(T dato) {
        this.dato = dato;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getIzquierda() {
        return izquierda;
    }

    public void setIzquierda(Nodo<T> izquierda) {
        this.izquierda = izquierda;
    }

    public Nodo<T> getDerecha() {
        return derecha;
    }

    public void setDerecha(Nodo<T> derecha) {
        this.derecha = derecha;
    }

    public Nodo<T> getAnterior() {
        return izquierda;
    }

    public void setAnterior(Nodo<T> anterior) {
        izquierda = anterior;
    }

    public Nodo<T> getSiguiente() {
        return derecha;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        derecha = siguiente;
    }
}
