package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameBoard gameBoard = new GameBoard(3, 3);

        System.out.println("Welcome to Take It Easy (3x3 Grid)!");
        gameBoard.printBoard();

        while (!gameBoard.checkGameOver()) {
            System.out.print("Insert row (0-2): ");
            int row = scanner.nextInt();
            System.out.print("Insert column (0-2): ");
            int col = scanner.nextInt();

            Tile tile = generateRandomTile();

            System.out.println("Generated Tile: " + tile);
            System.out.println("Choose a rotation: ");
            System.out.println("1 - 0° | 2 - 120° | 3 - 240°");
            int rotationChoice = scanner.nextInt();

            for (int i = 0; i < rotationChoice - 1; i++) {
                tile.rotate();
            }

            if (gameBoard.placeTile(row, col, tile)) {
                gameBoard.printBoard();
            } else {
                System.out.println("Invalid move! Try again.");
            }
        }

        System.out.println("Game Over! Final Score: " + gameBoard.calculateScore());
    }

    private static Tile generateRandomTile() {
        return new Tile(
                (int) (Math.random() * 9) + 1,
                (int) (Math.random() * 9) + 1,
                (int) (Math.random() * 9) + 1);
    }
}
