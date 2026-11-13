package modelo;

import estructuras.TablaHash;
import estructuras.Lista;

/**
 * Grafo que consulta TablaHash para manejar estaciones y conexiones entre
 * ellas. No dirigido
 *
 * @author Juan Plaz
 */
public class Grafo {

    private TablaHash<Estacion> estaciones;

    /**
     * Construye un grafo nuevo vacio
     */
    public Grafo() {
        this.estaciones = new TablaHash<>();
    }

    /**
     * Busca una estacion
     *
     * @param id de la estacion a buscar
     * @return regresa la estacion, o null si no existe
     */
    public Estacion buscarEstacion(String id) {
        if (id == null) {
            return null;
        }
        return estaciones.buscar(id);
    }

    /**
     * Agrega una estacion al grafo
     *
     * @param e estacion a agregar
     * @throws IllegalArgumentException si e es null, el id es null, e es un
     * string vacio o si ya existe
     */
    public void agregarEstacion(Estacion e) {
        if (e == null) {
            throw new IllegalArgumentException("Estacion vacia");
        }
        if (e.getId() == null) {
            throw new IllegalArgumentException("Id vacia");
        }
        if (e.getId().isBlank()) {
            throw new IllegalArgumentException("Id vacia");
        }
        if (estaciones.buscar(e.getId()) != null) {
            throw new IllegalArgumentException("Id repetido: " + e.getId());
        }
        estaciones.insertar(e.getId(), e);
    }

    /**
     * Agrega una conexion entre dos estaciones
     *
     * @param idA id de la estacion 1
     * @param idB id de la estacion 2
     * @param tiempo entre ambas
     * @return true si logra agregarse
     * @throws IllegalArgumentException estacion 1 o 2 no existe, iguales, ya
     * tienen conexion o tiempo es menor o igual a 0
     */
    public boolean agregarConexion(String idA, String idB, int tiempo) {
        Estacion A = buscarEstacion(idA);
        Estacion B = buscarEstacion(idB);
        if (A == null) {
            throw new IllegalArgumentException("La estacion: " + idA + " no existe");
        }
        if (B == null) {
            throw new IllegalArgumentException("La estacion: " + idB + " no existe");
        }
        if (idA.equals(idB)) {
            throw new IllegalArgumentException("Una estacion no puede conectarse consigo misma");
        }
        if (tiempo <= 0) {
            throw new IllegalArgumentException("El tiempo debe ser mayor que 0");
        }
        if (A.tieneConexionHacia(B)) {
            throw new IllegalArgumentException("Ya existe la conexion");
        }
        A.agregarConexion(new Conexion(B, tiempo));
        B.agregarConexion(new Conexion(A, tiempo));
        return true;
    }

    /**
     * Elimina una conexion entre dos estaciones, en los dos sentidos
     *
     * @param idA id de la estacion 1
     * @param idB id de la estacion 2
     * @return true si logra quitarlo
     */
    public boolean eliminarConexion(String idA, String idB) {
        Estacion A = buscarEstacion(idA);
        Estacion B = buscarEstacion(idB);
        if (A == null) {
            return false;
        }
        if (B == null) {
            return false;
        }
        boolean resultadoQuitar = A.eliminarConexionHacia(B);
        B.eliminarConexionHacia(A);
        return resultadoQuitar;
    }

    /**
     * Elimina una estacion
     *
     * @param id de la estacion a eliminar
     * @return true si se elimina, false si hay error
     */
    public boolean eliminarEstacion(String id) {
        Estacion E = buscarEstacion(id);
        if (E == null) {
            return false;
        }
        for (int i = 0; i < E.getConexiones().getTamano(); i++) {
            Conexion conexionActual = E.getConexiones().obtener(i);
            conexionActual.getDestino().eliminarConexionHacia(E);
        }
        estaciones.eliminar(id);
        return true;
    }

    /**
     * Devuelve la cantidad de estaciones
     *
     * @return entero numero de estaciones
     */
    public int getCantidadEstaciones() {
        return estaciones.getTamano();
    }

    /**
     * Devuelve todas las estaciones
     *
     * @return lista nueva de todas las estaciones
     */
    public Lista<Estacion> obtenerEstaciones() {
        Lista<Estacion> Todos = estaciones.obtenerTodos();
        return Todos;
    }
}
