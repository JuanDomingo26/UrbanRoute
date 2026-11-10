package estructuras;

/**
 *
 * @author Juan Plaz
 */
public class ColaPrioridad<T> {

    private Nodo<ElementoPrioridad<T>> pPrimero;
    private int iN;

    public ColaPrioridad() {
        this.pPrimero = null;
        this.iN = 0;
    }

    public boolean estaVacia() {
        return pPrimero == null;
    }

    public int getTamano() {
        return iN;
    }

    public T extraerMinimo() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola esta vacia");
        }
        T dato = pPrimero.getDato().getDato();
        pPrimero = pPrimero.getSiguiente();
        iN--;
        return dato;
    }

    public T verMinimo() {
        if (estaVacia()) {
            throw new IllegalStateException("La cola esta vacia");
        }
        return pPrimero.getDato().getDato();
    }

    public void insertar(T dato, int prioridad) {
        ElementoPrioridad<T> Elemento = new ElementoPrioridad<>(dato, prioridad);
        Nodo<ElementoPrioridad<T>> nuevo = new Nodo<>(Elemento);
        Nodo<ElementoPrioridad<T>> actual = pPrimero;
        if(estaVacia() || prioridad < pPrimero.getDato().getPrioridad()){
            nuevo.setSiguiente(pPrimero);
            pPrimero = nuevo;
        } else{
        while (actual.getSiguiente() != null && prioridad >= actual.getSiguiente().getDato().getPrioridad()) {
            actual = actual.getSiguiente();
        }
        nuevo.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(nuevo);
        }
        iN++;
        

    }
}
