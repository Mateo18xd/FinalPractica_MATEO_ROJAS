/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package vista;

import controlador.DAO.Pozo.PozoDao;
import controlador.TDA.grafos.PaintGraph;
import controlador.TDA.listas.DynamicList;
import controlador.Utiles.TiempoEjecucion;
import controlador.Utiles.Utiles;
import java.time.Duration;
import java.time.Instant;
import vista.util.TiempoTablaModel;
import vista.util.UtilVistaPozo;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Usuario iTC
 */
public class FrmGrafoPozo extends javax.swing.JDialog {
    
    private PozoDao pd = new PozoDao();
    private TiempoTablaModel ttm = new TiempoTablaModel();
    Double tiempoMedido;

    
    
    /**
     * Creates new form FrmGrafoPozo
     */
    public FrmGrafoPozo(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        limpiar();
        this.setLocationRelativeTo(null);
    }
    
    public void limpiar(){
        try {
            UtilVistaPozo.cargarComboPozo(cbxOrigen);
            UtilVistaPozo.cargarComboPozo(cbxDestino);
        } catch (Exception e) {
        }
    }
    
    public void mostrarGrafo() throws Exception {
        PaintGraph p = new PaintGraph();
        p.updateFileLabel(pd.getGrafo());
        Utiles.abrirArchivoHTML("src/d3/grafo.html");
    }
    
    public void mostrarMapa() throws Exception {
        UtilVistaPozo.crearMapaEscuela(pd.getGrafo());
        Runtime rt = Runtime.getRuntime();
        Utiles.abrirArchivoHTML("src/mapas/index.html");
    }

    public void insertarAdyacencias() throws Exception {
        Random random = new Random();
        int maxAdyacencias = 2;

        for (int i = 0; i < pd.getPozoList().getLenght(); i++) {
            int numAdyacencias = random.nextInt(maxAdyacencias - 1) + 2;

            DynamicList<Integer> disponibles = new DynamicList<>();
            for (int j = i + 1; j < pd.getPozoList().getLenght(); j++) {
                disponibles.add(j);
            }

            
            for (int k = 0; k < numAdyacencias; k++) {
                
                if (disponibles.isEmpty()) {
                    break;
                }

               
                int indiceAleatorio = random.nextInt(disponibles.getLenght());
                int indiceNodo = disponibles.getInfo(indiceAleatorio);

                
                Double distancia = UtilVistaPozo.calcularDistanciaPozo(pd.getPozoList().getInfo(i), pd.getPozoList().getInfo(indiceNodo));
                distancia = Utiles.redondear(distancia);
                pd.getGrafo().insertEdge(pd.getPozoList().getInfo(i), pd.getPozoList().getInfo(indiceNodo), distancia);
                disponibles.remove(indiceAleatorio);
            }
        }
    }
    
