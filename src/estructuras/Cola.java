package estructuras;

/**
 * Estructura FIFO generica
 *
 * @param <T> tipo de valor que guarda la cola
 * @author Juan Plaz
 */
public class Cola<T> {

    private Nodo<T> pFrente;
    private Nodo<T> pFinal;
    private int iN;

    /**
     * Construye una cola vacia
     */
    public Cola() {
        this.pFrente = null;
        this.pFinal = null;
        this.iN = 0;
    }

    /**
     * Verifica si la cola esta vacia
     *
     * @return booleano, true si esta vacia
     */
    public boolean estaVacia() {
        return pFrente == null;
    }

    /**
     * Devuelve el tamaño de la cola
     *
     * @return entero, valor con el tamaño de la cola
     */
    public int getTamano() {
        return iN;
    }

    /**
     * Inserta un elemento al final de la cola
     *
     * @param dato valor a ingresar a la cola
     */
    public void encolar(T dato) {
        Nodo<T> valor = new Nodo<>(dato);
        if (estaVacia()) {
            pFrente = valor;
            pFinal = valor;
        } else {
            pFinal.setSiguiente(valor);
            pFinal = valor;
        }
        iN++;
    }

    /**
     * Quita el primer elemento de la cola y devuelve su valor
     *
     * @return valor del dato en la posicion del frente
     * @throws IllegalStateException si la cola esta vacia
     */
    public T desencolar() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola esta vacia");
        }
        T dato = pFrente.getDato();
        pFrente = pFrente.getSiguiente();
        if (pFrente == null) {
            pFinal = null;
        }
        iN--;
        return dato;
    }

    /**
     * Devuelve el valor del primero de la cola
     * 
     * @return valor de el primero de la cola
     * @throws IllegalStateException si la cola esta vacia
     */
    public T verPrimero() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola esta vacia");
        }
        return pFrente.getDato();
    }
}
