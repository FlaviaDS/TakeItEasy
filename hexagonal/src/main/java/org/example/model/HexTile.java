package org.example.model;

import java.util.List;

public class HexTile {
    private final int topPath;
    private final int rightPath;
    private final int leftPath;

    public HexTile(int topPath, int rightPath, int leftPath) {
        this.topPath = topPath;
        this.rightPath = rightPath;
        this.leftPath = leftPath;
    }

    public List<Integer> getValues() {
        return List.of(topPath, rightPath, leftPath);
    }

    @Override
    public String toString() {
        return "[" + topPath + ", " + rightPath + ", " + leftPath + "]";
    }
}
