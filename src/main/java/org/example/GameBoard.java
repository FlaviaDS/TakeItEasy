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
}
