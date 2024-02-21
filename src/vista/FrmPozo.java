/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package vista;

import controlador.DAO.Pozo.PozoDao;
import controlador.Utiles.Utiles;
import java.io.File;
import java.util.UUID;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import vista.tablas.ModeloTablaPozo;

/**
 *
 * @author Usuario iTC
 */
public class FrmPozo extends javax.swing.JFrame {

    private File foto1;
    private File foto2;
    private ModeloTablaPozo mtp = new ModeloTablaPozo();
    private PozoDao pd = new PozoDao();

    /**
     * Creates new form FrmPozo
     */
    public FrmPozo() {
        initComponents();
        this.setLocationRelativeTo(null);
        limpiar();
        panelFoto1.setIcon(new ImageIcon("foto/Foto.png"));
        panelFoto2.setIcon(new ImageIcon("foto/Foto.png"));
    }

    public Boolean verificar() {
        return (!txtLatitud.getText().trim().isEmpty()
                && !txtLongitud.getText().trim().isEmpty()
                && !txtNombre.getText().trim().isEmpty());
    }

    private void cargarTabla() {
        mtp.setPozoList(pd.all());
        tbPozo.setModel(mtp);
        tbPozo.updateUI();
    }

    public void limpiar() {
        txtNombre.setText("");
        txtLatitud.setText("");
        txtLongitud.setText("");
        panelFoto1.setIcon(new ImageIcon("foto/Foto.png"));
        panelFoto2.setIcon(new ImageIcon("foto/Foto.png"));
        repaint();
        cargarTabla();
    }

    private File cargarFoto() throws Exception {
        File obj = null;
        JFileChooser chooser = new JFileChooser();
        Integer resp = chooser.showOpenDialog(this);
        chooser.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter
                = new FileNameExtensionFilter("Imagenes", "jpg", "png", "jpeg");
        chooser.addChoosableFileFilter(filter);
        if (resp == JFileChooser.APPROVE_OPTION) {
            obj = chooser.getSelectedFile();
            
            System.out.println("Ok" + obj.getName());
        } else {
            System.out.println("Error");
        }
        return obj;
    }

    public void guardar() throws Exception {
        if (verificar()) {
            String uuidF1 = UUID.randomUUID().toString();
            String uuidF2 = UUID.randomUUID().toString();
            Utiles.copiarArchivo(foto1, new File("foto/" + uuidF1 + "." + Utiles.extension(foto1.getName())));
            Utiles.copiarArchivo(foto2, new File("foto/" + uuidF2 + "." + Utiles.extension(foto2.getName())));
            pd.getPozo().getCoordenada().setLogitud(Double.parseDouble(txtLongitud.getText()));
            pd.getPozo().getCoordenada().setLatitud(Double.parseDouble(txtLatitud.getText()));
            pd.getPozo().setNombre(txtNombre.getText());
            pd.getPozo().setFoto1(uuidF1 + "." + Utiles.extension(foto1.getName()));
            pd.getPozo().setFoto2(uuidF2 + "." + Utiles.extension(foto2.getName()));

            if (pd.persist()) {
                limpiar();
                JOptionPane.showMessageDialog(null, "Datos guardados");
                pd.setPozoList(null);
            } else {
                JOptionPane.showMessageDialog(null, "No se pudo guardar, hubo un error");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Falta llenar campos", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void cargarVista() {
        int fila = tbPozo.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoja un registro de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                pd.setPozo(mtp.getPozoList().getInfo(fila));
                panelFoto1.setIcon(new ImageIcon("foto/" + pd.getPozo().getFoto1()));
                panelFoto2.setIcon(new ImageIcon("foto/" + pd.getPozo().getFoto2()));
                repaint();
                foto1 = new File("foto/" + pd.getPozo().getFoto1());
                foto2 = new File("foto/" + pd.getPozo().getFoto2());
                txtLatitud.setText(String.valueOf(pd.getPozo().getCoordenada().getLatitud()));
                txtLongitud.setText(String.valueOf(pd.getPozo().getCoordenada().getLogitud()));
                txtNombre.setText(pd.getPozo().getNombre());
            } catch (Exception ex) {
                ex.toString();
            }
        }
    }

