package supermercado.ventanas;

import supermercado.Producto;
import supermercado.Seccion;
import supermercado.Supermercado;
import Excepciones.ProductoNoEncontradoException;

import javax.swing.*;
import java.awt.*;

public class VentanaProductos extends javax.swing.JFrame {

    private Supermercado supermercado;

    private JButton jButton1; // Stock Actual
    private JButton jButton2; // Agregar Producto
    private JButton jButton3; // Eliminar Producto
    private JButton jButton4; // Buscar por código
    private JButton jButton5; // Actualizar Stock
    private JTextArea textAreaResultado;

    public VentanaProductos(Supermercado supermercado) {
        this.supermercado = supermercado;
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestión de Productos");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JLabel titulo = new JLabel("Gestión de Productos", SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 22));

        jButton1 = new JButton("Stock Actual");
        jButton2 = new JButton("Agregar Producto");
        jButton3 = new JButton("Eliminar Producto");
        jButton4 = new JButton("Buscar por código");
        jButton5 = new JButton("Actualizar Stock");

        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 5, 5));
        panelBotones.add(jButton1);
        panelBotones.add(jButton2);
        panelBotones.add(jButton3);
        panelBotones.add(jButton4);
        panelBotones.add(jButton5);

        textAreaResultado = new JTextArea();
        textAreaResultado.setEditable(false);
        textAreaResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(textAreaResultado);

        setLayout(new BorderLayout(10, 10));
        add(titulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);
        add(scroll, BorderLayout.CENTER);

        jButton1.addActionListener(e -> mostrarStockActual());
        jButton2.addActionListener(e -> agregarProducto());
        jButton3.addActionListener(e -> eliminarProducto());
        jButton4.addActionListener(e -> buscarProducto());
        jButton5.addActionListener(e -> actualizarStock());

        mostrarStockActual();
    }

    private void mostrarStockActual() {
        StringBuilder sb = new StringBuilder();
        if (supermercado.getSecciones().isEmpty()) {
            sb.append("No hay secciones registradas.\n");
        } else {
            for (Seccion s : supermercado.getSecciones().values()) {
                sb.append("== SECCIÓN: ").append(s.getNombre())
                  .append(" (").append(s.getCodigo()).append(") ==\n");
                if (s.getProductos().isEmpty()) {
                    sb.append("  (Sin productos)\n");
                } else {
                    for (Producto p : s.getProductos().values()) {
                        sb.append("  ").append(p.toString()).append("\n");
                    }
                }
                sb.append("\n");
            }
        }
        textAreaResultado.setText(sb.toString());
    }

    private void agregarProducto() {
        String codSeccion = JOptionPane.showInputDialog(this, "Código de la sección destino:");
        if (codSeccion == null) return;

        Seccion s = supermercado.buscarSeccion(codSeccion.trim());
        if (s == null) {
            JOptionPane.showMessageDialog(this, "La sección no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String codProd = JOptionPane.showInputDialog(this, "Código del producto:");
            if (codProd == null) return;

            if (s.buscarProducto(codProd.trim()) != null) {
                JOptionPane.showMessageDialog(this, "Ya existe un producto con ese código en esta sección.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String nombre = JOptionPane.showInputDialog(this, "Nombre del producto:");
            if (nombre == null) return;

            double precio = Double.parseDouble(JOptionPane.showInputDialog(this, "Precio:"));
            int stock = Integer.parseInt(JOptionPane.showInputDialog(this, "Stock inicial:"));
            int stockMinimo = Integer.parseInt(JOptionPane.showInputDialog(this, "Stock mínimo:"));
            int puntoReorden = Integer.parseInt(JOptionPane.showInputDialog(this, "Punto de reorden:"));

            Producto p = new Producto(codProd.trim(), nombre, precio, stock, stockMinimo, puntoReorden);
            s.agregarProducto(p);

            JOptionPane.showMessageDialog(this, "¡Producto agregado con éxito!");
            mostrarStockActual();

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese valores numéricos válidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void eliminarProducto() {
        String codigo = JOptionPane.showInputDialog(this, "Código del producto a eliminar:");
        if (codigo == null) return;

        try {
            supermercado.eliminarProducto(codigo.trim());
            JOptionPane.showMessageDialog(this, "¡Producto eliminado con éxito!");
            mostrarStockActual();
        } catch (ProductoNoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Excepción", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void buscarProducto() {
        String codigo = JOptionPane.showInputDialog(this, "Código del producto a buscar:");
        if (codigo == null) return;

        try {
            Producto p = supermercado.buscarProducto(codigo.trim());
            JOptionPane.showMessageDialog(this,
                    p.toString() + "\nStock Mínimo: " + p.getStockMinimo() + "\nPunto Reorden: " + p.getPuntoReOrden(),
                    "Producto encontrado", JOptionPane.INFORMATION_MESSAGE);
        } catch (ProductoNoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Excepción", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void actualizarStock() {
        String codigo = JOptionPane.showInputDialog(this, "Código del producto:");
        if (codigo == null) return;

        try {
            Producto p = supermercado.buscarProducto(codigo.trim());

            String cantStr = JOptionPane.showInputDialog(this, "Cantidad a ingresar:");
            if (cantStr == null) return;
            int cantidad = Integer.parseInt(cantStr);

            String motivo = JOptionPane.showInputDialog(this, "Motivo (opcional, dejar en blanco para omitir):");

            if (motivo == null || motivo.trim().isEmpty()) {
                p.aumentarStock(cantidad);
            } else {
                p.aumentarStock(cantidad, motivo);
            }

            JOptionPane.showMessageDialog(this, "Stock actualizado. Nuevo stock: " + p.getStock());
            mostrarStockActual();

        } catch (ProductoNoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Excepción", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un número válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}