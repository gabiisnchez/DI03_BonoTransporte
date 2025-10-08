import com.toedter.calendar.JMonthChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * Interfaz gráfica para recargar el Bono Bus de TUSSAM.
 * Permite al usuario seleccionar un mes y confirmar la recarga de su bono bus.
 * Diferenciado del Bono Metro con los colores corporativos de TUSSAM.
 *
 * @author Gabriel Sánchez Heredia
 */
public class BonoBus extends JFrame {

    // ============================
    // CONSTANTES DE COLORES
    // ============================
    // Paleta de colores oficiales de TUSSAM
    private static final Color ROJO_TUSSAM = new Color(227, 6, 19);
    private static final Color ROJO_OSCURO = new Color(180, 0, 10);
    private static final Color FONDO = new Color(255, 250, 250);
    private static final Color BLANCO = Color.WHITE;
    private static final Color GRIS_OSCURO = new Color(51, 51, 51);

    // ============================
    // COMPONENTES DE LA INTERFAZ
    // ============================

    // --- Paneles principales ---

    private JPanel contenedor;             // Panel contenedor principal de la ventana (BorderLayout)
    private JPanel panelSuperior;          // Panel del encabezado con título e icono
    private JPanel panelCentral;           // Panel central con selector de mes
    private JPanel panelInferior;          // Panel inferior con botón de recarga
    private JPanel contenedorMes;          // Panel que contiene el selector de mes y su etiqueta
    private JPanel panelTexto;             // Panel para agrupar título y subtítulo


    // --- Etiquetas (JLabel) ---

    private JLabel lblTitulo;              // Título principal "RECARGA BONO BUS"
    private JLabel lblSubtitulo;           // Subtítulo "TUSSAM"
    private JLabel iconoBus;               // Icono decorativo del bus (emoji 🚌)
    private JLabel lblInstruccion;         // Etiqueta que muestra instrucciones al usuario
    private JLabel lblMes;                 // Etiqueta "Mes:" junto al selector
    private JLabel lblInfo;                // Información adicional sobre la recarga


    // --- Componentes interactivos ---

    private JMonthChooser elegirMes;       // Componente para seleccionar el mes de recarga
    private JButton btnRecargar;           // Botón principal para confirmar la recarga


    // Array con los nombres de los meses en español para mostrar al usuario
    private final String[] nombresMeses = {
            "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio",
            "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre"
    };

    // ============================
    // CONSTRUCTOR
    // ============================
    /**
     * Constructor principal que inicializa la ventana y sus componentes.
     * Configura las propiedades básicas de la ventana y carga el icono.
     */
    public BonoBus() {
        // Personalización de los botones de diálogo en español
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");

        // Configuración básica de la ventana
        setTitle("TUSSAM - Recarga Bono Bus");
        // Requiere confirmación para cerrar
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Ventana de tamaño fijo
        setResizable(false);
        // Tamaño mínimo de la ventana
        setMinimumSize(new Dimension(600, 450));

        // Cargar el icono de la ventana (debe estar en la carpeta resources o raíz del proyecto)
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
    /**
     * Carga el icono de la aplicación para mostrarlo en la barra de título y la barra de tareas.
     * El archivo debe llamarse "bus_icon.png" y estar en la carpeta raíz o en resources.
     *
     * Formatos soportados: PNG, JPG, GIF
     */
    private void cargarIcono() {
        try {
            // Cargar icon desde la carpeta resources
            URL iconURL = getClass().getResource("/bus_icon.png");

            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                setIconImage(icon.getImage());
            } else {
                // Si no se encuentra el icono, mostrar advertencia en consola
                System.err.println("Advertencia: No se pudo cargar el icono 'bus_icon.png'");
                System.err.println("Asegúrese de que el archivo existe en la carpeta resources o raíz del proyecto");
            }
        } catch (Exception e) {
            // Capturar cualquier error al cargar el icono
            System.err.println("Error al cargar el icono: " + e.getMessage());
        }
    }

    // ============================
    // INICIALIZACIÓN DE COMPONENTES
    // ============================
    /**
     * Inicializa el contenedor principal y organiza los paneles de la interfaz.
     * Utiliza GridBagLayout para una distribución vertical centralizada.
     */
    private void initComponents() {
        // Crear el panel contenedor principal con GridBagLayout
        contenedor = new JPanel(new GridBagLayout());
        contenedor.setBackground(FONDO);
        contenedor.setBorder(BorderFactory.createEmptyBorder(25, 35, 25, 35));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(10, 0, 10, 0);

        // Agregar los paneles en disposición vertical
        gbc.gridy = 0;
        contenedor.add(crearPanelSuperior(), gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 0, 20, 0);
        contenedor.add(crearPanelCentral(), gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(10, 0, 10, 0);
        contenedor.add(crearPanelInferior(), gbc);

        add(contenedor);
    }

    /**
     * Crea el panel superior que contiene el encabezado de la aplicación.
     * Incluye título, subtítulo e icono en disposición horizontal compacta.
     *
     * @return JPanel configurado con el encabezado
     */
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROJO_TUSSAM, 3),
                BorderFactory.createEmptyBorder(25, 30, 25, 30)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 10, 5, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Icono/logo de TUSSAM
        JLabel iconoBus = new JLabel();
        try {
            URL logoURL = getClass().getResource("/titulo_Tussam.png");
            if (logoURL != null) {
                ImageIcon logoIcon = new ImageIcon(logoURL);
                // Redimensionar el logo si es necesario (ajustar según tamaño deseado)
                Image img = logoIcon.getImage().getScaledInstance(300, 80, Image.SCALE_SMOOTH);
                iconoBus.setIcon(new ImageIcon(img));
            }
        } catch (Exception ex) {
            // En caso de error, no mostrar nada
        }
        iconoBus.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(iconoBus, gbc);

