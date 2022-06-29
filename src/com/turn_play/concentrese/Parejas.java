package com.turn_play.concentrese;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class Parejas extends JFrame implements ActionListener {
    private Tablero tablero;
    private JButton[][] botones;
    private ImageIcon[] imagenes;
    private static int sw;// auxiliares
    private static int a, b, ii, jj;// auxiliares

    public Parejas() {
        sw = 0;
        tablero = new Tablero(6);
        tablero.genAleatorio();
        initComponents();
        configVentana();
    }

    public void configVentana() {
        setTitle("¡Voltea y Juega!");
        setSize(1000, 1000);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void initComponents() {
        int d = tablero.getDim();
        imagenes = new ImageIcon[d * d / 2 + 1];
        imagenes[0] = null;
        for (int i = 1; i <= d * d / 2; i++) {
            Image img = new ImageIcon(getClass().getResource("./images/" + i + ".png")).getImage();
            Image imageSize = img.getScaledInstance(150, 150, java.awt.Image.SCALE_SMOOTH);
            imagenes[i] = new ImageIcon(imageSize);
        }

        JPanel A = new JPanel(new GridLayout(d, d));
        botones = new JButton[d][d];
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                botones[i][j] = new JButton();
                botones[i][j].setBackground(Color.white);
                botones[i][j].addActionListener(this);
                A.add(botones[i][j]);
            }
        }
        this.add(A, "Center");
    }

    public void accion(int x, int y) {
        switch (sw) {
            case 0:
                if (!tablero.esClic(x, y)) {
                    tablero.clic(x, y);
                    botones[x][y].setIcon(imagenes[tablero.getPos(x, y)]);
                    sw = 1;
                    a = x;
                    b = y;
                }
                break;
            case 1:
                if (!tablero.esClic(x, y)) {
                    tablero.clic(x, y);
                    botones[x][y].setIcon(imagenes[tablero.getPos(x, y)]);
                    ii = x;
                    jj = y;
                    if (tablero.getPos(a, b) != tablero.getPos(ii, jj))
                        sw = 2;
                    else
                        sw = 0;
                }
                break;
            case 2:
                botones[a][b].setIcon(null);
                botones[ii][jj].setIcon(null);
                tablero.clic(a, b);
                tablero.clic(ii, jj);
                sw = 0;
                break;
        }
    }

    public void actionPerformed(ActionEvent ae) {
        int d = tablero.getDim();
        for (int i = 0; i < d; i++) {
            for (int j = 0; j < d; j++) {
                if (botones[i][j] == ae.getSource()) {
                    accion(i, j);
                    if (tablero.esCompleto()) {
                        JOptionPane.showMessageDialog(this, "Felicidades", "¡has ganado!",
                                JOptionPane.INFORMATION_MESSAGE, null);
                        System.exit(0);
                    }
                    return;
                }
            }
        }
    }
}