package logica;

import estructuras.Lista;

/**
 * Resultado de carga para gestor de archivos. Permite llevar los resumenes por separado
 * y identificar errores de carga
 * @author Juan Plaz
 */
public class ResultadoCarga {

    private int cargadas;
    private Lista<String> errores;

    /**
     * Construye un resultado de carga vacio
     */
    public ResultadoCarga() {
        this.cargadas = 0;
        this.errores = new Lista<>();
    }

    /**
     * Suma 1 a las cargadas
     */
    public void sumarCargada() {
        cargadas++;
    }

    /**
     * Agrega un error a la lista
     *
     * @param numeroLinea numero de linea del error
     * @param motivo string de razon del error
     */
    public void agregarError(int numeroLinea, String motivo) {
        errores.insertar("Linea " + numeroLinea + ": " + motivo);
    }

    /**
     * Devuelve la cantidad de cargadas
     *
     * @return entero con el valor de la cantidad de cargadas
     */
    public int getCargadas() {
        return cargadas;
    }

    /**
     * Devuelve la lista de errores para lectura
     *
     * @return Lista de strings
     */
    public Lista<String> getErrores() {
        return errores;
    }

    /**
     * Devuelve un booleano dependiendo de si hay errores o no
     *
     * @return regresa true si hay errores, false si no
     */
    public boolean tieneErrores() {
        return !errores.estaVacia();
    }
}
