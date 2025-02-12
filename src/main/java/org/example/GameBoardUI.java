package org.example;

import javax.swing.*;
import java.awt.*;

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
                // Final copies of index for func lambda
                final int row = i;
                final int col = j;
                buttons[row][col] = createButton(row, col);
                add(buttons[row][col]);
            }
        }
    }

    private JButton createButton(int row, int col) {
        JButton button = new JButton("");
        button.addActionListener(e -> onButtonClick(row, col, button));
        return button;
    }

    private void onButtonClick(int row, int col, JButton button) {
        if (button.getText().isEmpty()) {
            handleEmptyCell(row, col, button);
        } else {
            showMessage("This cell is full");
        }
    }

    private void handleEmptyCell(int row, int col, JButton button) {
        String input = getUserInput();
        if (isValidInput(input)) {
            int value = Integer.parseInt(input);
            updateGameState(row, col, value, button);
        } else {
            showMessage("Invalid input!");
        }
    }

    private String getUserInput() {
        return JOptionPane.showInputDialog(
                null,
                "Insert a number (1-9):",
                "Input",
                JOptionPane.PLAIN_MESSAGE);
    }

    private boolean isValidInput(String input) {
        return input != null && input.matches("[1-9]");
    }

    private void updateGameState(int row, int col, int value, JButton button) {
        gameBoard.placeTile(row, col, value);
        button.setText(String.valueOf(value));
        if (gameBoard.checkGameOver()) {
            showMessage("Game Over! Final score: " + gameBoard.calculateScore());
        }
    }

    private void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameBoardUI::new);
    }
}
