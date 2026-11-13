package logica;

import estructuras.Lista;
import modelo.Estacion;
import estructuras.TablaHash;
import modelo.Grafo;
import estructuras.Cola;
import modelo.Conexion;
import estructuras.ColaPrioridad;
import modelo.Estacion;

/**
 * Sistema de analisis de el grafo. Tanto de subredes, hubs y rutas mas rapidas
 * @author Juan Plaz
 */
public class AnalizadorRed {

    private Grafo grafo;
/**
 * Crea el analizador de red con su grafo a analizar
 * @param grafo con valores a analizar
 */
    public AnalizadorRed(Grafo grafo) {
        this.grafo = grafo;
    }

    /**
     * Calcula las subredes del grafo
     * @return lista de listas con sus estaciones, cada valor de la lista es una subred
     */
    public Lista<Lista<Estacion>> calcularSubredes() {
        Lista<Lista<Estacion>> subredes = new Lista<>();
        TablaHash<Estacion> visitadas = new TablaHash<Estacion>();
        Lista<Estacion> todas = grafo.obtenerEstaciones();
        for (int i = 0; i < todas.getTamano(); i++) {
            Estacion inicio = todas.obtener(i);
            if (visitadas.buscar(inicio.getId()) != null) {
                continue;
            }
            Lista<Estacion> subred = recorrerDesde(inicio, visitadas);
            subredes.insertar(subred);
        }
        return subredes;

    }

    
    /**
     * Metodo privado para hacer el recorrido de identificacion de subredes
     * @param inicio punto de inicio para analizar
     * @param visitadas TablaHash de las ya visitadas
     * @return una subred
     */
    private Lista<Estacion> recorrerDesde(Estacion inicio, TablaHash<Estacion> visitadas) {
        Lista<Estacion> subred = new Lista<>();
        Cola<Estacion> cola = new Cola<>();
        visitadas.insertar(inicio.getId(), inicio);
        cola.encolar(inicio);
        while (!cola.estaVacia()) {
            Estacion actual = cola.desencolar();
            subred.insertar(actual);
            for (int i = 0; i < actual.getConexiones().getTamano(); i++) {
                Conexion c = actual.getConexiones().obtener(i);
                Estacion v = c.getDestino();
                if (visitadas.buscar(v.getId()) == null) {
                    visitadas.insertar(v.getId(), v);
                    cola.encolar(v);
                }

            }

        }
        return subred;
    }

    
    /**
     * Algoritmo que calcula que estaciones poseen mas conexiones
     * @return el ranking organizado de todas las estaciones se mayor a menor hub
     */
    public Lista<Estacion> calcularRankingHubs() {
        ColaPrioridad<Estacion> cola = new ColaPrioridad<>();
        Lista<Estacion> todas = grafo.obtenerEstaciones();
        for (int i = 0; i < todas.getTamano(); i++) {
            Estacion e = todas.obtener(i);
            cola.insertar(e, -e.getGrado());
        }
        Lista<Estacion> ranking = new Lista<>();
        while (!cola.estaVacia()) {
            ranking.insertar(cola.extraerMinimo());
        }
        return ranking;

    }

    
    /**
     * Calcula la ruta mas rapida para llegar entre dos puntos
     * @param idOrigen inicio de la ruta
     * @param idDestino fin de la ruta
     * @return Resultado de ruta, una clase con el recorrido en lista y el tiempo que toma llevarlo a cabo. null si no hay camino
     * @throws IllegalArgumentException si alguna de las estaciones no existe
     */
    public ResultadoRuta calcularRuta(String idOrigen, String idDestino) {
        Estacion origen = grafo.buscarEstacion(idOrigen);
        Estacion destino = grafo.buscarEstacion(idDestino);
        if (origen == null || destino == null) {
            throw new IllegalArgumentException("La estacion no existe");
        }
        TablaHash<Integer> distancias = new TablaHash<>();
        TablaHash<Estacion> anterior = new TablaHash<>();
        TablaHash<Estacion> procesadas = new TablaHash<>();
        ColaPrioridad<Estacion> cola = new ColaPrioridad<>();

        distancias.insertar(origen.getId(), 0);
        cola.insertar(origen, 0);

        while (!cola.estaVacia()) {
            Estacion actual = cola.extraerMinimo();
            if (procesadas.buscar(actual.getId()) != null) {
                continue;
            }
            procesadas.insertar(actual.getId(), actual);
            if (actual == destino) {
                break;
            }
            int tiempoActual = distancias.buscar(actual.getId());
            for (int i = 0; i < actual.getConexiones().getTamano(); i++) {
                Conexion c = actual.getConexiones().obtener(i);
                Estacion v = c.getDestino();
                if (procesadas.buscar(v.getId()) == null) {
                    int nuevo = tiempoActual + c.getTiempo();
                    Integer conocido = distancias.buscar(v.getId());
                    if (conocido == null || nuevo < conocido) {
                        distancias.eliminar(v.getId());
                        distancias.insertar(v.getId(), nuevo);
                        anterior.eliminar(v.getId());
                        anterior.insertar(v.getId(), actual);
                        cola.insertar(v, nuevo);

                    }

                }

            }

        }
        if (distancias.buscar(destino.getId()) == null) {
            return null;
        }
        int tiempoTotal = distancias.buscar(destino.getId());
        Lista<Estacion> alReves = new Lista<>();
        Estacion paso = destino;
        while (paso != null) {
            alReves.insertar(paso);
            paso = anterior.buscar(paso.getId());

        }

        Lista<Estacion> recorrido = new Lista<>();

        for (int i = alReves.getTamano() - 1; i >= 0; i--) {
            recorrido.insertar(alReves.obtener(i));
        }
        return new ResultadoRuta(recorrido, tiempoTotal);
    }

}
