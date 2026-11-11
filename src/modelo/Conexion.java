package modelo;

/**
 * Conexion desde una estacion a otra, con su tiempo de viaje.
 * El origen es la estacion que tiene la conexion en su lista.
 * Como la red no es dirigida, cada conexion se guarda dos veces.
 * @author Juan Plaz
 */
public class Conexion {
    private Estacion destino;
    private int tiempo;
 
    /**
     * Constructor de conexion con un destino y tiempo
     * 
     * @param destino la estacion a que se llega
     * @param tiempo tiempo en minutos que toma
     */
    public Conexion(Estacion destino, int tiempo){
        this.destino = destino;
        this.tiempo = tiempo;
    }
    
    /**
     * Devuelve el destino de la conexion
     * 
     * @return devuelve la estacion de llegada
     */
    public Estacion getDestino(){
        return destino;             
    }
    
    /**
     * Devuelve el tiempo que toma la conexion
     * 
     * @return el tiempo del viaje en minutos
     */
    public int getTiempo(){
        return tiempo;
    }
}