    public void modificar(){
        int fila = tbPozo.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(null, "Escoja un registro de la tabla", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String uuidF1 = UUID.randomUUID().toString();
                String uuidF2 = UUID.randomUUID().toString();
                Utiles.copiarArchivo(foto1, new File("foto/" + uuidF1 + "." + Utiles.extension(foto1.getName())));
                Utiles.copiarArchivo(foto2, new File("foto/" + uuidF2 + "." + Utiles.extension(foto2.getName())));
                pd.getPozo().getCoordenada().setLogitud(Double.parseDouble(txtLongitud.getText()));
                pd.getPozo().getCoordenada().setLatitud(Double.parseDouble(txtLatitud.getText()));
                pd.getPozo().setNombre(txtNombre.getText());
                pd.getPozo().setFoto1(uuidF1 + "." + Utiles.extension(foto1.getName()));
                pd.getPozo().setFoto2(uuidF2 + "." + Utiles.extension(foto2.getName()));
                if (pd.merge(pd.getPozo(), fila)){
                    JOptionPane.showMessageDialog(null, "Datos modificados");
                    limpiar();
                    cargarTabla();
                } else {
                    JOptionPane.showMessageDialog(null, "No se pudo modificar, hubo un error");
                }
            } catch (Exception ex) {
                ex.toString();
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        panel1 = new org.edisoncor.gui.panel.Panel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtNombre = new org.edisoncor.gui.textField.TextFieldRound();
        btnFoto1 = new javax.swing.JButton();
        btnFoto2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtLongitud = new org.edisoncor.gui.textField.TextFieldRound();
        txtLatitud = new org.edisoncor.gui.textField.TextFieldRound();
        panelFoto1 = new org.edisoncor.gui.panel.PanelImage();
        panelFoto2 = new org.edisoncor.gui.panel.PanelImage();
        btnGuardar = new org.edisoncor.gui.button.ButtonAero();
        btnLimpiar = new org.edisoncor.gui.button.ButtonAero();
        jPanel1 = new javax.swing.JPanel();
        panelCurves1 = new org.edisoncor.gui.panel.PanelCurves();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbPozo = new javax.swing.JTable();
        buttonAero1 = new org.edisoncor.gui.button.ButtonAero();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        panel1.setColorPrimario(new java.awt.Color(0, 0, 204));
        panel1.setColorSecundario(new java.awt.Color(255, 255, 255));

        jLabel1.setBackground(new java.awt.Color(0, 0, 0));
        jLabel1.setFont(new java.awt.Font("CaskaydiaCove NFM", 0, 18)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Pozo de Luz");

        jLabel2.setBackground(new java.awt.Color(255, 255, 255));
        jLabel2.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nombre:");

        txtNombre.setBackground(new java.awt.Color(102, 102, 102));
        txtNombre.setForeground(new java.awt.Color(204, 204, 204));

        btnFoto1.setBackground(new java.awt.Color(0, 0, 0));
        btnFoto1.setFont(new java.awt.Font("Lucida Sans Typewriter", 0, 14)); // NOI18N
        btnFoto1.setForeground(new java.awt.Color(255, 255, 255));
        btnFoto1.setText("Cargar");
        btnFoto1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFoto1ActionPerformed(evt);
            }
        });

        btnFoto2.setBackground(new java.awt.Color(0, 0, 0));
        btnFoto2.setFont(new java.awt.Font("Lucida Sans Typewriter", 0, 14)); // NOI18N
        btnFoto2.setForeground(new java.awt.Color(255, 255, 255));
        btnFoto2.setText("Cargar");
        btnFoto2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnFoto2ActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Latitud:");

