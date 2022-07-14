import java.util.ArrayList;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        // put your code here
        Scanner scanner = new Scanner(System.in);

        ArrayList<String> inputsList = new ArrayList<>();

        while (true) {
            String input = scanner.nextLine();

            if (input.equals("0")) {
                break;
            }

            inputsList.add(input);
        }

        for (String inputString : inputsList) {
            try {
                int number = Integer.parseInt(inputString);
                System.out.println(number * 10);
            } catch (NumberFormatException e) {
                System.out.println("Invalid user input: " + inputString);
            }
        }
    }

}