package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameBoardTest {

    @Test
    public void testInitializeBoard() {
        GameBoard board = new GameBoard(3, 3);
        assertEquals(3, board.getRows());
        assertEquals(3, board.getCols());
    }

    @Test
    public void testCalculateScore() {
        GameBoard board = new GameBoard(3, 3);
        Tile tile = new Tile(5, 5, 5);
        board.placeTile(0, 0, tile);
        board.placeTile(0, 1, tile);
        board.placeTile(0, 2, tile);
        int score = board.calculateScore();
        assertEquals(15, score);
    }

    @Test
    public void testNoScoreForDifferentValuesInRow() {
        GameBoard board = new GameBoard(3, 3);
        Tile tile1 = new Tile(5, 5, 5);
        Tile tile2 = new Tile(3, 3, 3);

        board.placeTile(0, 0, tile1);
        board.placeTile(0, 1, tile2);
        board.placeTile(0, 2, tile1);
        int score = board.calculateScore();
        assertEquals(0, score);
    }

    @Test
    public void testEmptyBoardScore() {
        GameBoard board = new GameBoard(3, 3);
        int score = board.calculateScore();
        assertEquals(0, score);
    }

    @Test
    public void testIncompleteRowScore() {
        GameBoard board = new GameBoard(3, 3);
        Tile tile = new Tile(7, 7, 7);
        board.placeTile(0, 0, tile);
        board.placeTile(0, 1, tile);
        int score = board.calculateScore();
        assertEquals(0, score);
    }

    @Test
    public void testScoreForMultipleCompleteRows() {
        GameBoard board = new GameBoard(3, 3);
        Tile tile1 = new Tile(4, 4, 4);
        Tile tile2 = new Tile(2, 2, 2);

        board.placeTile(0, 0, tile1);
        board.placeTile(0, 1, tile1);
        board.placeTile(0, 2, tile1);

        board.placeTile(1, 0, tile2);
        board.placeTile(1, 1, tile2);
        board.placeTile(1, 2, tile2);

        int score = board.calculateScore();
        assertEquals(18, score);
    }

    @Test
    public void testCalculateScoreColumn() {
        GameBoard board = new GameBoard(3, 3);
        Tile tile = new Tile(4, 4, 4);
        board.placeTile(0, 0, tile);
        board.placeTile(1, 0, tile);
        board.placeTile(2, 0, tile);
        int score = board.calculateScore();
        assertEquals(12, score);
    }

    @Test
    public void testCalculateScoreForMainDiagonal() {
        GameBoard board = new GameBoard(3, 3);
        Tile tile = new Tile(3, 3, 3);
        board.placeTile(0, 0, tile);
        board.placeTile(1, 1, tile);
        board.placeTile(2, 2, tile);
        int score = board.calculateScore();
        assertEquals(9, score);
    }

    @Test
    public void testCalculateScoreForSecondaryDiagonal() {
        GameBoard board = new GameBoard(3, 3);
        Tile tile = new Tile(6, 6, 6);
        board.placeTile(0, 2, tile);
        board.placeTile(1, 1, tile);
        board.placeTile(2, 0, tile);
        int score = board.calculateScore();
        assertEquals(18, score);
    }

    @Test
    public void testGameOverCondition() {
        GameBoard board = new GameBoard(3, 3);
        Tile tile = new Tile(1, 1, 1);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.placeTile(i, j, tile);
            }
        }
        assertTrue(board.checkGameOver());
    }

    @Test
    public void testOutOfBoundsPlacement() {
        GameBoard board = new GameBoard(3, 3);
        Tile tile = new Tile(5, 5, 5);

        board.placeTile(4, 4, tile);

        assertEquals(0, board.getTileValue(4, 4));
    }

    @Test
    public void testOverwritePrevention() {
        GameBoard board = new GameBoard(3, 3);
        Tile tile1 = new Tile(3, 3, 3);
        Tile tile2 = new Tile(9, 9, 9);
        board.placeTile(0, 0, tile1);
        board.placeTile(0, 0, tile2);
        assertEquals(3, board.getTileValue(0, 0));
    }

    @Test
    public void testFullGameSimulation() {
        GameBoard board = new GameBoard(3, 3);
        Tile tile1 = new Tile(5, 5, 5);
        Tile tile2 = new Tile(4, 4, 4);
        Tile tile3 = new Tile(3, 3, 3);

        board.placeTile(0, 0, tile1);
        board.placeTile(0, 1, tile1);
        board.placeTile(0, 2, tile1);

        board.placeTile(1, 0, tile2);
        board.placeTile(1, 1, tile2);
        board.placeTile(1, 2, tile2);

        board.placeTile(2, 0, tile3);
        board.placeTile(2, 1, tile3);
        board.placeTile(2, 2, tile3);

        assertTrue(board.checkGameOver());
        assertTrue(board.calculateScore() > 0);
    }

    @Test
    public void testTileRotation() {
        Tile tile = new Tile(3, 6, 9);

        assertArrayEquals(new int[]{3, 6, 9}, tile.getValues());

        tile.rotate();
        assertArrayEquals(new int[]{6, 9, 3}, tile.getValues());

        tile.rotate();
        assertArrayEquals(new int[]{9, 3, 6}, tile.getValues());

        tile.rotate();
        assertArrayEquals(new int[]{3, 6, 9}, tile.getValues()); // Torna allo stato iniziale
    }
}
