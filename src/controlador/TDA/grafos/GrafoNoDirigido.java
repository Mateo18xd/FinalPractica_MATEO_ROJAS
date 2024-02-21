package controlador.TDA.grafos;

import controlador.TDA.grafos.exeption.VerticeException;
import controlador.Utiles.Utiles;


/**
 *
 * @author Usuario iTC
 */
public class GrafoNoDirigido extends GrafoDirigido {
    
    public GrafoNoDirigido(Integer num_vertices ) {
        super(num_vertices);
    }
     @Override
    public void insertar_arista(Integer v1, Integer v2, Double peso) throws Exception {
        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
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
    
    
    
    public static void main(String[] args) {
        Grafo f = new GrafoNoDirigido(6);
        System.out.println(f);
        try {
            f.insertar_arista(1, 3, 50.0);
            f.insertar_arista(4, 5, 10.0);
            f.insertar_arista(1, 2, 5.0);
            f.insertar_arista(4, 1, 30.0);
            f.insertar_arista(6, 3, 7.0);
            System.out.println(f);
            PaintGraph p =  new PaintGraph();
            p.updateFile(f);
            Utiles.abrirNavegadorPredeterminadorWindows("d3/grafo.html");
        } catch (Exception ex) {
            System.out.println("Error "+ex);
        }
    }

}
