package estructuras;

/**
 * Entrada para tabla hash
 *
 * @author Juan Plaz
 * @param <T> valor de la entrada
 */
public class EntradaHash<T> {

    private String clave;
    private T dato;

    /**
     * Construye una entrada hash con clave y dato
     *
     * @param clave valor de la clave hash
     * @param dato valor de la entrada
     */
    public EntradaHash(String clave, T dato) {
        this.clave = clave;
        this.dato = dato;
    }

    /**
     * Devuelve la clave hash
     *
     * @return clave string
     */
    public String getClave() {
        return clave;
    }

    /**
     * Devuelve el dato de la entrada
     *
     * @return dato, valor de la entrada
     */
    public T getDato() {
        return dato;
    }

}
