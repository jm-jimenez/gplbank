/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import constantes.TipoProducto;
import constantes.TipoProductoAdapter;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 * Ventana modal que recoge el formulario para cumplimentar los datos de un nuevo producto.
 * @author JoséMaría
 */
public class PlantillaNuevoProducto extends javax.swing.JDialog {
    
    private TipoProducto tipo;
    private double remuneracion;

    /**
     * Creates new form PlantillaNuevoProducto
     */
    public PlantillaNuevoProducto(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        rellenarCombo();
        ocultarPanel();
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    
    public TipoProducto getTipo(){
        return tipo;
    }
    
    public double getRemuneracion(){
        return remuneracion;
    }
    
    public void ocultarPanel(){
        panelRemuneracion.setVisible(false);
    }

    private void rellenarCombo() {
        jComboTipoProducto.addItem(new TipoProductoAdapter("Seleccione uno...", null));
        jComboTipoProducto.addItem(new TipoProductoAdapter("Tarjeta", TipoProducto.TARJETA));
        jComboTipoProducto.addItem(new TipoProductoAdapter("Cuenta nómina", TipoProducto.CUENTA_NOMINA));
        jComboTipoProducto.addItem(new TipoProductoAdapter("Cuenta ahorro", TipoProducto.CUENTA_AHORRO));
        jComboTipoProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ocultarPanel();
                TipoProducto seleccionado = ((TipoProductoAdapter) jComboTipoProducto.getSelectedItem()).getTipo();
                if (seleccionado != null) {
                    tipo = seleccionado;
                    if (tipo == TipoProducto.CUENTA_AHORRO){
                        panelRemuneracion.setVisible(true);
                    }
                }
            }
        });

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabelTipoProducto = new javax.swing.JLabel();
        jComboTipoProducto = new javax.swing.JComboBox();
        btnAceptar = new javax.swing.JButton();
        btnCancelar = new javax.swing.JButton();
        panelRemuneracion = new javax.swing.JPanel();
        jLabelRemuneracion = new javax.swing.JLabel();
        jtfRemuneracion = new javax.swing.JTextField();
        jLabelPorCiento = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jLabelTipoProducto.setText("Seleccione tipo de producto: ");

        btnAceptar.setText("Aceptar");
        btnAceptar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAceptarActionPerformed(evt);
            }
        });

        btnCancelar.setText("Cancelar");
        btnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelarActionPerformed(evt);
            }
        });

        jLabelRemuneracion.setText("Remuneración inicial:");

        jLabelPorCiento.setText("%");

        javax.swing.GroupLayout panelRemuneracionLayout = new javax.swing.GroupLayout(panelRemuneracion);
        panelRemuneracion.setLayout(panelRemuneracionLayout);
        panelRemuneracionLayout.setHorizontalGroup(
            panelRemuneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRemuneracionLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabelRemuneracion)
                .addGap(18, 18, 18)
                .addComponent(jtfRemuneracion, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelPorCiento)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        panelRemuneracionLayout.setVerticalGroup(
            panelRemuneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRemuneracionLayout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(panelRemuneracionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRemuneracion)
                    .addComponent(jtfRemuneracion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPorCiento))
                .addContainerGap(113, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btnAceptar)
                            .addComponent(jLabelTipoProducto))
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCancelar))
                        .addGap(0, 102, Short.MAX_VALUE))
                    .addComponent(panelRemuneracion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelTipoProducto)
                    .addComponent(jComboTipoProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(panelRemuneracion, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAceptar)
                    .addComponent(btnCancelar))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelarActionPerformed
        tipo = null;
        dispose();
    }//GEN-LAST:event_btnCancelarActionPerformed

    private void btnAceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAceptarActionPerformed
        if (tipo == null){
            JOptionPane.showMessageDialog(this, "No ha seleccionado ninguna opción", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        else{
            if (tipo == TipoProducto.CUENTA_AHORRO){
                remuneracion = Double.parseDouble(jtfRemuneracion.getText());
            }
            dispose();
        }
    }//GEN-LAST:event_btnAceptarActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        if (JOptionPane.showConfirmDialog(this, "¿Desea cancelar la contratación?", "ATENCIÓN", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
            tipo = null;
            dispose();
        }
    }//GEN-LAST:event_formWindowClosing


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAceptar;
    private javax.swing.JButton btnCancelar;
    private javax.swing.JComboBox jComboTipoProducto;
    private javax.swing.JLabel jLabelPorCiento;
    private javax.swing.JLabel jLabelRemuneracion;
    private javax.swing.JLabel jLabelTipoProducto;
    private javax.swing.JTextField jtfRemuneracion;
    private javax.swing.JPanel panelRemuneracion;
    // End of variables declaration//GEN-END:variables
}
