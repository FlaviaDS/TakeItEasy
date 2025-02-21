package org.example.model;
import java.util.ArrayList;
import java.util.List;

public record CubeCoordinates(int x, int y, int z) {
    public CubeCoordinates {
        if (x + y + z != 0) {
            throw new IllegalArgumentException("x + y + z must be 0");
        }
    }

    private static final CubeCoordinates[] DIRECTIONS = {
            new CubeCoordinates(1, -1, 0),
            new CubeCoordinates(1, 0, -1),
            new CubeCoordinates(0, 1, -1),
            new CubeCoordinates(-1, 1, 0),
            new CubeCoordinates(-1, 0, 1),
            new CubeCoordinates(0, -1, 1)
    };

    public CubeCoordinates cubeNeighbor(int direction) {
        CubeCoordinates d = DIRECTIONS[direction % 6];
        return new CubeCoordinates(x + d.x(), y + d.y(), z + d.z());
    }


    public List<CubeCoordinates> navigateSpiral(int radius) {
        List<CubeCoordinates> results = new ArrayList<>();
        for (int dx = -radius; dx <= radius; dx++) {
            for (int dy = Math.max(-radius, -dx - radius); dy <= Math.min(radius, -dx + radius); dy++) {
                int dz = -dx - dy;
                results.add(new CubeCoordinates(x + dx, y + dy, z + dz));
            }
        }
        return results;
    }

}
