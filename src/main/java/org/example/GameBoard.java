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
        for (int i = 0; i < rows; i++) { // Scorriamo le righe
            for (int j = 0; j < cols; j++) { // Scorriamo le colonne
                System.out.print(board[i][j] + " "); // Stampiamo il valore della cella
            }
            System.out.println(); // Andiamo a capo dopo ogni riga
        }
        System.out.println(); // Spazio extra per separare le stampe successive
    }

    public void placeTile(int row, int col, int value) {
        if (row >= 0 && row < rows && col >= 0 && col < cols) { // Controlliamo che la posizione sia valida
            board[row][col] = value;
        } else {
            System.out.println("Posizione non valida!");
        }
    }

    public int calculateScore() {
        int score = 0;
        for (int i = 0; i < rows; i++) {          // Scorriamo ogni riga
            boolean sameValue = true;
            int value = board[i][0];

            for (int j = 1; j < cols; j++) {      // Verifichiamo se tutta la riga ha lo stesso valore
                if (board[i][j] != value) {
                    sameValue = false;
                    break;
                }
            }

            if (sameValue && value != 0) {        // Se la riga è omogenea, sommiamo i valori
                score += value * cols;
            }
        }
        return score;
    }


}




