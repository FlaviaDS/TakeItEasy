package org.example.model;

public class GameBoard {
    private final int rows, cols;
    private final int[][] board;

    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new int[rows][cols];
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Returns the value at the specified cell.
     * If the position is out of bounds, returns 0.
     */
    public int getTileValue(int row, int col) {
        if (isValidPosition(row, col)) {
            return 0;
        }
        return board[row][col];
    }

    /**
     * Attempts to place a tile on the board.
     * Returns true if placement was successful (i.e. if the target cell is empty), false otherwise.
     */
    public boolean placeTile(int row, int col, Tile tile) {
        if (isValidPosition(row, col)) {
            return false;
        }
        // Only place if cell is empty (0)
        if (board[row][col] == 0) {
            board[row][col] = tile.getValues()[0]; // Uses the front value of the tile
            return true;
        }
        return false;
    }

    /**
     * Checks if the board is completely filled.
     */
    public boolean checkGameOver() {
        for (int[] curRow : board) {
            for (int cell : curRow) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Calculates the total score by summing up the scores for rows, columns, and diagonals.
     * For each line (row, column, or diagonal), if all three cells have the same non-zero value,
     * the score for that line is the common value multiplied by 3.
     * NOTE: This method is specific to a 3x3 grid.
     */
    public int calculateScore() {
        int score = 0;
        // Score for rows
        for (int i = 0; i < rows; i++) {
            score += scoreForLine(board[i][0], board[i][1], board[i][2]);
        }
        // Score for columns
        for (int j = 0; j < cols; j++) {
            score += scoreForLine(board[0][j], board[1][j], board[2][j]);
        }
        // Main diagonal
        score += scoreForLine(board[0][0], board[1][1], board[2][2]);
        // Secondary diagonal
        score += scoreForLine(board[0][2], board[1][1], board[2][0]);
        return score;
    }

    /**
     * Prints the board to the console.
     */
    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    // ------------ Private Helper Methods ------------

    /**
     * Checks if the given position is within board boundaries.
     */
    private boolean isValidPosition(int row, int col) {
        return row < 0 || row >= rows || col < 0 || col >= cols;
    }

    /**
     * Returns the score for a line.
     * If all three values are equal and non-zero, returns the common value multiplied by 3;
     * otherwise, returns 0.
     */
    private int scoreForLine(int a, int b, int c) {
        return (a != 0 && a == b && b == c) ? a * 3 : 0;
    }
}
