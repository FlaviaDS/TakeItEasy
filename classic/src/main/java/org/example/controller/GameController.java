package org.example.controller;

import org.example.model.GameBoard;
import org.example.model.Tile;

public class GameController {
    private final GameBoard gameBoard;

    public GameController(int rows, int cols) {
        gameBoard = new GameBoard(rows, cols);
    }

    /**
     * Places a tile at the specified position applying the given number of rotations.
     * Returns true if the move was successfull, false otherwise.
     */
    public boolean placeTile(int row, int col, Tile tile, int rotations) {
        // Apply the rotations to the tile
        for (int i = 0; i < rotations; i++) {
            tile.rotate();
        }
        return gameBoard.placeTile(row, col, tile);
    }

    public boolean isGameOver() {
        return gameBoard.checkGameOver();
    }

    public int getFinalScore() {
        return gameBoard.calculateScore();
    }

    public GameBoard getGameBoard() {
        return gameBoard;
    }
}
