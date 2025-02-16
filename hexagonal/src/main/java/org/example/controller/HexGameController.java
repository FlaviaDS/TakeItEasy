package org.example.controller;

import org.example.model.HexTile;
import org.example.model.HexagonalGameBoard;

public class HexGameController {
    private final HexagonalGameBoard gameBoard;

    public HexGameController() {
        gameBoard = new HexagonalGameBoard();
    }

    /**
     * Places a tile at the specified position applying the given number of rotations.
     * Returns true if successful.
     */
    public boolean placeTile(int row, int col, HexTile tile, int rotations) {
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

    public HexagonalGameBoard getGameBoard() {
        return gameBoard;
    }
}
