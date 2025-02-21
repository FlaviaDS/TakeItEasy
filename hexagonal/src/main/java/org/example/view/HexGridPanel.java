package org.example.view;

import org.example.control.GameController;
import org.example.model.HexTile;
import org.example.model.CubeCoordinates;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class HexGridPanel extends JPanel {
    private static final double SQRT3 = Math.sqrt(3);
    private int hexRadius = 40;
    private final GameController controller;
    private final Polygon[] hexagons = new Polygon[19];

    public HexGridPanel() {
        setPreferredSize(new Dimension(900, 800));
        setBackground(new Color(240, 240, 240));
        controller = new GameController();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                updateHexSize();
                repaint();
            }
        });

        repaint();
    }

    private void updateHexSize() {
        int minSize = Math.min(getWidth(), getHeight());
        hexRadius = minSize / 15;
    }

    private void handleClick(int x, int y) {
        for (int i = 0; i < 19; i++) {
            if (hexagons[i] != null && hexagons[i].contains(x, y)) {
                if (controller.placeTile(i)) {
                    repaint();
                    checkGameOver();
                }
                return;
            }
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<CubeCoordinates> coordinates = new CubeCoordinates(0, 0, 0).navigateSpiral(2);
        Point2D center = new Point2D.Double(getWidth()/2.0 - hexRadius * 3, getHeight()/2.0);

        for (int i = 0; i < 19; i++) {
            CubeCoordinates rotatedCoord = rotateCube(coordinates.get(i));
            Point2D position = cubeToPixel(rotatedCoord, center);
            hexagons[i] = createHexagon(position.getX(), position.getY());
            g2.setColor(controller.getTileAt(i) != null ? new Color(255, 255, 200) : new Color(200, 220, 255));
            g2.fill(hexagons[i]);
            g2.setColor(new Color(80, 80, 80));
            g2.draw(hexagons[i]);

            if (controller.getTileAt(i) != null) drawTileNumbers(g2, hexagons[i], controller.getTileAt(i));
        }
        drawCurrentTilePreview(g2);
    }

    private void drawCurrentTilePreview(Graphics2D g2) {
        HexTile currentTile = controller.getNextTile();
        if (currentTile == null) return;
        int sideX = getWidth() - hexRadius * 4;
        int sideY = getHeight() / 2 - hexRadius;
        Polygon previewHex = createHexagon(sideX, sideY);
        g2.setColor(new Color(255, 255, 200));
        g2.fill(previewHex);
        g2.setColor(new Color(80, 80, 80));
        g2.draw(previewHex);
        drawTileNumbers(g2, previewHex, currentTile);
    }

    private CubeCoordinates rotateCube(CubeCoordinates coord) {
        return new CubeCoordinates(-coord.z(), -coord.x(), -coord.y());
    }

    private Point2D cubeToPixel(CubeCoordinates coord, Point2D center) {
        double x = hexRadius * (3.0/2 * coord.x());
        double y = hexRadius * (SQRT3 * (coord.z() + coord.x()/2.0));

        return new Point2D.Double(center.getX() + x, center.getY() + y);
    }

    private Polygon createHexagon(double centerX, double centerY) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angle = Math.toRadians(60 * i);
            hex.addPoint(
                    (int) (centerX + hexRadius * Math.cos(angle)),
                    (int) (centerY + hexRadius * Math.sin(angle))
            );
        }
        return hex;
    }

    private static final Map<Integer, Color> numberColors = new HashMap<>();
    static {
        numberColors.put(1, new Color(135, 206, 250));
        numberColors.put(2, new Color(255, 223, 102));
        numberColors.put(3, new Color(255, 105, 97));
        numberColors.put(4, new Color(144, 238, 144));
        numberColors.put(5, new Color(255, 182, 193));
        numberColors.put(6, new Color(173, 216, 230));
        numberColors.put(7, new Color(238, 130, 238));
        numberColors.put(8, new Color(255, 165, 0));
        numberColors.put(9, new Color(220, 20, 60));
    }

    private void drawTileNumbers(Graphics2D g2, Polygon hex, HexTile tile) {
        Rectangle bounds = hex.getBounds();
        g2.setFont(new Font("Arial", Font.BOLD, hexRadius / 3));
        FontMetrics fm = g2.getFontMetrics();

        Point2D[] positions = {
                new Point2D.Double(bounds.getCenterX(), bounds.getMinY() + (double) hexRadius / 2),
                new Point2D.Double(bounds.getMaxX() - (double) hexRadius / 3, bounds.getCenterY()),
                new Point2D.Double(bounds.getMinX() + (double) hexRadius / 3, bounds.getCenterY())
        };

        List<Integer> values = tile.getValues();
        for (int i = 0; i < 3; i++) {
            int value = values.get(i);
            Color highlight = numberColors.getOrDefault(value, Color.GRAY);

            int textWidth = fm.stringWidth(String.valueOf(value));
            int textHeight = fm.getAscent();
            int x = (int) positions[i].getX() - textWidth / 2;
            int y = (int) positions[i].getY() + textHeight / 4;

            g2.setColor(highlight);
            g2.fillRoundRect(x - 5, y - textHeight, textWidth + 10, textHeight + 5, 10, 10);

            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(value), x, y);
        }
    }

    private void checkGameOver() {
        if (controller.isGameOver()) {
            int score = controller.getScore();
            JOptionPane.showMessageDialog(
                    this,
                    "Game Over! Score: " + score,
                    "End Game",
                    JOptionPane.INFORMATION_MESSAGE
            );
        }
    }
}