        // Título principal
        lblTitulo = new JLabel("RECARGA BONO BUS", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 32));
        lblTitulo.setForeground(ROJO_TUSSAM);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 10, 5, 10);
        panel.add(lblTitulo, gbc);

        // Subtítulo con el nombre del servicio
        JLabel lblSubtitulo = new JLabel("TUSSAM - Transporte Urbano de Sevilla", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(GRIS_OSCURO);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 5, 10);
        panel.add(lblSubtitulo, gbc);

        return panel;
    }

    /**
     * Crea el panel central que contiene el selector de mes.
     * Disposición vertical con todos los elementos centrados.
     *
     * @return JPanel con el selector de mes y las instrucciones
     */
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(FONDO);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // --- Etiqueta de instrucciones ---
        lblInstruccion = new JLabel("Seleccione el mes para recargar su bono:");
        lblInstruccion.setFont(new Font("Segoe UI", Font.BOLD, 15));
        lblInstruccion.setForeground(GRIS_OSCURO);
        lblInstruccion.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblInstruccion);

        panel.add(Box.createRigidArea(new Dimension(0, 25)));

        // --- Contenedor del selector de mes ---
        contenedorMes = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        contenedorMes.setBackground(BLANCO);
        contenedorMes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROJO_TUSSAM, 2),
                BorderFactory.createEmptyBorder(20, 30, 20, 30)
        ));
        contenedorMes.setMaximumSize(new Dimension(450, 100));

        // Etiqueta "Mes:"
        JLabel lblMes = new JLabel("Mes:");
        lblMes.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblMes.setForeground(ROJO_TUSSAM);

        // Selector de mes (JMonthChooser de la librería JCalendar)
        elegirMes = new JMonthChooser();
        elegirMes.getComboBox().setPreferredSize(new Dimension(200, 40));
        elegirMes.getComboBox().setFont(new Font("Segoe UI", Font.PLAIN, 14));

        contenedorMes.add(lblMes);
        contenedorMes.add(elegirMes);
        panel.add(contenedorMes);

        panel.add(Box.createRigidArea(new Dimension(0, 20)));

        // --- Información adicional sobre la recarga ---
        JLabel lblInfo = new JLabel("✓ Recarga válida para todo el mes seleccionado");
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(100, 100, 100));
        lblInfo.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(lblInfo);

        return panel;
    }

    /**
     * Crea el panel inferior que contiene el botón de recarga.
     *
     * @return JPanel con el botón de confirmación
     */
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 15));
        panel.setBackground(FONDO);

        // Botón principal de recarga
        btnRecargar = new JButton("CONFIRMAR RECARGA");
        btnRecargar.setFont(new Font("Segoe UI", Font.BOLD, 17));
        btnRecargar.setPreferredSize(new Dimension(320, 55));
        btnRecargar.setBackground(ROJO_TUSSAM);
        btnRecargar.setForeground(BLANCO);
        btnRecargar.setFocusPainted(false);
        btnRecargar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ROJO_OSCURO, 2),
                BorderFactory.createEmptyBorder(12, 25, 12, 25)
        ));
        btnRecargar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnRecargar.setToolTipText("Haga clic para confirmar la recarga de su bono bus.");

        // Efecto hover: cambiar color al pasar el ratón
        btnRecargar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRecargar.setBackground(ROJO_OSCURO);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRecargar.setBackground(ROJO_TUSSAM);
            }
        });

        panel.add(btnRecargar);
        return panel;
    }

    // ============================
    // GESTIÓN DE EVENTOS
    // ============================
    /**
     * Configura los listeners de eventos para los componentes interactivos.
     * Incluye el botón de recarga, el selector de mes y el cierre de ventana.
     */
    private void agregarEventos() {
        // Evento del botón de recarga: ejecuta la validación y confirmación
        btnRecargar.addActionListener(e -> validarYRecargar());

        // Evento del selector de mes: actualiza la etiqueta de instrucciones cuando cambia el mes
        elegirMes.addPropertyChangeListener(evt -> {
            if ("month".equals(evt.getPropertyName())) {
                int mes = elegirMes.getMonth();
                lblInstruccion.setText("Mes seleccionado: " + nombresMeses[mes] + " - ¿Desea continuar?");
            }
        });

        // Evento de cierre de ventana: pedir confirmación antes de salir
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
    /**
     * Valida la selección del usuario y procesa la recarga del bono bus.
     * Muestra un diálogo de confirmación y, si se acepta, confirma la recarga exitosa.
     */
    private void validarYRecargar() {
        // Obtener el mes seleccionado (0 = Enero, 11 = Diciembre)
        int mesSeleccionado = elegirMes.getMonth();
        String nombreMes = nombresMeses[mesSeleccionado];

        // Mostrar diálogo de confirmación con los datos de la recarga
        int resp = JOptionPane.showConfirmDialog(
                this,
                "Tipo de bono: BUS\nMes: " + nombreMes + "\n¿Confirma la recarga?",
                "Confirmar recarga",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // Si el usuario confirma, mostrar mensaje de éxito
        if (resp == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bono Bus recargado correctamente para " + nombreMes + ".",
                    "Recarga exitosa",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }

    /**
     * Metodo de inicialización personalizada de componentes.
     */
    private void createUIComponents() {
        elegirMes = new JMonthChooser();
    }

    // ============================
    // METODO PRINCIPAL
    // ============================
    /**
     * Punto de entrada de la aplicación.
     * Crea y muestra la ventana en el hilo de eventos de Swing.
     *
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        // Ejecutar la creación de la GUI en el Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            BonoBus ventana = new BonoBus();
            ventana.setVisible(true);
        });
    }
}