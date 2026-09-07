import java.util.Scanner;

public class TicTacToe {

    static char[][] board = {
            {' ', ' ', ' '},
            {' ', ' ', ' '},
            {' ', ' ', ' '}
    };

    static char currentPlayer = 'X';

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        System.out.println("🎮 Welcome to Tic Tac Toe! 🎮");
        System.out.println("❌ Player X vs ⭕ Player O");

        while (true) {

            printBoard();

            System.out.println("👉 Player " + currentPlayer + ", enter row (1-3): ");
            int row = sc.nextInt();

            System.out.println("👉 Player " + currentPlayer + ", enter column (1-3): ");
            int col = sc.nextInt();

            if (row < 1 || row > 3 || col < 1 || col > 3) {
                System.out.println("❌ Invalid position! Try again.");
                continue;
            }

            if (board[row - 1][col - 1] != ' ') {
                System.out.println("⚠️ Position already occupied! Try again.");
                continue;
            }

            board[row - 1][col - 1] = currentPlayer;

            if (checkWinner()) {
                printBoard();
                System.out.println("🏆Player " + currentPlayer + " wins!");
                break;
            }

            if (checkDraw()) {
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

        System.out.println(" Thanks for playing!");
        sc.close();
    }

    static void printBoard() {

        System.out.println();

        for (int i = 0; i < 3; i++) {

            System.out.println(" " + board[i][0] + " | "
                    + board[i][1] + " | "
                    + board[i][2]);

            if (i < 2) {
                System.out.println("---+---+---");
            }
        }

        System.out.println();
    }

    static boolean checkWinner() {

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == currentPlayer &&
                    board[i][1] == currentPlayer &&
                    board[i][2] == currentPlayer) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[0][i] == currentPlayer &&
                    board[1][i] == currentPlayer &&
                    board[2][i] == currentPlayer) {
                return true;
            }
        }

        if (board[0][0] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][2] == currentPlayer) {
            return true;
        }

        if (board[0][2] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][0] == currentPlayer) {
            return true;
        }

        return false;
    }

    static boolean checkDraw() {

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {

                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }

        return true;
    }
}