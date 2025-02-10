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
        if (row >= 0 && row < rows && col >= 0 && col < cols) {
            board[row][col] = value;
        } else {
            System.out.println("Invalid position!");
        }
    }

    public int calculateScore() {
        int score = 0;

        // ✅ Rows
        for (int i = 0; i < rows; i++) {
            boolean sameValue = true;
            int value = board[i][0];

            for (int j = 1; j < cols; j++) {
                if (board[i][j] != value) {
                    sameValue = false;
                    break;
                }
            }

            if (sameValue && value != 0)
                score += value * cols;
        }

        // ✅ Cols
        for (int j = 0; j < cols; j++) {
            boolean sameValue = true;
            int value = board[0][j];

            for (int i = 1; i < rows; i++) {
                if (board[i][j] != value) {
                    sameValue = false;
                    break;
                }
            }

            if (sameValue && value != 0)
                score += value * rows;
        }

        return score;
    }


}




