package SnakeGame;

import javax.swing.*;

public class SnakeGame extends JFrame {

    SnakeGame() {
        super("Snake game");
        add(new Board());
        pack();
        setResizable(false);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Ensure program exits on close
    }

    public static void main(String args[]) {
        new SnakeGame().setVisible(true);
        ;
    }
}
