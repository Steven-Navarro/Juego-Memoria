/**
 * Clase Memory - Representa la interfaz gráfica del juego de memoria "Encuentra las parejas".
 * El juego consiste en voltear cartas y recordar su ubicación para hacer coincidir dos cartas iguales.
 * Autor: Steven Navarro
 */

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class Memory extends JFrame {

    // Tamaño de la ventana principal
    private static final int VENTANA_ANCHO = 500;
    private static final int VENTANA_ALTO = 600;

    // Paneles y etiquetas seleccionados durante el juego
    private final JPanel[] panelesSelec = new JPanel[2];
    private final JLabel[] labelsSelec = new JLabel[2];

    private JPanel panelMain;

    /**
     * Constructor de la clase Memory.
     * Configura la ventana principal y sus componentes.
     */
    public Memory() {
        configurarVentana();
        configurarPanelPrincipal();
        configurarEncabezado();
        configurarContenido();
    }

    /**
     * Configura las propiedades de la ventana principal.
     */
    private void configurarVentana() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(VENTANA_ANCHO, VENTANA_ALTO);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);
    }

    /**
     * Configura el panel principal de la ventana, que tendrá un fondo con gradiente.
     */
    private void configurarPanelPrincipal() {
        panelMain = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                // Crear el gradiente de color
                GradientPaint gradient = new GradientPaint(0, 0, Color.MAGENTA, getWidth(), getHeight(), Color.CYAN);

                // Pintar el fondo con el gradiente
                Graphics2D g2d = (Graphics2D) g;
                g2d.setPaint(gradient);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        panelMain.setLayout(null);
        setContentPane(panelMain);
    }

    /**
     * Configura el encabezado de la ventana, que contendrá un botón de salir (X).
     */
    private void configurarEncabezado() {
        JPanel header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setBounds(0, 0, getWidth(), 30);
        header.setLayout(null);
        panelMain.add(header);

        //Boton salir del header
        JPanel btnExit = new JPanel();
        btnExit.setBounds(450, 0, 50, 30);
        btnExit.setBackground(Color.WHITE);
        btnExit.setLayout(null);
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        header.add(btnExit);

        JLabel lblExit = new JLabel("X");
        lblExit.setBounds(0, 0, 50, 30);
        lblExit.setForeground(Color.BLACK);
        lblExit.setHorizontalAlignment(SwingConstants.CENTER);
        lblExit.setFont(new Font("Roboto", Font.PLAIN, 16));
        btnExit.add(lblExit);

        btnExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.exit(0);
            }

            @Override
            public void mouseEntered(MouseEvent e) { // Cursor encima del boton
                btnExit.setBackground(Color.red);
                lblExit.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) { // Cursor fuera del boton
                btnExit.setBackground(Color.WHITE);
                lblExit.setForeground(Color.BLACK);
            }
        });
    }

    /**
     * Configura el contenido principal de la ventana, incluyendo las cartas y las instrucciones del juego.
     */
    private void configurarContenido() {
        // Titulo
        JLabel lblTitulo = new JLabel("JUEGO DE MEMORIA");
        lblTitulo.setBounds(0, 40, getWidth(), 30);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panelMain.add(lblTitulo);

        // Instrucciones del juego
        JLabel lblInstrucion1 = new JLabel("Encuentra las parejas de cartas volteándolas ");
        JLabel lblInstrucion2 = new JLabel("una por una y recordando su ubicación para hacer coincidir dos cartas iguales");
        lblInstrucion1.setBounds(0, 70, getWidth(), 20);
        lblInstrucion2.setBounds(0, 85, getWidth(), 20);
        lblInstrucion1.setForeground(Color.BLACK);
        lblInstrucion2.setForeground(Color.BLACK);
        lblInstrucion1.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstrucion2.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstrucion1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInstrucion2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelMain.add(lblInstrucion1);
        panelMain.add(lblInstrucion2);

        // Cartas
        char[] datos = generarDatos();
        JPanel[] cartas = new JPanel[16];
        disenarCartas(datos, cartas);

        // Pie de Pagina
        JLabel lblFooter = new JLabel("Copytight © 2023 - Designed by Steven Navarro");
        lblFooter.setBounds(0, 570, 500, 30);
        lblFooter.setForeground(Color.BLACK);
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        panelMain.add(lblFooter);
    }

    /**
     * Diseña las cartas del juego y agrega los MouseListeners para la interacción.
     */
    private void disenarCartas(char[] datos, JPanel[] cartas) {
        for (int i = 0; i < cartas.length; i++) {
            cartas[i] = new JPanel();
            cartas[i].setBounds(((i % 4) * 120) + 20, ((i / 4) * 120) + 110, 100, 100);
            cartas[i].setBackground(new Color(12, 138, 199));
            cartas[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelMain.add(cartas[i]);

            JLabel lbl = new JLabel(String.valueOf(datos[i]));
            lbl.setBounds(0, 0, 100, 100);
            cartas[i].add(lbl);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setForeground(new Color(0, 0, 0, 0));
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 60));

            cartas[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    JPanel btn = (JPanel) e.getSource();
                    if (!btn.getBackground().equals(Color.GREEN)) {
                        btn.setBackground(new Color(0, 156, 223));
                    }
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    JPanel btn = (JPanel) e.getSource();
                    if (!btn.getBackground().equals(Color.GREEN)) {
                        btn.setBackground(SystemColor.textHighlight);
                    }
                }

                @Override
                public void mouseClicked(MouseEvent e) {
                    configurarCartas(e);
                }
            });
        }
    }

    /**
     * Maneja el evento de clic en una carta durante el juego.
     */
    private void configurarCartas(MouseEvent e) {
        JPanel panelClick = (JPanel) e.getSource();
        JLabel labelClick = (JLabel) panelClick.getComponent(0);

        if (panelClick.getBackground().equals(Color.GREEN)) {
            panelClick.setBackground(SystemColor.textHighlight);
            deseleccionarPanel(panelClick);
        } else if (noAmbosPanelesSelec()) {
            selecPanel(panelClick, labelClick);
            if (ambosPanelesSelec()) {
                checkCoincidentes();
            }
        }
    }

    /**
     * Selecciona un panel (carta) y su etiqueta (contenido) durante el juego.
     */
    private void selecPanel(JPanel panel, JLabel label) {
        panel.setBackground(Color.GREEN);
        label.setForeground(Color.WHITE);

        for (int i = 0; i < panelesSelec.length; i++) {
            if (panelesSelec[i] == null) {
                panelesSelec[i] = panel;
                labelsSelec[i] = label;
                break;
            }
        }
    }

    /**
     * Deselecciona un panel (carta) y su etiqueta (contenido) durante el juego.
     */
    private void deseleccionarPanel(JPanel panel) {
        panel.setBackground(SystemColor.textHighlight);
        JLabel label = (JLabel) panel.getComponent(0);
        label.setForeground(new Color(0, 0, 0, 0));

        for (int i = 0; i < panelesSelec.length; i++) {
            if (panelesSelec[i] == panel) {
                panelesSelec[i] = null;
                labelsSelec[i] = null;
                break;
            }
        }
    }

    /**
     * Verifica si se han seleccionado dos paneles (cartas) durante el juego.
     */
    private boolean noAmbosPanelesSelec() {
        return panelesSelec[0] == null || panelesSelec[1] == null;
    }

    /**
     * Verifica si se han seleccionado 2 paneles (cartas) durante el juego.
     */
    private boolean ambosPanelesSelec() {
        return panelesSelec[0] != null && panelesSelec[1] != null;
    }

    /**
     * Verifica si las dos cartas seleccionadas coinciden durante el juego.
     */
    private void checkCoincidentes() {
        String label1 = labelsSelec[0].getText();
        String label2 = labelsSelec[1].getText();

        if (label1.equals(label2)) {
            disableCoincidentes();
        } else {
            iniciarTimerParaReverso();
        }
    }

    /**
     * Desactiva y elimina las cartas que coinciden del juego.
     */
    private void disableCoincidentes() {
        for (JPanel panel : panelesSelec) {
            panel.removeMouseListener(panel.getMouseListeners()[0]);
            for (int i = 0; i < panelesSelec.length; i++) {
                if (panelesSelec[i] == panel) {
                    panelesSelec[i] = null;
                    labelsSelec[i] = null;
                    break;
                }
            }
        }
    }

    /**
     * Inicia un temporizador para revertir la selección si las cartas no coinciden.
     */
    private void iniciarTimerParaReverso() {
        int retrasoMilisegundos = 1000; // Retraso de 1 segundo

        Timer timer = new Timer(retrasoMilisegundos, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (JPanel panel : panelesSelec) {
                    panel.setBackground(SystemColor.textHighlight);
                    JLabel label = (JLabel) panel.getComponent(0);
                    label.setForeground(new Color(0, 0, 0, 0));
                }
                panelesSelec[0] = null;
                panelesSelec[1] = null;
            }
        });

        timer.setRepeats(false); // Se ejecutará solo una vez
        timer.start();
    }

    /**
     * Genera datos aleatorios para las cartas del juego.
     */
    private char[] generarDatos() {
        // Crear una lista con las letras de la A a la H, cada letra se repite dos veces
        List<Character> letras = new ArrayList<>();
        for (char c = 'A'; c <= 'H'; c++) {
            letras.add(c);
            letras.add(c);
        }

        // Mezclar aleatoriamente la lista de letras
        Collections.shuffle(letras);

        // Convertir la lista mezclada en un arreglo de caracteres
        char[] datos = new char[letras.size()];
        for (int i = 0; i < letras.size(); i++) {
            datos[i] = letras.get(i);
        }

        // Imprimir el resultado desordenado
        for (char dato : datos) {
            System.out.println(dato);
        }

        return datos;
    }

}
