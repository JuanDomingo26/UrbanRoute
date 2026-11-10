package estructuras;

/**
 * ColaPrioridad, elementos organzados segun su nivel de prioridad
 *
 * @author Juan Plaz
 * @param <T> tipo de dato que guarda la cola
 */
public class ColaPrioridad<T> {

    private Nodo<ElementoPrioridad<T>> pPrimero;
    private int iN;

    /**
     * Crea la cola vacia
     *
     */
    public ColaPrioridad() {
        this.pPrimero = null;
        this.iN = 0;
    }

    /**
     * Devuelve verdadero si la cola esta vacia
     *
     * @return booleano, true si esta vacia
     */
    public boolean estaVacia() {
        return pPrimero == null;
    }

    /**
     * Devuelve el tamaño de la cola
     *
     * @return entero, el valor del tamaño de la cola
     */
    public int getTamano() {
        return iN;
    }

    /**
     * Desencola, que segun la organizacion de la cola, seria el valor con
     * prioridad minima
     *
     * @return T, valor dentro de el elemento de menor prioridad
     * @throws IllegalStateException si la cola esta vacia
     */
    public T extraerMinimo() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola esta vacia");
        }
        T dato = pPrimero.getDato().getDato();
        pPrimero = pPrimero.getSiguiente();
        iN--;
        return dato;
    }

    /**
     * Devuelve el valor de menor prioridad
     *
     * @return T, valor dentro de el elemento de menor prioridad
     * @throws IllegalStateException si la cola esta vacia
     */
    public T verMinimo() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola esta vacia");
        }
        return pPrimero.getDato().getDato();
    }

    /**
     * Inserta un valor
     *
     * @param dato valor a almacenar
     * @param prioridad entero que establece la prioridad del elemento
     */
    public void insertar(T dato, int prioridad) {
        ElementoPrioridad<T> Elemento = new ElementoPrioridad<>(dato, prioridad);
        Nodo<ElementoPrioridad<T>> nuevo = new Nodo<>(Elemento);
        Nodo<ElementoPrioridad<T>> actual = pPrimero;
        if (estaVacia() || prioridad < pPrimero.getDato().getPrioridad()) {
            nuevo.setSiguiente(pPrimero);
            pPrimero = nuevo;
        } else {
            while (actual.getSiguiente() != null && prioridad >= actual.getSiguiente().getDato().getPrioridad()) {
                actual = actual.getSiguiente();
            }
            nuevo.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(nuevo);
        }
        iN++;

    }
}
