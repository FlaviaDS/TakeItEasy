package org.example.view;

import org.example.control.GameController;
import org.example.model.HexTile;
import org.example.model.CubeCoordinates;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HexGridRenderer {
    private static final double SQRT3 = Math.sqrt(3);
    private static final int HEX_COUNT = 19;
    private final GameController controller;
    private int hexRadius = 40;
    private final Polygon[] hexagons;

    public HexGridRenderer(GameController controller) {
        this.controller = controller;
        this.hexagons = new Polygon[HEX_COUNT];
    }

    public void updateHexSize(int panelWidth, int panelHeight) {
        int minSize = Math.min(panelWidth, panelHeight);
        hexRadius = minSize / 15;
    }

    public void draw(Graphics g, int panelWidth, int panelHeight) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<CubeCoordinates> coordinates = new CubeCoordinates(0, 0, 0).navigateSpiral(2);
        Point2D center = new Point2D.Double(panelWidth / 2.0 - hexRadius * 3, panelHeight / 2.0);

        for (int i = 0; i < HEX_COUNT; i++) {
            CubeCoordinates rotatedCoord = rotateCube(coordinates.get(i));
            Point2D position = cubeToPixel(rotatedCoord, center);
            hexagons[i] = createHexagon(position.getX(), position.getY());

            g2.setColor(controller.getTileAt(i) != null ? new Color(255, 255, 200) : new Color(200, 220, 255));
            g2.fill(hexagons[i]);
            g2.setColor(new Color(80, 80, 80));
            g2.draw(hexagons[i]);

            if (controller.getTileAt(i) != null) {
                drawTileNumbers(g2, hexagons[i], controller.getTileAt(i));
            }
        }

        drawCurrentTilePreview(g2, panelWidth, panelHeight);
    }

    public int getHexIndexAt(int x, int y) {
        for (int i = 0; i < hexagons.length; i++) {
            if (hexagons[i] != null && hexagons[i].contains(x, y)) {
                return i;
            }
        }
        return -1;
    }

    private void drawCurrentTilePreview(Graphics2D g2, int panelWidth, int panelHeight) {
        HexTile currentTile = controller.getNextTile();
        if (currentTile == null) return;

        int sideX = panelWidth - hexRadius * 4;
        int sideY = panelHeight / 2 - hexRadius;

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
        double x = hexRadius * (3.0 / 2 * coord.x());
        double y = hexRadius * (SQRT3 * (coord.z() + coord.x() / 2.0));

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
                new Point2D.Double(bounds.getCenterX(), bounds.getMinY() + hexRadius / 3.0),
                new Point2D.Double(bounds.getMaxX() - hexRadius / 3.0, bounds.getCenterY()),
                new Point2D.Double(bounds.getMinX() + hexRadius / 3.0, bounds.getCenterY())
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
}
