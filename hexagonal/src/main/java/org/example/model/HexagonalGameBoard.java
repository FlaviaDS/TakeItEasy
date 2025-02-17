package org.example.model;

public class HexagonalGameBoard {
    private final int rows = 5;
    private final int cols = 5;
    private final HexTile[][] board;
    private final boolean[][] validMask;

    public HexagonalGameBoard() {
        board = new HexTile[rows][cols];
        validMask = new boolean[rows][cols];

        // Row 0: columns 2..4 (3 cells)
        for (int j = 2; j <= 4; j++) {
            validMask[0][j] = true;
        }
        // Row 1: columns 1..4 (4 cells)
        for (int j = 1; j <= 4; j++) {
            validMask[1][j] = true;
        }
        // Row 2: columns 0..4 (5 cells)
        for (int j = 0; j < cols; j++) {
            validMask[2][j] = true;
        }
        // Row 3: columns 1..4 (4 cells)
        for (int j = 1; j <= 4; j++) {
            validMask[3][j] = true;
        }
        // Row 4: columns 2..4 (3 cells)
        for (int j = 2; j <= 4; j++) {
            validMask[4][j] = true;
        }
    }

    public boolean isValidPosition(int row, int col) {
        return row >= 0 && row < rows && col >= 0 && col < cols && validMask[row][col];
    }

    public boolean placeTile(int row, int col, HexTile tile) {
        if (!isValidPosition(row, col) || board[row][col] != null) return false;
        board[row][col] = tile;
        return true;
    }

    public HexTile getTile(int row, int col) {
        return isValidPosition(row, col) ? board[row][col] : null;
    }

    // Modifica: se la cella è vuota, restituisce 0 anziché -1.
    public int getTileValue(int row, int col) {
        if (!isValidPosition(row, col)) return -1;
        return board[row][col] != null ? board[row][col].getValues()[0] : 0;
    }

    public boolean checkGameOver() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (validMask[i][j] && board[i][j] == null)
                    return false;
            }
        }
        return true;
    }

    public int calculateScore() {
        int score = 0;
        int[][] directions = {
                {1, 0},   // Vertical
                {1, 1},   // Diagonal down-right
                {1, -1}   // Diagonal down-left
        };

        System.out.println("===== Calculating Score =====");

        for (int[] d : directions) {
            int dr = d[0];
            int dc = d[1];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (!isValidPosition(r, c)) continue;
                    int prevR = r - dr;
                    int prevC = c - dc;
                    if (isValidPosition(prevR, prevC) && getTileValue(prevR, prevC) != 0) continue;
                    int commonValue = getTileValue(r, c);
                    if (commonValue == 0) continue;
                    int length = 0;
                    int curR = r, curC = c;
                    StringBuilder lineCoords = new StringBuilder();
                    while (isValidPosition(curR, curC)) {
                        int cellValue = getTileValue(curR, curC);
                        if (cellValue == 0 || cellValue != commonValue) break;
                        length++;
                        lineCoords.append("(").append(curR).append(",").append(curC).append(") ");
                        curR += dr;
                        curC += dc;
                    }
                    System.out.println("Checking line from (" + r + "," + c + ") with value " + commonValue + ": " + lineCoords);
                    if (length >= 2) {
                        int lineScore = length * commonValue;
                        score += lineScore;
                        System.out.println("Scored " + lineScore + " points from line: " + lineCoords);
                    }
                }
            }
        }

        System.out.println("===== Final Score: " + score + " =====");
        printBoard();
        return score;
    }

    public void printBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!validMask[i][j]) {
                    System.out.print("X  ");
                } else {
                    HexTile tile = board[i][j];
                    System.out.print((tile != null ? tile.getValues()[0] : ".") + "  ");
                }
            }
            System.out.println();
        }
    }
}
