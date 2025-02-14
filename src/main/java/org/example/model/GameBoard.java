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
     * If the position is out of bounds, it returns 0.
     */
    public int getTileValue(int row, int col) {
        if (!isValidPosition(row, col)) {
            return 0;
        }
        return board[row][col];
    }

    /**
     * Attempts to place a tile at the specified position.
     * Returns true if successfull, false otherwise.
     */
    public boolean placeTile(int row, int col, Tile tile) {
        if (!isValidPosition(row, col)) {
            return false;
        }
        // Prevents overwritting: only places if the cell is empty (0)
        if (board[row][col] == 0) {
            board[row][col] = tile.getValues()[0]; // Uses the front value of the tile
            return true;
        }
        return false;
    }

    /**
     * Checks if all the cells are filled.
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
     * Calcluates the total score by summing up the score for rows, columns, and diagonals.
     * NOTE: This method is specific to a 3x3 grid.
     */
    public int calculateScore() {
        int score = 0;
        // Calculate score for rows
        for (int i = 0; i < rows; i++) {
            score += scoreForLine(board[i][0], board[i][1], board[i][2]);
        }
        // Calculate score for columns
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
     * Prints the game board on the console.
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
     * Checks if the given position (row, col) is within the board boundaries.
     */
    private boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols;
    }

    /**
     * Returns the score for a line (row, column or diagonal).
     * If all three values are equal, returns the value multiplied by 3, else returns 0.
     */
    private int scoreForLine(int a, int b, int c) {
        return (a == b && b == c) ? a * 3 : 0;
    }
}
