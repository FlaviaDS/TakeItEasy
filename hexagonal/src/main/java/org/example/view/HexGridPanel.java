package org.example.view;

import org.example.control.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class HexGridPanel extends JPanel {
    private final GameController controller;
    private final HexGridRenderer renderer;

    public HexGridPanel() {
        setPreferredSize(new Dimension(900, 800));
        setBackground(new Color(240, 240, 240));

        controller = new GameController();
        renderer = new HexGridRenderer(controller);

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                handleClick(e.getX(), e.getY());
            }
        });

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                renderer.updateHexSize(getWidth(), getHeight());
                repaint();
            }
        });

        repaint();
    }

    private void handleClick(int x, int y) {
        int index = renderer.getHexIndexAt(x, y);
        if (index != -1 && controller.placeTile(index)) {
            repaint();
            checkGameOver();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        renderer.draw(g, getWidth(), getHeight());
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
