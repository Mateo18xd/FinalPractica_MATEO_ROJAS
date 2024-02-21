/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.TDA.grafos;

import controlador.TDA.grafos.exeption.VerticeException;
import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.Utiles.Utiles;


/**
 *
 * @author Usuario iTC
 */
public class GrafoDirigido extends Grafo{
    private Integer num_vertice;
    private Integer num_aristas;
    private DynamicList<Adyacencia> listaAdyacencias[];

    public GrafoDirigido(Integer num_vertices) {
        this.num_vertice = num_vertices;
        this.num_aristas = 0;
        listaAdyacencias = new DynamicList[num_vertices + 1];
        for (int i = 1; i <= this.num_vertice; i++) {
            listaAdyacencias[i] = new DynamicList<>();
        }
    }
    
    
    @Override
    public Integer num_vertices() {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return num_vertice;
    }

    @Override
    public Integer num_aristas() {
        // new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return num_aristas;
    }

    @Override
    public Boolean existe_arista(Integer v1, Integer v2) throws Exception{
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        Boolean band = false;
        if (v1.intValue() <= num_vertice && v2.intValue() <= num_vertice) {
            DynamicList<Adyacencia> listaA = listaAdyacencias[v1];
            for (int i = 0; i < listaA.getLenght(); i++) {
                Adyacencia a = listaA.getInfo(i);
                if (a.getDestino().intValue() == v2.intValue()) {
                    band = true;
                    break;
                }
            }
        } else {
            throw new VerticeException();
        }
        return band;
    }

