import java.util.Scanner;

public class ConnectFour {
    

    static char[][] board = new char[6][7];

    static char currentPlayer = 'X';

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        initializeBoard();

        System.out.println("================================");
        System.out.println("       CONNECT FOUR GAME");
        System.out.println("================================");
        System.out.println("Player X vs Player O");

        while (true) {

            printBoard();

            System.out.print("Player " + currentPlayer
                    + ", choose a column (1-7): ");

            int column = sc.nextInt();

            if (column < 1 || column > 7) {
                System.out.println("Invalid column! Choose 1-7.");
                continue;
            }

            int col = column - 1;
            int row = dropPiece(col);

            if (row == -1) {
                System.out.println("Column is full! Try another column.");
                continue;
            }

            if (checkWinner(row, col)) {
                printBoard();
                System.out.println("Player " + currentPlayer + " wins!");
                break;
            }

            if (isBoardFull()) {
                printBoard();
                System.out.println("Game Draw!");
                break;
            }

            if (currentPlayer == 'X') {
                currentPlayer = 'O';
            } else {
                currentPlayer = 'X';
            }
        }

        System.out.println("Thanks for playing!");
        sc.close();
    }

    static void initializeBoard() {

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                board[i][j] = ' ';
            }
        }
    }

    static void printBoard() {

        System.out.println();

        for (int i = 0; i < 6; i++) {

            System.out.print("|");

            for (int j = 0; j < 7; j++) {
                System.out.print(" " + board[i][j] + " |");
            }

            System.out.println();
            System.out.println("-----------------------------");
        }

        System.out.println("  1   2   3   4   5   6   7");
        System.out.println();
    }

    static int dropPiece(int col) {

        for (int row = 5; row >= 0; row--) {

            if (board[row][col] == ' ') {
                board[row][col] = currentPlayer;
                return row;
            }
        }

        return -1;
    }

    static boolean checkWinner(int row, int col) {

        return count(row, col, 0, 1) >= 4
                || count(row, col, 1, 0) >= 4
                || count(row, col, 1, 1) >= 4
                || count(row, col, 1, -1) >= 4;
    }

    static int count(int row, int col, int rowDirection, int colDirection) {

        int total = 1;

        total += countDirection(
                row, col,
                rowDirection, colDirection
        );

        total += countDirection(
                row, col,
                -rowDirection, -colDirection
        );

        return total;
    }

    static int countDirection(
            int row,
            int col,
            int rowDirection,
            int colDirection) {

        int count = 0;

        row += rowDirection;
        col += colDirection;

        while (row >= 0 && row < 6 &&
                col >= 0 && col < 7 &&
                board[row][col] == currentPlayer) {

            count++;

            row += rowDirection;
            col += colDirection;
        }

        return count;
    }

    static boolean isBoardFull() {

        for (int col = 0; col < 7; col++) {

            if (board[0][col] == ' ') {
                return false;
            }
        }

        return true;
    }
}