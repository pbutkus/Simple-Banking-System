package banking.logic;

import banking.domain.Card;

import java.sql.*;

public class DatabaseManager {

    private String db;

    public DatabaseManager(String db) {
        this.db = db;
        createCardTable();
    }

    public Connection connect() {
        Connection conn = null;

        try {
            String url = "jdbc:sqlite:" + this.db;
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }

    private void executeUpdate(String sql) {
        Connection conn = connect();
        Statement stmt = null;

        try {
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }

                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private void createCardTable() {
        String sql = "CREATE TABLE IF NOT EXISTS card " +
                "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "number VARCHAR(16) NOT NULL," +
                "pin VARCHAR(4) NOT NULL," +
                "balance INTEGER DEFAULT 0) ";

        executeUpdate(sql);
    }

    public void insertCardToDatabase(String cardNumber, String cardPin) {
        String sql = "INSERT INTO card(number, pin) VALUES(?, ?)";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardNumber);
            pstmt.setString(2, cardPin);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Card getCardFromDatabase(String cardNumber, String cardPin) {
        String sql = "SELECT * FROM card WHERE number=? AND pin=?";
        String number = null;
        String pin = null;
        int balance = 0;

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardNumber);
            pstmt.setString(2, cardPin);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                number = rs.getString(2);
                pin = rs.getString(3);
                balance = rs.getInt(4);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (number != null && pin != null) {
            return new Card(number, pin, balance);
        }

        return null;
    }

    public boolean doesCardExist(String cardNumber) {
        String sql = "SELECT number FROM card WHERE number=?";
        String cardNumberFromDB = null;

        try(Connection conn = this.connect();
            PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, cardNumber);

            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                cardNumberFromDB = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        if (cardNumberFromDB != null) {
            return true;
        }

        return false;
    }

    public void makeTransfer(String fromCard, String toCard, int amount) {
        String sql = "UPDATE card SET balance=balance+? WHERE number=?";

        try (Connection conn = this.connect();
             PreparedStatement from = conn.prepareStatement(sql);
             PreparedStatement to = conn.prepareStatement(sql)) {
            from.setInt(1, -amount);
            from.setString(2, fromCard);
            to.setInt(1, amount);
            to.setString(2, toCard);

            from.executeUpdate();
            to.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void addIncome(String cardNumber, int income) {
        String sql = "UPDATE card SET balance=balance+? WHERE number=?";

        try (Connection conn = this.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, income);
            preparedStatement.setString(2, cardNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public int getCardBalance(String cardNumber) {
        String sql = "SELECT balance FROM card WHERE number=?";
        int balance = 0;

        try (Connection conn = this.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                balance = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return balance;
    }

    public void closeAccount(String cardNumber) {
        String sql = "DELETE FROM card WHERE number=?";

        try (Connection conn = this.connect();
             PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, cardNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

}
