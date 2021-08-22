import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Functionality {
    static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/HWSQL";
    static final String USER = "postgres";
    static final String PASS = "Jktxrf_9";

    public void registration(String userName, String address) {

        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()
        ) {
            statement.executeUpdate("INSERT INTO users(user_name,address) " +
                    "VALUES (" + "\'" + userName + "\'" + " , " + "\'" + address + "\'" + ")");
            System.out.println("Пользовтаель " + userName + " создан");
        } catch (SQLException e) {
            System.out.println("failed");
            e.printStackTrace();
        }
    }

    public void addAccount(int userId, String currency) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()
        ) {
           if (currency.equals("BYN") || currency.equals("RUR") || currency.equals("EUR") || currency.equals("USD")) {
                ResultSet rs = statement.executeQuery("SELECT currency FROM accounts WHERE user_id=" + userId);
                ArrayList<String> currencies = new ArrayList<>();
                while (rs.next()) {
                    currencies.add(String.valueOf(rs.getString("currency")));
                }
                if (!currencies.contains(currency)) {
                    double balance = 0.0;
                    statement.executeUpdate("INSERT INTO accounts(user_id, balance, currency) " +
                            "VALUES (" + userId + " , " + balance + " , " + "\'" + currency + "\'" + ")");
                    System.out.println("Аккаунт создан");
                } else {
                    System.out.println(" У пользователя " + userId + " уже есть счет в этой валюте");
                }
            } else {
                System.out.println("Вы выбрали валюту не из списка");
            }
        } catch (SQLException e) {
            System.out.println("failed");
            e.printStackTrace();
        }
    }

    public void accountReplenishment(int accountId, double amount) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()
        ) {
            if (amount <= 100000000.0) {
                ResultSet resultSet = statement
                        .executeQuery("SELECT balance FROM accounts WHERE account_id =" + accountId);
                resultSet.next();
                double newBalance = resultSet.getDouble("balance") + amount;
                if (newBalance < 2000000000.0) {
                    statement.executeUpdate("INSERT INTO transactions (account_id, amount)"
                            + " VALUES (" + accountId + " , " + amount + ")");
                    statement.executeUpdate("UPDATE accounts SET balance =" + newBalance
                            + " WHERE account_id=" + accountId);
                    System.out.println("Транзакция выполнена");
                } else {
                    System.out.println("Баланс не может превышать 2 000 000 000");
                }
            } else {
                System.out.println("Сумма транзакции не может превышать 100 000 000");
            }

        } catch (SQLException e) {
            System.out.println("failed");
            e.printStackTrace();
        }
    }

    public boolean checkIsAccountIdExist(int accountId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()
        ) {
            List<Integer> accountsId = new ArrayList<>();
            ResultSet rs = statement.executeQuery("SELECT account_id FROM accounts");

            while (rs.next()) {
                accountsId.add(rs.getInt("account_id"));
            }
            if (accountsId.contains(accountId)) {
                return true;
            } else {
                System.out.println("Такого  Id аккауна не существует");
            }

        } catch (SQLException e) {
            System.out.println("failed");
            e.printStackTrace();
        }
        return false;
    }
    public boolean checkIsUserIdExist(int userId) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()
        ) {
            List<Integer> usersId = new ArrayList<>();
            ResultSet rs = statement.executeQuery("SELECT user_id FROM Users");

            while (rs.next()) {
                usersId.add(rs.getInt("user_id"));
            }
            if (usersId.contains(userId)) {
                return true;
            } else {
                System.out.println("Такого user Id не существует");
            }

        } catch (SQLException e) {
            System.out.println("failed");
            e.printStackTrace();
        }
        return false;
    }


    public void accountExpense(int accountId, double amount) {
        try (Connection connection = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement statement = connection.createStatement()
        ) {
            if (amount <= 100000000.0) {
                ResultSet rs = statement.executeQuery("SELECT balance FROM accounts WHERE account_id =" + accountId);
                rs.next();
                double newBalance = rs.getDouble("balance") - amount;
                if (newBalance >= 0.0) {
                    statement.executeUpdate("INSERT INTO transactions (account_id, amount) " +
                            "VALUES (" + accountId + " , " + -amount + ")");
                    statement.executeUpdate("UPDATE accounts SET balance =" + newBalance + " " +
                            "WHERE account_id=" + accountId);
                    System.out.println("Транзакция выполнена");
                } else {
                    System.out.println("Недостаточно средств");
                }
            } else {
                System.out.println("Сумма транзакции не может превышать 100000000");
            }
        } catch (SQLException e) {
            System.out.println("failed");
            e.printStackTrace();
        }
    }
}