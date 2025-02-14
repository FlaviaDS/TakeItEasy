package org.example.view;

import org.example.controller.GameController;
import org.example.model.Tile;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class GameBoardUI extends JFrame {
    private static final int GRID_SIZE = 3;
    private final JButton[][] buttons;
    private final GameController gameController;

    public GameBoardUI() {
        gameController = new GameController(GRID_SIZE, GRID_SIZE);
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

    /**
     * Creates a button for the given row and col.
     */
    private JButton createButton(int row, int col) {
        JButton button = new JButton("");
        button.addActionListener(e -> handleButtonClick(row, col, button));
        return button;
    }

    /**
     * Handles the button click, including asking for rotation and placing the tile.
     * If the game is over, displays the final score and disables all buttons.
     */
    private void handleButtonClick(int row, int col, JButton button) {
        if (button.getText().isEmpty()) {
            Tile tile = generateRandomTile();
            int rotationChoice = askForRotation(tile);
            if (gameController.placeTile(row, col, tile, rotationChoice)) {
                button.setText(String.valueOf(tile.getValues()[0]));
                if (gameController.isGameOver()) {
                    JOptionPane.showMessageDialog(this, "Game Over! Final Score: " + gameController.getFinalScore());
                    disableAllButtons();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Invalid move! Cell already filled.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "This cell is already filled!");
        }
    }

    /**
     * Shows a dialog to ask the user for the tile rotation.
     * Returns the number of rotations to apply (0 for 0°, 1 for 120° and 2 for 240°).
     */
    private int askForRotation(Tile tile) {
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
        return (choice < 0 ? 0 : choice);
    }

    /**
     * Disables all the buttons on the grid to avoid further interactions.
     */
    private void disableAllButtons() {
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                buttons[i][j].setEnabled(false);
            }
        }
    }

    /**
     * Generates a random tile.
     */
    private Tile generateRandomTile() {
        Random rand = new Random();
        return new Tile(
                rand.nextInt(9) + 1,
                rand.nextInt(9) + 1,
                rand.nextInt(9) + 1);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(GameBoardUI::new);
    }
}
