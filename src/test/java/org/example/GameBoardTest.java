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
        board.placeTile(0, 0, 5);
        board.placeTile(0, 1, 5);
        board.placeTile(0, 2, 5);
        int score = board.calculateScore();
        assertEquals(15, score);
    }

    @Test
    public void testNoScoreForDifferentValuesInRow() {
        GameBoard board = new GameBoard(3, 3);
        board.placeTile(0, 0, 5);
        board.placeTile(0, 1, 3);
        board.placeTile(0, 2, 5);
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
        board.placeTile(0, 0, 7);
        board.placeTile(0, 1, 7);
        int score = board.calculateScore();
        assertEquals(0, score);
    }

    @Test
    public void testScoreForMultipleCompleteRows() {
        GameBoard board = new GameBoard(3, 3);
        board.placeTile(0, 0, 4);
        board.placeTile(0, 1, 4);
        board.placeTile(0, 2, 4);
        board.placeTile(1, 0, 2);
        board.placeTile(1, 1, 2);
        board.placeTile(1, 2, 2);
        int score = board.calculateScore();
        assertEquals(18, score);
    }

    @Test
    public void testCalculateScoreColumn() {
        GameBoard board = new GameBoard(3, 3);
        board.placeTile(0, 0, 4);
        board.placeTile(1, 0, 4);
        board.placeTile(2, 0, 4);
        int score = board.calculateScore();
        assertEquals(12, score);
    }

    @Test
    public void testCalculateScoreForMainDiagonal() {
        GameBoard board = new GameBoard(3, 3);
        board.placeTile(0, 0, 3);
        board.placeTile(1, 1, 3);
        board.placeTile(2, 2, 3);
        int score = board.calculateScore();
        assertEquals(9, score);
    }

    @Test
    public void testCalculateScoreForSecondaryDiagonal() {
        GameBoard board = new GameBoard(3, 3);
        board.placeTile(0, 2, 6);
        board.placeTile(1, 1, 6);
        board.placeTile(2, 0, 6);
        int score = board.calculateScore();
        assertEquals(18, score);
    }

    @Test
    public void testGameOverCondition() {
        GameBoard board = new GameBoard(3, 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board.placeTile(i, j, 1);
            }
        }
        assertTrue(board.checkGameOver());
    }

    // Edge Case Test
    @Test
    public void testOutOfBoundsPlacement() {
        GameBoard board = new GameBoard(3, 3);
        board.placeTile(4, 4, 5);
        assertEquals(0, board.getTileValue(4, 4));
    }


    // Resilience Test
    @Test
    public void testOverwritePrevention() {
        GameBoard board = new GameBoard(3, 3);
        board.placeTile(0, 0, 3);
        board.placeTile(0, 0, 9);
        assertEquals(3, board.getTileValue(0, 0));
    }

    // Full Game Simulation Test
    @Test
    public void testFullGameSimulation() {
        GameBoard board = new GameBoard(3, 3);

        // Piazzamento con valori uguali per righe/colonne coerenti
        board.placeTile(0, 0, 5);
        board.placeTile(0, 1, 5);
        board.placeTile(0, 2, 5);

        board.placeTile(1, 0, 4);
        board.placeTile(1, 1, 4);
        board.placeTile(1, 2, 4);

        board.placeTile(2, 0, 3);
        board.placeTile(2, 1, 3);
        board.placeTile(2, 2, 3);

        assertTrue(board.checkGameOver());
        assertTrue(board.calculateScore() > 0);
    }

}
