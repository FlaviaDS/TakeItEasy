package org.example.model;

public class HexagonalGrid {
    private final int size;
    private final int spacing;
    private static final int GRID_SIZE = 15;

    public HexagonalGrid(int size, int spacing) {
        this.size = size;
        this.spacing = spacing;
    }

    public float[][][] getHexagonalGrid() {
        float[][][] grid = new float[GRID_SIZE][GRID_SIZE][2];
        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                grid[i][j][0] = i * this.size * this.spacing;
                grid[i][j][1] = j * this.size * this.spacing;
            }
        }
        return grid;
    }
}