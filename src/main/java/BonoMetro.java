import com.toedter.calendar.JMonthChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Interfaz para recargar el Bono de Tren del Metro de Sevilla
 * Diferenciado del Bono Bus con tonos verdes.
 * @author Tu Nombre
 * @version 1.3
 */
public class BonoMetro extends JFrame {

    // ============================
    // Colores oficiales Metro Sevilla
    // ============================
    private static final Color VERDE_CLARO = new Color(102, 204, 102);
    private static final Color VERDE_OSCURO = new Color(0, 120, 74);
    private static final Color FONDO = new Color(250, 255, 250);
    private static final Color BLANCO = Color.WHITE;
    private static final Color GRIS_OSCURO = new Color(51, 51, 51);

    // ============================
    // Componentes
    // ============================
    private JLabel lblTitulo;
    private JLabel lblInstruccion;
    private JPanel contenedorMes;
    private JMonthChooser elegirMes;
    private JButton btnRecargar;
    private JPanel contenedor;

    private final String[] nombresMeses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    // ============================
    // Constructor
    // ============================
    public BonoMetro() {
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");

        setTitle("Metro de Sevilla - Recarga Bono Tren");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(550, 550));

        initComponents();
        agregarEventos();
        pack();
        setLocationRelativeTo(null);
    }

    // ============================
    // Inicialización de Componentes
    // ============================
    private void initComponents() {
        contenedor = new JPanel(new BorderLayout(20, 20));
        contenedor.setBackground(FONDO);
        contenedor.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        contenedor.add(crearPanelSuperior(), BorderLayout.NORTH);
        contenedor.add(crearPanelCentral(), BorderLayout.CENTER);
        contenedor.add(crearPanelInferior(), BorderLayout.SOUTH);

        add(contenedor);
    }

    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(VERDE_CLARO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel iconoTren = new JLabel("🚄", SwingConstants.CENTER);
        iconoTren.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));

        lblTitulo = new JLabel("RECARGA BONO TREN", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(BLANCO);

        JLabel lblSubtitulo = new JLabel("Metro de Sevilla", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblSubtitulo.setForeground(VERDE_OSCURO);

        JPanel panelTexto = new JPanel(new GridLayout(2, 1, 0, 5));
        panelTexto.setBackground(VERDE_CLARO);
        panelTexto.add(lblTitulo);
        panelTexto.add(lblSubtitulo);

        panel.add(iconoTren, BorderLayout.WEST);
        panel.add(panelTexto, BorderLayout.CENTER);

        return panel;
    }

    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(VERDE_CLARO, 3),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        panel.setPreferredSize(new Dimension(450, 280));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- Instrucción ---
        lblInstruccion = new JLabel("Seleccione el mes para recargar su abono:");
        lblInstruccion.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblInstruccion.setForeground(GRIS_OSCURO);
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 15, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(lblInstruccion, gbc);

        // --- Selector de mes ---
        contenedorMes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        contenedorMes.setBackground(new Color(240, 255, 240));
        contenedorMes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(VERDE_OSCURO, 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        JLabel lblMes = new JLabel("Mes:");
        lblMes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMes.setForeground(VERDE_CLARO);

        elegirMes = new JMonthChooser();
        elegirMes.getComboBox().setPreferredSize(new Dimension(180, 35));
        elegirMes.getComboBox().setMinimumSize(new Dimension(180, 35));

        contenedorMes.add(lblMes);
        contenedorMes.add(elegirMes);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 10, 20, 10);
        panel.add(contenedorMes, gbc);

        // --- Información adicional ---
        JLabel lblInfo = new JLabel("Recarga válida para todo el mes seleccionado", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(100, 100, 100));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(lblInfo, gbc);

        return panel;
    }

    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panel.setBackground(FONDO);

        btnRecargar = new JButton("RECARGAR BONO TREN");
        btnRecargar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRecargar.setPreferredSize(new Dimension(280, 50));
        btnRecargar.setBackground(VERDE_CLARO);
        btnRecargar.setForeground(BLANCO);
        btnRecargar.setFocusPainted(false);
        btnRecargar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(VERDE_OSCURO, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnRecargar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRecargar.setToolTipText("Haga clic para confirmar la recarga de su bono de tren");

        // Cambio de color al pasar el ratón
        btnRecargar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRecargar.setBackground(VERDE_OSCURO);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRecargar.setBackground(VERDE_CLARO);
            }
        });

        panel.add(btnRecargar);
        return panel;
    }

    // ============================
    // Eventos
    // ============================
    private void agregarEventos() {
        btnRecargar.addActionListener(e -> validarYRecargar());

        elegirMes.addPropertyChangeListener(evt -> {
            if ("month".equals(evt.getPropertyName())) {
                int mes = elegirMes.getMonth();
                lblInstruccion.setText("Mes seleccionado: " + nombresMeses[mes] + " - ¿Desea continuar?");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int resp = JOptionPane.showConfirmDialog(
                        BonoMetro.this,
                        "¿Está seguro de que desea salir sin recargar?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (resp == JOptionPane.YES_OPTION) System.exit(0);
            }
        });
    }

    // ============================
    // Validación y recarga
    // ============================
    private void validarYRecargar() {
        int mesSeleccionado = elegirMes.getMonth();
        String nombreMes = nombresMeses[mesSeleccionado];

        int resp = JOptionPane.showConfirmDialog(
                this,
                "Tipo de abono: TREN\nMes: " + nombreMes + "\n¿Confirma la recarga?",
                "Confirmar recarga",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resp == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bono de tren recargado correctamente para " + nombreMes + ".",
                    "Recarga exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void createUIComponents() {
        elegirMes = new JMonthChooser();
    }

    // ============================
    // Main
    // ============================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BonoMetro().setVisible(true));
    }
}
