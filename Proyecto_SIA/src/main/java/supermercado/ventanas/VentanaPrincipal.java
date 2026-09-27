package supermercado.ventanas;

import supermercado.Supermercado;
import java.io.IOException;
import supermercado.LectorCSV;

public class VentanaPrincipal extends javax.swing.JFrame {

    private Supermercado supermercado;

    public VentanaPrincipal(Supermercado supermercado) {
        this.supermercado = supermercado;
        initComponents();
    }

    private void initComponents() {

        jLabelTitulo = new javax.swing.JLabel();
        jButtonProductos = new javax.swing.JButton();
        jButtonBuscarSeccion = new javax.swing.JButton();
        jButtonVentas = new javax.swing.JButton();
        jButtonGuardarSalir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("SIA - Supermercado");

        jLabelTitulo.setFont(new java.awt.Font("Tahoma", 1, 24));
        jLabelTitulo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabelTitulo.setText("Menú Principal");

        jButtonProductos.setText("Gestión de Productos");
        jButtonProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonProductosActionPerformed(evt);
            }
        });

        jButtonBuscarSeccion.setText("Buscar Sección");
        jButtonBuscarSeccion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonBuscarSeccionActionPerformed(evt);
            }
        });

        jButtonVentas.setText("Gestión de Ventas");
        jButtonVentas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonVentasActionPerformed(evt);
            }
        });

        jButtonGuardarSalir.setText("Guardar y Salir");
        jButtonGuardarSalir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarSalirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(jLabelTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jButtonProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jButtonBuscarSeccion, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jButtonVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                    .addComponent(jButtonGuardarSalir, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabelTitulo)
                .addGap(30, 30, 30)
                .addComponent(jButtonProductos)
                .addGap(15, 15, 15)
                .addComponent(jButtonBuscarSeccion)
                .addGap(15, 15, 15)
                .addComponent(jButtonVentas)
                .addGap(30, 30, 30)
                .addComponent(jButtonGuardarSalir)
                .addContainerGap(20, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }

    private void jButtonProductosActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaProductos ventana = new VentanaProductos(supermercado);
        ventana.setVisible(true);
    }

    private void jButtonBuscarSeccionActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaBuscarSeccion ventana = new VentanaBuscarSeccion(supermercado);
        ventana.setVisible(true);
    }

    private void jButtonVentasActionPerformed(java.awt.event.ActionEvent evt) {
        VentanaVentas ventana = new VentanaVentas(supermercado);
        ventana.setVisible(true);
    }

    private void jButtonGuardarSalirActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            LectorCSV.guardarDatosCSV(supermercado);
            javax.swing.JOptionPane.showMessageDialog(this, "Datos guardados exitosamente.");
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(this, "Error al guardar los datos: " + e.getMessage(),
                    "Error", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
        System.exit(0);
    }

    private javax.swing.JLabel jLabelTitulo;
    private javax.swing.JButton jButtonProductos;
    private javax.swing.JButton jButtonBuscarSeccion;
    private javax.swing.JButton jButtonVentas;
    private javax.swing.JButton jButtonGuardarSalir;
}