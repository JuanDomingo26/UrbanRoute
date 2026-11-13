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
     * @throws IllegalArgumentException estacion 1 o 2 no existe, iguales, ya
     * tienen conexion o tiempo es menor o igual a 0
     */
    public void agregarConexion(String idA, String idB, int tiempo) {
        Estacion a = buscarEstacion(idA);
        Estacion b = buscarEstacion(idB);
        if (a == null) {
            throw new IllegalArgumentException("La estacion: " + idA + " no existe");
        }
        if (b == null) {
            throw new IllegalArgumentException("La estacion: " + idB + " no existe");
        }
        if (idA.equals(idB)) {
            throw new IllegalArgumentException("Una estacion no puede conectarse consigo misma");
        }
        if (tiempo <= 0) {
            throw new IllegalArgumentException("El tiempo debe ser mayor que 0");
        }
        if (a.tieneConexionHacia(b)) {
            throw new IllegalArgumentException("Ya existe la conexion");
        }
        a.agregarConexion(new Conexion(b, tiempo));
        b.agregarConexion(new Conexion(a, tiempo));
    }

    /**
     * Elimina una conexion entre dos estaciones, en los dos sentidos
     *
     * @param idA id de la estacion 1
     * @param idB id de la estacion 2
     * @return true si logra quitarlo
     */
    public boolean eliminarConexion(String idA, String idB) {
        Estacion a = buscarEstacion(idA);
        Estacion b = buscarEstacion(idB);
        if (a == null) {
            return false;
        }
        if (b == null) {
            return false;
        }
        boolean resultadoQuitar = a.eliminarConexionHacia(b);
        b.eliminarConexionHacia(a);
        return resultadoQuitar;
    }

    /**
     * Elimina una estacion
     *
     * @param id de la estacion a eliminar
     * @return true si se elimina, false si hay error
     */
    public boolean eliminarEstacion(String id) {
        Estacion e = buscarEstacion(id);
        if (e == null) {
            return false;
        }
        for (int i = 0; i < e.getConexiones().getTamano(); i++) {
            Conexion conexionActual = e.getConexiones().obtener(i);
            conexionActual.getDestino().eliminarConexionHacia(e);
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
        Lista<Estacion> todos = estaciones.obtenerTodos();
        return todos;
    }
}
