package org.example.model;

public class HexagonalGameBoard {
    private final int rows = 5;
    private final int cols = 5;
    private final int[][] board;
    private final boolean[][] validMask;

    public HexagonalGameBoard() {
        board = new int[rows][cols];
        validMask = new boolean[rows][cols];

        // Row 0: valid columns 2, 3, 4 (3 cells)
        for (int j = 2; j <= 4; j++) {
            validMask[0][j] = true;
        }
        // Row 1: valid columns 1, 2, 3, 4 (4 cells)
        for (int j = 1; j <= 4; j++) {
            validMask[1][j] = true;
        }
        // Row 2: valid columns 0, 1, 2, 3, 4 (5 cells)
        for (int j = 0; j < cols; j++) {
            validMask[2][j] = true;
        }
        // Row 3: valid columns 1, 2, 3, 4 (4 cells)
        for (int j = 1; j <= 4; j++) {
            validMask[3][j] = true;
        }
        // Row 4: valid columns 2, 3, 4 (3 cells)
        for (int j = 2; j <= 4; j++) {
            validMask[4][j] = true;
        }
    }

    public boolean isValidPosition(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols)
            return false;
        return validMask[row][col];
    }

    public boolean placeTile(int row, int col, HexTile tile) {
        if (!isValidPosition(row, col))
            return false;
        if (board[row][col] == 0) {
            board[row][col] = tile.getValues()[0];
            return true;
        }
        return false;
    }

    public int getTileValue(int row, int col) {
        if (!isValidPosition(row, col))
            return -1;
        return board[row][col];
    }

    public boolean checkGameOver() {
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < cols; j++)
                if (validMask[i][j] && board[i][j] == 0)
                    return false;
        return true;
    }

    public int calculateScore() {
        int score = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (validMask[i][j])
                    score += board[i][j];
            }
        }
        return score;
    }
}
