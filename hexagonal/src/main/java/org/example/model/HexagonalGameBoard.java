package org.example.model;
import java.util.*;


public class HexagonalGameBoard {
    private static final int BOARD_SIZE = 19;
    private final HexTile[] board = new HexTile[BOARD_SIZE];

    public boolean isBoardFull() {
        for (HexTile tile : board) {
            if (tile == null) return false;
        }
        return true;
    }

    public boolean placeTile(int index, HexTile tile) {
        if (index < 0 || index >= BOARD_SIZE || board[index] != null) {
            return false;
        }
        board[index] = tile;
        return true;
    }

    public HexTile getTile(int index) {
        return (index >= 0 && index < BOARD_SIZE) ? board[index] : null;
    }

    public int calculateScore() {
        List<CubeCoordinates> coords = new CubeCoordinates(0, 0, 0).navigateSpiral(2);
        Map<CubeCoordinates, Integer> cellsMap = new HashMap<>();
        for (int i = 0; i < BOARD_SIZE; i++) {
            cellsMap.put(coords.get(i), i);
        }
        int score = 0;

        // 0 (vertical), 1 (diagonal dx), 2 (diagonal sx)
        for (int dir = 0; dir < 3; dir++) {
            Set<CubeCoordinates> available = new HashSet<>(cellsMap.keySet());
            while (!available.isEmpty()) {
                CubeCoordinates picked = available.iterator().next();
                List<CubeCoordinates> line = buildLine(picked, dir, cellsMap);

                if (isEdgeToEdge(line)) {
                    int lineScore = calculateLineScore(line, dir, cellsMap);
                    score += lineScore;
                }
                line.forEach(available::remove);
            }
        }
        return score;
    }

    private List<CubeCoordinates> buildLine(CubeCoordinates start, int dir, Map<CubeCoordinates, Integer> cellsMap) {
        List<CubeCoordinates> line = new ArrayList<>();
        exploreDirection(line, start, dir, cellsMap);
        exploreDirection(line, start, dir + 3, cellsMap);
        return line;
    }

    private void exploreDirection(List<CubeCoordinates> line, CubeCoordinates curr, int dir, Map<CubeCoordinates, Integer> cellsMap) {
        if (cellsMap.containsKey(curr) && !line.contains(curr)) {
            line.add(curr);
            exploreDirection(line, curr.cubeNeighbor(dir), dir, cellsMap);
        }
    }

    private boolean isEdgeToEdge(List<CubeCoordinates> line) {
        if (line.size() < 3) return false;
        return isOnEdge(line.getFirst()) && isOnEdge(line.getLast());
    }

    private boolean isOnEdge(CubeCoordinates coord) {
        return Math.abs(coord.x()) == 2 || Math.abs(coord.y()) == 2 || Math.abs(coord.z()) == 2;
    }

    private int calculateLineScore(List<CubeCoordinates> line, int dir, Map<CubeCoordinates, Integer> cellsMap) {
        Set<Integer> values = new HashSet<>();
        for (CubeCoordinates cc : line) {
            HexTile tile = board[cellsMap.get(cc)];
            if (tile == null) return 0;
            values.add(tile.getValues().get(dir % 3));
        }
        return (values.size() == 1) ? line.size() * values.iterator().next() : 0;
    }

}