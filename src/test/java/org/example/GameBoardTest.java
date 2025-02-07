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

}


