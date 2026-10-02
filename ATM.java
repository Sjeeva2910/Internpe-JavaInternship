import java.util.ArrayList;
import java.util.Scanner;

public class ATM {

    static Scanner sc = new Scanner(System.in);

    static double balance = 10000.00;
    static int pin = 1234;

    static ArrayList<String> transactions = new ArrayList<>();

    public static void main(String[] args) {

        System.out.println("================================");
        System.out.println("       WELCOME TO JAVA ATM       ");
        System.out.println("================================");

        // PIN Authentication
        boolean authenticated = false;

        for (int attempt = 1; attempt <= 3; attempt++) {

            System.out.print("Enter your PIN: ");
            int enteredPin = sc.nextInt();

            if (enteredPin == pin) {
                authenticated = true;
                System.out.println("\nLogin Successful!");
                break;
            } else {
                System.out.println("Incorrect PIN!");

                if (attempt < 3) {
                    System.out.println("Attempts remaining: " + (3 - attempt));
                }
            }
        }

        if (!authenticated) {
            System.out.println("\nToo many incorrect attempts.");
            System.out.println("Your account has been blocked.");
            return;
        }

        // ATM Menu
        while (true) {

            System.out.println("\n================================");
            System.out.println("            ATM MENU             ");
            System.out.println("================================");
            System.out.println("1. Check Balance");
            System.out.println("2. Deposit Money");
            System.out.println("3. Withdraw Money");
            System.out.println("4. Transfer Money");
            System.out.println("5. Change PIN");
            System.out.println("6. Transaction History");
            System.out.println("7. Exit");
            System.out.println("================================");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();

            switch (choice) {

                case 1:
                    checkBalance();
                    break;

                case 2:
                    depositMoney();
                    break;

                case 3:
                    withdrawMoney();
                    break;

                case 4:
                    transferMoney();
                    break;

                case 5:
                    changePin();
                    break;

                case 6:
                    showTransactions();
                    break;

                case 7:
                    System.out.println("\nThank you for using Java ATM!");
                    System.out.println("Please collect your card.");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Please try again.");
            }
        }
    }

    // Check Balance
    static void checkBalance() {

        System.out.println("\n------ BALANCE ------");
        System.out.printf("Available Balance: ₹%.2f%n", balance);
    }

    // Deposit Money
    static void depositMoney() {

        System.out.println("\n------ DEPOSIT ------");

        System.out.print("Enter amount to deposit: ₹");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        balance += amount;

        transactions.add("Deposited ₹" + amount);

        System.out.println("Amount deposited successfully!");
        System.out.printf("New Balance: ₹%.2f%n", balance);
    }

    // Withdraw Money
    static void withdrawMoney() {

        System.out.println("\n------ WITHDRAW ------");

        System.out.print("Enter amount to withdraw: ₹");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance -= amount;

        transactions.add("Withdrawn ₹" + amount);

        System.out.println("Please collect your cash.");
        System.out.printf("Remaining Balance: ₹%.2f%n", balance);
    }

    // Transfer Money
    static void transferMoney() {

        System.out.println("\n------ MONEY TRANSFER ------");

        System.out.print("Enter account number: ");
        long accountNumber = sc.nextLong();

        System.out.print("Enter amount to transfer: ₹");
        double amount = sc.nextDouble();

        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return;
        }

        if (amount > balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance -= amount;

        transactions.add(
            "Transferred ₹" + amount +
            " to Account " + accountNumber
        );

        System.out.println("Money transferred successfully!");
        System.out.printf("Remaining Balance: ₹%.2f%n", balance);
    }

    // Change PIN
    static void changePin() {

        System.out.println("\n------ CHANGE PIN ------");

        System.out.print("Enter current PIN: ");
        int currentPin = sc.nextInt();

        if (currentPin != pin) {
            System.out.println("Incorrect current PIN!");
            return;
        }

        System.out.print("Enter new PIN: ");
        int newPin = sc.nextInt();

        if (newPin < 1000 || newPin > 9999) {
            System.out.println("PIN must contain exactly 4 digits.");
            return;
        }

        System.out.print("Confirm new PIN: ");
        int confirmPin = sc.nextInt();

        if (newPin != confirmPin) {
            System.out.println("PIN confirmation failed!");
            return;
        }

        pin = newPin;

        transactions.add("PIN changed successfully");

        System.out.println("PIN changed successfully!");
    }

    // Transaction History
    static void showTransactions() {

        System.out.println("\n------ TRANSACTION HISTORY ------");

        if (transactions.isEmpty()) {
            System.out.println("No transactions available.");
            return;
        }

        for (int i = 0; i < transactions.size(); i++) {
            System.out.println((i + 1) + ". " + transactions.get(i));
        }
    }
}