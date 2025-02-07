package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.

public class Main {  // ✅
    public static void main(String[] args) { // ✅
        GameBoard board = new GameBoard(3, 3); // 3x3 grid
        System.out.println("Starting Grid:");
        board.printBoard();

        // Inseriamo alcune tessere
        board.placeTile(0, 0, 5);
        board.placeTile(1, 1, 3);
        board.placeTile(2, 2, 7);

        System.out.println("Grid after inserting tiles:");
        board.printBoard();
    }
}

