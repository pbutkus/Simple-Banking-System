package banking;

import banking.logic.Bank;
import banking.logic.DatabaseManager;
import banking.ui.UserInterface;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String db = "";

        for (int i = 0; i < args.length; i++) {
            if (i % 2 == 0 && "-fileName".equals(args[i]) && args.length > i + 1) {
                db = args[i + 1];
            }
        }

        Scanner scanner = new Scanner(System.in);
        DatabaseManager dbManager = new DatabaseManager(db);
        Bank bank = new Bank(dbManager);
        UserInterface ui = new UserInterface(scanner, bank);

        ui.start();
    }

}