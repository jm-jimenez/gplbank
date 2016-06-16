/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import controlador.ControlProductos;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelo.productos.CuentaNomina;
import utils.Info;

/**
 * Permite mostrar una ventana modal con la información detallada de una cuenta nómina seleccionada.
 * @author JoséMaría
 */
public class DetalleCuentaNomina extends javax.swing.JDialog {

    private CuentaNomina cuentaNomina;
    private ControlProductos controlProductos;
    /**
     * Crea una nueva ventana modal, con la cuenta nómina seleccionada en la vista principal. Cuando se crea
     * esta clase, se recoge el parámetro cuentaNomina (la cuenta seleccionada) y el controlProductos (necesario
     * para llamara a los métodos dao). Ambas instancias ya están creadas en la vista principal y al construirse,
     * se comparten con esta clase.
     */
    public DetalleCuentaNomina(java.awt.Frame parent, boolean modal, CuentaNomina cuentaNomina, ControlProductos productosContratadosModel) {
        super(parent, modal);
        initComponents();
        ocultarPaneles();
        this.cuentaNomina = cuentaNomina;
        this.controlProductos = productosContratadosModel;
        ((DefaultTableModel) tablaDetalleCuentaNomina.getModel()).addRow(new Object[]{cuentaNomina.getIban(), cuentaNomina.getImporte(), cuentaNomina.getProximoCargo()});
    }
    
    private void ocultarPaneles(){
        panelIngresar.setVisible(false);
        panelRetirar.setVisible(false);
    }
    
    private void refrescarTabla(){
        ((DefaultTableModel) tablaDetalleCuentaNomina.getModel()).removeRow(0);
        ((DefaultTableModel) tablaDetalleCuentaNomina.getModel()).addRow(new Object[]{cuentaNomina.getIban(), cuentaNomina.getImporte(), cuentaNomina.getProximoCargo()});
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tablaDetalleCuentaNomina = new javax.swing.JTable();
        btnIngresar = new javax.swing.JButton();
        panelIngresar = new javax.swing.JPanel();
        jLabelIngresar = new javax.swing.JLabel();
        jtfIngresar = new javax.swing.JTextField();
        btnConfirmarI = new javax.swing.JButton();
        panelRetirar = new javax.swing.JPanel();
        jLabelRetirar = new javax.swing.JLabel();
        jtfRetirar = new javax.swing.JTextField();
        btnConfirmarR = new javax.swing.JButton();
        btnRetirar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        tablaDetalleCuentaNomina.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "IBAN", "SALDO", "PROXIMO CARGO"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Double.class, java.lang.Double.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tablaDetalleCuentaNomina);

        btnIngresar.setText("Ingresar");
        btnIngresar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIngresarActionPerformed(evt);
            }
        });

        jLabelIngresar.setText("Introduzca cantidad a ingresar: ");

        btnConfirmarI.setText("Confirmar");
        btnConfirmarI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelIngresarLayout = new javax.swing.GroupLayout(panelIngresar);
        panelIngresar.setLayout(panelIngresarLayout);
        panelIngresarLayout.setHorizontalGroup(
            panelIngresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelIngresarLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabelIngresar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtfIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnConfirmarI)
                .addContainerGap(74, Short.MAX_VALUE))
        );
        panelIngresarLayout.setVerticalGroup(
            panelIngresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelIngresarLayout.createSequentialGroup()
                .addContainerGap(64, Short.MAX_VALUE)
                .addGroup(panelIngresarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelIngresar)
                    .addComponent(jtfIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmarI))
                .addGap(78, 78, 78))
        );

        jLabelRetirar.setText("Introduzca cantidad a retirar: ");

        btnConfirmarR.setText("Confirmar");
        btnConfirmarR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConfirmarRActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelRetirarLayout = new javax.swing.GroupLayout(panelRetirar);
        panelRetirar.setLayout(panelRetirarLayout);
        panelRetirarLayout.setHorizontalGroup(
            panelRetirarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelRetirarLayout.createSequentialGroup()
                .addGap(49, 49, 49)
                .addComponent(jLabelRetirar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jtfRetirar, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(btnConfirmarR)
                .addContainerGap(61, Short.MAX_VALUE))
        );
        panelRetirarLayout.setVerticalGroup(
            panelRetirarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelRetirarLayout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(panelRetirarLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelRetirar)
                    .addComponent(jtfRetirar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConfirmarR))
                .addGap(56, 56, 56))
        );

        btnRetirar.setText("Retirar");
        btnRetirar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRetirarActionPerformed(evt);
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
                        .addComponent(panelIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(128, 128, 128)
                        .addComponent(btnIngresar)
                        .addGap(94, 94, 94)
                        .addComponent(btnRetirar))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 495, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(20, 20, 20)
                    .addComponent(panelRetirar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addComponent(panelIngresar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnIngresar)
                    .addComponent(btnRetirar))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(112, Short.MAX_VALUE)
                    .addComponent(panelRetirar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(54, 54, 54)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnIngresarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIngresarActionPerformed
        ocultarPaneles();
        panelIngresar.setVisible(true);
    }//GEN-LAST:event_btnIngresarActionPerformed

    private void btnConfirmarIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarIActionPerformed
        double cantidad = Double.parseDouble(jtfIngresar.getText());
        cuentaNomina.setImporte(cuentaNomina.getImporte() + cantidad);
        Info i = controlProductos.modificarImporteCuentaNomina(cuentaNomina);
        if (i.isSuccess()){
            JOptionPane.showMessageDialog(this, i.getMsg(), "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            refrescarTabla();
        }
        else{
            JOptionPane.showMessageDialog(this, i.getMsg(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
        
    }//GEN-LAST:event_btnConfirmarIActionPerformed

    private void btnConfirmarRActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConfirmarRActionPerformed
        double cantidad = Double.parseDouble(jtfRetirar.getText());
        cuentaNomina.setImporte(cuentaNomina.getImporte() - cantidad);
        Info i = controlProductos.modificarImporteCuentaNomina(cuentaNomina);
        if (i.isSuccess()){
            JOptionPane.showMessageDialog(this, i.getMsg(), "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            refrescarTabla();
        }
        else{
            JOptionPane.showMessageDialog(this, i.getMsg(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnConfirmarRActionPerformed

    private void btnRetirarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRetirarActionPerformed
        ocultarPaneles();
        panelRetirar.setVisible(true);
    }//GEN-LAST:event_btnRetirarActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnConfirmarI;
    private javax.swing.JButton btnConfirmarR;
    private javax.swing.JButton btnIngresar;
    private javax.swing.JButton btnRetirar;
    private javax.swing.JLabel jLabelIngresar;
    private javax.swing.JLabel jLabelRetirar;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jtfIngresar;
    private javax.swing.JTextField jtfRetirar;
    private javax.swing.JPanel panelIngresar;
    private javax.swing.JPanel panelRetirar;
    private javax.swing.JTable tablaDetalleCuentaNomina;
    // End of variables declaration//GEN-END:variables
}
