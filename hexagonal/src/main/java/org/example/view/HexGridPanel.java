package org.example.view;

import org.example.model.*;
import org.example.utils.TileLoader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

public class HexGridPanel extends JPanel {
    private static final int HEX_RADIUS = 40;
    private static final double SQRT3 = Math.sqrt(3);
    private final HexagonalGameBoard board = new HexagonalGameBoard();
    private final Polygon[] hexagons = new Polygon[19];
    private HexTile currentTile;

    public HexGridPanel() {
        setPreferredSize(new Dimension(800, 800));
        setBackground(new Color(240, 240, 240));

        TileLoader.loadTiles(); // Carica il set di 27 tessere

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });

        // 🔹 Mostra la prima tessera SOLO dopo che la finestra è visibile
        SwingUtilities.invokeLater(this::showCurrentTilePopup);
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

        // Posiziona la tessera corrente sulla board
        board.placeTile(index, currentTile);
        currentTile = null;

        // 🔹 Mostra subito la prossima tessera
        SwingUtilities.invokeLater(this::showCurrentTilePopup);
    }

    private void showCurrentTilePopup() {
        if (currentTile == null) {
            currentTile = TileLoader.drawTile(); // Pesca una nuova tessera
        }

        if (currentTile == null) {
            JOptionPane.showMessageDialog(this, "No more tiles available!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // 🔹 Popup che mostra i numeri della tessera corrente
        String message = "Your next tile:\n" +
                "Top: " + currentTile.getValues().get(0) + "\n" +
                "Right: " + currentTile.getValues().get(1) + "\n" +
                "Left: " + currentTile.getValues().get(2);
        JOptionPane.showMessageDialog(this, message, "Next Tile", JOptionPane.INFORMATION_MESSAGE);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        List<CubeCoordinates> coordinates = new CubeCoordinates(0, 0, 0).navigateSpiral(2);
        Point2D center = new Point2D.Double(getWidth()/2.0, getHeight()/2.0);

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

    private CubeCoordinates rotateCube(CubeCoordinates coord) {
        return new CubeCoordinates(-coord.z(), -coord.x(), -coord.y());
    }

    private Point2D cubeToPixel(CubeCoordinates coord, Point2D center) {
        double x = (double) HexGridPanel.HEX_RADIUS * (3.0/2 * coord.x());
        double y = (double) HexGridPanel.HEX_RADIUS * (SQRT3 * (coord.z() + coord.x()/2.0));

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
            double angle = Math.toRadians(60 * i);
            hex.addPoint(
                    (int) (centerX + (double) HexGridPanel.HEX_RADIUS * Math.cos(angle)),
                    (int) (centerY + (double) HexGridPanel.HEX_RADIUS * Math.sin(angle))
            );
        }
        return hex;
    }

    private static final Map<Integer, Color> numberColors = new HashMap<>();
    static {
        numberColors.put(1, new Color(135, 206, 250)); // Azzurro
        numberColors.put(2, new Color(255, 223, 102)); // Giallino
        numberColors.put(3, new Color(255, 105, 97));  // Rosso chiaro
        numberColors.put(4, new Color(144, 238, 144)); // Verde chiaro
        numberColors.put(5, new Color(255, 182, 193)); // Rosa tenue
        numberColors.put(6, new Color(173, 216, 230)); // Azzurro chiaro
        numberColors.put(7, new Color(238, 130, 238)); // Viola
        numberColors.put(8, new Color(255, 165, 0));   // Arancione
        numberColors.put(9, new Color(220, 20, 60));   // Rosso scuro
    }

    private void drawTileNumbers(Graphics2D g2, Polygon hex, HexTile tile) {
        Rectangle bounds = hex.getBounds();
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        FontMetrics fm = g2.getFontMetrics();

        Point2D[] positions = {
                new Point2D.Double(bounds.getCenterX(), bounds.getMinY() + 20),
                new Point2D.Double(bounds.getMaxX() - 20, bounds.getCenterY()),
                new Point2D.Double(bounds.getMinX() + 20, bounds.getCenterY())
        };

        List<Integer> values = tile.getValues();
        for (int i = 0; i < 3; i++) {
            int value = values.get(i);
            Color highlight = numberColors.getOrDefault(value, Color.GRAY); // Default a GRIGIO se qualcosa va storto

            int textWidth = fm.stringWidth(String.valueOf(value));
            int textHeight = fm.getAscent();
            int x = (int) positions[i].getX() - textWidth / 2;
            int y = (int) positions[i].getY() + textHeight / 4;

            // Disegna il riquadro colorato dietro il numero
            g2.setColor(highlight);
            g2.fillRoundRect(x - 5, y - textHeight, textWidth + 10, textHeight + 5, 10, 10);

            // Disegna il numero sopra il riquadro colorato
            g2.setColor(Color.BLACK);
            g2.drawString(String.valueOf(value), x, y);
        }
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
