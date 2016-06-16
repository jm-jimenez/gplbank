/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vista;

import constantes.Mensajes;
import constantes.TipoProducto;
import controlador.ControlClientes;
import controlador.ControlEmpleados;
import controlador.ControlProductos;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;
import modelo.personas.Cliente;
import modelo.personas.Empleado;
import modelo.productos.CuentaNomina;
import modelo.productos.Producto;
import modelo.productos.Tarjeta;
import utils.Info;
import utils.Parsear;

/**
 * Vista principal del cliente para empleados.
 * @author JoséMaría
 */
public class GPLBankEmpleadosGUI extends javax.swing.JFrame {

    private String empleadoActivo;
    private ControlEmpleados controlEmpleados;
    private ControlClientes controlClientes;
    private ControlProductos controlProductos;

    private Cliente clienteActual;

    private EmpleadoResumenModel empleadoResumenModel;
    private ProductosContratadosModel productosContratadosModel;

    /**
     * Creates new form GPLBankGUI
     */
    public GPLBankEmpleadosGUI() {
        initComponents();
        controlEmpleados = new ControlEmpleados();
        solicitarClaveCifrado();
        controlClientes = new ControlClientes();
        controlProductos = new ControlProductos();
        panelVisible(panelLogin);
        VistaUtils.getInstance().ponerKeyListenerAlJtf(jtfDNICliente);
        VistaUtils.getInstance().ponerKeyListenerAlJtf(jtfDNIlogin);
        VistaUtils.getInstance().ponerKeyListenerAlJtf(jtfInteresCA);
    }

    private void solicitarClaveCifrado() {
        controlEmpleados.solicitarClave();
    }

    private void panelVisible(JComponent panel) {
        panelLogin.setVisible(false);
        panelJefe.setVisible(false);
        panelEmpleado.setVisible(false);
        panelPosicionGlobalCliente.setVisible(false);
        panel.setVisible(true);
    }

