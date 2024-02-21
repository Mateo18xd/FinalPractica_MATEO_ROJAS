/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.grafos.exeption.VerticeException;
import controlador.TDA.listas.DynamicList;
import controlador.Utiles.Utiles;


/**
 *
 * @author Usuario iTC
 */
public class GrafoEtiquetadosNoDirigido<E> extends GrafosEtiquetadosDirigidos<E>{
  
    public GrafoEtiquetadosNoDirigido(Integer num_vertices, Class clazz) {
        super(num_vertices, clazz);
    }
    
     @Override
    public void insertar_arista(Integer v1, Integer v2, Double peso) throws Exception {
        if(v1.intValue() <= num_vertices() && v2.intValue() <= num_vertices()){
            if(!existe_arista(v1, v2)) {
                setNum_aristas(num_aristas()+1);
                getListaAdyacencias()[v1].add(new Adyacencia(v2, peso));
                getListaAdyacencias()[v2].add(new Adyacencia(v1, peso));
                //num_aristas++;                
                //listaAdycencias[v1].add(new Adycencia(v2, peso));
            }
        } else 
            throw new VerticeException();
    }
}
