package org.example.view;

import org.example.model.HexTile;
import org.example.model.HexagonalGameBoard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HexGridPanel extends JPanel {
    private final int rows = 5;
    private final int cols = 5;
    private final HexagonalGameBoard board;

    public HexGridPanel() {
        board = new HexagonalGameBoard();
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });
        setPreferredSize(new Dimension(600, 600));
    }

    private void handleClick(int mouseX, int mouseY) {
        Polygon[][] polygons = computeHexPolygons();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!board.isValidPosition(r, c)) continue;
                Polygon poly = polygons[r][c];
                if (poly != null && poly.contains(mouseX, mouseY)) {
                    placeRandomTile(r, c);
                    repaint();
                    if (board.checkGameOver()) {
                        JOptionPane.showMessageDialog(this, "Game Over! Score = " + board.calculateScore());
                    }
                    return;
                }
            }
        }
    }

    private void placeRandomTile(int row, int col) {
        HexTile tile = generateRandomTile();
        String[] rotations = {"0°", "120°", "240°"};
        int choice = JOptionPane.showOptionDialog(
                this,
                "Scegli la rotazione del tile:\n" + tile,
                "Rotazione Tile",
                JOptionPane.YES_NO_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                rotations,
                rotations[0]
        );
        for (int i = 0; i < choice; i++) {
            tile.rotate();
        }
        boolean placed = board.placeTile(row, col, tile);
        if (!placed) {
            JOptionPane.showMessageDialog(this, "Mossa non valida (cella occupata o fuori limiti).");
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Polygon[][] polygons = computeHexPolygons();
        Graphics2D g2 = (Graphics2D) g;
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!board.isValidPosition(r, c)) continue;
                Polygon poly = polygons[r][c];
                if (poly == null) continue;
                g2.setColor(Color.LIGHT_GRAY);
                g2.fill(poly);
                g2.setColor(Color.DARK_GRAY);
                g2.draw(poly);
                int val = board.getTileValue(r, c);
                if (val > 0) {
                    Rectangle bounds = poly.getBounds();
                    int cx = bounds.x + bounds.width / 2;
                    int cy = bounds.y + bounds.height / 2;
                    String text = String.valueOf(val);
                    FontMetrics fm = g2.getFontMetrics();
                    int textWidth = fm.stringWidth(text);
                    int textHeight = fm.getAscent();
                    g2.setColor(Color.BLACK);
                    g2.drawString(text, cx - textWidth / 2, cy + textHeight / 2);
                }
            }
        }
    }

    private Polygon[][] computeHexPolygons() {
        Polygon[][] result = new Polygon[rows][cols];
        int w = getWidth();
        int h = getHeight();

        double radius = Math.min(w, h) / 12.0; // Modifica questo valore per aumentare o diminuire gli esagoni
        double horizontalSpacing = radius * Math.sqrt(3);
        double verticalSpacing = radius * 1.5;

        double offsetX = 50; // Sposta l'intera griglia orizzontalmente
        double offsetY = 50; // Sposta l'intera griglia verticalmente

        // rowOffsets: modifica questi valori per spostare le singole righe
        double[] rowOffsets = {0.0, 0.5, 1.0, 0.5, 0.0};

        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (!board.isValidPosition(r, c)) {
                    result[r][c] = null;
                    continue;
                }
                double x = offsetX + (c + rowOffsets[r]) * horizontalSpacing;
                double y = offsetY + r * verticalSpacing + radius;
                result[r][c] = createHexPolygon(x, y, radius);
            }
        }
        return result;
    }

    private Polygon createHexPolygon(double cx, double cy, double r) {
        Polygon hex = new Polygon();
        for (int i = 0; i < 6; i++) {
            double angleDeg = 60 * i - 30;
            double angleRad = Math.toRadians(angleDeg);
            double x = cx + r * Math.cos(angleRad);
            double y = cy + r * Math.sin(angleRad);
            hex.addPoint((int)x, (int)y);
        }
        return hex;
    }

    private HexTile generateRandomTile() {
        int v1 = (int)(Math.random() * 9) + 1;
        int v2 = (int)(Math.random() * 9) + 1;
        int v3 = (int)(Math.random() * 9) + 1;
        return new HexTile(v1, v2, v3);
    }
}
