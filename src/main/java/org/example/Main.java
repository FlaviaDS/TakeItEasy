package org.example;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        GameBoard board = new GameBoard(3, 3);
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to Take It Easy (3x3 Grid)!");

        while (!board.checkGameOver()) {
            board.printBoard();

            try {
                System.out.print("Insert row (0-2): ");
                int row = scanner.nextInt();
                System.out.print("Insert column (0-2): ");
                int col = scanner.nextInt();
                System.out.print("Insert tile value (1-9): ");
                int value = scanner.nextInt();

                if (row < 0 || row > 2 || col < 0 || col > 2 || value < 1 || value > 9) {
                    System.out.println("Invalid input! Please enter numbers within the correct range.");
                    continue;
                }

                board.placeTile(row, col, value);
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter numbers only.");
                scanner.nextLine(); // Clear the invalid input
            }
        }

        System.out.println("Game Over!");
        board.printBoard();
        System.out.println("Final Score: " + board.calculateScore());
    }
}
