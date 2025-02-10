package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// ✅ Import GameBoard


public class GameBoardTest {

    @Test
    public void testInitializeBoard() {
        GameBoard board = new GameBoard(3, 3); // Grid 3x3
        assertEquals(3, board.getRows());
        assertEquals(3, board.getCols());
    }

    @Test
    public void testCalculateScore() {
        GameBoard board = new GameBoard(3, 3);

        // Insert tiles to simulate a play
        board.placeTile(0, 0, 5);
        board.placeTile(0, 1, 5);
        board.placeTile(0, 2, 5);

        int score = board.calculateScore();
        assertEquals(15, score);  // 5 + 5 + 5 = 15
    }

    @Test
    public void testNoScoreForDifferentValuesInRow() {
        GameBoard board = new GameBoard(3, 3);

        board.placeTile(0, 0, 5);
        board.placeTile(0, 1, 3);
        board.placeTile(0, 2, 5);  // Different values in same row

        int score = board.calculateScore();
        assertEquals(0, score);  // No score
    }

    @Test
    public void testEmptyBoardScore() {
        GameBoard board = new GameBoard(3, 3);

        int score = board.calculateScore();
        assertEquals(0, score);  // ✅ No score in empty grid
    }

    @Test
    public void testIncompleteRowScore() {
        GameBoard board = new GameBoard(3, 3);

        board.placeTile(0, 0, 7);
        board.placeTile(0, 1, 7);
        // La cella (0, 2) è vuota

        int score = board.calculateScore();
        assertEquals(0, score);  // ✅ No score for incomplete rows
    }

    @Test
    public void testScoreForMultipleCompleteRows() {
        GameBoard board = new GameBoard(3, 3);

        board.placeTile(0, 0, 4);
        board.placeTile(0, 1, 4);
        board.placeTile(0, 2, 4);  // First row: 4 + 4 + 4 = 12

        board.placeTile(1, 0, 2);
        board.placeTile(1, 1, 2);
        board.placeTile(1, 2, 2);  // Second row: 2 + 2 + 2 = 6

        int score = board.calculateScore();
        assertEquals(18, score);  // ✅ 12 + 6 = 18
    }

    @Test
    public void testCalculateScoreColumn() {
        GameBoard board = new GameBoard(3, 3);

        board.placeTile(0, 0, 4);
        board.placeTile(1, 0, 4);
        board.placeTile(2, 0, 4);  // Complete Column w/ value 4

        int score = board.calculateScore();
        assertEquals(12, score);  // 4 + 4 + 4 = 12
    }

    @Test
    public void testCalculateScoreForMainDiagonal() {
        GameBoard board = new GameBoard(3, 3);

        board.placeTile(0, 0, 3);
        board.placeTile(1, 1, 3);
        board.placeTile(2, 2, 3);  // Main diagonal complete with value 3
        int score = board.calculateScore();
        assertEquals(9, score);  // 3 + 3 + 3 = 9
    }

    @Test
    public void testCalculateScoreForSecondaryDiagonal() {
        GameBoard board = new GameBoard(3, 3);

        board.placeTile(0, 2, 6);
        board.placeTile(1, 1, 6);
        board.placeTile(2, 0, 6);  // Second diagonal complete with value 6

        int score = board.calculateScore();
        assertEquals(18, score);  // 6 + 6 + 6 = 18
    }



}


