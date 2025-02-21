package org.example;
import org.example.model.BoardUtils;
import org.example.model.HexagonalGameBoard;
import org.example.model.HexTile;
import org.example.utils.TileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

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
        assertNotNull(tile, "Tile drawn should no be null");
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
    void testScoreCalculationWithFullBoard() {
        for (int i = 0; i < 19; i++) {
            board.placeTile(i, new HexTile(3, 3, 3));
        }
        int expectedScore = 171;
        assertEquals(expectedScore, board.calculateScore());
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
        assertNull(TileLoader.drawTile(), "Should give back null.");
    }

    @Test
    void testPartialBoardScore() {
        board.placeTile(0, new HexTile(5, 5, 5));
        board.placeTile(1, new HexTile(5, 5, 5));
        assertEquals(0, board.calculateScore());
    }

    @Test
    void testEdgeToEdgeScoring() {
        int[] indices = {7, 8, 9, 10, 11};
        for (int i : indices) board.placeTile(i, new HexTile(3, 3, 3));
        assertEquals(15, board.calculateScore()); // 5 * 3 = 15
    }

    @Test
    void testIncompleteDiagonalLine() {
        int[] indices = {4, 9, 14};
        for (int i : indices) board.placeTile(i, new HexTile(4, 4, 4));
        assertEquals(0, board.calculateScore());
    }

    @Test
    void testTilePlacementOrder() {
        HexTile firstTile = TileLoader.drawTile();
        HexTile secondTile = TileLoader.drawTile();
        assertNotEquals(firstTile, secondTile, "Tiles drawn have to be different.");
    }

    @Test
    void testBoardUtilsConversion() {
        assertEquals(0, BoardUtils.getIndexFromRowCol(0, 2));
        assertEquals(4, BoardUtils.getIndexFromRowCol(1, 2));
        assertEquals(9, BoardUtils.getIndexFromRowCol(2, 2));
        assertEquals(-1, BoardUtils.getIndexFromRowCol(4, 1));
    }

    @Test
    void testSingleCompleteLine() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        int[] indices = {0, 4, 9, 14, 18};
        for (int i : indices) {
            board.placeTile(i, new HexTile(4, 4, 4));
        }
        assertEquals(20, board.calculateScore()); // 4 * 5 = 20
    }

    @Test
    void testIncompleteLines() {
        HexagonalGameBoard board = new HexagonalGameBoard();
        int[] almostLine = {0, 4, 9, 14};
        for (int i : almostLine) {
            board.placeTile(i, new HexTile(7, 7, 7));
        }
        assertEquals(0, board.calculateScore());
    }

}
