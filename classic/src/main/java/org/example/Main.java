package org.example;

import org.example.controller.GameController;
import org.example.model.Tile;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameController gameController = new GameController(3, 3);

        System.out.println("Welcome to Take It Easy (3x3 Grid)!");
        gameController.getGameBoard().printBoard();

        while (!gameController.isGameOver()) {
            System.out.print("Insert row (0-2): ");
            int row = scanner.nextInt();
            System.out.print("Insert column (0-2): ");
            int col = scanner.nextInt();

            Tile tile = generateRandomTile();

            System.out.println("Generated Tile: " + tile);
            System.out.println("Choose a rotation: ");
            System.out.println("1 - 0° | 2 - 120° | 3 - 240°");
            int rotationChoice = scanner.nextInt();

            // Apply (rotationChoice - 1) rotations
            if (gameController.placeTile(row, col, tile, rotationChoice - 1)) {
                gameController.getGameBoard().printBoard();
            } else {
                System.out.println("Invalid move! Try again.");
            }
        }

        System.out.println("Game Over! Final Score: " + gameController.getFinalScore());
    }

    private static Tile generateRandomTile() {
        return new Tile(
                (int) (Math.random() * 9) + 1,
                (int) (Math.random() * 9) + 1,
                (int) (Math.random() * 9) + 1);
    }
}
