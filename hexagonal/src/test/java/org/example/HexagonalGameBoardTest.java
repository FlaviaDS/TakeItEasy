package org.example;

import org.example.model.HexagonalGameBoard;
import org.example.model.HexTile;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class HexagonalGameBoardTest {

    @Test
    public void testValidMask() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        // Row 0
        assertFalse(board.isValidPosition(0, 0));
        assertFalse(board.isValidPosition(0, 1));
        assertTrue(board.isValidPosition(0, 2));
        assertTrue(board.isValidPosition(0, 3));
        assertTrue(board.isValidPosition(0, 4));
        // Row 1
        assertFalse(board.isValidPosition(1, 0));
        assertTrue(board.isValidPosition(1, 1));
        assertTrue(board.isValidPosition(1, 2));
        assertTrue(board.isValidPosition(1, 3));
        assertTrue(board.isValidPosition(1, 4));
        // Row 2
        for (int col = 0; col < 5; col++) {
            assertTrue(board.isValidPosition(2, col));
        }
        // Row 3
        assertFalse(board.isValidPosition(3, 0));
        assertTrue(board.isValidPosition(3, 1));
        assertTrue(board.isValidPosition(3, 2));
        assertTrue(board.isValidPosition(3, 3));
        assertTrue(board.isValidPosition(3, 4));
        // Row 4
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
    public void testPartialBoardNotGameOver() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(1, 1, 1);
        board.placeTile(2, 2, tile);
        board.placeTile(0, 3, tile);
        assertFalse(board.checkGameOver());
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

    @Test
    public void testInvalidIndices() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        assertFalse(board.isValidPosition(-1, 0));
        assertFalse(board.isValidPosition(0, -1));
        assertFalse(board.isValidPosition(5, 2));
        assertFalse(board.isValidPosition(2, 5));
    }

    // --- Scoring tests (vertical and diagonal only) ---

    @Test
    public void testCalculateScoreVerticalLineOfFive() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile9 = new HexTile(9, 9, 9);
        // Vertical line in column 2 (rows 0 to 4)
        board.placeTile(0, 2, tile9);
        board.placeTile(1, 2, tile9);
        board.placeTile(2, 2, tile9);
        board.placeTile(3, 2, tile9);
        board.placeTile(4, 2, tile9);
        int score = board.calculateScore();
        System.out.println("Vertical line of five score: " + score);
        assertEquals(45, score);
    }

    @Test
    public void testCalculateScoreDiagonalLineOfFour() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile7 = new HexTile(7, 7, 7);
        // Diagonal down-right: (1,1), (2,2), (3,3), (4,4)
        board.placeTile(1, 1, tile7);
        board.placeTile(2, 2, tile7);
        board.placeTile(3, 3, tile7);
        board.placeTile(4, 4, tile7);
        int score = board.calculateScore();
        System.out.println("Diagonal line of four score: " + score);
        assertEquals(28, score);
    }

    @Test
    public void testCalculateScoreDiagonalLineOfThree() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile5 = new HexTile(5, 5, 5);
        // Diagonal down-left: (1,4), (2,3), (3,2)
        board.placeTile(1, 4, tile5);
        board.placeTile(2, 3, tile5);
        board.placeTile(3, 2, tile5);
        int score = board.calculateScore();
        System.out.println("Diagonal line of three score: " + score);
        assertEquals(15, score);
    }

    @Test
    public void testCalculateScoreMultipleLines() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        // Vertical line in column 2: (0,2),(1,2),(2,2) -> 3*3 = 9
        HexTile tile3 = new HexTile(3, 3, 3);
        board.placeTile(0, 2, tile3);
        board.placeTile(1, 2, tile3);
        board.placeTile(2, 2, tile3);
        // Diagonal down-left: (1,4),(2,3),(3,2) -> 3*5 = 15
        HexTile tile5 = new HexTile(5, 5, 5);
        board.placeTile(1, 4, tile5);
        board.placeTile(2, 3, tile5);
        board.placeTile(3, 2, tile5);
        int score = board.calculateScore();
        System.out.println("Multiple lines score: " + score);
        assertEquals(24, score);
    }
}