    @Override
    public Double peso_arista(Integer v1, Integer v2) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        Double peso = Double.NaN;
        if (existe_arista(v1, v2)) {
            DynamicList<Adyacencia> listaA = listaAdyacencias[v1];
            for (int i = 0; i < listaA.getLenght(); i++) {
                Adyacencia a = listaA.getInfo(i);
                if (a.getDestino().intValue() == v2.intValue()) {
                    peso = a.getPeso();
                    break;
                }
            }
        }
        return peso;
    }

    @Override
    public void insertar_arista(Integer v1, Integer v2, Double peso) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        if (v1.intValue() <= num_vertice && v2.intValue() <= num_vertice) {
            if (!existe_arista(v1, v2)) {
                num_aristas++;
                listaAdyacencias[v1].add(new Adyacencia(v2, peso));
            }
        } else {
            throw new VerticeException();
        }
    }

    @Override
    public void insertar_arista(Integer v1, Integer v2) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        insertar_arista(v1, v2, Double.NaN);
    }

    @Override
    public DynamicList<Adyacencia> adyacentes(Integer v1) {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        return listaAdyacencias[v1];
    }

    @Override
    public DynamicList<Integer> recorridoAnchura(Integer v) throws Exception {
        DynamicList<Integer> recorrido = new DynamicList<>();
        DynamicList<Integer> cola = new DynamicList<>();
        DynamicList<Integer> visitados = new DynamicList<>();
        Integer w;
        visitados.add(v);
        recorrido.add(v);
        cola.add(v);
        while (!cola.isEmpty()) {
            Integer u = cola.remove(0);
            DynamicList<Adyacencia> listaA = adyacentes(u);
            for (int i = 0; i < listaA.getLenght(); i++) {
                Adyacencia a = listaA.getInfo(i);
                w = a.getDestino();
                if (!visitados.contains(w)) {
                    visitados.add(w);
                    recorrido.add(w);
                    cola.add(w);
                }
            }
        }
        return recorrido;
    }

    @Override
    public DynamicList<Integer> recorridoProfundidad(Integer v) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        DynamicList<Integer> recorrido = new DynamicList<>();
        DynamicList<Integer> pila = new DynamicList<>();
        DynamicList<Integer> visitados = new DynamicList<>();
        Integer w;
        pila.add(v);
        while (!pila.isEmpty()) {
            Integer u = pila.remove(pila.getLenght() - 1);
            if (!visitados.contains(u)) {
                visitados.add(u);
                recorrido.add(u);
                DynamicList<Adyacencia> listaA = adyacentes(u);
                for (int i = 0; i < listaA.getLenght(); i++) {
                    Adyacencia a = listaA.getInfo(i);
                    w = a.getDestino();
                    if (!visitados.contains(w)) {
                        pila.add(w);
                    }
                }
            }
        }
        return recorrido;
    }

    @Override
    public Double[][] floydRecorrido () throws Exception {
        int V = num_vertices();
        Double[][] distancias = new Double[V + 1][V + 1];
        Double[][] predecesores = new Double[V + 1][V + 1];

        for (int i = 1; i <= V; i++) {
            for (int j = 1; j <= V; j++) {
                if (i == j) {
                    distancias[i][j] = 0.0;
                    predecesores[i][j] = (double)i;
                } else if (existe_arista(i, j)) {
                    distancias[i][j] = peso_arista(i, j);
                    predecesores[i][j] = (double)i;
                } else {
                    distancias[i][j] = Double.MAX_VALUE;
                    predecesores[i][j] = null;
                }
            }
        }

        for (int k = 1; k <= V; k++) {
            for (int i = 1; i <= V; i++) {
                for (int j = 1; j <= V; j++) {
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        predecesores[i][j] = predecesores[k][j];
                    }
                }
            }
        }
        return predecesores;
    }

    //metodo para las distancia de floyd
    public Double[][] recorridoFloydDistancias() throws Exception {
        int V = num_vertices();
        Double[][] distancias = new Double[V + 1][V + 1];
        Double[][] predecesores = new Double[V + 1][V + 1];

        for (int i = 1; i <= V; i++) {
            for (int j = 1; j <= V; j++) {
                if (i == j) {
                    distancias[i][j] = 0.0;
                    predecesores[i][j] = (double) i;
                } else if (existe_arista(i, j)) {
                    distancias[i][j] = peso_arista(i, j);
                    predecesores[i][j] = (double) i;
                } else {
                    distancias[i][j] = Double.MAX_VALUE;
                    predecesores[i][j] = null;
                }
            }
        }

        for (int k = 1; k <= V; k++) {
            for (int i = 1; i <= V; i++) {
                for (int j = 1; j <= V; j++) {
                    if (distancias[i][k] + distancias[k][j] < distancias[i][j]) {
                        distancias[i][j] = distancias[i][k] + distancias[k][j];
                        predecesores[i][j] = predecesores[k][j];
                    }
                }
            }
        }
        return distancias;
    }

    @Override
    public Double[] bellmanFordRecorrido(Integer v) throws Exception {
        int V = num_vertices();
        Double[] distancias = new Double[V + 1];
        Double[] predecesores = new Double[V + 1];

        for (int i = 1; i <= V; i++) {
            distancias[i] = Double.MAX_VALUE;
            predecesores[i] = null;
        }
        distancias[v] = 0.0;

        for (int i = 1; i <= V - 1; i++) {
            for (int j = 1; j <= V; j++) {
                DynamicList<Adyacencia> listaA = adyacentes(j);
                for (int k = 0; k < listaA.getLenght(); k++) {
                    Adyacencia a = listaA.getInfo(k);
                    if (distancias[j] + a.getPeso() < distancias[a.getDestino()]) {
                        distancias[a.getDestino()] = distancias[j] + a.getPeso();
                        predecesores[a.getDestino()] = (double) j;
                    }
                }
            }
        }

        for (int j = 1; j <= V; j++) {
            DynamicList<Adyacencia> listaA = adyacentes(j);
            for (int k = 0; k < listaA.getLenght(); k++) {
                Adyacencia a = listaA.getInfo(k);
                if (distancias[j] + a.getPeso() < distancias[a.getDestino()]) {
                    throw new Exception("El grafo contiene un ciclo de peso negativo");
                }
            }
        }
        return predecesores;
    }

    public Double[] recorridoBellmanDistancias(Integer v) throws Exception {
        int V = num_vertices();
        Double[] distancias = new Double[V + 1];
        Double[] predecesores = new Double[V + 1];

        for (int i = 1; i <= V; i++) {
            distancias[i] = Double.MAX_VALUE;
            predecesores[i] = null;
        }
        distancias[v] = 0.0;

        for (int i = 1; i <= V - 1; i++) {
            for (int j = 1; j <= V; j++) {
                DynamicList<Adyacencia> listaA = adyacentes(j);
                for (int k = 0; k < listaA.getLenght(); k++) {
                    Adyacencia a = listaA.getInfo(k);
                    if (distancias[j] + a.getPeso() < distancias[a.getDestino()]) {
                        distancias[a.getDestino()] = distancias[j] + a.getPeso();
                        predecesores[a.getDestino()] = (double) j;
                    }
                }
            }
        }

        for (int j = 1; j <= V; j++) {
            DynamicList<Adyacencia> listaA = adyacentes(j);
            for (int k = 0; k < listaA.getLenght(); k++) {
                Adyacencia a = listaA.getInfo(k);
                if (distancias[j] + a.getPeso() < distancias[a.getDestino()]) {
                    throw new Exception("El grafo contiene un ciclo de peso negativo");
                }
            }
        }
        return distancias;
    }

    public Integer getNum_aristas() {
        return num_aristas;
    }

    public void setNum_aristas(Integer num_aristas) {
        this.num_aristas = num_aristas;
    }

    public DynamicList<Adyacencia>[] getListaAdyacencias() {
        return listaAdyacencias;
    }

    public void setListaAdyacencias(DynamicList<Adyacencia>[] listaAdyacencias) {
        this.listaAdyacencias = listaAdyacencias;
    }

    public DynamicList<Adyacencia> getAdyacencias(Integer v) {
        return listaAdyacencias[v];
    }
    
    public static void main(String[] args) {
        Grafo f = new GrafoDirigido(6);
        System.out.println(f);
        try {
            f.insertar_arista(1, 3, 50.0);
            f.insertar_arista(2, 6, 20.0);
            f.insertar_arista(3, 4, 30.0);
            f.insertar_arista(5, 1, 40.0);
            f.insertar_arista(2, 4, 70.0);
            f.insertar_arista(5, 6, 80.0);
            f.insertar_arista(1, 4, 5.0);
            PaintGraph p = new PaintGraph();
            p.updateFile(f);
            Utiles.abrirArchivoHTML("c:/Users/romer/Documents/NetBeansProjects/Pozos _luz/d3/grafo.html");
            System.out.println(f);
        } catch (Exception ex) {
            System.out.println("Error" + ex);
        }
    }


    
}
