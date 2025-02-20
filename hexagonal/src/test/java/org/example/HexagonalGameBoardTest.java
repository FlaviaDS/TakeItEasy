package org.example;

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
        for (int i = 0; i < 19; i++) {
            board.placeTile(i, new HexTile(1, 1, 1));
        }
        assertTrue(board.isBoardFull());
    }

    @Test
    void testScoringForEdgeToEdgeLines() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(5, 5, 5);
        // edge-to-edge diagonal (7, 8, 9, 10, 11)
        int[] indices = {7, 8, 9, 10, 11};
        for (int i : indices) board.placeTile(i, tile);
        assertEquals(25, board.calculateScore()); // 5 * 5 = 25
        // edge-to-edge vertical (0, 4, 9, 14, 18)
        board = new HexagonalGameBoard();
        tile = new HexTile(8, 8, 8);
        indices = new int[]{0, 4, 9, 14, 18};
        for (int i : indices) board.placeTile(i, tile);
        assertEquals(40, board.calculateScore()); // 5 * 8 = 40
    }

    @Test
    void testPlacingDifferentTiles() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile1 = new HexTile(3, 4, 5);
        HexTile tile2 = new HexTile(1, 2, 3);
        assertTrue(board.placeTile(0, tile1));
        assertTrue(board.placeTile(1, tile2));
        assertEquals(List.of(3, 4, 5), board.getTile(0).getValues());
        assertEquals(List.of(1, 2, 3), board.getTile(1).getValues());
    }

    @Test
    void testTileRotations() {
        HexTile tile = new HexTile(2, 4, 6);
        tile.rotate();
        assertEquals(List.of(4, 6, 2), tile.getValues());
        tile.rotate();
        assertEquals(List.of(6, 2, 4), tile.getValues());
        tile.rotate();
        assertEquals(List.of(2, 4, 6), tile.getValues()); // Torna allo stato iniziale
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
    void testIncompleteLine() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(4, 4, 4);
        // Non edge-to-edge
        int[] indices = {4, 9, 14};
        for (int i : indices) board.placeTile(i, tile);
        assertEquals(0, board.calculateScore());
        board = new HexagonalGameBoard();
        board.placeTile(0, tile);
        board.placeTile(4, tile);
        assertEquals(0, board.calculateScore());
    }

    @Test
    void testHorizontalLineScoring() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(7, 7, 7);
        int[] indices = {3, 4, 5, 6};
        for (int i : indices) board.placeTile(i, tile);
        assertEquals(28, board.calculateScore()); // 4 * 7 = 28
    }

    @Test
    void testBrokenLine() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        HexTile tile = new HexTile(4, 4, 4);
        int[] indices = {4, 9, 14};
        for (int i : indices) board.placeTile(i, tile);
        assertEquals(0, board.calculateScore());
    }
}
