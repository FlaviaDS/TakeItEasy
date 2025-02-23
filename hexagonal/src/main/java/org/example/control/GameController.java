package org.example.control;

import org.example.model.HexTile;
import org.example.model.HexagonalGameBoard;
import org.example.utils.TileLoader;

public class GameController {
    private final HexagonalGameBoard board;
    private HexTile currentTile;

    public GameController() {
        board = new HexagonalGameBoard();
        TileLoader.loadTiles();
        currentTile = TileLoader.drawTile();
    }

    public boolean placeTile(int index) {
        if (board.getTile(index) != null) {
            return false;
        }
        if (!board.placeTile(index, currentTile)) {
            return false;
        }
        currentTile = TileLoader.drawTile();
        return true;
    }


    public HexTile getNextTile() {
        return currentTile;
    }

    public boolean isGameOver() {
        return board.isBoardFull();
    }

    public int getScore() {
        return board.calculateScore();
    }

    public HexTile getTileAt(int index) {
        return board.getTile(index);
    }
}