        jLabel5.setFont(new java.awt.Font("Segoe UI Semibold", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Longitud:");

        txtLongitud.setBackground(new java.awt.Color(102, 102, 102));
        txtLongitud.setForeground(new java.awt.Color(204, 204, 204));

        txtLatitud.setBackground(new java.awt.Color(102, 102, 102));
        txtLatitud.setForeground(new java.awt.Color(204, 204, 204));

        javax.swing.GroupLayout panelFoto1Layout = new javax.swing.GroupLayout(panelFoto1);
        panelFoto1.setLayout(panelFoto1Layout);
        panelFoto1Layout.setHorizontalGroup(
            panelFoto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panelFoto1Layout.setVerticalGroup(
            panelFoto1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout panelFoto2Layout = new javax.swing.GroupLayout(panelFoto2);
        panelFoto2.setLayout(panelFoto2Layout);
        panelFoto2Layout.setHorizontalGroup(
            panelFoto2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );
        panelFoto2Layout.setVerticalGroup(
            panelFoto2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 180, Short.MAX_VALUE)
        );

        btnGuardar.setBackground(new java.awt.Color(0, 0, 0));
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        btnLimpiar.setBackground(new java.awt.Color(0, 0, 0));
        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimpiarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(187, 187, 187)
                        .addComponent(jLabel1))
                    .addGroup(panel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtLongitud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4))
                                .addGap(18, 18, 18)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE)
                                    .addComponent(txtLatitud, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(panel1Layout.createSequentialGroup()
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(panelFoto1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(btnFoto1, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 137, Short.MAX_VALUE)
                                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(panelFoto2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(btnFoto2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panel1Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(91, Short.MAX_VALUE))
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(34, 34, 34)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtLatitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(18, 18, 18)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtLongitud, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(30, 30, 30)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(panelFoto1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(panelFoto2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnFoto1, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnFoto2, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(53, 53, 53)
                .addGroup(panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jPanel1.setBackground(new java.awt.Color(0, 255, 255));

        panelCurves1.setBackground(new java.awt.Color(255, 255, 255));

        tbPozo.setModel(new javax.swing.table.DefaultTableModel(
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
        tbPozo.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbPozoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbPozo);

        buttonAero1.setBackground(new java.awt.Color(204, 204, 0));
        buttonAero1.setText("Modificar");
        buttonAero1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonAero1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelCurves1Layout = new javax.swing.GroupLayout(panelCurves1);
        panelCurves1.setLayout(panelCurves1Layout);
        panelCurves1Layout.setHorizontalGroup(
            panelCurves1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCurves1Layout.createSequentialGroup()
                .addGroup(panelCurves1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelCurves1Layout.createSequentialGroup()
                        .addGap(470, 470, 470)
                        .addComponent(buttonAero1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelCurves1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 603, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelCurves1Layout.setVerticalGroup(
            panelCurves1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelCurves1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 490, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(buttonAero1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addComponent(panelCurves1, javax.swing.GroupLayout.PREFERRED_SIZE, 582, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(panelCurves1, javax.swing.GroupLayout.PREFERRED_SIZE, 492, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu1.setText("Grafos");

        jMenuItem1.setText("Grafo y mapas");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnFoto1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFoto1ActionPerformed
        try {
            foto1 = cargarFoto();
            if (foto1 != null) {
                ImageIcon imageIcon = new ImageIcon(foto1.getAbsolutePath());
                panelFoto1.setIcon(imageIcon);
                repaint();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnFoto1ActionPerformed

    private void btnFoto2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnFoto2ActionPerformed
        try {
            foto2 = cargarFoto();
            if (foto2 != null) {
                ImageIcon imageIcon = new ImageIcon(foto2.getAbsolutePath());
                panelFoto2.setIcon(imageIcon);
                repaint();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnFoto2ActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        try {
            guardar();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Datos guardados");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiar();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new FrmGrafoPozo(null, true).setVisible(true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void buttonAero1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonAero1ActionPerformed
        modificar();
    }//GEN-LAST:event_buttonAero1ActionPerformed

    private void tbPozoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbPozoMouseClicked

        if (evt.getClickCount() >= 2) {
            cargarVista();
        }
    }//GEN-LAST:event_tbPozoMouseClicked

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
            java.util.logging.Logger.getLogger(FrmPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmPozo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FrmPozo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnFoto1;
    private javax.swing.JButton btnFoto2;
    private org.edisoncor.gui.button.ButtonAero btnGuardar;
    private org.edisoncor.gui.button.ButtonAero btnLimpiar;
    private org.edisoncor.gui.button.ButtonAero buttonAero1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private org.edisoncor.gui.panel.Panel panel1;
    private org.edisoncor.gui.panel.PanelCurves panelCurves1;
    private org.edisoncor.gui.panel.PanelImage panelFoto1;
    private org.edisoncor.gui.panel.PanelImage panelFoto2;
    private javax.swing.JTable tbPozo;
    private org.edisoncor.gui.textField.TextFieldRound txtLatitud;
    private org.edisoncor.gui.textField.TextFieldRound txtLongitud;
    private org.edisoncor.gui.textField.TextFieldRound txtNombre;
    // End of variables declaration//GEN-END:variables
}
