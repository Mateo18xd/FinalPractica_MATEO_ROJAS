/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.grafos.exeption.LabelEdgeException;
import controlador.TDA.grafos.exeption.VerticeException;
import controlador.TDA.listas.DynamicList;
import controlador.Utiles.Utiles;
import java.lang.reflect.Array;
import java.util.HashMap;


/**
 *
 * @author Usuario iTC
 */
public class GrafosEtiquetadosDirigidos<E> extends GrafoDirigido {
    protected E labels[];    
    protected HashMap<E, Integer> dicVerctices;
    private Class<E> clazz;
    
    public GrafosEtiquetadosDirigidos(Integer num_vertices, Class clazz) {
        super(num_vertices);
        this.clazz = clazz;
        labels = (E[]) Array.newInstance(clazz, num_vertices + 1);
        dicVerctices = new HashMap<>(num_vertices);
    }
    
    /**
     * Metodo que permite rescatar el nro de vertice aosciado a la etiqueta
     * @return 
     */
    
    public Integer getVerticeE(E label) throws Exception{
        Integer aux = dicVerctices.get(label);
        if (aux != null) {
            return  aux;
        } else {
            throw new VerticeException("No se encunetra el vertice asociado a esta etiqueta");
        }
    }
    
    public E getLabelE(Integer v) throws Exception{
        if (v <= num_vertices()) {
            return  labels[v];
        } else {
            throw new VerticeException("No se encunetra el vertice");
        }
    }
    
    public Boolean isEdgeE(E o, E d) throws Exception{
        if (isAllLabelsGraph()) {
            return existe_arista(getVerticeE(o), getVerticeE(d));
        } else {
            throw new LabelEdgeException();
        }
        
    }
    
    public void insertEdge(E o, E d, Double weight) throws Exception{
        if (isAllLabelsGraph()) {
            insertar_arista(getVerticeE(o), getVerticeE(d), weight);
        } else {
            throw new LabelEdgeException();
        }
        
    }
    
    public void insertEdge(E o, E d) throws Exception{
        if (isAllLabelsGraph()) {
            insertar_arista(getVerticeE(o), getVerticeE(d), Double.NaN);
        } else {
            throw new LabelEdgeException();
        }
    }
    
    public DynamicList<Adyacencia> adjacents(E label) throws Exception{
        if (isAllLabelsGraph()) {
            return adyacentes(getVerticeE(label));
        } else {
            throw new LabelEdgeException();
        }
        
    }
    
    /**
     * Metodo principal que permite etiquetar grafos
     * 
     * @param v nro de vertices
     * @param label Objeto Etiqueta
     */
    
    public void labelVertice(Integer v, E label){
        labels[v] = label;
        dicVerctices.put(label, v); 
    }
    
    public Boolean isAllLabelsGraph(){
        Boolean band = true;
        for (int i = 1; i < labels.length; i++) {
            if (labels[i] == null) {
                band = false;
                break;
            }
        }
        return band;
    }

    @Override
    public String toString() {
        StringBuffer grafo = new StringBuffer("Grafo").append("\n");
        try {
            for(int i = 1; i <= num_vertices(); i++){
                grafo.append("[").append(i).append("] =").append(getLabelE(i)).append("\n");
                DynamicList<Adyacencia> list = adyacentes(i);
                for (int j = 0; j < list.getLenght(); j++) {
                    Adyacencia a = list.getInfo(j);
                    grafo.append("ady ").append(a.getDestino()).append(" peso ").append(a.getPeso()).append("\n");
                }
            }
        } catch (Exception e) {
            
        }
        return grafo.toString(); 
    }
    
    
    
    public static void main(String[] args) {
        try {
            GrafosEtiquetadosDirigidos<String> ged = new GrafosEtiquetadosDirigidos<>(6, String.class);
            ged.labelVertice(1, "Estefania");
            ged.labelVertice(2, "Luna");
            ged.labelVertice(3, "Jimenez");
            ged.labelVertice(4, "Criollo");
            ged.labelVertice(5, "Nivelo");
            ged.labelVertice(6, "Darwin");
            ged.insertEdge("Estefania", "Darwin", 100.0);
            ged.insertEdge("Estefania", "Jimenez", 100.0);
            ged.insertEdge("Jimenez", "Criollo", 100.0);
            System.out.println(ged.toString());
            System.out.println(ged.toString());
            PaintGraph p =  new PaintGraph();
            p.updateFile(ged);
            Utiles.abrirArchivoHTML("c:/Users/romer/Documents/NetBeansProjects/Pozos _luz/d3/grafo.html");
        } catch (Exception e) {
            System.out.println("Error: " + e);
        }
    }
}
