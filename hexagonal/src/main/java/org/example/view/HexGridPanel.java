package org.example.view;

import org.example.model.HexTile;
import org.example.model.HexagonalGameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * HexGridPanel arranges 19 cells (3-4-5-4-3) in a diamond shape (pointy‑top),
 * then rotates the entire diamond 90° anticlockwise by computing the final positions
 * of each cell relative to the panel center.
 * In this approach, the polygons are built in their final coordinates so that click detection works correctly.
 */
public class HexGridPanel extends JPanel {
    private static final int ROWS = 5;
    private static final int COLS = 5;

    // VALID_COLUMNS: valid column indices for each row (diamond shape)
    private static final int[][] VALID_COLUMNS = {
            {2, 3, 4},      // Row 0: 3 cells
            {1, 2, 3, 4},   // Row 1: 4 cells
            {0, 1, 2, 3, 4},// Row 2: 5 cells
            {1, 2, 3, 4},   // Row 3: 4 cells
            {2, 3, 4}       // Row 4: 3 cells
    };

    /**
     * ROW_OFFSETS: horizontal offsets (in "cell units") for each row in the unrotated grid.
     * For a row with 5 possible cell positions:
     * - Row 0 (3 cells): offset = (5-3)/2 = 1.0
     * - Row 1 (4 cells): offset = (5-4)/2 = 0.5
     * - Row 2 (5 cells): offset = 0.0
     * - Row 3 (4 cells): offset = 0.5
     * - Row 4 (3 cells): offset = 1.0
     */
    private static final double[] ROW_OFFSETS = {1.0, 0.5, 0.0, 0.5, 1.0};

    private final HexagonalGameBoard board;
    private Polygon[][] hexPolys; // Final computed polygons in data space

    public HexGridPanel() {
        board = new HexagonalGameBoard();
        setPreferredSize(new Dimension(600, 600));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                processClick(e.getX(), e.getY());
            }
        });
    }

    /**
     * processClick uses the final computed polygons (in final coordinates)
     * to detect a click and place a tile.
     */
    private void processClick(int x, int y) {
        if (hexPolys == null) return;
        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (!board.isValidPosition(r, c)) continue;
                Polygon poly = hexPolys[r][c];
                if (poly != null && poly.contains(x, y)) {
                    placeTileAt(r, c);
                    repaint();
                    if (board.checkGameOver()) {
                        int finalScore = board.calculateScore();
                        board.printBoard();
                        JOptionPane.showMessageDialog(this, "Game Over! Score = " + finalScore);
                    }
                    return;
                }
            }
        }
    }

    private void placeTileAt(int row, int col) {
        HexTile tile = generateRandomTile();
        String[] rotationOptions = {"0°", "120°", "240°"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Choose tile rotation:\n" + tile,
                "Tile Rotation",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                rotationOptions,
                rotationOptions[0]
        );
        for (int i = 0; i < choice; i++) {
            tile.rotate();
        }
        if (!board.placeTile(row, col, tile)) {
            JOptionPane.showMessageDialog(this, "Invalid move (occupied or out of bounds).");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        hexPolys = computePolygonsFinal();
        Graphics2D g2 = (Graphics2D) g;

        for (int r = 0; r < ROWS; r++) {
            for (int c = 0; c < COLS; c++) {
                if (!board.isValidPosition(r, c)) continue;
                Polygon poly = hexPolys[r][c];
                if (poly == null) continue;

                g2.setColor(new Color(255, 240, 150));
                g2.fill(poly);

                g2.setColor(Color.DARK_GRAY);
                g2.draw(poly);

                HexTile tile = board.getTile(r, c);
                if (tile != null) {
                    drawTileNumbers(g2, poly.getBounds(), tile);
                }
            }
        }
    }


    private void drawTileNumbers(Graphics2D g2, Rectangle bounds, HexTile tile) {
        int[] values = tile.getValues();
        g2.getFontMetrics();
        int cx = bounds.x + bounds.width / 2;
        int cy = bounds.y + bounds.height / 2;
        g2.setColor(Color.BLACK);
        g2.drawString(String.valueOf(values[0]), cx - 10, cy - 20);
        g2.drawString(String.valueOf(values[1]), cx + 10, cy + 10);
        g2.drawString(String.valueOf(values[2]), cx - 30, cy + 10);
    }

    /**
     * Computes the final polygons for the grid.
     * The method:
     * 1. Computes the unrotated cell centers in a local coordinate system.
     * 2. Computes the diamond center as the center of the cell (2,2) in unrotated space.
     * 3. For each valid cell, calculates its delta from the diamond center.
     * 4. Rotates the delta by 90° anticlockwise.
     * 5. Adds the rotated delta to the panel center to obtain the final cell center.
     * 6. Creates the hexagon polygon for that final center.
     */
    private Polygon[][] computePolygonsFinal() {
        Polygon[][] polys = new Polygon[ROWS][COLS];
        int panelW = getWidth();
        int panelH = getHeight();

        // Use a divisor to determine hexagon size (experiment to adjust)
        double radius = Math.min(panelW, panelH) / 12.0;
        double hs = radius * Math.sqrt(3); // horizontal spacing per cell unit
        double vs = radius * 1.5;          // vertical spacing per row

        // Compute unrotated positions in a local coordinate system (without fixed pixel offsets)
        // For each row r and each valid cell index k (0-based within that row)
        // The local center = ( (ROW_OFFSETS[r] + k) * hs, r * vs )
        // Diamond center: For row 2 (which has 5 cells) the center cell is the 3rd cell (k=2).
        double diamondCenterX = (ROW_OFFSETS[2] + 2) * hs;
        double diamondCenterY = 2 * vs;
        // Panel center:
        double panelCenterX = panelW / 2.0;
        double panelCenterY = panelH / 2.0;

        for (int r = 0; r < ROWS; r++) {
            int[] validCols = VALID_COLUMNS[r];
            for (int k = 0; k < validCols.length; k++) {
                int c = validCols[k];
                // Unrotated local center for this cell:
                double localX = (ROW_OFFSETS[r] + k) * hs;
                double localY = r * vs;
                // Delta from diamond center:
                double deltaX = localX - diamondCenterX;
                double deltaY = localY - diamondCenterY;
                // Rotate delta by 90° anticlockwise: newDelta = (-deltaY, deltaX)
                double rotatedDeltaX = -deltaY;
                // Final cell center = panel center + rotated delta
                double finalX = panelCenterX + rotatedDeltaX;
                double finalY = panelCenterY + deltaX;
                polys[r][c] = createHexPolygon(finalX, finalY, radius);
            }
        }
        return polys;
    }

    /**
     * Creates a pointy-top hexagon polygon centered at (cx, cy) with radius r.
     * Orientation: angle = 60*i - 30 (top vertex at (cx, cy - r)).
     */
    private Polygon createHexPolygon(double cx, double cy, double r) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angleDeg = 60 * i;
            double angleRad = Math.toRadians(angleDeg);
            double x = cx + r * Math.cos(angleRad);
            double y = cy + r * Math.sin(angleRad);
            hex.addPoint((int) x, (int) y);
        }
        return hex;
    }

    private HexTile generateRandomTile() {
        int v1 = (int) (Math.random() * 9) + 1;
        int v2 = (int) (Math.random() * 9) + 1;
        int v3 = (int) (Math.random() * 9) + 1;
        return new HexTile(v1, v2, v3);
    }
}
