package org.example;
import org.example.model.HexagonalGameBoard;
import org.example.model.HexTile;
import org.example.utils.TileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import java.util.HashSet;

public class HexagonalGameBoardTest {

    private HexagonalGameBoard board;

    @BeforeEach
    void setUp() {
        board = new HexagonalGameBoard();
        TileLoader.loadTiles();
    }

    @Test
    void testPlaceTileValidIndex() {
        HexTile tile = TileLoader.drawTile();
        assertNotNull(tile, "Tile drawn should not be null");
        assertTrue(board.placeTile(9, tile));
        assertSame(tile, board.getTile(9));
    }

    @Test
    void testPlaceTileInvalidIndex() {
        HexTile tile = TileLoader.drawTile();
        assertFalse(board.placeTile(-1, tile));
        assertFalse(board.placeTile(19, tile));
    }

    @Test
    void testFullBoardDetection() {
        for (int i = 0; i < 19; i++) {
            board.placeTile(i, TileLoader.drawTile());
        }
        assertTrue(board.isBoardFull());
    }

    @Test
    void testDrawTileRemovesFromDeck() {
        int initialSize = TileLoader.getRemainingTiles();
        HexTile tile = TileLoader.drawTile();
        int afterDrawSize = TileLoader.getRemainingTiles();
        assertNotNull(tile);
        assertEquals(initialSize - 1, afterDrawSize);
    }

    @Test
    void testNoMoreTiles() {
        for (int i = 0; i < 27; i++) {
            TileLoader.drawTile();
        }
        assertNull(TileLoader.drawTile(), "Should return null when deck is empty");
    }

    @Test
    void testTilePlacementOrderAndUniqueness() {
        Set<String> uniqueTiles = new HashSet<>();
        for (int i = 0; i < 27; i++) {
            HexTile tile = TileLoader.drawTile();
            assertNotNull(tile, "Tile should not be null");
            assertTrue(uniqueTiles.add(tile.toString()), "Duplicate tile: " + tile);
        }
    }

    @Test
    void testIncompleteDiagonalLine() {
        int[] indices = {4, 9, 14};
        for (int i : indices) board.placeTile(i, new HexTile(4, 4, 4));
        assertEquals(0, board.calculateScore());
    }

    @Test
    void testTileCannotBePlacedTwice() {
        HexTile tile1 = new HexTile(1, 2, 3);
        HexTile tile2 = new HexTile(4, 5, 6);
        board.placeTile(5, tile1);
        assertFalse(board.placeTile(5, tile2), "Should not be able to place tile on occupied hexagon");
        assertEquals(tile1, board.getTile(5), "Original tile should remain");
    }

    @Test
    void testScoreForFullVerticalLine() {
        int[] verticalLine = {0, 4, 9, 14, 18};
        for (int index : verticalLine) {
            board.placeTile(index, new HexTile(5, 5, 5));
        }
        assertEquals(25, board.calculateScore(), "Incorrect score for full vertical line");
    }

    @Test
    void testScoreForIncompleteLine() {
        board.placeTile(0, new HexTile(5, 5, 5));
        board.placeTile(4, new HexTile(5, 5, 5));
        assertEquals(0, board.calculateScore(), "Incomplete line no score");
    }

    @Test
    void testSingleCompleteLine() {
        int[] indices = {0, 4, 9, 14, 18};
        for (int i : indices) {
            board.placeTile(i, new HexTile(4, 4, 4));
        }
        assertEquals(20, board.calculateScore(), "Incorrect score for completed line");
    }

    @Test
    void testScoreForIntersectingLines() {
        int[] verticalLine = {0, 4, 9, 14, 18};
        int[] diagonalLine = {4, 9, 14};
        for (int index : verticalLine) {
            board.placeTile(index, new HexTile(6, 6, 6));
        }
        for (int index : diagonalLine) {
            board.placeTile(index, new HexTile(6, 6, 6));
        }
        assertEquals(30, board.calculateScore(), "Incorrect score for intersecting lines");
    }

    @Test
    void testScoreForIntersectingLines2() {
        int[] verticalLine = {0, 4, 9, 14, 18};
        for (int index : verticalLine) {
            board.placeTile(index, new HexTile(6, 6, 6));
        }
        board.placeTile(4, new HexTile(4, 6, 4));
        board.placeTile(9, new HexTile(4, 6, 4));
        board.placeTile(14, new HexTile(4, 6, 4));
        int expectedScore = 30;
        assertEquals(expectedScore, board.calculateScore(), "Incorrect score for intersecting lines");
    }

    @Test
    void testScoreForMixedNumbersInLine() {
        board.placeTile(7, new HexTile(2, 2, 2));
        board.placeTile(8, new HexTile(2, 2, 2));
        board.placeTile(9, new HexTile(3, 3, 3));
        board.placeTile(10, new HexTile(2, 2, 2));
        board.placeTile(11, new HexTile(2, 2, 2));
        assertEquals(0, board.calculateScore(), "Lines with different numbers should not count");
    }

    @Test
    void testScoreForEdgeCases() {
        board.placeTile(0, new HexTile(9, 9, 9));
        board.placeTile(18, new HexTile(9, 9, 9));
        assertEquals(0, board.calculateScore(), "Two separate tiles should not create a valid line");
    }
}
