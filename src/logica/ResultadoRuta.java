package logica;
import estructuras.Lista;
import modelo.Estacion;
/**
 * Clase para guardar datos de algoritmo de calculo de ruta mas rapida
 * @author Juan Plaz
 */
public class ResultadoRuta {
    private Lista<Estacion> recorrido;
    private int tiempoTotal;
    /**
     * Crea un Resultado de ruta con su recorrido y tiempo total
     * @param lista de estaciones por las que se pasa
     * @param tiempo suma de los tiempos
     */
    public ResultadoRuta(Lista<Estacion> lista, int tiempo){
        this.recorrido = lista;
        this.tiempoTotal = tiempo;
    }
    /**
     * Devuelve la lista del recorrido
     * @return lista de estaciones del recorrido
     */
    public Lista<Estacion> getRecorrido(){
      return recorrido;
    }
    
    /**
     * Devuelve el tiempo total de la ruta
     * @return entero valor del tiempo total
     */
    public int getTiempo(){
        return tiempoTotal;
    }
}
