package org.example;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
// ✅ Importa GameBoard


public class GameBoardTest {

    @Test
    public void testInitializeBoard() {
        GameBoard board = new GameBoard(3, 3); // Griglia 3x3
        assertEquals(3, board.getRows());
        assertEquals(3, board.getCols());
    }
}


