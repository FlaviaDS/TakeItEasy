package org.example;

import org.example.model.HexagonalGameBoard;
import org.example.model.HexTile;
import org.example.utils.TileDeckManager;
import org.example.utils.TileLoader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Set;
import java.util.HashSet;
import java.util.stream.IntStream;

public class HexagonalGameBoardTest {

    private HexagonalGameBoard board;
    private TileDeckManager deckManager;

    @BeforeEach
    void setUp() {
        board = new HexagonalGameBoard();
        deckManager = new TileDeckManager();
        deckManager.loadTiles(TileLoader.loadTiles());
    }

    @Test
    void testPlaceTileValidIndex() {
        HexTile tile = deckManager.drawTile();
        assertNotNull(tile, "Tile drawn should not be null");
        assertTrue(board.placeTile(9, tile));
        assertSame(tile, board.getTile(9));
    }

    @Test
    void testPlaceTileInvalidIndex() {
        HexTile tile = deckManager.drawTile();
        assertFalse(board.placeTile(-1, tile));
        assertFalse(board.placeTile(19, tile));
    }

    @Test
    void testFullBoardDetection() {
        for (int i = 0; i < 19; i++) {
            board.placeTile(i, deckManager.drawTile());
        }
        assertTrue(board.isBoardFull());
    }

    @Test
    void testDrawTileRemovesFromDeck() {
        int initialSize = deckManager.getRemainingTiles();
        HexTile tile = deckManager.drawTile();
        int afterDrawSize = deckManager.getRemainingTiles();
        assertNotNull(tile);
        assertEquals(initialSize - 1, afterDrawSize);
    }

    @Test
    void testTilePlacementOrder() {
        Set<String> uniqueTiles = new HashSet<>();
        for (int i = 0; i < 27; i++) {
            HexTile tile = deckManager.drawTile();
            assertNotNull(tile, "Tile should not be null");
            assertTrue(uniqueTiles.add(tile.toString()), "Duplicate tile: " + tile);
        }
    }

    @Test
    void testCannotBePlacedTwice() {
        HexTile tile1 = new HexTile(1, 2, 3);
        HexTile tile2 = new HexTile(4, 5, 6);
        board.placeTile(5, tile1);
        assertFalse(board.placeTile(5, tile2), "Should not be able to place tile on occupied hexagon");
        assertEquals(tile1, board.getTile(5), "Original tile should remain");
    }

    @Test
    void testIncompleteDiagonalLine() {
        int[] indices = {4, 9, 14};
        for (int i : indices) board.placeTile(i, new HexTile(5, 4, 7));
        assertEquals(0, board.calculateScore());
    }

    @Test
    void testScoreForIncompleteLine() {
        board.placeTile(0, new HexTile(3, 2, 1));
        board.placeTile(4, new HexTile(5, 2, 7));
        assertEquals(0, board.calculateScore(), "Incomplete line no score");
    }

    @Test
    void testScoreForIncompleteLine2() {
        board.placeTile(1, new HexTile(3, 2, 1));
        board.placeTile(2, new HexTile(5, 2, 7));
        assertEquals(0, board.calculateScore(), "Incomplete line no score");
    }

    @Test
    void testDifferentValuesNOScore() {
        int[] line = {0, 4, 9, 14, 18};
        IntStream.range(0, line.length).forEach(i -> board.placeTile(line[i], new HexTile(i+1, i+1, i+1)));
        assertEquals(0, board.calculateScore());
    }

    @Test
    void testMinimumScoringLineLength() {
        board.placeTile(0, new HexTile(5,8,7));
        board.placeTile(1, new HexTile(5,4,7));
        board.placeTile(2, new HexTile(1,8,7));
        assertEquals(21, board.calculateScore());
    }

    @Test
    void testScoreForFullVerticalLine() {
        int[] verticalLine = {0, 4, 9, 14, 18};
        HexTile[] tiles = {
                new HexTile(7, 1, 3),
                new HexTile(6, 1, 4),
                new HexTile(2, 1, 8),
                new HexTile(7, 1, 2),
                new HexTile(2, 1, 4)
        };
        for (int i = 0; i < verticalLine.length; i++) {
            board.placeTile(verticalLine[i], tiles[i]);
        }
        assertEquals(5, board.calculateScore(), "Incorrect score for full vertical line");
    }

