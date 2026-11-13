package logica;

import estructuras.Lista;

import modelo.Estacion;
import modelo.Grafo;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import modelo.Conexion;
import java.nio.charset.StandardCharsets;

/**
 * Gestiona el Ingreso d elos archivos de carga
 *
 * @author Juan Plaz
 */
public class GestorArchivo {

    /**
     * crea un gestor de archivos
     */
    public GestorArchivo() {
    }

    /**
     * Lee las lineas de un archivo
     *
     * @param archivo a leer las lineas
     * @return Lista con un string por linea
     * @throws IOException si el archivo no existe o no se puede leer
     */
    private Lista<String> leerLineas(File archivo) throws IOException {
        Lista<String> lineas = new Lista<String>();
        try (BufferedReader lector = new BufferedReader(new InputStreamReader(new FileInputStream(archivo), StandardCharsets.UTF_8))) {
            String linea = lector.readLine();
            while (linea != null) {
                lineas.insertar(linea);
                linea = lector.readLine();
            }
            return lineas;
        }
    }

    /**
     * Recibe un archivo y lo dirige a conexiones o estaciones segun sea
     * necesario
     *
     * @param archivo el cual se va a procesar
     * @param grafo donde se cargan los datos
     * @return Resultado de carga con un resumen de errores
     * @throws IOException en caso de que el archivo no exista o no se pueda
     * leer
     * @throws IllegalArgumentException si el archivo esta vacio o el encabezado
     * no cumple especificaciones
     */
    public ResultadoCarga cargarArchivo(File archivo, Grafo grafo) throws IOException {
        Lista<String> lineas = leerLineas(archivo);
        if (lineas.estaVacia()) {
            throw new IllegalArgumentException("El archivo esta vacio");
        }
        String[] columnas = lineas.obtener(0).split(",");
        if (columnas.length == 3) {
            return cargarConexiones(lineas, grafo);
        }
        if (columnas.length == 5) {
            return cargarEstaciones(lineas, grafo);
        }
        throw new IllegalArgumentException("Encabezado invalido: se esperaban 3 columnas o 5");

    }

    /**
     * Se encarga de procesar el archivo de conexiones, y enviar la informacion
     * al grafo para generarlas. Una linea mala no detiene la carga
     *
     * @param lineas lista de Strings que son las lineas de un archivo
     * @param grafo donde se cargan las conexiones
     * @return resultado de carga con el status y resumen del archivo
     */
    private ResultadoCarga cargarConexiones(Lista<String> lineas, Grafo grafo) {
        ResultadoCarga resultado = new ResultadoCarga();
        for (int i = 1; i < lineas.getTamano(); i++) {
            int numeroLinea = i + 1;
            String linea = lineas.obtener(i);
            if (linea.isBlank()) {
                continue;
            }
            String[] partes = linea.split(",");
            if (partes.length != 3) {
                resultado.agregarError(numeroLinea, "Archivo Invalido");
                continue;
            }
            String origen = partes[0].trim();
            String destino = partes[1].trim();
            if (origen.isEmpty() || destino.isEmpty()) {
                resultado.agregarError(numeroLinea, "Origen o destino null");
                continue;
            }
            int tiempo;
            try {
                tiempo = Integer.parseInt(partes[2].trim());
            } catch (NumberFormatException e) {
                resultado.agregarError(numeroLinea, "El tiempo no es un numero entero");
                continue;
            }
            if (tiempo <= 0) {
                resultado.agregarError(numeroLinea, "tiempo menor o igual a cero");
                continue;
            }
            if (origen.equals(destino)) {
                resultado.agregarError(numeroLinea, "Una estacion no puede conectarse a si misma");
                continue;
            }
            if (grafo.buscarEstacion(origen) == null) {
                grafo.agregarEstacion(new Estacion(origen));
            }
            if (grafo.buscarEstacion(destino) == null) {
                grafo.agregarEstacion(new Estacion(destino));
            }
            try {
                grafo.agregarConexion(origen, destino, tiempo);
                resultado.sumarCargada();
            } catch (IllegalArgumentException e) {
                resultado.agregarError(numeroLinea, e.getMessage());
            }

        }
        return resultado;
    }

