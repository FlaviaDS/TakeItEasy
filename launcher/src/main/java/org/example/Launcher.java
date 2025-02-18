package org.example;

import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// Rectangular version
import org.example.view.GameBoardUI;

// Hexagonal version
import org.example.view.HexGridPanel;

public class Launcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Select the game version:");
        System.out.println("1 - Rectangular");
        System.out.println("2 - Hexagonal");

        int choice = scanner.nextInt();

        if (choice == 1) {
            System.out.println("Launching rectangular version...");
            SwingUtilities.invokeLater(() -> GameBoardUI.main(args));
        } else if (choice == 2) {
            System.out.println("Launching hexagonal version...");
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Hexagonal version");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new HexGridPanel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        } else {
            System.out.println("Invalid choice. Exiting.");
        }
    }
}
