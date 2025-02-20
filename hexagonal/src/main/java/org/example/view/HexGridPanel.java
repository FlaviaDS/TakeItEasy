package org.example.view;

import org.example.model.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;

public class HexGridPanel extends JPanel {
    private static final int HEX_RADIUS = 40;
    private static final double SQRT3 = Math.sqrt(3);
    private final HexagonalGameBoard board = new HexagonalGameBoard();
    private final Polygon[] hexagons = new Polygon[19];
    private HexTile currentTile;

    public HexGridPanel() {
        setPreferredSize(new Dimension(800, 800));
        setBackground(new Color(240, 240, 240));
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
    }

    private void handleClick(int x, int y) {
        for (int i = 0; i < 19; i++) {
            if (hexagons[i] != null && hexagons[i].contains(x, y)) {
                placeTile(i);
                repaint();
                checkGameOver();
                return;
            }
        }
    }

    private void placeTile(int index) {
        if (board.getTile(index) != null) return;

        if (currentTile == null) {
            currentTile = generateRandomTile();
        }

        String[] rotations = {"0°", "120°", "240°"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Select Rotation for:\n" + currentTile,
                "Tile Rotation",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.PLAIN_MESSAGE,
                null,
                rotations,
                rotations[0]
        );

        if (choice < 0 || choice > 2) {
            currentTile = null;
            return;
        }

        for (int i = 0; i < choice; i++) currentTile.rotate();
        board.placeTile(index, currentTile);
        currentTile = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<CubeCoordinates> coordinates = new CubeCoordinates(0, 0, 0).navigateSpiral(2);
        Point2D center = new Point2D.Double(getWidth()/2.0, getHeight()/2.0);

        // print grid rotated 90°
        for (int i = 0; i < 19; i++) {
            CubeCoordinates rotatedCoord = rotateCube(coordinates.get(i));
            Point2D position = cubeToPixel(rotatedCoord, center);
            hexagons[i] = createHexagon(position.getX(), position.getY());

            g2.setColor(board.getTile(i) != null ? new Color(255, 255, 200) : new Color(200, 220, 255));
            g2.fill(hexagons[i]);
            g2.setColor(new Color(80, 80, 80));
            g2.draw(hexagons[i]);

            if (board.getTile(i) != null) drawTileNumbers(g2, hexagons[i], board.getTile(i));
        }
    }

    // Rotation of cubic coordinates 90°
    private CubeCoordinates rotateCube(CubeCoordinates coord) {
        return new CubeCoordinates(-coord.z(), -coord.x(), -coord.y());
    }

    private Point2D cubeToPixel(CubeCoordinates coord, Point2D center) {
        // flat-top
        double x = (double) HexGridPanel.HEX_RADIUS * (3.0/2 * coord.x());
        double y = (double) HexGridPanel.HEX_RADIUS * (SQRT3 * (coord.z() + coord.x()/2.0));

        // Centering
        double totalWidth = 4 * (double) HexGridPanel.HEX_RADIUS * 1.5;
        double totalHeight = 5 * (double) HexGridPanel.HEX_RADIUS * SQRT3;
        return new Point2D.Double(
                center.getX() + x - totalWidth/2 + (double) HexGridPanel.HEX_RADIUS,
                center.getY() + y - totalHeight/2 + (double) HexGridPanel.HEX_RADIUS * 0.8
        );
    }

    private Polygon createHexagon(double centerX, double centerY) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i); // Flat-top
            hex.addPoint(
                    (int) (centerX + (double) HexGridPanel.HEX_RADIUS * Math.cos(angle)),
                    (int) (centerY + (double) HexGridPanel.HEX_RADIUS * Math.sin(angle))
            );
        }
        return hex;
    }

    private void drawTileNumbers(Graphics2D g2, Polygon hex, HexTile tile) {
        Rectangle bounds = hex.getBounds();
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 14));

        // Alignment of numbers
        Point2D[] positions = {
                new Point2D.Double(bounds.getCenterX(), bounds.getMinY() + 20),    // top
                new Point2D.Double(bounds.getMaxX() - 20, bounds.getCenterY()),    // right
                new Point2D.Double(bounds.getMinX() + 20, bounds.getCenterY())     // left
        };

        List<Integer> values = tile.getValues();
        for (int i = 0; i < 3; i++) {
            g2.drawString(
                    String.valueOf(values.get(i)),
                    (int) positions[i].getX(),
                    (int) positions[i].getY()
            );
        }
    }

    private HexTile generateRandomTile() {
        return new HexTile(
                (int) (Math.random() * 5) + 1,
                (int) (Math.random() * 5) + 1,
                (int) (Math.random() * 5) + 1
        );
    }

    private void checkGameOver() {
        if (board.isBoardFull()) {
            int score = board.calculateScore();
            JOptionPane.showMessageDialog(
                    this,
                    "Game Over! Score: " + score,
                    "End Game",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}