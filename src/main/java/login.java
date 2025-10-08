import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

/**
 * Interfaz de login para el sistema de transporte público de Sevilla.
 * Permite al usuario seleccionar entre BonoBus y BonoMetro e introducir
 * su número de tarjeta para acceder al sistema de recarga.
 *
 * @author Gabriel Sánchez Heredia
 */
public class login extends JFrame {

    // ============================
    // CONSTANTES DE COLORES
    // ============================
    private static final Color NARANJA_SEVILLA = new Color(244, 123, 32);
    private static final Color AZUL_OSCURO = new Color(0, 51, 102);
    private static final Color FONDO = new Color(245, 245, 250);
    private static final Color BLANCO = Color.WHITE;
    private static final Color GRIS_OSCURO = new Color(51, 51, 51);
    private static final Color GRIS_CLARO = new Color(200, 200, 200);

    // ============================
    // COMPONENTES DE LA INTERFAZ
    // ============================
    private JPanel contenedor;
    private JPanel panelSuperior;
    private JPanel panelCentral;
    private JPanel panelInferior;

    private JLabel lblTitulo;
    private JLabel lblSubtitulo;
    private JLabel lblImagen;
    private JLabel lblTipoBono;
    private JLabel lblNumeroTarjeta;

    private JComboBox<String> cmbTipoBono;
    private JTextField txtNumeroTarjeta;
    private JButton btnAcceder;

    // Opciones del ComboBox
    private final String[] opcionesBono = {"-- Seleccione tipo de bono --", "BonoBus (Tussam)", "BonoMetro"};

