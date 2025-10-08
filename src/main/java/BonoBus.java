import com.toedter.calendar.JMonthChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * Interfaz gráfica para recargar el Bono Bus de Tussam (Sevilla).
 * Permite al usuario seleccionar un mes y confirmar la recarga de su bonobus.
 * Diseño con paleta de colores corporativos de Tussam (naranja y rojo).
 *
 * @author Alejandro Martínez Bou
 */
public class BonoBus extends JFrame {

    // ============================
    // CONSTANTES DE COLORES - PALETA TUSSAM
    // ============================
    private static final Color NARANJA_TUSSAM = new Color(255, 140, 0);
    private static final Color ROJO_TUSSAM = new Color(220, 50, 50);
    private static final Color NARANJA_CLARO = new Color(255, 180, 100);
    private static final Color FONDO = new Color(255, 250, 245);
    private static final Color BLANCO = Color.WHITE;
    private static final Color GRIS_OSCURO = new Color(51, 51, 51);
    private static final Color GRIS_BOTON = new Color(128, 128, 128);
    private static final Color GRIS_BOTON_HOVER = new Color(100, 100, 100);
    private static final Color CREMA = new Color(255, 245, 230);

    // ============================
    // COMPONENTES DE LA INTERFAZ
    // ============================

    // --- Paneles principales ---
    private JPanel contenedor;
    private JPanel panelIzquierdo;
    private JPanel panelDerecho;
    private JPanel panelHeader;
    private JPanel panelSelector;
    private JPanel panelBotones;
    private JPanel panelInfo;

    // --- Etiquetas (JLabel) ---
    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel iconoBus;
    private JLabel lblInstruccion;
    private JLabel lblMes;
    private JLabel lblInfo;
    private JLabel lblDias;
    private JLabel lblCalendario;

    // --- Componentes interactivos ---
    private JMonthChooser elegirMes;
    private JButton btnRecargar;
    private JButton btnVolver;

    // Array con los nombres de los meses en español
    private final String[] nombresMeses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    // ============================
    // CONSTRUCTOR
    // ============================
    public BonoBus() {
        // Personalización de los botones de diálogo en español
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");

        // Configuración básica de la ventana
        setTitle("Tussam - Recarga BonoBus");
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setMinimumSize(new Dimension(700, 500));

        // Cargar el icono de la ventana
        cargarIcono();

        // Inicializar componentes visuales y eventos
        initComponents();
        agregarEventos();

        // Ajustar tamaño y centrar en pantalla
        pack();
        setLocationRelativeTo(null);
    }

    // ============================
    // CARGA DE ICONO
    // ============================
    private void cargarIcono() {
        try {
            URL iconURL = getClass().getResource("/bus_icon.png");

            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                setIconImage(icon.getImage());
            } else {
                System.err.println("Advertencia: No se pudo cargar el icono 'bus_icon.png'");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el icono: " + e.getMessage());
        }
    }