    @Test
    void testSingleCompleteLine() {
        int[] indices = {0, 4, 9, 14, 18};
        HexTile[] tiles = {
                new HexTile(7, 9, 2),
                new HexTile(6, 9, 4),
                new HexTile(2, 9, 8),
                new HexTile(7, 9, 3),
                new HexTile(6, 9, 3)
        };
        for (int i = 0; i < indices.length; i++) {
            board.placeTile(indices[i], tiles[i]);
        }
        assertEquals(45, board.calculateScore(), "Incorrect score for completed line");
    }

    @Test
    void testScoreForIntersectingLines() {
        int[] verticalLine = {0, 4, 9, 14, 18};
        int[] diagonalLine = {4, 9, 14};
        for (int index : verticalLine) {
            board.placeTile(index, new HexTile(3, 7, 5));
        }
        for (int index : diagonalLine) {
            board.placeTile(index, new HexTile(3, 7, 5));
        }
        assertEquals(35, board.calculateScore(), "Incorrect score for intersecting lines");
    }

    @Test
    void testScoreForIntersectingLines2() {
        int[] verticalLine = {0, 4, 9, 14, 18};
        for (int index : verticalLine) {
            board.placeTile(index, new HexTile(4, 6, 2));
        }
        board.placeTile(4, new HexTile(2, 5, 7));
        board.placeTile(9, new HexTile(2, 5, 7));
        board.placeTile(14, new HexTile(2, 5, 7));
        int expectedScore = 30;
        assertEquals(expectedScore, board.calculateScore(), "Incorrect score for intersecting lines");
    }

    @Test
    void testScoreForMixedNumbersInLine() {
        board.placeTile(7, new HexTile(5, 3, 1));
        board.placeTile(8, new HexTile(6, 4, 2));
        board.placeTile(9, new HexTile(7, 5, 3));
        board.placeTile(10, new HexTile(2, 8, 6));
        board.placeTile(11, new HexTile(3, 7, 9));
        assertEquals(0, board.calculateScore(), "Lines with different numbers should not count");
    }

    @Test
    void testScoreForEdges() {
        board.placeTile(0, new HexTile(1, 9, 8));
        board.placeTile(18, new HexTile(2, 7, 6));
        assertEquals(0, board.calculateScore(), "Two separate tiles should not create a valid line");
    }

    @Test
    void testRemainingTilesAfterDraw() {
        int initialSize = deckManager.getRemainingTiles();
        for (int i = 0; i < 5; i++) {
            deckManager.drawTile();
        }
        assertEquals(initialSize - 5, deckManager.getRemainingTiles(),
                "The number of remaining tiles is incorrect after 5 draws.");
    }

    @Test
    void testMaximumTiles() {
        for (int i = 0; i < 19; i++) {
            assertTrue(board.placeTile(i, deckManager.drawTile()),
                    "Tile was not placed correctly at position " + i);
        }
        assertTrue(board.isBoardFull(), "The board should be full.");
    }

    @Test
    void testPartialBoardState() {
        board.placeTile(3, new HexTile(7, 7, 7));
        board.placeTile(7, new HexTile(3, 3, 3));
        board.placeTile(15, new HexTile(5, 5, 5));

        assertNotNull(board.getTile(3), "Tile at position 3 should not be null.");
        assertNotNull(board.getTile(7), "Tile at position 7 should not be null.");
        assertNotNull(board.getTile(15), "Tile at position 15 should not be null.");
        assertNull(board.getTile(10), "Tile at position 10 should be null.");
    }

    @Test
    void testValidTileGeneration() {
        for (int i = 0; i < 27; i++) {
            HexTile tile = deckManager.drawTile();
            assertNotNull(tile, "Drawn tile should not be null.");
            assertEquals(3, tile.getValues().size(), "Each tile must have exactly 3 values.");
        }
    }

    @Test
    void testAllTilesUsed() {
        Set<String> usedTiles = new HashSet<>();
        // Place all 19 tiles
        for (int i = 0; i < 19; i++) {
            HexTile tile = deckManager.drawTile();
            assertNotNull(tile, "Tile should not be null before the deck is empty.");
            usedTiles.add(tile.toString());
            board.placeTile(i, tile);
        }
        assertEquals(8, deckManager.getRemainingTiles(), "Deck should have 8 remaining tiles after a full board placement.");
        // no duplicate tiles
        assertEquals(19, usedTiles.size(), "All 19 placed tiles should be unique.");
    }
}