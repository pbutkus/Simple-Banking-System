package banking.ui;

import banking.domain.Card;
import banking.logic.Bank;

import java.util.Scanner;

public class UserInterface {

    private Scanner scanner;
    private Bank bank;
    private boolean runUI;

    public UserInterface(Scanner scanner, Bank bank) {
        this.scanner = scanner;
        this.bank = bank;
        this.runUI = true;
    }

    public void start() {
        boolean run = true;

        while (this.runUI) {
            System.out.println("1. Create an account");
            System.out.println("2. Log into account");
            System.out.println("0. Exit");
            int input = scanner.nextInt();

            switch (input) {
                case 1:
                    createAccount();
                    break;
                case 2:
                    logIn();
                    break;
                case 0:
                    this.runUI = false;
                    break;
            }
        }
    }

    private void createAccount() {
        Card newCard = this.bank.createAccount();

        System.out.println();
        System.out.println("Your card has been created");
        System.out.println("Your card number:");
        System.out.println(newCard.getCardNumber());
        System.out.println("Your card PIN:");
        System.out.println(newCard.getPin());
        System.out.println();
    }

    private void logIn() {
        System.out.println();
        System.out.println("Enter your card number:");
        scanner.nextLine();
        String cardNumber = scanner.nextLine();
        System.out.println("Enter your PIN:");
        String pin = scanner.nextLine();
        System.out.println();

        Card card = this.bank.logIn(cardNumber, pin);

        if (card != null) {
            System.out.println("You have successfully logged in!");
            System.out.println();
            manageCard(card);
        } else {
            System.out.println("Wrong card number or PIN!");
        }

        System.out.println();
    }

    private void manageCard(Card card) {
        boolean run = true;

        while (run) {
            System.out.println("1. Balance");
            System.out.println("2. Add income");
            System.out.println("3. Do transfer");
            System.out.println("4. Close account");
            System.out.println("5. Log out");
            System.out.println("0. Exit");
            int input = Integer.parseInt(scanner.nextLine());
            System.out.println();

            switch (input) {
                case 1:
                    System.out.println("Balance: " + bank.getCardBalance(card));
                    System.out.println();
                    break;
                case 2:
                    System.out.println("Enter income:");
                    int income = Integer.parseInt(scanner.nextLine());
                    System.out.println(bank.addIncome(card, income));
                    System.out.println();
                    break;
                case 3:
                    System.out.println("Transfer");
                    System.out.println("Enter card number:");
                    String cardNumber = scanner.nextLine();
                    if (bank.isCardValid(cardNumber)) {
                        if (bank.doesCardExistInDatabase(cardNumber)) {
                            System.out.println("How much money you want to transfer:");
                            int moneyToTransfer = Integer.parseInt(scanner.nextLine());

                            if (moneyToTransfer > card.getBalance()) {
                                System.out.println("Not enough money!");
                            } else {
                                bank.makeTransfer(card, cardNumber, moneyToTransfer);
                                System.out.println("Success!");
                            }
                        } else {
                            System.out.println("Such card does not exist.");
                        }
                    } else {
                        System.out.println("Probably you made a mistake in the card number. Please try again!");
                    }
                    System.out.println();
                    break;
                case 4:
                    bank.closeAccount(card);
                    System.out.println("The account has been closed!");
                    run = false;
                    break;
                case 5:
                    System.out.println("You have successfully logged out!");
                    run = false;
                    break;
                case 0:
                    run = false;
                    this.runUI = false;
                    break;
            }
        }

    }

}
