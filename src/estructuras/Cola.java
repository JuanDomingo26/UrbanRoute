package estructuras;

/**
 *
 * @author juand
 */
public class Cola<T> {

    private Nodo<T> pFrente;
    private Nodo<T> pFinal;
    private int iN;

    public Cola() {
        this.pFrente = null;
        this.pFinal = null;
        this.iN = 0;
    }

    public boolean estaVacia() {
        return pFrente == null;
    }

    public int getTamano() {
        return iN;
    }

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
    
    public T desencolar(){
        if(estaVacia()){throw new IllegalStateException("La cola esta vacia");}
        T dato = pFrente.getDato();
        pFrente = pFrente.getSiguiente();
        if(pFrente == null){pFinal = null;}
        iN--;
        return dato;            
    }
    
    public T verPrimero(){
        if(estaVacia()){throw new IllegalStateException("La cola esta vacia");}
        return pFrente.getDato();
    }
}
