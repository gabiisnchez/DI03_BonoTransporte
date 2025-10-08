import com.toedter.calendar.JMonthChooser;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * Interfaz gráfica para recargar el Bono de Tren del Metro de Sevilla.
 * Permite al usuario seleccionar un mes y confirmar la recarga de su bonometro.
 * Diferenciado del Bono Bus con tonos verdes característicos del Metro.
 *
 * @author Gabriel Sánchez Heredia
 */
public class BonoMetro extends JFrame {

    // ============================
    // CONSTANTES DE COLORES
    // ============================
    // Paleta de colores oficiales del Metro de Sevilla
    private static final Color VERDE_CLARO = new Color(102, 204, 102);
    private static final Color VERDE_OSCURO = new Color(0, 120, 74);
    private static final Color FONDO = new Color(250, 255, 250);
    private static final Color BLANCO = Color.WHITE;
    private static final Color GRIS_OSCURO = new Color(51, 51, 51);
    private static final Color GRIS_BOTON = new Color(128, 128, 128);
    private static final Color GRIS_BOTON_HOVER = new Color(100, 100, 100);

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

    private JLabel lblTitulo;              // Título principal "RECARGA BONOMETRO"
    private JLabel lblSubtitulo;           // Subtítulo "Metro de Sevilla"
    private JLabel iconoTren;              // Icono decorativo del tren (emoji 🚄)
    private JLabel lblInstruccion;         // Etiqueta que muestra instrucciones al usuario
    private JLabel lblMes;                 // Etiqueta "Mes:" junto al selector
    private JLabel lblInfo;                // Información adicional sobre la recarga


    // --- Componentes interactivos ---

