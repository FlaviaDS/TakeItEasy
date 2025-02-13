package org.example;

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

    // Modifica: se la richiesta è fuori dai limiti, restituisce 0
    public int getTileValue(int row, int col) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return 0;
        }
        return board[row][col];
    }

    public boolean placeTile(int row, int col, Tile tile) {
        if (row < 0 || row >= rows || col < 0 || col >= cols) {
            return false;
        }
        // Impedisce la sovrascrittura: solo se la cella è 0 (vuota)
        if (board[row][col] == 0) {
            board[row][col] = tile.getValues()[0]; // Usa il valore frontale della tessera
            return true;
        }
        return false;
    }

    public boolean checkGameOver() {
        for (int[] row : board) {
            for (int cell : row) {
                if (cell == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public int calculateScore() {
        int score = 0;
        // Controlla le righe
        for (int i = 0; i < rows; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                score += board[i][0] * 3;
            }
        }
        // Controlla le colonne
        for (int j = 0; j < cols; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                score += board[0][j] * 3;
            }
        }
        // Diagonale principale
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            score += board[0][0] * 3;
        }
        // Diagonale secondaria
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            score += board[0][2] * 3;
        }
        return score;
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
}
