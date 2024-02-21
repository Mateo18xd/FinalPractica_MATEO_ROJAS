/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista.tablas;

import controlador.TDA.listas.DynamicList;
import javax.swing.table.AbstractTableModel;
import modelo.Pozo;

/**
 *
 * @author Usuario iTC
 */
public class ModeloTablaPozo extends AbstractTableModel {
    private DynamicList<Pozo> pozoList;

    @Override
    public int getRowCount() {
        return pozoList.getLenght();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            Pozo p = pozoList.getInfo(rowIndex);
            switch (columnIndex) {
                case 0:
                    return (p != null) ? p.getNombre(): " ";
                case 1:
                    return (p != null) ? p.getFoto1(): "";
                case 2:
                    return (p != null) ? p.getFoto2(): "";
                case 3:
                    return (p != null) ? p.getCoordenada().getLatitud(): "";
                case 4:
                    return (p != null) ? p.getCoordenada().getLogitud() : "";
                default:
                    return null;
            }
        } catch (Exception e) {
            return null;
        }
    }
    
    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "NOMBRE";
            case 1:
                return "FOTO 1";
            case 2:
                return "FOTO 2";
            case 3:
                return "LATITUD";
            case 4:
                return "LONGITUD";                
            default:
                return null;
        }
    }

    public DynamicList<Pozo> getPozoList() {
        return pozoList;
    }

    public void setPozoList(DynamicList<Pozo> pozoList) {
        this.pozoList = pozoList;
    }
    
    
    
}
