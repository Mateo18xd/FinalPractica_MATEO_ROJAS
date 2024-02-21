package controlador.Utiles;


/**
 *
 * @author Usuario iTC
 */
public class TiempoEjecucion {
    private String algoritmo;
    private long tiempo;

    public TiempoEjecucion(String algoritmo, long tiempo) {
        this.algoritmo = algoritmo;
        this.tiempo = tiempo;
    }

    public String getAlgoritmo() {
        return algoritmo;
    }

    public long getTiempo() {
        return tiempo;
    }
}
