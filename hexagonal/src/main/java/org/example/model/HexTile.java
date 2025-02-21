package org.example.model;
import java.util.ArrayList;
import java.util.List;

public class HexTile {
    private final List<Integer> values;

    public HexTile(int v1, int v2, int v3) {
        values = new ArrayList<>();
        values.add(v1);
        values.add(v2);
        values.add(v3);
    }

    public List<Integer> getValues() {
        return values;
    }

    @Override
    public String toString() {
        return values.toString();
    }
}