package SnakeGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Board extends JPanel implements ActionListener {

    private Image apple, dot, head;
    private final int All_DOTS = 2500;
    private final int Dot_SIZE = 10;
    private final int Random_position = 29;
    private int apple_x, apple_y;
    private Timer timer;
    private final int x[] = new int[All_DOTS];
    private final int y[] = new int[All_DOTS];
    private int dots;

    private boolean leftDirection = false;
    private boolean rightDirection = true;
    private boolean upDirection = false;
    private boolean downDirection = false;
    private boolean ingame = true;

    Board() {
        addKeyListener(new TAdapter());
        setBackground(Color.BLACK);
        setFocusable(true);
        setPreferredSize(new Dimension(500, 500));
        loadImages();
        initGame();
    }

    public void loadImages() {
        apple = new ImageIcon(getClass().getResource("/SnakeGame/icons/apple.png")).getImage();
        dot = new ImageIcon(getClass().getResource("/SnakeGame/icons/dot.png")).getImage();
        head = new ImageIcon(getClass().getResource("/SnakeGame/icons/head.png")).getImage();
    }

    public void initGame() {
        dots = 3;
        for (int i = 0; i < dots; i++) {
            y[i] = 50;
            x[i] = 50 - i * Dot_SIZE;
        }
        locateApple();
        timer = new Timer(140, this);
        timer.start();
    }

    public void locateApple() {
        int r = (int) (Math.random() * Random_position);
        apple_x = r * Dot_SIZE;
        r = (int) (Math.random() * Random_position);
        apple_y = r * Dot_SIZE;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        if (ingame) {
            g.drawImage(apple, apple_x, apple_y, this);
            for (int i = 0; i < dots; i++) {
                g.drawImage(i == 0 ? head : dot, x[i], y[i], this);
            }
            Toolkit.getDefaultToolkit().sync();
        } else {
            gameOver(g);
        }
    }

    public void gameOver(Graphics g) {
        String msg = "Game Over!!";
        Font font = new Font("SAN_SERIF", Font.BOLD, 14);
        FontMetrics metrics = getFontMetrics(font);

        g.setColor(Color.WHITE);
        g.setFont(font);
        g.drawString(msg, (getWidth() - metrics.stringWidth(msg)) / 2, getHeight() / 2);
    }

    public void move() {
        for (int i = dots - 1; i > 0; i--) { // Fixed index
            x[i] = x[i - 1];
            y[i] = y[i - 1];
        }

        if (leftDirection)
            x[0] -= Dot_SIZE;
        if (rightDirection)
            x[0] += Dot_SIZE;
        if (upDirection)
            y[0] -= Dot_SIZE;
        if (downDirection)
            y[0] += Dot_SIZE;
    }

    public void checkApple() {
        if ((x[0] == apple_x) && (y[0] == apple_y)) {
            dots++;
            locateApple();
        }
    }

    public void checkCollision() {
        for (int i = dots - 1; i > 0; i--) { // Fixed index
            if ((i > 4) && (x[0] == x[i]) && (y[0] == y[i])) {
                ingame = false;
            }
        }

        if (y[0] >= getHeight() || x[0] >= getWidth() || y[0] < 0 || x[0] < 0) {
            ingame = false;
        }

        if (!ingame) {
            timer.stop();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (ingame) {
            move();
            checkApple();
            checkCollision();
        }
        repaint();
    }

    private class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if (key == KeyEvent.VK_LEFT && !rightDirection) {
                leftDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_RIGHT && !leftDirection) {
                rightDirection = true;
                upDirection = false;
                downDirection = false;
            }
            if (key == KeyEvent.VK_UP && !downDirection) {
                upDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
            if (key == KeyEvent.VK_DOWN && !upDirection) {
                downDirection = true;
                leftDirection = false;
                rightDirection = false;
            }
        }
    }
}