    /**
     * Se encarga de procesar el archivo de estaciones, y enviar la informacion
     * al grafo para generarlas. Una linea mala no detiene la carga
     *
     * @param lineas lista de Strings que son las lineas de un archivo
     * @param grafo donde se cargan las estaciones
     * @return resultado de carga con el status y resumen del archivo
     */
    private ResultadoCarga cargarEstaciones(Lista<String> lineas, Grafo grafo) {
        ResultadoCarga resultado = new ResultadoCarga();
        for (int i = 1; i < lineas.getTamano(); i++) {
            int numeroLinea = i + 1;
            String linea = lineas.obtener(i);
            if (linea.isBlank()) {
                continue;
            }
            String[] partes = linea.split(",");
            if (partes.length != 5) {
                resultado.agregarError(numeroLinea, "Archivo Invalido");
                continue;
            }
            String id = partes[0].trim();
            String nombre = partes[1].trim();
            String lineaMetro = partes[2].trim();
            String zona = partes[3].trim();
            if (id.isEmpty()) {
                resultado.agregarError(numeroLinea, "Id vacio");
                continue;
            }
            if (nombre.isEmpty()) {
                resultado.agregarError(numeroLinea, "nombre vacio");
                continue;
            }
            if (lineaMetro.isEmpty()) {
                resultado.agregarError(numeroLinea, "linea vacio");
                continue;
            }
            if (zona.isEmpty()) {
                resultado.agregarError(numeroLinea, "zona vacio");
                continue;
            }
            int capacidad;
            try {
                capacidad = Integer.parseInt(partes[4].trim());
            } catch (NumberFormatException e) {
                resultado.agregarError(numeroLinea, "Capacidad no es un entero");
                continue;
            }
            if (capacidad < 0) {
                resultado.agregarError(numeroLinea, "La capacidad no puede ser 0");
                continue;
            }
            Estacion estacion = grafo.buscarEstacion(id);
            if (estacion == null) {
                estacion = new Estacion(id);
                grafo.agregarEstacion(estacion);
            } else {
                if (estacion.getNombre() != null) {
                    resultado.agregarError(numeroLinea, "la estacion tiene datos repetidos");
                    continue;
                }

            }
            estacion.setNombre(nombre);
            estacion.setLinea(lineaMetro);
            estacion.setZona(zona);
            estacion.setCapacidad(capacidad);
            resultado.sumarCargada();
        }
        return resultado;
    }

    /**
     * Escribe en un archivo de conexiones, identico en formato al de ingreso, con las modificaciones que haya hecho el aplicativo
     * @param archivo donde cargar la informacion
     * @param grafo de donde saca la informacion
     * @throws IOException si no se logra abrir el archivo
     */
    public void guardarConexiones(File archivo, Grafo grafo) throws IOException {
        Lista<Estacion> estaciones = grafo.obtenerEstaciones();
        try (BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8))) {
            escritor.write("Estacion_Origen,Estacion_Destino,Tiempo_Minutos");
            escritor.newLine();
            for (int i = 0; i < estaciones.getTamano(); i++) {
                Estacion origen = estaciones.obtener(i);
                for (int j = 0; j < origen.getConexiones().getTamano(); j++) {
                    Conexion c = origen.getConexiones().obtener(j);
                    Estacion destino = c.getDestino();
                    if (origen.getId().compareTo(destino.getId()) < 0) {
                        escritor.write(origen.getId() + "," + destino.getId() + "," + c.getTiempo());
                        escritor.newLine();
                    }

                }
            }
        }

    }

    /**
     * Escribe en un archivo de estaciones, identico en formato al de ingreso, con las modificaciones que haya hecho el aplicativo
     * @param archivo donde cargar la informacion
     * @param grafo de donde saca la informacion
     * @throws IOException si no se logra abrir el archivo
     */
    public void guardarEstaciones(File archivo, Grafo grafo) throws IOException {
        Lista<Estacion> estaciones = grafo.obtenerEstaciones();
        try (BufferedWriter escritor = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(archivo), StandardCharsets.UTF_8))) {
            escritor.write("ID_Estacion,Nombre,Linea,Zona,Capacidad_Pasajeros_Hora");
            escritor.newLine();
            for(int i = 0; i < estaciones.getTamano(); i++){
                Estacion e = estaciones.obtener(i);
                if(e.getNombre() == null){
                    continue;
                }
                escritor.write(e.getId()+","+e.getNombre()+","+e.getLinea()+","+e.getZona()+","+e.getCapacidad());
                escritor.newLine();                                      
            }
        }
    }
    }
