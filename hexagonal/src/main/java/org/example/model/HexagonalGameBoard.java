package org.example.model;

public class HexagonalGameBoard {
    private final int rows = 5;
    private final int cols = 5;
    private final HexTile[][] board;
    private final boolean[][] validMask;
    private static final int[][] VALID_COLUMNS = {
            {2, 3, 4},      // Row 0
            {1, 2, 3, 4},   // Row 1
            {0, 1, 2, 3, 4},// Row 2
            {1, 2, 3, 4},   // Row 3
            {2, 3, 4}       // Row 4
    };

    public HexagonalGameBoard() {
        board = new HexTile[rows][cols];
        validMask = new boolean[rows][cols];
        for (int j = 2; j <= 4; j++) validMask[0][j] = true;
        for (int j = 1; j <= 4; j++) validMask[1][j] = true;
        for (int j = 0; j < cols; j++) validMask[2][j] = true;
        for (int j = 1; j <= 4; j++) validMask[3][j] = true;
        for (int j = 2; j <= 4; j++) validMask[4][j] = true;
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

    public void printBoard() {
        System.out.println("Current Board:");
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!validMask[i][j])
                    System.out.print("X  ");
                else {
                    HexTile tile = board[i][j];
                    System.out.print((tile != null ? tile.getValues()[0] : ".") + "  ");
                }
            }
            System.out.println();
        }
    }

    public int calculateScore() {
        int score = 0;
        // Consider directions: vertical, diagonal down-right, diagonal down-left.
        int[][] directions = {
                {1, 0},
                {1, 1},
                {1, -1}
        };

        System.out.println("===== Calculating Score (edge-to-edge) =====");
        for (int[] d : directions) {
            int dr = d[0], dc = d[1];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (!isValidPosition(r, c)) continue;
                    int prevR = r - dr, prevC = c - dc;
                    // The start cell must be at the edge: previous cell out-of-bounds or empty.
                    boolean startEdge = !isValidPosition(prevR, prevC) || getTileValue(prevR, prevC) == 0;
                    if (!startEdge) continue;
                    int commonValue = getTileValue(r, c);
                    if (commonValue == 0) continue;
                    int length = 0;
                    int curR = r, curC = c;
                    StringBuilder lineCoords = new StringBuilder();
                    while (isValidPosition(curR, curC) && getTileValue(curR, curC) == commonValue) {
                        length++;
                        lineCoords.append("(").append(curR).append(",").append(curC).append(") ");
                        curR += dr;
                        curC += dc;
                    }
                    int lastR = curR - dr, lastC = curC - dc;
                    // The line is complete if both start and last cells are borders.
                    boolean complete = isBorderCell(r, c) && isBorderCell(lastR, lastC);
                    System.out.println("Checking line from (" + r + "," + c + ") with value "
                            + commonValue + ": " + lineCoords + " length=" + length + " complete=" + complete);
                    if (length >= 3 && complete) {
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

    private boolean isBorderCell(int row, int col) {
        if (!isValidPosition(row, col)) return false;
        if (row == 0 || row == rows - 1) return true;
        int[] valid = VALID_COLUMNS[row];
        int min = valid[0];
        int max = valid[valid.length - 1];
        return col == min || col == max;
    }
}