package org.example;

import org.example.model.HexagonalGameBoard;
import org.example.model.HexTile;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class HexagonalGameBoardTest {

    @Test
    public void testValidMask() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        // Row 0: valid columns 2,3,4
        assertFalse(board.isValidPosition(0, 0));
        assertFalse(board.isValidPosition(0, 1));
        assertTrue(board.isValidPosition(0, 2));
        assertTrue(board.isValidPosition(0, 3));
        assertTrue(board.isValidPosition(0, 4));

        // Row 1: valid columns 1,2,3,4
        assertFalse(board.isValidPosition(1, 0));
        assertTrue(board.isValidPosition(1, 1));
        assertTrue(board.isValidPosition(1, 2));
        assertTrue(board.isValidPosition(1, 3));
        assertTrue(board.isValidPosition(1, 4));

        // Row 2: valid columns 0,1,2,3,4
        for (int col = 0; col < 5; col++) {
            assertTrue(board.isValidPosition(2, col));
        }

        // Row 3: valid columns 1,2,3,4
        assertFalse(board.isValidPosition(3, 0));
        assertTrue(board.isValidPosition(3, 1));
        assertTrue(board.isValidPosition(3, 2));
        assertTrue(board.isValidPosition(3, 3));
        assertTrue(board.isValidPosition(3, 4));

        // Row 4: valid columns 2,3,4
        assertFalse(board.isValidPosition(4, 0));
        assertFalse(board.isValidPosition(4, 1));
        assertTrue(board.isValidPosition(4, 2));
        assertTrue(board.isValidPosition(4, 3));
        assertTrue(board.isValidPosition(4, 4));
    }

    @Test
    public void testPlaceTile() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(5, 5, 5);

        boolean placed = board.placeTile(2, 2, tile);
        assertTrue(placed);
        assertEquals(5, board.getTileValue(2, 2));

        placed = board.placeTile(2, 2, tile);
        assertFalse(placed);

        placed = board.placeTile(0, 0, tile);
        assertFalse(placed);
        assertEquals(-1, board.getTileValue(0, 0));
    }

    @Test
    public void testGetTileValue() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(2, 2, 2);
        board.placeTile(2, 0, tile);
        assertEquals(2, board.getTileValue(2, 0));
        assertEquals(-1, board.getTileValue(0, 0));
    }

    @Test
    public void testCheckGameOver() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(1, 1, 1);
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 5; col++) {
                if (board.isValidPosition(row, col)) {
                    board.placeTile(row, col, tile);
                }
            }
        }
        assertTrue(board.checkGameOver());
    }

    @Test
    public void testCalculateScore() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(3, 3, 3);
        board.placeTile(2, 0, tile);
        board.placeTile(2, 1, tile);
        board.placeTile(2, 2, tile);
        assertEquals(9, board.calculateScore());
        board.placeTile(0, 2, tile);
        assertEquals(12, board.calculateScore());
    }

    @Test
    public void testTileRotation() {
        HexTile tile = new HexTile(1, 2, 3);
        assertArrayEquals(new int[]{1, 2, 3}, tile.getValues());
        tile.rotate();
        assertArrayEquals(new int[]{2, 3, 1}, tile.getValues());
        tile.rotate();
        assertArrayEquals(new int[]{3, 1, 2}, tile.getValues());
        tile.rotate();
        assertArrayEquals(new int[]{1, 2, 3}, tile.getValues());
    }
}
