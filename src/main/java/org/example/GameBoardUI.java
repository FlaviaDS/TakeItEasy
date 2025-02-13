package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameBoardUI extends JFrame {
    private static final int GRID_SIZE = 3;
    private final JButton[][] buttons;
    private final GameBoard gameBoard;

    public GameBoardUI() {
        gameBoard = new GameBoard(GRID_SIZE, GRID_SIZE);
        buttons = new JButton[GRID_SIZE][GRID_SIZE];

        setupUI();
        initializeButtons();
    }

    private void setupUI() {
        setTitle("Take It Easy - " + GRID_SIZE + "x" + GRID_SIZE + " Grid");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(GRID_SIZE, GRID_SIZE));
        setVisible(true);
    }

    private void initializeButtons() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j] = createButton(i, j);
                add(buttons[i][j]);
            }
        }
    }

    private JButton createButton(int row, int col) {
        JButton button = new JButton("");
        button.addActionListener(e -> handleButtonClick(row, col, button));
        return button;
    }

    private void handleButtonClick(int row, int col, JButton button) {
        if (button.getText().isEmpty()) {
            Tile tile = generateRandomTile();

            String[] rotations = {"0°", "120°", "240°"};
            int choice = JOptionPane.showOptionDialog(
                    this,
                    "Choose a tile rotation:\n" + tile,
                    "Rotate Tile",
                    JOptionPane.YES_NO_CANCEL_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    rotations,
                    rotations[0]);

            for (int i = 0; i < choice; i++) {
                tile.rotate();
            }

            if (gameBoard.placeTile(row, col, tile)) {
                button.setText(String.valueOf(tile.getValues()[0]));
                if (gameBoard.checkGameOver()) {
                    JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + gameBoard.calculateScore());
                    disableAllButtons();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid move! Cell already filled.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "This cell is already filled!");
        }
    }

    private void disableAllButtons() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    private Tile generateRandomTile() {
        Random rand = new Random();
        return new Tile(rand.nextInt(9) + 1, rand.nextInt(9) + 1, rand.nextInt(9) + 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameBoardUI::new);
    }
}
