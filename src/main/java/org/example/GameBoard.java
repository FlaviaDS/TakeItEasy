package org.example;

public class GameBoard {
    private int rows, cols;
    private int[][] board;

    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new int[rows][cols]; // Grid Init
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public void printBoard() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public void placeTile(int row, int col, int value) {
        if (row >= 0 && row < rows && col >= 0 && col < cols && board[row][col] == 0) {
            board[row][col] = value;
            if (checkGameOver()) {
                System.out.println("Final score: " + calculateScore());
            }
        } else {
            System.out.println("Invalid move!");
        }
    }


    public int calculateScore() {
        int score = 0;

        // ✅ Score for rows
        for (int i = 0; i < rows; i++) {
            boolean sameValue = true;
            int value = board[i][0];

            for (int j = 1; j < cols; j++) {
                if (board[i][j] != value) {
                    sameValue = false;
                    break;
                }
            }

            if (sameValue && value != 0) {
                score += value * cols;
            }
        }

        // ✅ Score fro columns
        for (int j = 0; j < cols; j++) {
            boolean sameValue = true;
            int value = board[0][j];

            for (int i = 1; i < rows; i++) {
                if (board[i][j] != value) {
                    sameValue = false;
                    break;
                }
            }

            if (sameValue && value != 0) {
                score += value * rows;
            }
        }

        // ✅ Score for main diagonal (\)
        boolean sameDiagonalMain = true;
        int valueDiagonalMain = board[0][0];
        for (int i = 1; i < rows; i++) {
            if (board[i][i] != valueDiagonalMain) {
                sameDiagonalMain = false;
                break;
            }
        }
        if (sameDiagonalMain && valueDiagonalMain != 0) {
            score += valueDiagonalMain * rows;
        }

        // ✅ Score for second diagonal (/)
        boolean sameDiagonalSecondary = true;
        int valueDiagonalSecondary = board[0][cols - 1];
        for (int i = 1; i < rows; i++) {
            if (board[i][cols - 1 - i] != valueDiagonalSecondary) {
                sameDiagonalSecondary = false;
                break;
            }
        }
        if (sameDiagonalSecondary && valueDiagonalSecondary != 0) {
            score += valueDiagonalSecondary * rows;
        }

        return score;
    }

    private boolean isGameOver = false; // Game status

    public boolean checkGameOver() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (board[i][j] == 0) { // IF still an empty cell
                    return false;
                }
            }
        }
        isGameOver = true; // All cells are full
        return true;
    }

    public int getTileValue(int row, int col) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            return board[row][col];
        }
        return 0; // Restituisce 0 se fuori dai limiti
    }

}




