/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador.DAO.Pozo;

import controlador.DAO.DaoImplement;

import controlador.TDA.grafos.GrafosEtiquetadosDirigidos;
import controlador.TDA.listas.DynamicList;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import modelo.Pozo;

/**
 *
 * @author Usuario iTC
 */
public class PozoDao extends DaoImplement<Pozo>{
    private DynamicList<Pozo> pozoList = new DynamicList<>();
    private Pozo pozo;
    private GrafosEtiquetadosDirigidos<Pozo>  grafo;
    
    public PozoDao() {
        super(Pozo.class);
    }

    public GrafosEtiquetadosDirigidos<Pozo> getGrafo() throws Exception {
        if (grafo == null) {
            DynamicList<Pozo> list = getPozoList();
            if (!list.isEmpty()) {
                grafo = new GrafosEtiquetadosDirigidos(list.getLenght(), Pozo.class);
                for (int i = 0; i < list.getLenght(); i++) {
                    grafo.labelVertice((i + 1), list.getInfo(i));
                }
            }
        }
        return grafo;
    }

    public void setGrafo(GrafosEtiquetadosDirigidos<Pozo> grafo) {
        this.grafo = grafo;
    }
    
    public DynamicList<Pozo> getPozoList() {
        if (pozoList.isEmpty()) {
            pozoList = all();
        }
        return pozoList;
    }

    public void setPozoList(DynamicList<Pozo> pozoList) {
        this.pozoList = pozoList;
    }

    public Pozo getPozo() {
        if (pozo == null) {
            pozo = new Pozo();
        }
        return pozo;
    }

    public void setPozo(Pozo pozo) {
        this.pozo = pozo;
    }
    
    public Boolean persist(){
        pozo.setId(all().getLenght() + 1);
        pozo.getCoordenada().setId(all().getLenght() + 1);
        return persist(pozo);
    }
    
    public void guardarGrafo() throws Exception{
        getConection().toXML(grafo, new FileWriter("files/grafo.json"));
    }
    
    public void cargarGrafo() throws FileNotFoundException, Exception{
        grafo = (GrafosEtiquetadosDirigidos<Pozo>)
                getConection().fromXML(new FileReader("files/grafo.json"));
        //().toXML(grafo, new FileWriter("files/grafo.json"));
        pozoList.reset();
        for (int i = 1; i <= grafo.num_vertices(); i++) {
            pozoList.add(grafo.getLabelE(i));
        }
    }
}
