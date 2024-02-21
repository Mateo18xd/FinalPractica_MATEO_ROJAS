/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.listas.DynamicList;

import java.io.FileWriter;
import java.io.IOException;

/**
 *
 * @author Usuario iTC
 */
public class PaintGraph {
    String URL = "src/d3/grafo.js";
    public void updateFile(Grafo graph) throws IOException{
        String paint = "var nodes = new vis.DataSet([\n";
        for (int i = 1; i < graph.num_vertices()+1; i++) {
            paint += "{id: " + i + ", label: \"Node " + i  + "\"},\n";
        }
        paint += "]);\n";
        
        paint += "var edges = new vis.DataSet([\n";
        for (int i = 1; i < graph.num_vertices()+1; i++) {
            try {
                DynamicList<Adyacencia> adyacencias = graph.adyacentes(i);
                for (int j = 0; j < adyacencias.getLenght(); j++) {
                    Adyacencia adyacencia = adyacencias.getInfo(j);
                    paint += "{from: " + i + ", to: " + adyacencia.getDestino() + ", label: \"" + adyacencia.getPeso().toString() + "\"" + "},\n";
                }
            } catch (Exception e) {
            }
        }
        paint += "]);\n";
        //to do

        paint += "var container = document.getElementById(\"mynetwork\");\n" +
"      var data = {\n" +
"        nodes: nodes,\n" +
"        edges: edges,\n" +
"      };\n" +
"      var options = {};\n" +
"      var network = new vis.Network(container, data, options);";
        FileWriter load = new FileWriter(URL);
        load.write(paint);
        load.close();
    }

    //grafo etiquetado
    public void updateFileLabel (GrafosEtiquetadosDirigidos graph) throws IOException{
        String paint = "var nodes = new vis.DataSet([\n";
        for (int i = 1; i < graph.num_vertices()+1; i++) {
            try {
                paint += "{id: " + i + ", label: \"" + graph.getLabelE(i) + "\"},\n";
            } catch (Exception e) {
            }
        }
        paint += "]);\n";

        paint += "var edges = new vis.DataSet([\n";
        for (int i = 1; i < graph.num_vertices()+1; i++) {
            try {
                DynamicList<Adyacencia> adyacencias = graph.adyacentes(i);
                for (int j = 0; j < adyacencias.getLenght(); j++) {
                    Adyacencia adyacencia = adyacencias.getInfo(j);
                    paint += "{from: " + i + ", to: " + adyacencia.getDestino() + ", label: \"" + adyacencia.getPeso().toString() + "\"" + "},\n";
                }
            } catch (Exception e) {
            }
        }
        paint += "]);\n";
        //to do

        paint += "var container = document.getElementById(\"mynetwork\");\n" + "      var data = {\n" + "        nodes: nodes,\n" + "        edges: edges,\n" + "      };\n" + "      var options = {};\n" + "      var network = new vis.Network(container, data, options);";
        FileWriter load = new FileWriter(URL);
        load.write(paint);
        load.close();
    }
}
