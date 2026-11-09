package estructuras;

    /**
 * Lista enlazada compuesta por nodos
 * @author Juan Plaz
 * @param <T> valor guardado por la lista
 * 
 */
public class Lista<T> {

    private Nodo<T> pPrimero;
    private Nodo<T> pUltimo;
    private int iN;

    /**
     * Crea una lista nueva vacia
     *
     */
    public Lista() {
        this.pPrimero = null;
        this.pUltimo = null;
        this.iN = 0;
    }

    /**
     * Devuelve true si la lista esta vacia
     *
     * @return si la lista esta vacia o no
     */
    public boolean estaVacia() {
        return pPrimero == null;
    }

    /**
     * Da el tamaño de la lista
     *
     * @return devuelve el entero de tamaño de la lista
     */
    public int getTamano() {
        return iN;
    }

    /**
     * Inserta un nodo al final de la lista
     *
     * @param dato valor a insertar en la lista
     */
    public void insertar(T dato) {
        Nodo<T> valor = new Nodo<>(dato);
        if (estaVacia()) {
            pPrimero = valor;
            pUltimo = valor;
        } else {
            pUltimo.setSiguiente(valor);
            pUltimo = valor;
        }
        iN++;
    }

    /**
     * Busca si una lista posee un dato
     *
     * @param dato valor a buscar en la lista
     * @return booleano, true si lo contiene, false si no
     */
    public boolean contiene(T dato) {
        Nodo<T> actual = pPrimero;
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return true;
            }
            actual = actual.getSiguiente();

        }
        return false;
    }

    /**
     * Devuelve el valor que se encuentra en un indice especifico
     *
     * @param indice entero de posicion
     * @return el dato ubicado en el indice ingresado (si existe)
     * @throws IndexOutOfBoundsException si el indice es negativo o mayor o igual al tamaño
     */
    public T obtener(int indice) {
        if (indice < 0 || indice >= getTamano()) {
            throw new IndexOutOfBoundsException("Indice fuera de rango:" + indice);
        }
        Nodo<T> actual = pPrimero;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();

    }

    /**
     * Elimina un valor especifico de la lista si este existe, regresa true en ese caso
     * @param dato valor a eliminar
     * @return booleano true en caso de borrar y false en el caso de que no
     */
    public boolean eliminar(T dato) {
        if (estaVacia()) {
            return false;
        }
        Nodo<T> actual = pPrimero;
        if (actual.getDato().equals(dato)) {
            pPrimero = pPrimero.getSiguiente();
            iN--;
            if (estaVacia()) {
                pUltimo = null;
            }
            return true;
        }
        while (actual != pUltimo) {
            if (actual.getSiguiente().getDato().equals(dato)) {
                if (actual.getSiguiente() == pUltimo) {
                    pUltimo = actual;
                }
                actual.setSiguiente(actual.getSiguiente().getSiguiente());
                iN--;
                return true;
            }
            actual = actual.getSiguiente();

        }

        return false;

    }

}
