package modelo;

import estructuras.Lista;

/**
 * Estacion de metro con sus datos y lista de conexiones
 *
 * @author Juan Plaz
 */
public class Estacion {

    private String id;
    private String nombre;
    private String linea;
    private String zona;
    private int capacidad;
    private Lista<Conexion> conexiones;

    /**
     * Construye una estacion con su id, sin datos ni conexiones
     *
     * @param id string usada despues para ubicar la estacion
     */
    public Estacion(String id) {
        this.id = id;
        this.nombre = null;
        this.linea = null;
        this.zona = null;
        this.capacidad = 0;
        this.conexiones = new Lista<>();
    }

    /**
     * Devuelve el Id
     *
     * @return Id de la estacion
     */
    public String getId() {
        return id;
    }

    /**
     * Devuelve el nombre de la estacion
     *
     * @return valor de la estacion
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Devuelve la linea de la estacion
     *
     * @return valor de linea
     */
    public String getLinea() {
        return linea;
    }

    /**
     * Devuelve la zona de la estacion
     *
     * @return valor de zona
     */
    public String getZona() {
        return zona;
    }

    /**
     * Devuelve la capacidad
     *
     * @return entero de la capacidad de la estacion
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Devuelve las conexione de la estacion
     *
     * @return una lista de las conexiones de la estacion
     */
    public Lista<Conexion> getConexiones() {
        return conexiones;
    }

    /**
     * Asigna el nombre a la estacion
     *
     * @param nombre string a asignar
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Asigna la linea a la estacion
     *
     * @param linea valor a asignar a la linea de la estacion
     */
    public void setLinea(String linea) {
        this.linea = linea;
    }

    /**
     * Asigna la zona a una estacion
     *
     * @param zona valor a asignar a la zona de la estacion
     */
    public void setZona(String zona) {
        this.zona = zona;
    }

    /**
     * Asigna la capacidad de la estacion
     *
     * @param capacidad entero que asigna la capacidad a la estacion
     */
    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    /**
     * Agrega una conexion a la lista de conexiones de la estacion
     *
     * @param conexion valor de la conexion a agregar
     */
    public void agregarConexion(Conexion conexion) {
        conexiones.insertar(conexion);
    }

    /**
     * Devuelve la cantidad de conexiones que tiene
     *
     * @return tamaño de conexiones
     */
    public int getGrado() {
        return conexiones.getTamano();
    }

    /**
     * Metodo privado para buscar conexiones hacia un cierto destino
     *
     * @param destino valor a verificar
     * @return la conexion si eiste o null si no
     */
    private Conexion buscarConexionHacia(Estacion destino) {
        for (int i = 0; i < conexiones.getTamano(); i++) {
            Conexion conexionActual = conexiones.obtener(i);
            if (conexionActual.getDestino() == destino) {
                return conexionActual;
            }
        }
        return null;
    }

    /**
     * Busca si hay conexion hacia un destino especifico
     *
     * @param destino estacion a evaluar
     * @return booleano, true si existe, false si no
     */
    public boolean tieneConexionHacia(Estacion destino) {
        return buscarConexionHacia(destino) != null;
    }

    /**
     * Elimina una conexion de una estacion
     *
     * @param destino estacion que se quiere eliminar su conexion hacia la
     * actual
     * @return regresa un booleano segun el metodo .eliminar() si encuentra que
     * eliminar, sino, regresa false
     */
    public boolean eliminarConexionHacia(Estacion destino) {
        Conexion encontrada = buscarConexionHacia(destino);
        if (encontrada == null) {
            return false;
        } else {
            return conexiones.eliminar(encontrada);
        }
    }
}
