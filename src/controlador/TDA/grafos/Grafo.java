/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;

/**
 *
 * @author Usuario iTC
 */
public abstract class Grafo {
    // ---- G = {v, e}
    public abstract Integer num_vertices();
    public abstract Integer num_aristas();
    //v1 ------ v2
    public abstract Boolean existe_arista(Integer v1, Integer v2) throws Exception;
    public abstract Double peso_arista(Integer v1, Integer v2) throws Exception;
    public abstract void insertar_arista(Integer v1, Integer v2, Double peso) throws Exception;
    public abstract  void insertar_arista(Integer v1, Integer v2) throws  Exception;
    public abstract DynamicList<Adyacencia> adyacentes(Integer v1) throws Exception;
    
    //G = 20v
    //MA = 20 x 20 ---> grafos grandes

    @Override
    public String toString() {
        StringBuffer grafo = new StringBuffer("Grafo").append("\n");
        try {
            for(int i = 1; i <= num_vertices(); i++){
                grafo.append("V").append(i).append("\n");
                DynamicList<Adyacencia> list = adyacentes(i);
                for (int j = 0; j < list.getLenght(); j++) {
                    Adyacencia a = list.getInfo(j);
                    grafo.append("ady ").append(a.getDestino()).append(" peso ").append(a.getPeso()).append("\n");
                }
            }
        } catch (Exception e) {
        }
        return grafo.toString(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }

    //Recorridos
    public abstract DynamicList<Integer> recorridoAnchura(Integer v) throws Exception;
    public abstract DynamicList<Integer> recorridoProfundidad(Integer v) throws Exception;
    //floyd
    public abstract Double[][] floydRecorrido() throws Exception;
    //Bellman Ford
    public abstract Double[] bellmanFordRecorrido(Integer v) throws Exception;



}