    // ============================
    // CONSTRUCTOR
    // ============================
    /**
     * Constructor principal que inicializa la ventana de login.
     */
    public login() {
        // Personalización de los botones de diálogo en español
        UIManager.put("OptionPane.okButtonText", "Aceptar");
        UIManager.put("OptionPane.yesButtonText", "Sí");
        UIManager.put("OptionPane.noButtonText", "No");

        // Configuración básica de la ventana
        setTitle("Transporte Público de Sevilla - Acceso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setMinimumSize(new Dimension(500, 550));

        // Cargar icono de la ventana
        cargarIcono();

        // Inicializar componentes y eventos
        initComponents();
        agregarEventos();

        // Ajustar tamaño y centrar
        pack();
        setLocationRelativeTo(null);
    }

    // ============================
    // CARGA DE ICONO
    // ============================
    /**
     * Carga el icono de la aplicación.
     */
    private void cargarIcono() {
        try {
            URL iconURL = getClass().getResource("/sevilla_icon.png");
            if (iconURL != null) {
                ImageIcon icon = new ImageIcon(iconURL);
                setIconImage(icon.getImage());
            } else {
                System.err.println("Advertencia: No se pudo cargar el icono 'sevilla_icon.png'");
            }
        } catch (Exception e) {
            System.err.println("Error al cargar el icono: " + e.getMessage());
        }
    }

    // ============================
    // INICIALIZACIÓN DE COMPONENTES
    // ============================
    /**
     * Inicializa todos los componentes de la interfaz.
     */
    private void initComponents() {
        contenedor = new JPanel(new BorderLayout(0, 0));
        contenedor.setBackground(FONDO);

        contenedor.add(crearPanelSuperior(), BorderLayout.NORTH);
        contenedor.add(crearPanelCentral(), BorderLayout.CENTER);
        contenedor.add(crearPanelInferior(), BorderLayout.SOUTH);

        add(contenedor);
    }

    /**
     * Crea el panel superior con el título y espacio para imagen del ayuntamiento.
     */
    private JPanel crearPanelSuperior() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(NARANJA_SEVILLA);
        panel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Panel para la imagen del ayuntamiento
        JPanel panelImagen = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelImagen.setBackground(NARANJA_SEVILLA);

        lblImagen = new JLabel();
        cargarImagenAyuntamiento();
        panelImagen.add(lblImagen);

        // Panel de texto con título y subtítulo
        JPanel panelTexto = new JPanel(new GridLayout(2, 1, 0, 8));
        panelTexto.setBackground(NARANJA_SEVILLA);

        lblTitulo = new JLabel("TRANSPORTE PÚBLICO DE SEVILLA", SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(BLANCO);

        lblSubtitulo = new JLabel("Sistema de Recarga de Bonos", SwingConstants.CENTER);
        lblSubtitulo.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        lblSubtitulo.setForeground(AZUL_OSCURO);

        panelTexto.add(lblTitulo);
        panelTexto.add(lblSubtitulo);

        panel.add(panelImagen, BorderLayout.NORTH);
        panel.add(panelTexto, BorderLayout.CENTER);

        return panel;
    }

    /**
     * Intenta cargar la imagen del ayuntamiento de Sevilla.
     */
    private void cargarImagenAyuntamiento() {
        try {
            URL imgURL = getClass().getResource("/ayuntamiento_sevilla.png");
            if (imgURL != null) {
                ImageIcon icon = new ImageIcon(imgURL);
                // Mostrar la imagen en su tamaño original
                lblImagen.setIcon(icon);
            } else {
                // Si no existe la imagen, mostrar un icono genérico
                lblImagen.setText("🏛️");
                lblImagen.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
            }
        } catch (Exception e) {
            lblImagen.setText("🏛️");
            lblImagen.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 60));
        }
    }

    /**
     * Crea el panel central con el formulario de acceso.
     */
    private JPanel crearPanelCentral() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(BLANCO);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 30, 20, 30),
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(NARANJA_SEVILLA, 3),
                        BorderFactory.createEmptyBorder(30, 30, 30, 30)
                )
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;

        // Título del formulario
        JLabel lblFormulario = new JLabel("Acceso al Sistema", SwingConstants.CENTER);
        lblFormulario.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblFormulario.setForeground(AZUL_OSCURO);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 10, 20, 10);
        panel.add(lblFormulario, gbc);

        // Etiqueta "Tipo de Bono"
        lblTipoBono = new JLabel("Tipo de Bono:");
        lblTipoBono.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTipoBono.setForeground(GRIS_OSCURO);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 5, 10);
        panel.add(lblTipoBono, gbc);

        // ComboBox de tipo de bono
        cmbTipoBono = new JComboBox<>(opcionesBono);
        cmbTipoBono.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cmbTipoBono.setPreferredSize(new Dimension(280, 35));
        cmbTipoBono.setBackground(BLANCO);
        cmbTipoBono.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_CLARO, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 10, 15, 10);
        panel.add(cmbTipoBono, gbc);

        // Etiqueta "Número de Tarjeta"
        lblNumeroTarjeta = new JLabel("Número de Tarjeta (12 dígitos):");
        lblNumeroTarjeta.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblNumeroTarjeta.setForeground(GRIS_OSCURO);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.insets = new Insets(10, 10, 5, 10);
        panel.add(lblNumeroTarjeta, gbc);

        // Campo de texto para número de tarjeta
        txtNumeroTarjeta = new JTextField();
        txtNumeroTarjeta.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        txtNumeroTarjeta.setPreferredSize(new Dimension(280, 35));
        txtNumeroTarjeta.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(GRIS_CLARO, 2),
                BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        txtNumeroTarjeta.setToolTipText("Introduzca un número de 12 dígitos");
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.insets = new Insets(0, 10, 10, 10);
        panel.add(txtNumeroTarjeta, gbc);

        // Información adicional
        JLabel lblInfo = new JLabel("<html><center>Introduzca sus datos para acceder<br>al sistema de recarga</center></html>", SwingConstants.CENTER);
        lblInfo.setFont(new Font("Segoe UI", Font.ITALIC, 12));
        lblInfo.setForeground(new Color(100, 100, 100));
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.insets = new Insets(20, 10, 10, 10);
        panel.add(lblInfo, gbc);

        return panel;
    }

    /**
     * Crea el panel inferior con el botón de acceso.
     */
    private JPanel crearPanelInferior() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 20));
        panel.setBackground(FONDO);

        btnAcceder = new JButton("ACCEDER");
        btnAcceder.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnAcceder.setPreferredSize(new Dimension(220, 45));
        btnAcceder.setBackground(NARANJA_SEVILLA);
        btnAcceder.setForeground(BLANCO);
        btnAcceder.setFocusPainted(false);
        btnAcceder.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(AZUL_OSCURO, 2),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        btnAcceder.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnAcceder.setToolTipText("Acceder al sistema de recarga");

        // Efecto hover
        btnAcceder.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnAcceder.setBackground(AZUL_OSCURO);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnAcceder.setBackground(NARANJA_SEVILLA);
            }
        });

        panel.add(btnAcceder);
        return panel;
    }

    // ============================
    // GESTIÓN DE EVENTOS
    // ============================
    /**
     * Configura los listeners de eventos.
     */
    private void agregarEventos() {
        // Evento del botón acceder
        btnAcceder.addActionListener(e -> validarYAcceder());

        // Permitir acceder con Enter en el campo de texto
        txtNumeroTarjeta.addActionListener(e -> validarYAcceder());

        // Efecto de foco en el campo de texto
        txtNumeroTarjeta.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtNumeroTarjeta.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(NARANJA_SEVILLA, 2),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtNumeroTarjeta.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(GRIS_CLARO, 2),
                        BorderFactory.createEmptyBorder(5, 10, 5, 10)
                ));
            }
        });
    }

    // ============================
    // LÓGICA DE VALIDACIÓN
    // ============================
    /**
     * Valida los datos introducidos y abre la ventana correspondiente.
     */
    private void validarYAcceder() {
        String tipoBonoSeleccionado = (String) cmbTipoBono.getSelectedItem();
        String numeroTarjeta = txtNumeroTarjeta.getText().trim();

        boolean tipoBonoValido = validarTipoBono(tipoBonoSeleccionado);
        boolean numeroTarjetaValido = validarNumeroTarjeta(numeroTarjeta);

        // Caso 1: Ambos inválidos
        if (!tipoBonoValido && !numeroTarjetaValido) {
            JOptionPane.showMessageDialog(
                    this,
                    "El tipo de bono y el número de tarjeta no son correctos.\n" +
                            "Por favor, seleccione un tipo de bono válido e introduzca un número de 12 dígitos.",
                    "Error de validación",
                    JOptionPane.ERROR_MESSAGE
            );
            return;
        }

        // Caso 2: Solo tipo de bono inválido
        if (!tipoBonoValido) {
            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un tipo de bono válido (BonoBus o BonoMetro).",
                    "Error en tipo de bono",
                    JOptionPane.ERROR_MESSAGE
            );
            cmbTipoBono.requestFocus();
            return;
        }

        // Caso 3: Solo número de tarjeta inválido
        if (!numeroTarjetaValido) {
            JOptionPane.showMessageDialog(
                    this,
                    "El número de tarjeta debe contener exactamente 12 dígitos.",
                    "Error en número de tarjeta",
                    JOptionPane.ERROR_MESSAGE
            );
            txtNumeroTarjeta.requestFocus();
            txtNumeroTarjeta.selectAll();
            return;
        }

        // Caso 4: Ambos válidos - Abrir ventana correspondiente
        abrirVentanaCorrespondiente(tipoBonoSeleccionado);
    }

    /**
     * Valida que se haya seleccionado un tipo de bono válido.
     */
    private boolean validarTipoBono(String tipoBono) {
        return tipoBono != null &&
                !tipoBono.equals(opcionesBono[0]) &&
                (tipoBono.equals("BonoBus (Tussam)") || tipoBono.equals("BonoMetro"));
    }

    /**
     * Valida que el número de tarjeta tenga exactamente 12 dígitos.
     */
    private boolean validarNumeroTarjeta(String numero) {
        return numero != null &&
                numero.matches("\\d{12}");
    }

    /**
     * Abre la ventana correspondiente según el tipo de bono seleccionado.
     */
    private void abrirVentanaCorrespondiente(String tipoBono) {
        // Ocultar la ventana de login
        this.setVisible(false);

        if (tipoBono.equals("BonoBus (Tussam)")) {
            // Abrir ventana BonoBus
            SwingUtilities.invokeLater(() -> {
                try {
                    // Aquí deberías tener la clase BonoBus creada de forma similar a BonoMetro
                    // BonoBus ventanaBus = new BonoBus();
                    // ventanaBus.setVisible(true);

                    // Por ahora mostramos un mensaje
                    JOptionPane.showMessageDialog(
                            null,
                            "Accediendo al sistema BonoBus...\n(Debe crear la clase BonoBus similar a BonoMetro)",
                            "BonoBus",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    this.setVisible(true); // Volver a mostrar el login
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error al abrir BonoBus: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    this.setVisible(true);
                }
            });
        } else if (tipoBono.equals("BonoMetro")) {
            // Abrir ventana BonoMetro
            SwingUtilities.invokeLater(() -> {
                try {
                    BonoMetro ventanaMetro = new BonoMetro();
                    ventanaMetro.setVisible(true);
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            null,
                            "Error al abrir BonoMetro: " + e.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                    this.setVisible(true);
                }
            });
        }

        // Cerrar la ventana de login después de abrir la ventana correspondiente
        this.dispose();
    }

    // ============================
    // MÉTODO PRINCIPAL
    // ============================
    /**
     * Punto de entrada de la aplicación.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            login ventana = new login();
            ventana.setVisible(true);
        });
    }
}