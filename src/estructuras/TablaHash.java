package estructuras;

/**
 * tabla hash generica con clave de texto. Encadenamiento separado
 * @param <T> tipo de dato guardado en la tabla
 * @author Juan Plaz
 */
public class TablaHash<T> {

    private Lista<EntradaHash<T>>[] tabla;
    private int iN;

    /**
     * Constructor de TablaHash vacia
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public TablaHash() {
        this.tabla = new Lista[11];
        for (int i = 0; i < tabla.length; i++) {
            this.tabla[i] = new Lista<>();
        }
        this.iN = 0;
    }

    /**
     * Devuelve la cantidad de datos de la tabla hash
     *
     * @return devuelve un entero co el tamaño de la tabla
     */
    public int getTamano() {
        return iN;
    }

    /**
     * Confirma si la tabla esta vacia o no
     * 
     * @return devuelve true si la tabla esta vacia
     */
    public boolean estaVacia() {
        return iN == 0;
    }

    /**
     * Hashea una clave
     * El 31 se usa ya que es un numero primo y reduce colisiones al utilizar % para sacar la posicion
     * @param clave String de la clave
     * @return regresa la posicion en el arreglo
     */
    private int hash(String clave) {
        int h = 0;
        for (int i = 0; i < clave.length(); i++) {
            h = (h * 31 + clave.charAt(i)) % tabla.length;
        }
        return h;
    }

    /**
     * Busca una entrada en la tabla
     * @param clave valor a buscar
     * @return regresa la entrada si la consigue, sino, null
     */
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

    /**
     * Busca si un una clave esta y devuelve su dato
     * @param clave del la entrada cuyo dato se desea
     * @return el dato de la entrada si se encuentra, sino, null
     */
    public T buscar(String clave) {
        EntradaHash<T> entrada = buscarEntrada(clave);
        if (entrada == null) {
            return null;
        }
        return entrada.getDato();

    }

    /**
     * Inserta una entrada en la tabla
     * @param clave clave que se guarda de la ubicacion del dato
     * @param dato valor del dato a guardar
     * @return true si se inserta, false si la clave ya existia
     */
    public boolean insertar(String clave, T dato) {
        if (buscarEntrada(clave) != null) {
            return false;
        }
        tabla[hash(clave)].insertar(new EntradaHash<>(clave, dato));
        iN++;
        if (iN > tabla.length * 0.75) {
            redimensionar();
        }
        return true;
    }

    /**
     * Elimina una entrada de la tabla
     * @param clave de la entrada a eliminar
     * @return true si se elimina, false si no se encuentra
     */
    public boolean eliminar(String clave) {
        EntradaHash<T> entrada = buscarEntrada(clave);
        if (entrada == null) {
            return false;
        }
        tabla[hash(clave)].eliminar(entrada);
        iN--;
        return true;
    }

    /**
     * Devuelve todos los valores de la tabla
     * @return regresa todas los datos de las entradas de la tabla
     */
    public Lista<T> obtenerTodos() {
        Lista<T> resultado = new Lista<T>();
        for (int i = 0; i < tabla.length; i++) {
            for (int j = 0; j < tabla[i].getTamano(); j++) {
                resultado.insertar(tabla[i].obtener(j).getDato());
            }
        }
        return resultado;
    }

    /**
     * redimensiona la tabla 2n + 1 para que las listas no crezcan demasiado
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private void redimensionar() {
        Lista<EntradaHash<T>>[] vieja = tabla;
        tabla = new Lista[vieja.length * 2 + 1];
        for (int i = 0; i < tabla.length; i++) {
            this.tabla[i] = new Lista<>();
        }
        for (int i = 0; i < vieja.length; i++) {
            for (int j = 0; j < vieja[i].getTamano(); j++) {
                EntradaHash<T> entrada = vieja[i].obtener(j);
                tabla[hash(entrada.getClave())].insertar(entrada);
            }
        }
    }

}
