/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pozos._luz;

import controlador.TDA.grafos.Grafo;
import controlador.TDA.grafos.GrafoDirigido;
import controlador.TDA.grafos.PaintGraph;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.Utiles.Utiles;

/**
 *
 * @author Usuario iTC
 */
public class Pozos_luz {
    public static final double INF = Double.POSITIVE_INFINITY;

    public static void floydWarshall(Grafo grafo) throws Exception {
        int numVertices = grafo.num_vertices();
        double[][] distancias = new double[numVertices + 1][numVertices + 1];

        // Inicializar matriz de distancias
        for (int i = 1; i <= numVertices; i++) {
            for (int j = 1; j <= numVertices; j++) {
                if (i == j) {
                    distancias[i][j] = 0; // La distancia de un vértice a sí mismo es 0
                } else if (grafo.existe_arista(i, j)) {
                    distancias[i][j] = grafo.peso_arista(i, j); // Peso de la arista si existe
                } else {
                    distancias[i][j] = INF; // Infinito si no hay arista directa
                }
            }
        }

        // Algoritmo de Floyd-Warshall
        for (int k = 1; k <= numVertices; k++) {
            for (int i = 1; i <= numVertices; i++) {
                for (int j = 1; j <= numVertices; j++) {
                    if (distancias[i][k] != INF && distancias[k][j] != INF &&
                            distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                    }
                }
            }
        }

        // Imprimir distancias mínimas entre todos los pares de vértices
        System.out.println("Distancias mínimas entre todos los pares de vértices:");
        for (int i = 1; i <= numVertices; i++) {
            for (int j = 1; j <= numVertices; j++) {
                System.out.print(distancias[i][j] == INF ? "INF\t" : distancias[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) throws EmptyException {
        try {
            Grafo grafo = new GrafoDirigido(6);
            grafo.insertar_arista(1, 2, 2.0);
            grafo.insertar_arista(1, 3, 5.0);
            grafo.insertar_arista(2, 3, 1.0);
            grafo.insertar_arista(2, 4, 3.0);
            grafo.insertar_arista(3, 4, 2.0);
            grafo.insertar_arista(4, 5, 4.0);
            grafo.insertar_arista(5, 6, 1.0);
            grafo.insertar_arista(4, 6, 7.0);
            PaintGraph p = new PaintGraph();
            p.updateFile(grafo);
            Utiles.abrirArchivoHTML("src/d3/grafo.html");

            floydWarshall(grafo);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }
}
