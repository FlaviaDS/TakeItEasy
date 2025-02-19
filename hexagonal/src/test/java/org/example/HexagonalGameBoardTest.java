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
        assertEquals(-1, board.getTileValue(0, 0)); // invalid cell
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

    // --- Scoring tests (edge-to-edge, complete lines) ---

    @Test
    public void testCalculateScoreVerticalLineOfFive() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile9 = new HexTile(9, 9, 9);
        // Vertical line in column 2: rows 0,1,2,3,4 are all valid borders.
        board.placeTile(0, 2, tile9);
        board.placeTile(1, 2, tile9);
        board.placeTile(2, 2, tile9);
        board.placeTile(3, 2, tile9);
        board.placeTile(4, 2, tile9);
        int score = board.calculateScore();
        // Expected: 5 * 9 = 45
        assertEquals(45, score);
    }

    @Test
    public void testCalculateScoreDiagonalLineOfFour() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile7 = new HexTile(7, 7, 7);
        // Diagonal down-right: (1,1) to (4,4).
        // (1,1) is border in row1 (min valid = 1) and (4,4) is border in row4.
        board.placeTile(1, 1, tile7);
        board.placeTile(2, 2, tile7);
        board.placeTile(3, 3, tile7);
        board.placeTile(4, 4, tile7);
        int score = board.calculateScore();
        // Expected: 4 * 7 = 28
        assertEquals(28, score);
    }

    @Test
    public void testCalculateScoreDiagonalLineOfThree() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile5 = new HexTile(5, 5, 5);
        // Diagonal down-left: (1,4), (2,3), (3,2)
        // (1,4) is border (max in row1) but (3,2) is NOT border in row3 (borders are 1 and 4)
        // quindi la linea non è completa edge-to-edge.
        board.placeTile(1, 4, tile5);
        board.placeTile(2, 3, tile5);
        board.placeTile(3, 2, tile5);
        int score = board.calculateScore();
        // Expected: 0 (line not complete edge-to-edge)
        assertEquals(0, score);
    }

    @Test
    public void testCalculateScoreVerticalLineOfFour() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile8 = new HexTile(8, 8, 8);
        // Vertical line in column 2: rows 0-3.
        // Row0 is border, ma row3 (in row3 valid: {1,2,3,4}) non è border (bordi: 1 e 4)
        board.placeTile(0, 2, tile8);
        board.placeTile(1, 2, tile8);
        board.placeTile(2, 2, tile8);
        board.placeTile(3, 2, tile8);
        int score = board.calculateScore();
        // Expected: 0 (line not complete edge-to-edge)
        assertEquals(0, score);
    }

    @Test
    public void testCalculateScoreVerticalLineOfThree() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile6 = new HexTile(6, 6, 6);
        // Vertical line in column 1: rows 1,2,3.
        // In row1 valid: {1,2,3,4} → (1,1) is border (min), in row3 valid: {1,2,3,4} → (3,1) is border.
        board.placeTile(1, 1, tile6);
        board.placeTile(2, 1, tile6);
        board.placeTile(3, 1, tile6);
        int score = board.calculateScore();
        // Expected: 3 * 6 = 18
        assertEquals(18, score);
    }

    @Test
    public void testCalculateScoreMultipleLines() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        // Vertical line in column 2: rows 0,1,2 → would yield 3*3=9, but check borders:
        // For row0 (valid {2,3,4}), (0,2) is border.
        // For row2 (valid {0,1,2,3,4}), borders are 0 and 4; (2,2) is NOT border → thus incomplete vertical line.
        // Therefore, expected score from vertical line = 0.
        HexTile tile3 = new HexTile(3, 3, 3);
        board.placeTile(0, 2, tile3);
        board.placeTile(1, 2, tile3);
        board.placeTile(2, 2, tile3);

        // Diagonal down-right complete: (2,0), (3,1), (4,2)
        // Row2 valid: {0,1,2,3,4} → (2,0) is border (min); row4 valid: {2,3,4} → (4,2) is border (min).
        HexTile tile5 = new HexTile(5, 5, 5);
        board.placeTile(2, 0, tile5);
        board.placeTile(3, 1, tile5);
        board.placeTile(4, 2, tile5);
        int score = board.calculateScore();
        // Expected: only the diagonal line scores: 3*5 = 15.
        assertEquals(15, score);
    }

    @Test
    public void testCalculateScoreIncompleteVerticalLine() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile4 = new HexTile(4, 4, 4);
        HexTile tile7 = new HexTile(7, 7, 7);
        // Vertical line: rows 0 and 1 get tile4, row 2 gets tile7 → line is broken.
        board.placeTile(0, 2, tile4);
        board.placeTile(1, 2, tile4);
        board.placeTile(2, 2, tile7);
        int score = board.calculateScore();
        // Expected: 0
        assertEquals(0, score);
    }

    @Test
    public void testCalculateScoreDiagonalLineIncomplete() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile7 = new HexTile(7, 7, 7);
        // Diagonal down-right incomplete: place tile only in (2,2) and (3,3).
        board.placeTile(2, 2, tile7);
        board.placeTile(3, 3, tile7);
        int score = board.calculateScore();
        // Expected: 0 (line not complete edge-to-edge, length less than 3)
        assertEquals(0, score);
    }
}
