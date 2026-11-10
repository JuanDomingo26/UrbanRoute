package estructuras;

/**
 * Par formado por un dato y prioridad
 *
 * @author Juan Plaz
 * @param <T> valor del elemento de prioridad
 */
public class ElementoPrioridad<T> {

    private T dato;
    private int prioridad;

    /**
     * Crea un elemento para el tipo de estructura ColaPrioridad
     *
     * @param dato valor del elemento de prioridad
     * @param prioridad del elemento en la cola
     */
    public ElementoPrioridad(T dato, int prioridad) {
        this.dato = dato;
        this.prioridad = prioridad;
    }

    /**
     * Devuelve el dato de el elemento
     *
     * @return valor del elemento
     */
    public T getDato() {
        return dato;
    }

    /**
     * Devuelve la prioridad de el elemento
     *
     * @return prioridad del elemento
     */
    public int getPrioridad() {
        return prioridad;
    }

}
