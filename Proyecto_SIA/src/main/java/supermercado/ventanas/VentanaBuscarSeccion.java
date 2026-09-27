package supermercado.ventanas;

import supermercado.Seccion;
import supermercado.Supermercado;

import javax.swing.*;
import java.awt.*;

public class VentanaBuscarSeccion extends javax.swing.JFrame {

    private Supermercado supermercado;

    private JTextField campoCodigo;
    private JButton jButtonBuscar;
    private JTextArea textAreaResultado;

    public VentanaBuscarSeccion(Supermercado supermercado) {
        this.supermercado = supermercado;
        initComponents();
    }

    private void initComponents() {
        setTitle("Buscar Sección");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        JLabel titulo = new JLabel("Buscar Sección", SwingConstants.CENTER);
        titulo.setFont(new Font("Tahoma", Font.BOLD, 20));

        JPanel panelBusqueda = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBusqueda.add(new JLabel("Código de sección:"));
        campoCodigo = new JTextField(15);
        panelBusqueda.add(campoCodigo);
        jButtonBuscar = new JButton("Buscar");
        panelBusqueda.add(jButtonBuscar);

        textAreaResultado = new JTextArea();
        textAreaResultado.setEditable(false);
        textAreaResultado.setFont(new Font("Monospaced", Font.PLAIN, 13));
        JScrollPane scroll = new JScrollPane(textAreaResultado);

        setLayout(new BorderLayout(10, 10));
        add(titulo, BorderLayout.NORTH);
        add(panelBusqueda, BorderLayout.PAGE_START);
        add(scroll, BorderLayout.CENTER);

        jButtonBuscar.addActionListener(e -> buscarSeccion());
        campoCodigo.addActionListener(e -> buscarSeccion());
    }

    private void buscarSeccion() {
        String codigo = campoCodigo.getText().trim();
        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese un código de sección.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Seccion s = supermercado.buscarSeccion(codigo);
        if (s == null) {
            textAreaResultado.setText("No se encontró ninguna sección con código: " + codigo);
            return;
        }

        StringBuilder sb = new StringBuilder();
        sb.append("Sección encontrada:\n");
        sb.append("Código: ").append(s.getCodigo()).append("\n");
        sb.append("Nombre: ").append(s.getNombre()).append("\n");
        sb.append("Cantidad de productos: ").append(s.getProductos().size()).append("\n\n");

        if (!s.getProductos().isEmpty()) {
            sb.append("--- PRODUCTOS ---\n");
            s.getProductos().values().forEach(p -> sb.append("  ").append(p.toString()).append("\n"));
        }

        textAreaResultado.setText(sb.toString());
    }
}