
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import static java.awt.Font.PLAIN;

public class Calculadora extends JFrame {
    public JPanel panel;
    public JTextField cajaTexto;
    public int numBotones = 19;
    public int numNumeros = 11;
    public JButton botones[] = new JButton[numBotones];
    public String textoBotones[] = {".", "AC", "<-", "+", "7", "8", "9", "-", "4", "5", "6", "*", "1", "2", "3", "/", "00", "0", "="};
    int xBotones[] = {15, 107, 202, 295, 15, 107, 202, 295, 15, 107, 202, 295, 15, 107, 202, 295, 15, 107, 202};
    int yBotones[] = {90, 90, 90, 90, 180, 180, 180, 180, 270, 270, 270, 270, 360, 360, 360, 360, 450, 450, 450};
    int anchoBoton = 70;
    int altoBoton = 70;
    boolean nuevaEntrada = true;
    int posnumeros[] = {17, 16, 14, 13, 12, 10, 9, 8, 6, 5, 4};
    int posbotonescalc[] = {15, 11, 7, 3};
    boolean punto = false;
    double num1 = 0;
    double num2 = 0;
    double res = 0;
    String simboloOperacion = "";


    public Calculadora() {
        setSize(400, 570);
        setTitle("Calculadora Alejandro Legrá");
        setResizable(false);
        setLocationRelativeTo(null);
        setMaximumSize(new Dimension(200, 200));
        iniciarComponentes();
        NomesNums(cajaTexto);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }

    private void iniciarComponentes() {
        colocarPaneles();
        colocarCajadeTexto();
        colocarBotones();
        accionsAC();
        accionsNumeros();
        accionsPunto();
        accionsBorrarUn();
        accionsCalcular();
        accionsVerResultado();
    }

    private void colocarPaneles() {
        panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.GRAY);
        this.getContentPane().add(panel);
    }

    private void colocarCajadeTexto() {
        cajaTexto = new JTextField("0");
        cajaTexto.setBounds(15, 15, 350, 60);
        cajaTexto.setBackground(Color.BLACK);
        cajaTexto.setForeground(Color.GREEN);
        cajaTexto.setBorder(new LineBorder(Color.DARK_GRAY));
        cajaTexto.setFont(new Font("MONOSPACED", PLAIN, 24));
        cajaTexto.setHorizontalAlignment(SwingConstants.RIGHT);
        setMaximumSize(new Dimension(200, 200));
        panel.add(cajaTexto);
    }

    private void NomesNums(JTextField a) {
        a.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if (!Character.isDigit(c) && c != '.') {
                    e.consume();
                }
                if (c == '.' && cajaTexto.getText().contains(".")) {
                    e.consume();
                }
            }
        });
    }

    private void colocarBotones() {

        for (int i = 0; i < numBotones; i++) {
            botones[i] = new JButton(textoBotones[i]);
            if (i == 18) {
                anchoBoton = 163;
            }
            botones[i].setBounds(xBotones[i], yBotones[i], anchoBoton, altoBoton);
            botones[i].setEnabled(true);
            botones[i].setFont(new Font("MONOSPACED", PLAIN, 24));
            botones[i].setOpaque(true);
            botones[i].setFocusPainted(false);
            botones[i].setBackground(Color.DARK_GRAY);
            botones[i].setForeground(Color.WHITE);
            botones[i].setBorder(new LineBorder(Color.DARK_GRAY));
            panel.add(botones[i]);

        }
        for (int j = 0; j < numNumeros; j++) {
            int botonsNums = posnumeros[j];
            botones[botonsNums].setBackground(Color.black);

        }

    }

    private void accionsNumeros() {

        for (int i = 0; i < 11; i++) {
            int numBoton = posnumeros[i];
            botones[numBoton].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (nuevaEntrada) {
                        if (!textoBotones[numBoton].equals("0")) {
                            cajaTexto.setText(textoBotones[numBoton]);
                            nuevaEntrada = false;
                        }
                    } else {
                        cajaTexto.setText(cajaTexto.getText() + textoBotones[numBoton]);
                    }
                }
            });
        }


    }

    private void accionsAC() {

        botones[1].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cajaTexto.setText("0");
                reset();

            }
        });

    }

    private void reset() {
        num1 = 0;
        num2 = 0;
        simboloOperacion = "";
        punto = false;

        if (!nuevaEntrada) {
            nuevaEntrada = true;
        }
    }

    private void accionsBorrarUn() {
        botones[2].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (cajaTexto.getText().length() != 0) {
                    cajaTexto.setText(cajaTexto.getText().substring(0, cajaTexto.getText().length() - 1));
                }
            }
        });

    }

    private void accionsPunto() {

        botones[0].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!punto) {
                    cajaTexto.setText(cajaTexto.getText() + textoBotones[0]);
                    punto = true;
                    nuevaEntrada = false;
                }
            }
        });

    }

    private void accionsCalcular() {
        for (int i = 0; i < 4; i++) {
            int numBoton = posbotonescalc[i];
            botones[numBoton].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (simboloOperacion.equals("")) {
                        simboloOperacion = textoBotones[numBoton];
                        num2 = Double.parseDouble(cajaTexto.getText());
                        nuevaEntrada = true;
                        punto = false;
                    }
                    else {
                        num2 = resultadoFinal();
                        simboloOperacion = textoBotones[numBoton];
                    }

                }
            });
        }

    }

    private double resultadoFinal() {

        num1 = Double.parseDouble(cajaTexto.getText());

        switch (simboloOperacion){

            case "+" :  res = num2 + num1;
                break;
            case "-" :  res = num2 - num1;
                break;
            case "*" :  res = num2 * num1;
                break;
            case "/" :  res = num2 / num1;
                break;

        }

        cajaTexto.setText(String.valueOf(res));
        reset();
        return res;
    }

    private void accionsVerResultado() {

        botones[18].addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultadoFinal();

            }
        });

    }
}




