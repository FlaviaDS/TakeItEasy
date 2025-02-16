package org.example;

import java.util.Scanner;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

// Importa la versione classica
import org.example.view.GameBoardUI;

// Importa la versione esagonale (HexGridPanel)
import org.example.view.HexGridPanel;

public class Launcher {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Seleziona la versione da lanciare:");
        System.out.println("1 - Versione classica rettangolare");
        System.out.println("2 - Versione esagonale");

        int scelta = scanner.nextInt();

        if (scelta == 1) {
            System.out.println("Avvio della versione classica...");
            // Avvia la versione classica (GameBoardUI deve avere un metodo main)
            SwingUtilities.invokeLater(() -> GameBoardUI.main(args));
        } else if (scelta == 2) {
            System.out.println("Avvio della versione esagonale...");
            // Avvia la versione esagonale creando un JFrame con HexGridPanel
            SwingUtilities.invokeLater(() -> {
                JFrame frame = new JFrame("Versione Esagonale");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.add(new HexGridPanel());
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);
            });
        } else {
            System.out.println("Scelta non valida. Uscita.");
        }
    }
}
