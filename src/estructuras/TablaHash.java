package estructuras;

/**
 *
 * @author Juan Plaz
 */
public class TablaHash<T> {

    private Lista<EntradaHash<T>>[] tabla;
    private int iN;

    @SuppressWarnings({"unchecked", "rawtypes"})
    public TablaHash() {
        this.tabla = new Lista[11];
        for (int i = 0; i < tabla.length; i++) {
            this.tabla[i] = new Lista<>();
        }
        this.iN = 0;
    }

    public int getTamano() {
        return iN;
    }

    public boolean estaVacia() {
        return iN == 0;
    }

    private int hash(String clave) {
        int h = 0;
        for (int i = 0; i < clave.length(); i++) {
            h = (h * 31 + clave.charAt(i)) % tabla.length;
        }
        return h;
    }

    private EntradaHash<T> buscarEntrada(String clave) {
        Lista<EntradaHash<T>> seccion = tabla[hash(clave)];
        for (int i = 0; i < seccion.getTamano(); i++) {
            EntradaHash<T> entrada = seccion.obtener(i);
            if (entrada.getClave().equals(clave)) {
                return entrada;
            }
        }
        return null;
    }

    public T buscar(String clave) {
        EntradaHash<T> entrada = buscarEntrada(clave);
        if (entrada == null) {
            return null;
        }
        return entrada.getDato();

    }

    public boolean insertar(String clave, T dato) {
        if (buscarEntrada(clave) != null) {
            return false;
        }
        tabla[hash(clave)].insertar(new EntradaHash<>(clave, dato));
        iN++;
        return true;
    }

    public boolean eliminar(String clave) {
        EntradaHash<T> entrada = buscarEntrada(clave);
        if (entrada == null) {
            return false;
        }
        tabla[hash(clave)].eliminar(entrada);
        iN--;
        return true;
    }
    
    public Lista<T> obtenerTodos(){
        Lista<T> resultado = new Lista<T>();
        for(int i = 0; i < tabla.length; i++){
            for(int j = 0; j < tabla[i].getTamano(); j++){
                resultado.insertar(tabla[i].obtener(j).getDato());
            }
        }
        return resultado;
    }

}