    private JMonthChooser elegirMes;       // Componente para seleccionar el mes de recarga
    private JButton btnRecargar;           // Botón principal para confirmar la recarga
    private JButton btnVolver;             // Botón para volver a la ventana de login


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
    public BonoMetro() {
        // Personalización de los botones de diálogo en español
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");

        // Configuración básica de la ventana
        setTitle("Metro de Sevilla - Recarga Bonometro");
        // Requiere confirmación para cerrar
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        // Ventana de tamaño fijo
        setResizable(false);
        // Tamaño mínimo de la ventana
        setMinimumSize(new Dimension(550, 550));

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
     * El archivo debe llamarse "metro_icon.png" y estar en la carpeta raíz o en resources.
     *
     * Formatos soportados: PNG, JPG, GIF
     */
    private void cargarIcono() {
        try {
            // Cargar icon desde la carpeta resources
            URL iconURL = getClass().getResource("/metro_icon.png");

            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                setIconImage(icon.getImage());
            } else {
                // Si no se encuentra el icono, mostrar advertencia en consola
                System.err.println("Advertencia: No se pudo cargar el icono 'metro_icon.png'");
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
     * Utiliza BorderLayout para dividir la ventana en tres secciones: superior, central e inferior.
     */
    private void initComponents() {
        // Crear el panel contenedor principal con márgenes
        contenedor = new JPanel(new BorderLayout(20, 20));
        contenedor.setBackground(FONDO);
        contenedor.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));

        // Agregar los tres paneles principales
        contenedor.add(crearPanelSuperior(), BorderLayout.NORTH);   // Encabezado con título
        contenedor.add(crearPanelCentral(), BorderLayout.CENTER);   // Área de selección de mes
        contenedor.add(crearPanelInferior(), BorderLayout.SOUTH);   // Botones de acción

        add(contenedor);
    }

    /**
     * Crea el panel superior que contiene el encabezado de la aplicación.
     * Incluye un icono de tren, el título principal y un subtítulo.
     *
     * @return JPanel configurado con el encabezado
     */
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(VERDE_CLARO);
        panel.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // Icono decorativo del tren (emoji)
        iconoTren = new JLabel();
        try {
            URL imgURL = getClass().getResource("/title_image.png");
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                // Escalar la imagen a un tamaño adecuado (por ejemplo, 80x80)
                Image imagenEscalada = icon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
                iconoTren.setIcon(new ImageIcon(imagenEscalada));
            } else {
                // Fallback si no se encuentra la imagen
                iconoTren.setText("🚄");
                iconoTren.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
                System.err.println("Advertencia: No se encontró 'title_image.png' en resources.");
            }
        } catch (Exception e) {
            iconoTren.setText("🚄");
            iconoTren.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 48));
            System.err.println("Error al cargar la imagen del título: " + e.getMessage());
        }
        iconoTren.setHorizontalAlignment(SwingConstants.CENTER);

        // Título principal en blanco y negrita
        lblTitulo = new JLabel("RECARGA BONOMETRO", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lblTitulo.setForeground(BLANCO);

        // Subtítulo con el nombre del servicio
        lblSubtitulo = new JLabel("Metro de Sevilla", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        lblSubtitulo.setForeground(VERDE_OSCURO);

        // Panel para agrupar título y subtítulo verticalmente
        panelTexto = new JPanel(new GridLayout(2, 1, 0, 5));
        panelTexto.setBackground(VERDE_CLARO);
        panelTexto.add(lblTitulo);
        panelTexto.add(lblSubtitulo);

        // Ensamblar el panel: icono a la izquierda, texto al centro
        panel.add(iconoTren, BorderLayout.WEST);
        panel.add(panelTexto, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Crea el panel central que contiene el selector de mes.
     * Es el área principal de interacción donde el usuario elige el mes de recarga.
     *
     * @return JPanel con el selector de mes y las instrucciones
     */
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(VERDE_CLARO, 3),
                BorderFactory.createEmptyBorder(30, 30, 30, 30)
        ));
        panel.setPreferredSize(new Dimension(450, 280));

        // Configuración del layout con GridBagConstraints para posicionamiento flexible
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // --- Etiqueta de instrucciones ---
        lblInstruccion = new JLabel("Seleccione el mes para recargar su bono: ");
        lblInstruccion.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblInstruccion.setForeground(GRIS_OSCURO);
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 10, 15, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;
        panel.add(lblInstruccion, gbc);

        // --- Contenedor del selector de mes ---
        contenedorMes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        contenedorMes.setBackground(new Color(240, 255, 240));  // Verde muy claro
        contenedorMes.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(VERDE_OSCURO, 2),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // Etiqueta "Mes:"
        lblMes = new JLabel("Mes:");
        lblMes.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblMes.setForeground(VERDE_OSCURO);

        // Selector de mes (JMonthChooser de la librería JCalendar)
        elegirMes = new JMonthChooser();
        elegirMes.getComboBox().setPreferredSize(new Dimension(180, 35));
        elegirMes.getComboBox().setMinimumSize(new Dimension(180, 35));

        contenedorMes.add(lblMes);
        contenedorMes.add(elegirMes);

        gbc.gridy = 1;
        gbc.insets = new Insets(20, 10, 20, 10);
        panel.add(contenedorMes, gbc);

        // --- Información adicional sobre la recarga ---
        lblInfo = new JLabel("Recarga válida para todo el mes seleccionado", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(100, 100, 100));
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(lblInfo, gbc);

        return panel;
    }

    /**
     * Crea el panel inferior que contiene los botones de acción.
     * Incluye el botón de volver y el botón de recarga.
     *
     * @return JPanel con los botones de navegación y confirmación
     */
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 20));
        panel.setBackground(FONDO);

        // Botón de volver a la ventana de login
        btnVolver = new JButton("VOLVER");
        btnVolver.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnVolver.setPreferredSize(new Dimension(140, 50));
        btnVolver.setBackground(GRIS_BOTON);
        btnVolver.setForeground(BLANCO);
        btnVolver.setFocusPainted(false);
        btnVolver.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_OSCURO, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnVolver.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnVolver.setToolTipText("Volver a la ventana principal de login");

        // Efecto hover para el botón volver
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

        // Botón principal de recarga
        btnRecargar = new JButton("RECARGAR BONOMETRO");
        btnRecargar.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnRecargar.setPreferredSize(new Dimension(280, 50));
        btnRecargar.setBackground(VERDE_CLARO);
        btnRecargar.setForeground(BLANCO);
        btnRecargar.setFocusPainted(false);  // Eliminar borde de foco
        btnRecargar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(VERDE_OSCURO, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnRecargar.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));  // Cursor de mano
        btnRecargar.setToolTipText("Haga clic para confirmar la recarga de su bonometro");

        // Efecto hover: cambiar color al pasar el ratón
        btnRecargar.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnRecargar.setBackground(VERDE_OSCURO);  // Oscurecer al pasar el ratón
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnRecargar.setBackground(VERDE_CLARO);   // Restaurar color original
            }
        });

        panel.add(btnVolver);
        panel.add(btnRecargar);
        return panel;
    }

    // ============================
    // GESTIÓN DE EVENTOS
    // ============================
    /**
     * Configura los listeners de eventos para los componentes interactivos.
     * Incluye el botón de recarga, el botón de volver, el selector de mes y el cierre de ventana.
     */
    private void agregarEventos() {
        // Evento del botón de volver: regresa a la ventana de login
        btnVolver.addActionListener(e -> volverALogin());

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
                        BonoMetro.this,
                        "¿Está seguro de que desea salir sin recargar?",
                        "Confirmar salida",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE
                );
                if (resp == JOptionPane.YES_OPTION) {
                    System.exit(0);  // Cerrar la aplicación
                }
            }
        });
    }

    // ============================
    // LÓGICA DE NEGOCIO
    // ============================
    /**
     * Vuelve a la ventana de login cerrando la ventana actual.
     * Cierra esta ventana y abre una nueva instancia de la ventana Login.
     */
    private void volverALogin() {
        // Cerrar la ventana actual
        dispose();

        // Crear y mostrar la ventana de Login
        SwingUtilities.invokeLater(() -> {
            login ventanaLogin = new login();
            ventanaLogin.setVisible(true);
        });
    }

    /**
     * Valída la selección del usuario y procesa la recarga del bonometro.
     * Muestra un diálogo de confirmación y, si se acepta, confirma la recarga exitosa.
     */
    private void validarYRecargar() {
        // Obtener el mes seleccionado (0 = Enero, 11 = Diciembre)
        int mesSeleccionado = elegirMes.getMonth();
        String nombreMes = nombresMeses[mesSeleccionado];

        // Mostrar diálogo de confirmación con los datos de la recarga
        int resp = JOptionPane.showConfirmDialog(
                this,
                "Tipo de bono: METRO\nMes: " + nombreMes + "\n¿Confirma la recarga?",
                "Confirmar recarga",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        // Si el usuario confirma, mostrar mensaje de éxito
        if (resp == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(
                    this,
                    "Bonometro recargado correctamente para " + nombreMes + ".",
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
            BonoMetro ventana = new BonoMetro();
            ventana.setVisible(true);
        });
    }
}