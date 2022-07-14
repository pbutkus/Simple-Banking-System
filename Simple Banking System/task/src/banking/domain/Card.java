package banking.domain;

import java.util.Random;

public class Card {

    private String pin;
    private int balance;
    private String cardNumber;

    public Card() {
        Random random = new Random();

        this.pin = String.format("%04d", random.nextInt(10000));
        this.balance = 0;

        String BIN = "400000";
        String customerAccountNumber = String.format("%09d", random.nextInt(1000000000));
        String cardNumberWithoutChecksum = BIN + customerAccountNumber;
        int checksum = generateChecksum(cardNumberWithoutChecksum);
        this.cardNumber = BIN + customerAccountNumber + checksum;
    }

    public Card(String cardNumber, String cardPin, int balance) {
        this.cardNumber = cardNumber;
        this.pin = cardPin;
        this.balance = balance;
    }

    public Card(String cardNumberWithoutChecksum) {
        this.pin = "0000";
        this.balance = 0;
        this.cardNumber = cardNumberWithoutChecksum + generateChecksum(cardNumberWithoutChecksum);
    }

    public String getCardNumber() {
        return this.cardNumber;
    }

    public String getPin() {
        return this.pin;
    }

    public int getBalance() {
        return this.balance;
    }

    private int generateChecksum(String cardNumberWithoutChecksum) {
        String[] parts = cardNumberWithoutChecksum.split("");
        int sum = 0;

        for (int i = 0; i < cardNumberWithoutChecksum.length(); i++) {
            int tempNum = Integer.parseInt(parts[i]);

            if (i % 2 == 0) {
                tempNum = tempNum * 2;

                if (tempNum > 9) {
                    tempNum = tempNum - 9;
                }

                parts[i] = String.valueOf(tempNum);
            }

            sum += tempNum;
        }

        return (10 - (sum % 10)) % 10;
    }

    public void setBalance(int amount) {
        this.balance = this.balance + amount;
    }

}
