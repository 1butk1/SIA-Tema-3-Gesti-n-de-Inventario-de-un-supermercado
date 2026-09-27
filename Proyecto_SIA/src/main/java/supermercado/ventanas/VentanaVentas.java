package supermercado.ventanas;

import supermercado.Producto;
import supermercado.Seccion;
import supermercado.Supermercado;
import supermercado.Venta;
import Excepciones.ProductoNoEncontradoException;
import Excepciones.StockInsuficienteException;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class VentanaVentas extends javax.swing.JFrame {

    private Supermercado supermercado;
    private Venta ventaActual;
    private HashMap<String, Producto> inventarioGlobal;

    private JTextArea textAreaCarrito;
    private JButton jButtonAgregar;
    private JButton jButtonConfirmar;
    private JButton jButtonCancelar;
    private JLabel labelTotal;

    public VentanaVentas(Supermercado supermercado) {
        this.supermercado = supermercado;
        initComponents();
        iniciarNuevaVenta();
    }

    private void initComponents() {
        setTitle("Gestión de Ventas");
        setSize(600, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JLabel titulo = new JLabel("Gestión de Ventas", SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 22));

        textAreaCarrito = new JTextArea();
        textAreaCarrito.setEditable(false);
        textAreaCarrito.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(textAreaCarrito);

        jButtonAgregar = new JButton("Agregar Producto al Carrito");
        jButtonConfirmar = new JButton("Confirmar Venta");
        jButtonCancelar = new JButton("Cancelar Venta");

        labelTotal = new JLabel("Total: $0.0", SwingConstants.CENTER);
        labelTotal.setFont(new Font("Tahoma", Font.BOLD, 16));

        JPanel panelBotones = new JPanel(new GridLayout(1, 3, 5, 5));
        panelBotones.add(jButtonAgregar);
        panelBotones.add(jButtonConfirmar);
        panelBotones.add(jButtonCancelar);

        JPanel panelSur = new JPanel(new BorderLayout(5, 5));
        panelSur.add(labelTotal, BorderLayout.NORTH);
        panelSur.add(panelBotones, BorderLayout.SOUTH);

        setLayout(new BorderLayout(10, 10));
        add(titulo, BorderLayout.NORTH);
        add(scroll, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

        jButtonAgregar.addActionListener(e -> agregarProductoAlCarrito());
        jButtonConfirmar.addActionListener(e -> confirmarVenta());
        jButtonCancelar.addActionListener(e -> iniciarNuevaVenta());
    }

    private void iniciarNuevaVenta() {
        String idVenta = "V-" + System.currentTimeMillis();
        String fecha = java.time.LocalDate.now().toString();
        ventaActual = new Venta(idVenta, fecha);

        inventarioGlobal = new HashMap<>();
        for (Seccion s : supermercado.getSecciones().values()) {
            inventarioGlobal.putAll(s.getProductos());
        }

        actualizarVistaCarrito();
    }

    private void actualizarVistaCarrito() {
        StringBuilder sb = new StringBuilder();
        sb.append("Venta: ").append(ventaActual.getIdVenta()).append("\n");
        sb.append("Fecha: ").append(ventaActual.getFecha()).append("\n\n");
        sb.append("--- CARRITO ---\n");

        if (ventaActual.getProductosVendidos().isEmpty()) {
            sb.append("(vacío)\n");
        } else {
            for (String codigo : ventaActual.getProductosVendidos().keySet()) {
                Producto p = inventarioGlobal.get(codigo);
                int cantidad = ventaActual.getProductosVendidos().get(codigo);
                String nombre = (p != null) ? p.getNombre() : codigo;
                sb.append("  ").append(nombre).append(" x").append(cantidad).append("\n");
            }
        }

        textAreaCarrito.setText(sb.toString());
        double total = ventaActual.calcularTotal(inventarioGlobal);
        labelTotal.setText("Total: $" + total);
    }

    private void agregarProductoAlCarrito() {
        String codigo = JOptionPane.showInputDialog(this, "Código del producto:");
        if (codigo == null) return;

        try {
            Producto p = supermercado.buscarProducto(codigo.trim());
            int yaEnCarrito = ventaActual.getProductosVendidos().getOrDefault(codigo.trim(), 0);
            int disponibleReal = p.getStock() - yaEnCarrito;

            String cantStr = JOptionPane.showInputDialog(this,
                    "Producto: " + p.getNombre() + " | Disponible: " + disponibleReal + "\nCantidad a llevar:");
            if (cantStr == null) return;

            int cantidad = Integer.parseInt(cantStr);
            if (cantidad <= 0) {
                JOptionPane.showMessageDialog(this, "La cantidad debe ser mayor a 0.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (cantidad > disponibleReal) {
                JOptionPane.showMessageDialog(this, "Stock insuficiente. Disponible: " + disponibleReal, "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            ventaActual.agregarProducto(p, cantidad);
            actualizarVistaCarrito();

        } catch (ProductoNoEncontradoException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Excepción", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un valor numérico válido.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void confirmarVenta() {
        if (ventaActual.getProductosVendidos().isEmpty()) {
            JOptionPane.showMessageDialog(this, "El carrito está vacío.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int confirmar = JOptionPane.showConfirmDialog(this,
                "Total a pagar: $" + ventaActual.calcularTotal(inventarioGlobal) + "\n¿Confirmar compra y descontar inventario?",
                "Confirmar Venta", JOptionPane.YES_NO_OPTION);

        if (confirmar != JOptionPane.YES_OPTION) return;

        try {
            if (ventaActual.procesarVenta(inventarioGlobal)) {
                StringBuilder alertas = new StringBuilder();
                for (String cod : ventaActual.getProductosVendidos().keySet()) {
                    Producto prod = inventarioGlobal.get(cod);
                    if (prod != null && prod.requiereRebastecimiento()) {
                        alertas.append("- ").append(prod.getNombre()).append("\n");
                    }
                }

                String mensaje = "¡Venta " + ventaActual.getIdVenta() + " procesada con éxito!";
                if (alertas.length() > 0) {
                    mensaje += "\n\n[ALERTA STOCK] Requieren reabastecimiento:\n" + alertas;
                }
                JOptionPane.showMessageDialog(this, mensaje);

                iniciarNuevaVenta();
            } else {
                JOptionPane.showMessageDialog(this, "No se pudo completar la transacción.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (StockInsuficienteException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error al procesar venta", JOptionPane.ERROR_MESSAGE);
        }
    }
}