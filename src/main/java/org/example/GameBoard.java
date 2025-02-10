package org.example; // Aggiungi il package corretto se necessario

public class GameBoard {
    private int rows, cols;
    private int[][] board; // Matrice di gioco

    public GameBoard(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
        board = new int[rows][cols]; // Inizializza una griglia vuota
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
            System.out.println("Posizione non valida!");
        }
    }

    public int calculateScore() {
        int score = 0;
        for (int i = 0; i < rows; i++) {
            boolean sameValue = true;
            int value = board[i][0];

            for (int j = 1; j < cols; j++) {
                if (board[i][j] == value) {
                    continue;
                }
                sameValue = false;
                break;
            }

            if (!sameValue || value == 0) continue;  // If the values are the same, sum them
            score += value * cols;

        }
        return score;
    }

}




