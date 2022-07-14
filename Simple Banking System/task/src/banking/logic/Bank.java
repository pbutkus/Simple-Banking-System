package banking.logic;

import banking.domain.Card;

import javax.xml.crypto.Data;
import java.util.HashMap;
import java.util.Map;

public class Bank {

    private DatabaseManager dbManager;

    public Bank(DatabaseManager dbManager) {
        this.dbManager = dbManager;
    }

    public Card createAccount() {
        Card newCard = new Card();

        dbManager.insertCardToDatabase(newCard.getCardNumber(), newCard.getPin());

        return newCard;
    }

    public Card logIn(String cardNumber, String pin) {
        return dbManager.getCardFromDatabase(cardNumber, pin);
    }

    public String addIncome(Card card, int income) {
        dbManager.addIncome(card.getCardNumber(), income);
        card.setBalance(income);

        return "Income was added";
    }

    public boolean isCardValid(String cardNumber) {
        String cardNumberWithoutChecksum = cardNumber.substring(0, cardNumber.length() - 1);
        Card tempCard = new Card(cardNumberWithoutChecksum);

        if (cardNumber.equals(tempCard.getCardNumber())) {
            return true;
        }

        return false;
    }

    public boolean doesCardExistInDatabase(String cardNumber) {
        return dbManager.doesCardExist(cardNumber);
    }

    public void makeTransfer(Card fromCard, String toCard, int amount) {
        dbManager.makeTransfer(fromCard.getCardNumber(), toCard, amount);
        fromCard.setBalance(-amount);
    }

    public int getCardBalance(Card card) {
        return dbManager.getCardBalance(card.getCardNumber());
    }

    public void closeAccount(Card card) {
        dbManager.closeAccount(card.getCardNumber());
    }

}
