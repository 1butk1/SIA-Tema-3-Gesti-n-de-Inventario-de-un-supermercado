package supermercado.ventanas;

import supermercado.Seccion;
import supermercado.Supermercado;
import javax.swing.*;
import java.awt.*;

public class VentanaSecciones extends javax.swing.JFrame {

    private Supermercado supermercado;
    private JTextArea textAreaResultado;

    public VentanaSecciones(Supermercado supermercado) {
        this.supermercado = supermercado;
        initComponents();
    }

    private void initComponents() {
        setTitle("Gestión de Secciones");
        setSize(600, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JLabel titulo = new JLabel("Gestión de Secciones", SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 22));

        JButton btnAgregar = new JButton("Agregar");
        JButton btnListar = new JButton("Listar Todas");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar");

        JPanel panelBotones = new JPanel(new GridLayout(1, 5, 5, 5));
        panelBotones.add(btnAgregar);
        panelBotones.add(btnListar);
        panelBotones.add(btnEditar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);

        textAreaResultado = new JTextArea();
        textAreaResultado.setEditable(false);
        textAreaResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(textAreaResultado);

        setLayout(new BorderLayout(10, 10));
        add(titulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.SOUTH);
        add(scroll, BorderLayout.CENTER);

        btnAgregar.addActionListener(e -> agregarSeccion());
        btnListar.addActionListener(e -> listarSecciones());
        btnEditar.addActionListener(e -> editarSeccion());
        btnEliminar.addActionListener(e -> eliminarSeccion());
        btnBuscar.addActionListener(e -> buscarSeccion());
        
        listarSecciones();
    }

    private void agregarSeccion() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese código de la nueva sección:");
        if (codigo == null || codigo.trim().isEmpty()) return;

        if (supermercado.buscarSeccion(codigo.trim()) != null) {
            JOptionPane.showMessageDialog(this, "Ya existe una sección con ese código.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nombre = JOptionPane.showInputDialog(this, "Ingrese nombre de la sección:");
        if (nombre == null || nombre.trim().isEmpty()) return;

        supermercado.agregarSeccion(new Seccion(codigo.trim(), nombre.trim()));
        JOptionPane.showMessageDialog(this, "Sección agregada con éxito.");
        listarSecciones();
    }

    private void listarSecciones() {
        StringBuilder sb = new StringBuilder();
        if (supermercado.getSecciones().isEmpty()) {
            sb.append("No hay secciones registradas.");
        } else {
            sb.append("--- LISTADO DE SECCIONES ---\n\n");
            for (Seccion s : supermercado.getSecciones().values()) {
                sb.append("Código: [").append(s.getCodigo()).append("] | Nombre: ").append(s.getNombre())
                  .append(" | Cant. Productos: ").append(s.getProductos().size()).append("\n");
            }
        }
        textAreaResultado.setText(sb.toString());
    }

    private void editarSeccion() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código de la sección a editar:");
        if (codigo == null || codigo.trim().isEmpty()) return;

        Seccion s = supermercado.buscarSeccion(codigo.trim());
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Sección no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String nuevoNombre = JOptionPane.showInputDialog(this, "Nuevo nombre (Actual: " + s.getNombre() + "):");
        if (nuevoNombre != null && !nuevoNombre.trim().isEmpty()) {
            s.setNombre(nuevoNombre.trim());
            JOptionPane.showMessageDialog(this, "Sección actualizada con éxito.");
            listarSecciones();
        }
    }

    private void eliminarSeccion() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código de la sección a eliminar:");
        if (codigo == null || codigo.trim().isEmpty()) return;

        Seccion s = supermercado.buscarSeccion(codigo.trim());
        if (s == null) {
            JOptionPane.showMessageDialog(this, "Sección no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (!s.getProductos().isEmpty()) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Esta sección tiene " + s.getProductos().size() + " producto(s). ¿Eliminar de todas formas?", 
                "Confirmar Eliminación", JOptionPane.YES_NO_OPTION);
            if (confirm != JOptionPane.YES_OPTION) return;
        }

        supermercado.eliminarSeccion(codigo.trim());
        JOptionPane.showMessageDialog(this, "Sección eliminada con éxito.");
        listarSecciones();
    }

    private void buscarSeccion() {
        String codigo = JOptionPane.showInputDialog(this, "Ingrese el código de la sección:");
        if (codigo == null || codigo.trim().isEmpty()) return;

        Seccion s = supermercado.buscarSeccion(codigo.trim());
        if (s != null) {
            textAreaResultado.setText("--- SECCIÓN ENCONTRADA ---\nCódigo: " + s.getCodigo() + 
                                      "\nNombre: " + s.getNombre() + 
                                      "\nCantidad de productos: " + s.getProductos().size());
        } else {
            JOptionPane.showMessageDialog(this, "Sección no encontrada.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}