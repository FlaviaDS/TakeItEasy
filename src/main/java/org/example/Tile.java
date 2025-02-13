package org.example;

import java.util.Arrays;

public class Tile {
    private final int[] values;

    public Tile(int v1, int v2, int v3) {
        values = new int[]{v1, v2, v3};
    }

    public void rotate() {
        int temp = values[0];
        values[0] = values[1];
        values[1] = values[2];
        values[2] = temp;
    }

    public int[] getValues() {
        return values;
    }

    @Override
    public String toString() {
        return Arrays.toString(values);
    }
}
