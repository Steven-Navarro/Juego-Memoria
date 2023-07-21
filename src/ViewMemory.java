import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;

public class ViewMemory extends JFrame {
    private JPanel panelMain;
    private JPanel header;
    private JPanel btnExit;
    private JLabel lblExit;
    private JLabel lblTitulo;
    private JLabel lblInstrucion1;
    private JLabel lblInstrucion2;
    private JPanel btn1 = new JPanel();
    ;
    private JLabel lbl1 = new JLabel("1");
    private JPanel btn2 = new JPanel();
    ;
    private JLabel lbl2 = new JLabel("2");
    private JPanel[] botones = new JPanel[16];
    private JLabel lblFooter;
    private int panelesSeleccionados = 0;
    private String sel1;
    private String sel2;
    private JPanel sel[] = new JPanel[2];
    private JLabel lblsel[] = new JLabel[2];
    private int nsel = 2;

    public ViewMemory() {
        //Configuracion ventana
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 600);
        setResizable(false);
        setLocationRelativeTo(null);
        setUndecorated(true);

        //Configuracion panel principal
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

        configurationHeader();
        configurationContented();

    }

    private void configurationHeader() {
        //Header de ventana
        header = new JPanel();
        header.setBackground(Color.WHITE);
        header.setBounds(0, 0, 500, 30);
        header.setLayout(null);
        panelMain.add(header);

        //Boton salir del header
        btnExit = new JPanel();
        btnExit.setBounds(450, 0, 50, 30);
        btnExit.setBackground(Color.WHITE);
        btnExit.setLayout(null);
        btnExit.setCursor(new Cursor(Cursor.HAND_CURSOR));
        header.add(btnExit);

        lblExit = new JLabel("X");
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
            public void mouseEntered(MouseEvent e) {
                btnExit.setBackground(Color.red);
                lblExit.setForeground(Color.white);
            }

            @Override
            public void mouseExited(MouseEvent e) { //cursor fuera del boton
                btnExit.setBackground(Color.WHITE);
                lblExit.setForeground(Color.BLACK);
            }
        });
    }

    private void configurationContented() {
        //Titulo
        lblTitulo = new JLabel("JUEGO DE MEMORIA");
        lblTitulo.setBounds(0, 40, 500, 30);
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 28));
        panelMain.add(lblTitulo);

        //Instruccion
        lblInstrucion1 = new JLabel("Encuentra las parejas de cartas volteándolas");
        lblInstrucion2 = new JLabel("una por una y recordando su ubicación para hacer coincidir dos cartas iguales");
        lblInstrucion1.setBounds(0, 70, 500, 20);
        lblInstrucion2.setBounds(0, 85, 500, 20);
        lblInstrucion1.setForeground(Color.BLACK);
        lblInstrucion2.setForeground(Color.BLACK);
        lblInstrucion1.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstrucion2.setHorizontalAlignment(SwingConstants.CENTER);
        lblInstrucion1.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblInstrucion2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        panelMain.add(lblInstrucion1);
        panelMain.add(lblInstrucion2);

        //Botones
        char[] datos = generarDatos();
        for (int i = 0; i < botones.length; i++) {
            botones[i] = new JPanel();
            botones[i].setBounds(((i % 4) * 120) + 20, ((i / 4) * 120) + 110, 100, 100);
            botones[i].setBackground(new Color(12, 138, 199));
            botones[i].setCursor(new Cursor(Cursor.HAND_CURSOR));
            panelMain.add(botones[i]);

            JLabel lbl = new JLabel(String.valueOf(datos[i]));
            lbl.setBounds(0, 0, 100, 100);
            botones[i].add(lbl);
            lbl.setHorizontalAlignment(SwingConstants.CENTER);
            lbl.setForeground(new Color(0, 0, 0, 0));
            //lbl.setForeground(Color.WHITE);
            lbl.setFont(new Font("Segoe UI", Font.BOLD, 60));

            botones[i].addMouseListener(new MouseAdapter() {
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
                    sel[panelesSeleccionados] = (JPanel) e.getSource();
                    lblsel[panelesSeleccionados] = (JLabel) sel[panelesSeleccionados].getComponent(0);

                    if (sel[panelesSeleccionados].getBackground().equals(Color.GREEN)) {
                        sel[panelesSeleccionados].setBackground(SystemColor.textHighlight);
                        panelesSeleccionados--;
                    } else if (panelesSeleccionados < 2) {
                        sel[panelesSeleccionados].setBackground(Color.GREEN);
                        lblsel[panelesSeleccionados].setForeground(Color.WHITE);
                        panelesSeleccionados++;

                        if (panelesSeleccionados == 2) {
                            sel2 = lblsel[1].getText();
                            sel1 = lblsel[0].getText();
                            if (sel1.equals(sel2)) {
                                for (JPanel panel : botones) {
                                    if (panel.getBackground().equals(Color.GREEN)) {
                                        var mouseListeners = panel.getMouseListeners();
                                        if (mouseListeners.length > 0) {
                                            panel.removeMouseListener(mouseListeners[0]);
                                        }
                                    }
                                }
                                panelesSeleccionados = 0;
                            } else {
                                for (JPanel panel : botones) {
                                    if (panel.getBackground().equals(Color.GREEN)) {
                                        int retrasoMilisegundos = 1000; // Retraso de 1 segundo

                                        Timer timer = new Timer(retrasoMilisegundos, new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                sel[1].setBackground(SystemColor.textHighlight);
                                                sel[0].setBackground(SystemColor.textHighlight);
                                                lblsel[1].setForeground(new Color(0, 0, 0, 0));
                                                lblsel[0].setForeground(new Color(0, 0, 0, 0));
                                                panelesSeleccionados = 0;
                                            }
                                        });

                                        timer.setRepeats(false); // Se ejecutará solo una vez
                                        timer.start();
                                    }
                                    panelesSeleccionados = 0;
                                }
                            }
                        }
                    }
                }
            });
        }

        //Pie de Pagina
        lblFooter = new JLabel("Copytight © 2023 - Designed by Steven Navarro");
        lblFooter.setBounds(0, 570, 500, 30);
        lblFooter.setForeground(Color.BLACK);
        lblFooter.setHorizontalAlignment(SwingConstants.CENTER);
        lblFooter.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        panelMain.add(lblFooter);
    }

    private boolean verificarSeleccion() {
        return true;
    }

    private char[] generarDatos() {
        char[] datos = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        float[] numAleatorios = generarNumAleatorios(datos.length);

        // Crear un arreglo de pares (dato, número aleatorio)
        ParDatoNumero[] pares = new ParDatoNumero[datos.length];
        for (int i = 0; i < datos.length; i++) {
            pares[i] = new ParDatoNumero(datos[i], numAleatorios[i]);
        }

        // Ordenar el arreglo de pares utilizando metodo burbuja
        for (int i = 0; i < pares.length - 1; i++) {
            for (int j = 0; j < pares.length - i - 1; j++) {
                if (pares[j].numeroAleatorio > pares[j + 1].numeroAleatorio) {
                    // Intercambiar los elementos si están en el orden incorrecto
                    ParDatoNumero temp = pares[j];
                    pares[j] = pares[j + 1];
                    pares[j + 1] = temp;
                }
            }
        }

        // Obtener el resultado ordenado
        char[] datosOrdenados = new char[datos.length];
        for (int i = 0; i < datos.length; i++) {
            datosOrdenados[i] = pares[i].dato;
        }

        // Imprimir el resultado ordenado
        for (char dato : datosOrdenados) {
            System.out.println(dato);
        }
        return datosOrdenados;
    }

    private float[] generarNumAleatorios(int cantidad) {
        Random random = new Random();
        float[] numerosAleatorios = new float[cantidad];

        for (int i = 0; i < cantidad; i++) {
            numerosAleatorios[i] = random.nextFloat() * 9 + 1;
        }

        return numerosAleatorios;
    }

    private class ParDatoNumero {
        char dato;
        float numeroAleatorio;

        ParDatoNumero(char dato, float numeroAleatorio) {
            this.dato = dato;
            this.numeroAleatorio = numeroAleatorio;
        }
    }
}
