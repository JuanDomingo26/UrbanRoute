package estructuras;

/**
 * Pieza elemental de listas enlazadas, que posee un valor y un apuntador al
 * siguiente nodo de la lista
 *
 * @author Juan Plaz
 * @param <T> el tipo de dato que guarda el nodo
 */
public class Nodo<T> {

    private T dato;
    private Nodo<T> pSiguiente;

    /**
     * Crea un nodo con dato especifico y siguente null
     *
     * @param x el dato que guarda el nodo
     */
    public Nodo(T x) {
        this.dato = x;
        this.pSiguiente = null;
    }

    /**
     * Devuelve el valor del nodo
     *
     * @return el dato guardado en el nodo
     */
    public T getDato() {
        return dato;
    }

    /**
     * Regresa el apuntador al siguiente nodo en la lista
     *
     * @return el siguiente nodo
     */
    public Nodo<T> getSiguiente() {
        return pSiguiente;
    }

    /**
     * Asigna el valor de nuestro apuntador pSiguiente
     *
     * @param valor el nodo que se quiere establecer como siguiente
     */
    public void setSiguiente(Nodo<T> valor) {
        pSiguiente = valor;
    }
}
