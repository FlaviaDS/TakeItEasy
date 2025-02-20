package org.example;

import org.example.model.BoardUtils;
import org.example.model.HexagonalGameBoard;
import org.example.model.HexTile;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class HexagonalGameBoardTest {

    @Test
    void testPlaceTileValidIndex() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(1, 2, 3);
        assertTrue(board.placeTile(9, tile));
        assertSame(tile, board.getTile(9));
    }

    @Test
    void testPlaceTileInvalidIndex() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(1, 2, 3);

        assertFalse(board.placeTile(-1, tile));
        assertFalse(board.placeTile(19, tile));
    }

    @Test
    void testFullBoardDetection() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        for(int i = 0; i < 19; i++) {
            board.placeTile(i, new HexTile(1,1,1));
        }
        assertTrue(board.isBoardFull());
    }

    @Test
    void testVerticalScoring() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(5, 5, 5);
        // edge-to-edge (indexes: 7, 8, 9, 10, 11)
        int[] indices = {7, 8, 9, 10, 11};
        for (int i : indices) board.placeTile(i, tile);
        assertEquals(25, board.calculateScore()); // 5 * 5 = 25
    }

    @Test
    void testTileRotation() {
        HexTile tile = new HexTile(1,2,3);
        assertEquals(List.of(1,2,3), tile.getValues());

        tile.rotate();
        assertEquals(List.of(2,3,1), tile.getValues());

        tile.rotate();
        assertEquals(List.of(3,1,2), tile.getValues());
    }

    @Test
    void testBoardUtilsConversion() {
        // Mapping after 90°
        assertEquals(0, BoardUtils.getIndexFromRowCol(0, 2)); // (0,2) → index 0
        assertEquals(4, BoardUtils.getIndexFromRowCol(1, 2)); // (1,2) → index 4
        assertEquals(9, BoardUtils.getIndexFromRowCol(2, 2)); // (2,2) → index 9 (center)
        assertEquals(-1, BoardUtils.getIndexFromRowCol(4, 1)); // Invalid
    }

    @Test
    void testPartialBoardScore() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        board.placeTile(0, new HexTile(5,5,5));
        board.placeTile(1, new HexTile(5,5,5));
        assertEquals(0, board.calculateScore());
    }

    /*Scores*/

    @Test
    void testVerticalLineOfFive() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(8, 8, 8);
        // edge-to-edge (0, 4, 9, 14, 18)
        int[] indices = {0, 4, 9, 14, 18};
        for (int i : indices) board.placeTile(i, tile);
        assertEquals(40, board.calculateScore()); // 5 * 8 = 40
    }

    @Test
    void testEdgeToEdgeScoring() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        // diagonal complete (7, 8, 9, 10, 11)
        int[] indices = {7, 8, 9, 10, 11};
        for (int i : indices) board.placeTile(i, new HexTile(3,3,3));
        assertEquals(15, board.calculateScore()); // 5 * 3 = 15
    }

    @Test
    void testMixedValuesInLine() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        board.placeTile(7, new HexTile(2, 2, 2));
        board.placeTile(8, new HexTile(2, 2, 2));
        board.placeTile(9, new HexTile(3, 3, 3));
        assertEquals(0, board.calculateScore());
    }

    @Test
    void testIncompleteDiagonalLine() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(4, 4, 4);
        // non edge-to-edge
        int[] indices = {4, 9, 14};
        for (int i : indices) board.placeTile(i, tile);
        assertEquals(0, board.calculateScore());
    }

    @Test
    void testLineWithEmptyTiles() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(4, 4, 4);
        board.placeTile(0, tile);
        board.placeTile(4, tile);
        // Missing tile in 9 → incomplete line
        assertEquals(0, board.calculateScore());
    }
}