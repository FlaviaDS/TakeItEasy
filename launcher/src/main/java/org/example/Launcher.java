package org.example;

import org.example.view.HexGridPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Hexagonal Game");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new GridLayout(1, 1, 10, 10)); // Solo un pulsante ora
            JButton btnHex = getButton(frame);
            frame.add(btnHex);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

    private static JButton getButton(JFrame frame) {
        JButton btnHex = new JButton("Start TakeItEasy");
        btnHex.setFont(new Font("Arial", Font.BOLD, 16));
        btnHex.addActionListener((ActionEvent _) -> {
            JFrame hexFrame = new JFrame("Hexagonal Game");
            hexFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            hexFrame.add(new HexGridPanel());
            hexFrame.pack();
            hexFrame.setLocationRelativeTo(null);
            hexFrame.setVisible(true);
            frame.dispose();
        });
        return btnHex;
    }
}
