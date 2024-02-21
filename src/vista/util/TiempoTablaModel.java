package vista.util;

import controlador.TDA.listas.DynamicList;
import controlador.TDA.listas.Exception.EmptyException;
import controlador.Utiles.TiempoEjecucion;

import javax.swing.table.AbstractTableModel;


/**
 *
 * @author Usuario iTC
 */
public class TiempoTablaModel extends AbstractTableModel {
    private DynamicList<TiempoEjecucion> tiemposEjecucion;
    private String[] columnNames = {"Algoritmo", "Tiempo (nanosegundos)"};

    public TiempoTablaModel() {
        tiemposEjecucion = new DynamicList<>();
    }

    public void agregarTiempo(TiempoEjecucion tiempo) {
        tiemposEjecucion.add(tiempo);
        fireTableDataChanged(); // Notificar a la tabla que los datos han cambiado
    }

    @Override
    public int getRowCount() {
        return tiemposEjecucion.getLenght();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        try {
            TiempoEjecucion tiempo = tiemposEjecucion.getInfo(rowIndex);

            switch (columnIndex) {
                case 0:
                    return tiempo.getAlgoritmo();
                case 1:
                    return tiempo.getTiempo();
                default:
                    return null;
            }
        } catch (EmptyException e) {
            return null;
        }
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }
}