    private void inicializarTablaEmpleados() {
        empleadoResumenModel = new EmpleadoResumenModel(controlEmpleados);
        tablaEmpleados.setModel(empleadoResumenModel);
        tablaEmpleados.setAutoCreateRowSorter(true);
        tablaEmpleados.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        tablaEmpleados.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int row = tablaEmpleados.rowAtPoint(e.getPoint());
                    tablaEmpleados.setRowSelectionInterval(row, row);
                }
            }

        });
    }

    private void inicializarTablaProductos() {
        productosContratadosModel = new ProductosContratadosModel(controlProductos, clienteActual);
        tablaProductos.setModel(productosContratadosModel);
        tablaProductos.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent me) {
                if (me.getClickCount() == 2) {
                    JTable jtable = (JTable) me.getSource();
                    int row = jtable.getSelectedRow();
                    Producto p = productosContratadosModel.getProductAtRow(row);
                    if (p instanceof CuentaNomina) {
                        DetalleCuentaNomina dcn = new DetalleCuentaNomina(GPLBankEmpleadosGUI.this, true, (CuentaNomina) p, controlProductos);
                        dcn.setVisible(true);
                    } else if (p instanceof Tarjeta) {
                        DetalleTarjeta dt = new DetalleTarjeta(GPLBankEmpleadosGUI.this, true, (Tarjeta) p, controlProductos);
                        dt.setVisible(true);
                    }
                }
            }
        });
    }

    public String getEmpleadoActivo() {
        return empleadoActivo;
    }

    public JTable getTablaDatosCliente() {
        return tablaDatosCliente;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ascenderPopup = new javax.swing.JPopupMenu();
        ascenderItem = new javax.swing.JMenuItem();
        panelLogin = new javax.swing.JPanel();
        panelLoginDatos = new javax.swing.JPanel();
        panelLoginFields = new javax.swing.JPanel();
        panelFieldDNI = new javax.swing.JPanel();
        labelDNIlogin = new javax.swing.JLabel();
        jtfDNIlogin = new javax.swing.JTextField();
        panelFieldPassword = new javax.swing.JPanel();
        labelPassLogin = new javax.swing.JLabel();
        jtfPassLogin = new javax.swing.JPasswordField();
        panelLoginBotones = new javax.swing.JPanel();
        btnValidarLogin = new javax.swing.JButton();
        panelJefe = new javax.swing.JTabbedPane();
        tabEmpleados = new javax.swing.JPanel();
        jLabelListadoEmpleados = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaEmpleados = new javax.swing.JTable();
        botonesEmpleados = new javax.swing.JPanel();
        btnAlta = new javax.swing.JButton();
        btnBaja = new javax.swing.JButton();
        tabGestion = new javax.swing.JPanel();
        jLabelICA = new javax.swing.JLabel();
        jtfInteresCA = new javax.swing.JTextField();
        jLabelPercentaje = new javax.swing.JLabel();
        btnModificarInteres = new javax.swing.JButton();
        panelEmpleado = new javax.swing.JPanel();
        panelBuscarCliente = new javax.swing.JPanel();
        jLabelBuscarDNI = new javax.swing.JLabel();
        jtfDNICliente = new javax.swing.JTextField();
        btnBuscar = new javax.swing.JButton();
        botonesClientes = new javax.swing.JPanel();
        btnNuevoCliente = new javax.swing.JButton();
        panelPosicionGlobalCliente = new javax.swing.JPanel();
        jLabelDatos = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaDatosCliente = new javax.swing.JTable();
        btnModificarDatosCliente = new javax.swing.JButton();
        jLabelProductosContratados = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaProductos = new javax.swing.JTable();
        btnContratar = new javax.swing.JButton();
        btnBajaProducto = new javax.swing.JButton();
        btnBajaCliente = new javax.swing.JButton();

        ascenderItem.setText("Ascender a Jefe");
        ascenderItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ascenderItemActionPerformed(evt);
            }
        });
        ascenderPopup.add(ascenderItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        panelLogin.setLayout(new java.awt.BorderLayout());

        panelLoginFields.setLayout(new javax.swing.BoxLayout(panelLoginFields, javax.swing.BoxLayout.Y_AXIS));

        labelDNIlogin.setText("DNI:");
        panelFieldDNI.add(labelDNIlogin);

        jtfDNIlogin.setMinimumSize(new java.awt.Dimension(6, 23));
        jtfDNIlogin.setPreferredSize(new java.awt.Dimension(125, 23));
        jtfDNIlogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jtfDNIloginActionPerformed(evt);
            }
        });
        panelFieldDNI.add(jtfDNIlogin);

        panelLoginFields.add(panelFieldDNI);

        labelPassLogin.setText("Password: ");
        panelFieldPassword.add(labelPassLogin);

        jtfPassLogin.setMinimumSize(new java.awt.Dimension(6, 23));
        jtfPassLogin.setPreferredSize(new java.awt.Dimension(125, 23));
        jtfPassLogin.setRequestFocusEnabled(false);
        panelFieldPassword.add(jtfPassLogin);

        panelLoginFields.add(panelFieldPassword);

        javax.swing.GroupLayout panelLoginDatosLayout = new javax.swing.GroupLayout(panelLoginDatos);
        panelLoginDatos.setLayout(panelLoginDatosLayout);
        panelLoginDatosLayout.setHorizontalGroup(
            panelLoginDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginDatosLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panelLoginFields, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                .addContainerGap())
        );
        panelLoginDatosLayout.setVerticalGroup(
            panelLoginDatosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelLoginDatosLayout.createSequentialGroup()
                .addGap(95, 95, 95)
                .addComponent(panelLoginFields, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        panelLogin.add(panelLoginDatos, java.awt.BorderLayout.CENTER);

        btnValidarLogin.setText("Validar");
        btnValidarLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnValidarLoginActionPerformed(evt);
            }
        });
        panelLoginBotones.add(btnValidarLogin);

        panelLogin.add(panelLoginBotones, java.awt.BorderLayout.SOUTH);

        tabEmpleados.setLayout(new java.awt.BorderLayout());

        jLabelListadoEmpleados.setText("Listado empleados:");
        tabEmpleados.add(jLabelListadoEmpleados, java.awt.BorderLayout.PAGE_START);

        tablaEmpleados.setModel(new javax.swing.table.DefaultTableModel(
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
        tablaEmpleados.setComponentPopupMenu(ascenderPopup);
        tablaEmpleados.setMinimumSize(new java.awt.Dimension(300, 64));
        jScrollPane1.setViewportView(tablaEmpleados);

        tabEmpleados.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        btnAlta.setText("Dar de alta");
        btnAlta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAltaActionPerformed(evt);
            }
        });
        botonesEmpleados.add(btnAlta);

        btnBaja.setText("Dar de baja");
        btnBaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaActionPerformed(evt);
            }
        });
        botonesEmpleados.add(btnBaja);

        tabEmpleados.add(botonesEmpleados, java.awt.BorderLayout.PAGE_END);

        panelJefe.addTab("Operaciones Empleados", tabEmpleados);

        jLabelICA.setText("Nueva remuneración para las cuentas de ahorro:");

        jLabelPercentaje.setText("%");

        btnModificarInteres.setText("Modificar");
        btnModificarInteres.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarInteresActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout tabGestionLayout = new javax.swing.GroupLayout(tabGestion);
        tabGestion.setLayout(tabGestionLayout);
        tabGestionLayout.setHorizontalGroup(
            tabGestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabGestionLayout.createSequentialGroup()
                .addGroup(tabGestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btnModificarInteres)
                    .addGroup(tabGestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(tabGestionLayout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabelICA))
                        .addGroup(tabGestionLayout.createSequentialGroup()
                            .addGap(130, 130, 130)
                            .addComponent(jtfInteresCA, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jLabelPercentaje))))
                .addContainerGap(275, Short.MAX_VALUE))
        );
        tabGestionLayout.setVerticalGroup(
            tabGestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(tabGestionLayout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(jLabelICA)
                .addGap(30, 30, 30)
                .addGroup(tabGestionLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jtfInteresCA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelPercentaje))
                .addGap(59, 59, 59)
                .addComponent(btnModificarInteres)
                .addContainerGap(278, Short.MAX_VALUE))
        );

        panelJefe.addTab("Operaciones Gestión", tabGestion);

        panelEmpleado.setLayout(new java.awt.BorderLayout());

        jLabelBuscarDNI.setText("Buscar DNI:");

        btnBuscar.setText("Buscar");
        btnBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelBuscarClienteLayout = new javax.swing.GroupLayout(panelBuscarCliente);
        panelBuscarCliente.setLayout(panelBuscarClienteLayout);
        panelBuscarClienteLayout.setHorizontalGroup(
            panelBuscarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelBuscarClienteLayout.createSequentialGroup()
                .addGroup(panelBuscarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelBuscarClienteLayout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addComponent(jLabelBuscarDNI)
                        .addGap(26, 26, 26)
                        .addComponent(jtfDNICliente, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(panelBuscarClienteLayout.createSequentialGroup()
                        .addGap(175, 175, 175)
                        .addComponent(btnBuscar)))
                .addContainerGap(225, Short.MAX_VALUE))
        );
        panelBuscarClienteLayout.setVerticalGroup(
            panelBuscarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, panelBuscarClienteLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(panelBuscarClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelBuscarDNI, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jtfDNICliente, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(btnBuscar)
                .addGap(112, 112, 112))
        );

        panelEmpleado.add(panelBuscarCliente, java.awt.BorderLayout.CENTER);

        btnNuevoCliente.setText("Nuevo Cliente");
        btnNuevoCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNuevoClienteActionPerformed(evt);
            }
        });
        botonesClientes.add(btnNuevoCliente);

        panelEmpleado.add(botonesClientes, java.awt.BorderLayout.SOUTH);

        jLabelDatos.setText("Datos del cliente:");
        jLabelDatos.setToolTipText("");

        tablaDatosCliente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "DNI", "NOMBRE", "APELLIDO1", "APELLIDO2"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tablaDatosCliente);

        btnModificarDatosCliente.setText("Modificar...");
        btnModificarDatosCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarDatosClienteActionPerformed(evt);
            }
        });

        jLabelProductosContratados.setText("Productos contratados:");

        tablaProductos.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane3.setViewportView(tablaProductos);

        btnContratar.setText("Contratar producto");
        btnContratar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnContratarActionPerformed(evt);
            }
        });

        btnBajaProducto.setText("Dar de baja producto");
        btnBajaProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaProductoActionPerformed(evt);
            }
        });

        btnBajaCliente.setText("Dar de baja cliente");
        btnBajaCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBajaClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout panelPosicionGlobalClienteLayout = new javax.swing.GroupLayout(panelPosicionGlobalCliente);
        panelPosicionGlobalCliente.setLayout(panelPosicionGlobalClienteLayout);
        panelPosicionGlobalClienteLayout.setHorizontalGroup(
            panelPosicionGlobalClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPosicionGlobalClienteLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(panelPosicionGlobalClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(panelPosicionGlobalClienteLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 369, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(27, 27, 27)
                        .addComponent(btnModificarDatosCliente))
                    .addComponent(jLabelProductosContratados)
                    .addComponent(jLabelDatos)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(23, Short.MAX_VALUE))
            .addGroup(panelPosicionGlobalClienteLayout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(btnContratar)
                .addGap(18, 18, 18)
                .addComponent(btnBajaProducto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnBajaCliente)
                .addGap(36, 36, 36))
        );
        panelPosicionGlobalClienteLayout.setVerticalGroup(
            panelPosicionGlobalClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(panelPosicionGlobalClienteLayout.createSequentialGroup()
                .addComponent(jLabelDatos)
                .addGap(25, 25, 25)
                .addGroup(panelPosicionGlobalClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificarDatosCliente))
                .addGap(18, 18, 18)
                .addComponent(jLabelProductosContratados)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 33, Short.MAX_VALUE)
                .addGroup(panelPosicionGlobalClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnContratar)
                    .addComponent(btnBajaProducto)
                    .addComponent(btnBajaCliente))
                .addGap(30, 30, 30))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelJefe))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelEmpleado, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelPosicionGlobalCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panelLogin, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelJefe, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(panelEmpleado, javax.swing.GroupLayout.DEFAULT_SIZE, 343, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(0, 0, Short.MAX_VALUE)
                    .addComponent(panelPosicionGlobalCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnValidarLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnValidarLoginActionPerformed

        if (VistaUtils.getInstance().comprobarJtfRellena(jtfDNIlogin)) {
            String dni = jtfDNIlogin.getText();
            String pass = new String(jtfPassLogin.getPassword());
            Info i = controlEmpleados.validate(dni, pass);
            if (i.isSuccess()) {
                empleadoActivo = dni;
                Empleado e = Parsear.getInstance().empleadoFromJson(i.getMsg());
                if (e.isJefe()) {
                    panelVisible(panelJefe);
                    inicializarTablaEmpleados();
                } else {
                    panelVisible(panelEmpleado);
                }
            } else {
                JOptionPane.showMessageDialog(this, i.getMsg(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        else {
            JOptionPane.showMessageDialog(this, Mensajes.EMPTY_FIELDS, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnValidarLoginActionPerformed

    private void btnAltaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAltaActionPerformed
        PlantillaNuevoEmpleado dialog = new PlantillaNuevoEmpleado(this, true, empleadoResumenModel);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnAltaActionPerformed

    private void btnBajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaActionPerformed
        int filaSeleccionada = tablaEmpleados.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, Mensajes.NO_ROW_SELECTED_ERROR, "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            String codEmp = (String) empleadoResumenModel.getValueAt(filaSeleccionada, 0);
            if (JOptionPane.showConfirmDialog(this, String.format(Mensajes.CONFIRM_DELETE_EMP, codEmp), "ATENCION", JOptionPane.YES_NO_OPTION)
                    == JOptionPane.YES_OPTION) {
                Info info = empleadoResumenModel.deleteEmpleado(filaSeleccionada);
                if (!info.isSuccess()) {
                    JOptionPane.showMessageDialog(this, info.getMsg(), "ERROR", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }//GEN-LAST:event_btnBajaActionPerformed

    private void btnNuevoClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoClienteActionPerformed
        PlantillaNuevoCliente dialog = new PlantillaNuevoCliente(this, true, controlClientes, controlEmpleados, false, clienteActual, productosContratadosModel);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnNuevoClienteActionPerformed

    private void btnBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarActionPerformed
        if (VistaUtils.getInstance().comprobarJtfRellena(jtfDNICliente)) {
            String dni = jtfDNICliente.getText();
            Info info = controlClientes.buscarClienteConDni(dni);
            if (info.isSuccess()) {
                clienteActual = Parsear.getInstance().clienteFromJson(info.getMsg());
                ((DefaultTableModel) tablaDatosCliente.getModel()).addRow(new Object[]{clienteActual.getDni(), clienteActual.getNombre(), clienteActual.getApellido1(), clienteActual.getApellido2()});
                inicializarTablaProductos();
                panelVisible(panelPosicionGlobalCliente);
            } else {
                JOptionPane.showMessageDialog(this, info.getMsg(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, Mensajes.NO_DNI, "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_btnBuscarActionPerformed

    private void btnModificarDatosClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarDatosClienteActionPerformed
        PlantillaNuevoCliente dialog = new PlantillaNuevoCliente(this, true, controlClientes, controlEmpleados, true, clienteActual, productosContratadosModel);
        dialog.setVisible(true);
    }//GEN-LAST:event_btnModificarDatosClienteActionPerformed

    private void btnContratarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnContratarActionPerformed
        PlantillaNuevoProducto plantilla = new PlantillaNuevoProducto(this, true);
        plantilla.setVisible(true);
        TipoProducto tipo = plantilla.getTipo();
        if (tipo != null) {
            Info i = null;
            switch (tipo) {
                case TARJETA:
                    i = productosContratadosModel.addTarjeta();
                    break;
                case CUENTA_AHORRO:
                    i = productosContratadosModel.addCAhorro(plantilla.getRemuneracion());
                    System.out.println("Cuenta ahorro");
                    break;
                case CUENTA_NOMINA:
                    i = productosContratadosModel.addCNomina();
                    System.out.println("Cuenta nómina");
                    break;
            }
            if (i.isSuccess()) {
                JOptionPane.showMessageDialog(this, i.getMsg(), "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
                controlEmpleados.anotarProductoVendido(empleadoActivo);
            } else {
                JOptionPane.showMessageDialog(this, i.getMsg(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnContratarActionPerformed

    private void btnBajaClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaClienteActionPerformed
        Info i = controlClientes.deleteClienteConDni(clienteActual);
        if (i.isSuccess()) {
            JOptionPane.showMessageDialog(this, i.getMsg(), "SUCCESS", JOptionPane.OK_OPTION);
            panelVisible(panelEmpleado);
        } else {
            JOptionPane.showMessageDialog(this, i.getMsg(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnBajaClienteActionPerformed

    private void btnBajaProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBajaProductoActionPerformed
        int filaSeleccionada = tablaProductos.getSelectedRow();
        if (filaSeleccionada == -1) {
            JOptionPane.showMessageDialog(this, "No ha seleccionado ningún producto", "ERROR", JOptionPane.ERROR_MESSAGE);
        } else {
            Info i = productosContratadosModel.removeProducto(filaSeleccionada);
            if (i.isSuccess()) {
                JOptionPane.showMessageDialog(this, i.getMsg(), "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, i.getMsg(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnBajaProductoActionPerformed

    private void ascenderItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ascenderItemActionPerformed
        Info i = empleadoResumenModel.ascenderEmpleadoAtRow(tablaEmpleados.getSelectedRow());
        if (i.isSuccess()) {
            JOptionPane.showMessageDialog(this, i.getMsg(), "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, i.getMsg(), "ERROR", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_ascenderItemActionPerformed

    private void btnModificarInteresActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarInteresActionPerformed
        double importe = VistaUtils.getInstance().comprobarDoubleJtf(jtfInteresCA);
        if (importe != -1){
            Info i = controlProductos.modificarTiposCA(importe);
            if (i.isSuccess()){
                JOptionPane.showMessageDialog(this, i.getMsg(), "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                JOptionPane.showMessageDialog(this, i.getMsg(), "ERROR", JOptionPane.ERROR_MESSAGE);
            }
        }
        else{
            JOptionPane.showMessageDialog(this, Mensajes.NUMERIC_FIELD, "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarInteresActionPerformed

    private void jtfDNIloginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jtfDNIloginActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jtfDNIloginActionPerformed

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
            java.util.logging.Logger.getLogger(GPLBankEmpleadosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GPLBankEmpleadosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GPLBankEmpleadosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GPLBankEmpleadosGUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new GPLBankEmpleadosGUI().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuItem ascenderItem;
    private javax.swing.JPopupMenu ascenderPopup;
    private javax.swing.JPanel botonesClientes;
    private javax.swing.JPanel botonesEmpleados;
    private javax.swing.JButton btnAlta;
    private javax.swing.JButton btnBaja;
    private javax.swing.JButton btnBajaCliente;
    private javax.swing.JButton btnBajaProducto;
    private javax.swing.JButton btnBuscar;
    private javax.swing.JButton btnContratar;
    private javax.swing.JButton btnModificarDatosCliente;
    private javax.swing.JButton btnModificarInteres;
    private javax.swing.JButton btnNuevoCliente;
    private javax.swing.JButton btnValidarLogin;
    private javax.swing.JLabel jLabelBuscarDNI;
    private javax.swing.JLabel jLabelDatos;
    private javax.swing.JLabel jLabelICA;
    private javax.swing.JLabel jLabelListadoEmpleados;
    private javax.swing.JLabel jLabelPercentaje;
    private javax.swing.JLabel jLabelProductosContratados;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField jtfDNICliente;
    private javax.swing.JTextField jtfDNIlogin;
    private javax.swing.JTextField jtfInteresCA;
    private javax.swing.JPasswordField jtfPassLogin;
    private javax.swing.JLabel labelDNIlogin;
    private javax.swing.JLabel labelPassLogin;
    private javax.swing.JPanel panelBuscarCliente;
    private javax.swing.JPanel panelEmpleado;
    private javax.swing.JPanel panelFieldDNI;
    private javax.swing.JPanel panelFieldPassword;
    private javax.swing.JTabbedPane panelJefe;
    private javax.swing.JPanel panelLogin;
    private javax.swing.JPanel panelLoginBotones;
    private javax.swing.JPanel panelLoginDatos;
    private javax.swing.JPanel panelLoginFields;
    private javax.swing.JPanel panelPosicionGlobalCliente;
    private javax.swing.JPanel tabEmpleados;
    private javax.swing.JPanel tabGestion;
    private javax.swing.JTable tablaDatosCliente;
    private javax.swing.JTable tablaEmpleados;
    private javax.swing.JTable tablaProductos;
    // End of variables declaration//GEN-END:variables
}