    // ============================
    // INICIALIZACIÓN DE COMPONENTES
    // ============================
    private void initComponents() {
        // Panel contenedor principal con diseño horizontal
        contenedor = new JPanel(new BorderLayout());
        contenedor.setBackground(FONDO);

        // Crear y añadir los paneles
        contenedor.add(crearPanelHeader(), BorderLayout.NORTH);

        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 0, 0));
        panelCentral.setBackground(FONDO);
        panelCentral.add(crearPanelIzquierdo());
        panelCentral.add(crearPanelDerecho());

        contenedor.add(panelCentral, BorderLayout.CENTER);
        contenedor.add(crearPanelBotones(), BorderLayout.SOUTH);

        add(contenedor);
    }

    /**
     * Crea el panel del encabezado superior
     */
    private JPanel crearPanelHeader() {
        panelHeader = new JPanel();
        panelHeader.setLayout(new BoxLayout(panelHeader, BoxLayout.Y_AXIS));
        panelHeader.setBackground(ROJO_TUSSAM);
        panelHeader.setBorder(BorderFactory.createEmptyBorder(25, 30, 25, 30));

        // Título principal
        lblTitulo = new JLabel("RECARGA BONOBUS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(BLANCO);
        lblTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Subtítulo
        lblSubtitulo = new JLabel("Transportes Urbanos de Sevilla - TUSSAM");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(NARANJA_CLARO);
        lblSubtitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelHeader.add(lblTitulo);
        panelHeader.add(Box.createVerticalStrut(8));
        panelHeader.add(lblSubtitulo);

        return panelHeader;
    }

    /**
     * Crea el panel izquierdo con el icono y la información
     */
    private JPanel crearPanelIzquierdo() {
        panelIzquierdo = new JPanel();
        panelIzquierdo.setLayout(new BoxLayout(panelIzquierdo, BoxLayout.Y_AXIS));
        panelIzquierdo.setBackground(NARANJA_TUSSAM);
        panelIzquierdo.setBorder(BorderFactory.createEmptyBorder(40, 30, 40, 30));

        // Icono del bus
        iconoBus = new JLabel();
        try {
            URL imgURL = getClass().getResource("/bus_image.png");
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                Image imagenEscalada = icon.getImage().getScaledInstance(120, 120, Image.SCALE_SMOOTH);
                iconoBus.setIcon(new ImageIcon(imagenEscalada));
            } else {
                iconoBus.setText("🚌");
                iconoBus.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
            }
        } catch (Exception e) {
            iconoBus.setText("🚌");
            iconoBus.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 80));
        }
        iconoBus.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel de información adicional
        panelInfo = new JPanel();
        panelInfo.setLayout(new BoxLayout(panelInfo, BoxLayout.Y_AXIS));
        panelInfo.setBackground(CREMA);
        panelInfo.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROJO_TUSSAM, 2),
                BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        panelInfo.setMaximumSize(new Dimension(280, 200));

        JLabel lblInfoTitulo = new JLabel("Información del Bono");
        lblInfoTitulo.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblInfoTitulo.setForeground(ROJO_TUSSAM);
        lblInfoTitulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblDias = new JLabel("30 días naturales");
        lblDias.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblDias.setForeground(GRIS_OSCURO);
        lblDias.setAlignmentX(Component.CENTER_ALIGNMENT);

        lblInfo = new JLabel("Viajes ilimitados en todas las líneas");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(80, 80, 80));
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelInfo.add(lblInfoTitulo);
        panelInfo.add(Box.createVerticalStrut(15));
        panelInfo.add(lblDias);
        panelInfo.add(Box.createVerticalStrut(10));
        panelInfo.add(lblInfo);

        panelIzquierdo.add(Box.createVerticalGlue());
        panelIzquierdo.add(iconoBus);
        panelIzquierdo.add(Box.createVerticalStrut(30));
        panelIzquierdo.add(panelInfo);
        panelIzquierdo.add(Box.createVerticalGlue());

        return panelIzquierdo;
    }

    /**
     * Crea el panel derecho con el selector de mes
     */
    private JPanel crearPanelDerecho() {
        panelDerecho = new JPanel();
        panelDerecho.setLayout(new BoxLayout(panelDerecho, BoxLayout.Y_AXIS));
        panelDerecho.setBackground(BLANCO);
        panelDerecho.setBorder(BorderFactory.createEmptyBorder(50, 40, 50, 40));

        // Emoticono de calendario
        lblCalendario = new JLabel("📅");
        lblCalendario.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
        lblCalendario.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Instrucción
        lblInstruccion = new JLabel("Seleccione el mes para su recarga:");
        lblInstruccion.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblInstruccion.setForeground(ROJO_TUSSAM);
        lblInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);

        // Panel selector de mes
        panelSelector = new JPanel();
        panelSelector.setLayout(new BoxLayout(panelSelector, BoxLayout.Y_AXIS));
        panelSelector.setBackground(CREMA);
        panelSelector.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(NARANJA_TUSSAM, 3),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        panelSelector.setMaximumSize(new Dimension(300, 180));

        lblMes = new JLabel("MES:");
        lblMes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMes.setForeground(NARANJA_TUSSAM);
        lblMes.setAlignmentX(Component.CENTER_ALIGNMENT);

        elegirMes = new JMonthChooser();
        elegirMes.getComboBox().setPreferredSize(new Dimension(200, 40));
        elegirMes.getComboBox().setMaximumSize(new Dimension(200, 40));
        elegirMes.setAlignmentX(Component.CENTER_ALIGNMENT);

        panelSelector.add(lblMes);
        panelSelector.add(Box.createVerticalStrut(15));
        panelSelector.add(elegirMes);

        panelDerecho.add(Box.createVerticalGlue());
        panelDerecho.add(lblCalendario);
        panelDerecho.add(Box.createVerticalStrut(20));
        panelDerecho.add(lblInstruccion);
        panelDerecho.add(Box.createVerticalStrut(40));
        panelDerecho.add(panelSelector);
        panelDerecho.add(Box.createVerticalGlue());

        return panelDerecho;
    }

    /**
     * Crea el panel inferior con los botones
     */
    private JPanel crearPanelBotones() {
        panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 25));
        panelBotones.setBackground(FONDO);

        // Botón volver
        btnVolver = new JButton("VOLVER");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setPreferredSize(new Dimension(150, 50));
        btnVolver.setBackground(GRIS_BOTON);
        btnVolver.setForeground(BLANCO);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createLineBorder(GRIS_OSCURO, 2));
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.setToolTipText("Volver a la ventana principal de login");

        btnVolver.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnVolver.setBackground(GRIS_BOTON_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnVolver.setBackground(GRIS_BOTON);
            }
        });

        // Botón recargar
        btnRecargar = new JButton("RECARGAR BONOBUS");
        btnRecargar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRecargar.setPreferredSize(new Dimension(300, 50));
        btnRecargar.setBackground(NARANJA_TUSSAM);
        btnRecargar.setForeground(BLANCO);
        btnRecargar.setFocusPainted(false);
        btnRecargar.setBorder(BorderFactory.createLineBorder(ROJO_TUSSAM, 3));
        btnRecargar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRecargar.setToolTipText("Haga clic para confirmar la recarga de su bonobus");

        btnRecargar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRecargar.setBackground(ROJO_TUSSAM);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRecargar.setBackground(NARANJA_TUSSAM);
            }
        });

        panelBotones.add(btnVolver);
        panelBotones.add(btnRecargar);

        return panelBotones;
    }

    // ============================
    // GESTIÓN DE EVENTOS
    // ============================
    private void agregarEventos() {
        btnVolver.addActionListener(e -> volverALogin());
        btnRecargar.addActionListener(e -> validarYRecargar());

        elegirMes.addPropertyChangeListener(evt -> {
            if ("month".equals(evt.getPropertyName())) {
                int mes = elegirMes.getMonth();
                lblInstruccion.setText("Mes seleccionado: " + nombresMeses[mes]);
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int resp = JOptionPane.showConfirmDialog(
                        BonoBus.this,
                        "¿Está seguro de que desea salir sin recargar?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (resp == JOptionPane.YES_OPTION) {
                    System.exit(0);
                }
            }
        });
    }

    // ============================
    // LÓGICA DE NEGOCIO
    // ============================
    private void volverALogin() {
        dispose();

        SwingUtilities.invokeLater(() -> {
            login ventanaLogin = new login();
            ventanaLogin.setVisible(true);
        });
    }

    private void validarYRecargar() {
        int mesSeleccionado = elegirMes.getMonth();
        String nombreMes = nombresMeses[mesSeleccionado];

        int resp = JOptionPane.showConfirmDialog(
                this,
                "Tipo de bono: BUS\nMes: " + nombreMes + "\n¿Confirma la recarga?",
                "Confirmar recarga",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resp == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    this,
                    "BonoBus recargado correctamente para " + nombreMes + ".",
                    "Recarga exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    private void createUIComponents() {
        elegirMes = new JMonthChooser();
    }

    // ============================
    // METODO PRINCIPAL
    // ============================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BonoBus ventana = new BonoBus();
            ventana.setVisible(true);
        });
    }
}