    public void guardarGrafo() {
        try {
            int i = JOptionPane.
                    showConfirmDialog(null, "Esta seguro de guardar?", "Advertencia", JOptionPane.OK_CANCEL_OPTION);

            if (i == JOptionPane.OK_OPTION) {
                if (pd.getGrafo() != null) {
                    pd.guardarGrafo();
                    JOptionPane.showMessageDialog(null, "Guardado");
                } else {
                    JOptionPane.showMessageDialog(null, "Los grafos vacion no se guardaran");
                }
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }
    
    public void load() throws Exception{
        int i = JOptionPane.
                    showConfirmDialog(null, "Esta seguro de guardar?", "Advertencia", JOptionPane.OK_CANCEL_OPTION);

            if (i == JOptionPane.OK_OPTION) {
                pd.cargarGrafo();
                limpiar();
                JOptionPane.showMessageDialog(null, "Guardar");
            }
    }

    public void floyd() throws Exception {
        long inicio = System.nanoTime();
        if (pd.getGrafo() != null){
            Integer o = cbxOrigen.getSelectedIndex() + 1;
            Integer d = cbxDestino.getSelectedIndex() + 1;
            Double[][] matriz = pd.getGrafo().floydRecorrido();
            Double[][] distancia = pd.getGrafo().recorridoFloydDistancias();

            if (matriz[o][d] == Double.POSITIVE_INFINITY || matriz[o][d] == null) {
                JOptionPane.showMessageDialog(null, "No existe camino entre los vértices " + o + " y " + distancia);
            } else if (o.intValue() == d.intValue()) {
                JOptionPane.showMessageDialog(null, "El vértice de origen y destino son iguales");
            }
            else {
                StringBuilder sb = new StringBuilder();
                sb.append("La distancia entre los vértices ").append(o).append(" y ").append(d).append(" es: ").append(distancia[o][d]);
                sb.append("\n");
                DynamicList<Integer> camino = caminoFloyd(matriz, o, d);
                sb.append("El camino es: ").append(camino.toString());
                txtFloyd.setText(sb.toString());
                txtFloyd.setEnabled(false);
            }
        }
        long fin = System.nanoTime();
        long tiempo = fin - inicio;
        ttm.agregarTiempo(new TiempoEjecucion("Floyd", tiempo));
        tbTiempo.setModel(ttm);
        tbTiempo.updateUI();
    }

    private DynamicList<Integer> caminoFloyd(Double[][] matriz, Integer o, Integer d) throws Exception {
        DynamicList<Integer> camino = new DynamicList<>();
        Integer vertice = d;

        while (vertice != o) {
            camino.add(vertice);
            vertice = matriz[o][vertice].intValue();
        }
        camino.add(o);
        camino.invertir_orden();
        return camino;
    }

    public void bellman() throws Exception {
        long inicio = System.nanoTime();
        Integer o = cbxOrigen.getSelectedIndex() + 1;
        Integer d = cbxDestino.getSelectedIndex() + 1;
        Double[] matriz = pd.getGrafo().bellmanFordRecorrido(o);
        Double[] distancia = pd.getGrafo().recorridoBellmanDistancias(o);
        if (matriz[d] == Double.POSITIVE_INFINITY) {
            JOptionPane.showMessageDialog(null, "No existe camino entre los vértices " + o + " y " + d);
        } else  if (o.intValue() == d.intValue()) {
            JOptionPane.showMessageDialog(null, "El vértice de origen y destino son iguales");
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append("La distancia entre los vértices ").append(o).append(" y ").append(d).append(" es: ").append(distancia[d]);
            sb.append("\n");
            DynamicList<Integer> camino = caminoBellman(matriz, o, d);
            sb.append("El camino es: ").append(camino.toString());
            txtBellman.setText(sb.toString());
            txtBellman.setEnabled(false);
        }
        long fin = System.nanoTime();
        long tiempo = fin - inicio;
        ttm.agregarTiempo(new TiempoEjecucion("Bellman", tiempo));
        tbTiempo.setModel(ttm);
        tbTiempo.updateUI();
    }

    private DynamicList<Integer> caminoBellman(Double[] matriz, Integer o, Integer d) throws Exception {
        DynamicList<Integer> camino = new DynamicList<>();
        Integer vertice = d;

        while (vertice != o) {
            camino.add(vertice);
            vertice = matriz[vertice].intValue();
        }
        camino.add(o);
        camino.invertir_orden();
        return camino;
    }

    public void  recorridoAnchura() throws Exception {
        Integer o = cbxOrigen.getSelectedIndex() + 1;
        DynamicList<Integer> recorrido = pd.getGrafo().recorridoAnchura(o);
        txtAnchura.setText(recorrido.toString());
        txtAnchura.setEnabled(false);
    }

    public void recorridoProfundidad() throws Exception {
        Integer o = cbxOrigen.getSelectedIndex() + 1;
        DynamicList<Integer> recorrido = pd.getGrafo().recorridoProfundidad(o);
        txtProfundidad.setText(recorrido.toString());
        txtProfundidad.setEnabled(false);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        cbxOrigen = new javax.swing.JComboBox<>();
        cbxDestino = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        btnAnchura = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtAnchura = new javax.swing.JTextArea();
        jLabel5 = new javax.swing.JLabel();
        btnProfundidad = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtProfundidad = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        txtBellman = new javax.swing.JTextArea();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txtFloyd = new javax.swing.JTextArea();
        btnBellman = new javax.swing.JButton();
        BtnFloyd = new javax.swing.JButton();
        buttonAero1 = new org.edisoncor.gui.button.ButtonAero();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbTiempo = new javax.swing.JTable();
        buttonAero2 = new org.edisoncor.gui.button.ButtonAero();
        buttonAero3 = new org.edisoncor.gui.button.ButtonAero();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 0, 18)); // NOI18N
        jLabel1.setText("Pozos");

        cbxOrigen.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxDestino.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(103, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(188, 188, 188))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(88, 88, 88)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cbxOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addComponent(cbxOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cbxDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel2.setText("Grafo");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel3.setText("Mapa");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(jLabel2)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(58, 58, 58)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(114, 114, 114))))
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel4.setText("Recorrido anchura:");

        btnAnchura.setText("Calcular");
        btnAnchura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnchuraActionPerformed(evt);
            }
        });

        txtAnchura.setColumns(20);
        txtAnchura.setRows(5);
        jScrollPane1.setViewportView(txtAnchura);

        jLabel5.setText("Recorrido en profundidad");

        btnProfundidad.setText("Calcular");
        btnProfundidad.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnProfundidadActionPerformed(evt);
            }
        });

        txtProfundidad.setColumns(20);
        txtProfundidad.setRows(5);
        jScrollPane2.setViewportView(txtProfundidad);

        jLabel6.setText("Recorrido Bellman Ford");

        txtBellman.setColumns(20);
        txtBellman.setRows(5);
        jScrollPane3.setViewportView(txtBellman);

        jLabel7.setText("Recorrido de floyd");

        txtFloyd.setColumns(20);
        txtFloyd.setRows(5);
        jScrollPane4.setViewportView(txtFloyd);

        btnBellman.setText("Calcular");
        btnBellman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBellmanActionPerformed(evt);
            }
        });

        BtnFloyd.setText("Calcular");
        BtnFloyd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnFloydActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnProfundidad))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBellman))
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(BtnFloyd))
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnAnchura)))
                        .addContainerGap())))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(btnAnchura))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(btnProfundidad))
                .addGap(15, 15, 15)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6)
                    .addComponent(btnBellman))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(BtnFloyd))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        buttonAero1.setText("Conectar grafo");
        buttonAero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAero1ActionPerformed(evt);
            }
        });

        tbTiempo.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(tbTiempo);

        buttonAero2.setBackground(new java.awt.Color(0, 204, 51));
        buttonAero2.setText("Guardar grafo");
        buttonAero2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAero2ActionPerformed(evt);
            }
        });

        buttonAero3.setText("Cargar grafo");
        buttonAero3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAero3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(buttonAero2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 111, Short.MAX_VALUE)
                                .addComponent(buttonAero3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(102, 102, 102)
                        .addComponent(buttonAero1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane5)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(buttonAero1, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonAero2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(buttonAero3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
         
            mostrarGrafo();
            
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            
            mostrarMapa();
           
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void buttonAero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAero1ActionPerformed
        try {
            insertarAdyacencias();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: ");
        }
    }//GEN-LAST:event_buttonAero1ActionPerformed

    private void btnBellmanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBellmanActionPerformed
        try {
            Instant start = Instant.now();
            bellman();
            Instant end = Instant.now();
            Duration duration = Duration.between(start,end);
            tiempoMedido = (double) duration.toMillis();
            JOptionPane.showMessageDialog(null, tiempoMedido + "milisegundos");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error: " + e);
        }
    }//GEN-LAST:event_btnBellmanActionPerformed

    private void btnAnchuraActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnchuraActionPerformed
        try {
            Instant start = Instant.now();
            recorridoAnchura();
            Instant end = Instant.now();
            Duration duration = Duration.between(start,end);
            tiempoMedido = (double) duration.toMillis();
            JOptionPane.showMessageDialog(null, tiempoMedido + "milisegundos");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_btnAnchuraActionPerformed

    private void btnProfundidadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProfundidadActionPerformed
        try {
            Instant start = Instant.now();
            recorridoProfundidad();
            Instant end = Instant.now();
            Duration duration = Duration.between(start,end);
            tiempoMedido = (double) duration.toMillis();
            JOptionPane.showMessageDialog(null, tiempoMedido + "milisegundos");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_btnProfundidadActionPerformed

    private void BtnFloydActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnFloydActionPerformed
        try {
            Instant start = Instant.now();
            floyd();
            Instant end = Instant.now();
            Duration duration = Duration.between(start,end);
            tiempoMedido = (double) duration.toMillis();
            JOptionPane.showMessageDialog(null, tiempoMedido + "milisegundos");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Error: " + ex);
        }
    }//GEN-LAST:event_BtnFloydActionPerformed

    private void buttonAero2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAero2ActionPerformed
        guardarGrafo();
    }//GEN-LAST:event_buttonAero2ActionPerformed

    private void buttonAero3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAero3ActionPerformed
        try {
            load();
        } catch (Exception ex) {
        }
    }//GEN-LAST:event_buttonAero3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmGrafoPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmGrafoPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmGrafoPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmGrafoPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmGrafoPozo dialog = new FrmGrafoPozo(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnFloyd;
    private javax.swing.JButton btnAnchura;
    private javax.swing.JButton btnBellman;
    private javax.swing.JButton btnProfundidad;
    private org.edisoncor.gui.button.ButtonAero buttonAero1;
    private org.edisoncor.gui.button.ButtonAero buttonAero2;
    private org.edisoncor.gui.button.ButtonAero buttonAero3;
    private javax.swing.JComboBox<String> cbxDestino;
    private javax.swing.JComboBox<String> cbxOrigen;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTable tbTiempo;
    private javax.swing.JTextArea txtAnchura;
    private javax.swing.JTextArea txtBellman;
    private javax.swing.JTextArea txtFloyd;
    private javax.swing.JTextArea txtProfundidad;
    // End of variables declaration//GEN-END:variables
}
