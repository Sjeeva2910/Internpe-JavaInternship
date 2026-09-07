import java.util.Scanner;
import java.util.Random;

public class RockPaperScissors {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        Random random = new Random();

        char again;

        do {
            System.out.println("\n===== Rock Paper Scissors =====");
            System.out.println("1. Rock");
            System.out.println("2. Paper");
            System.out.println("3. Scissors");

            System.out.print("Enter your choice: ");
            int user = sc.nextInt();

            int computer = random.nextInt(3) + 1;

            System.out.println("You chose: " + user);
            System.out.println("Computer chose: " + computer);

            if (user == computer) {
                System.out.println("It's a Draw!");
            }
            else if ((user == 1 && computer == 3) ||
                     (user == 2 && computer == 1) ||
                     (user == 3 && computer == 2)) {
                System.out.println("You Win!");
            }
            else {
                System.out.println("Computer Wins!");
            }

            System.out.print("Do you want to play again? (y/n): ");
            again = sc.next().charAt(0);

        } while (again == 'y' || again == 'Y');

        System.out.println("Thanks for playing!");

        sc.close();
    }
}