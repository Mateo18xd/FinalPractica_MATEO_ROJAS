/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Usuario iTC
 */
public class Coordenada {
    private Integer id;
    private Double  logitud;
    private Double latitud;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getLogitud() {
        return logitud;
    }

    public void setLogitud(Double logitud) {
        this.logitud = logitud;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    @Override
    public String toString() {
        return "logitud=" + logitud + ", latitud=" + latitud;
    } 
}
