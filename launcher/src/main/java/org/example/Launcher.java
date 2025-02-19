package org.example;

import org.example.view.HexGridPanel;
import org.example.view.GameBoardUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Choose Game Version");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(300, 200);
            frame.setLayout(new GridLayout(2, 1, 10, 10));
            JButton btnClassic = new JButton("3x3 Rectangular Version");
            btnClassic.setFont(new Font("Arial", Font.BOLD, 16));
            btnClassic.addActionListener((ActionEvent _) -> {
                JFrame classicFrame = new JFrame("Rectangular Game");
                classicFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                classicFrame.add(new GameBoardUI());
                classicFrame.pack();
                classicFrame.setLocationRelativeTo(null);
                classicFrame.setVisible(true);
                frame.dispose();
            });

            JButton btnHex = new JButton("5x5 Hexagonal Version");
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

            frame.add(btnClassic);
            frame.add(btnHex);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
