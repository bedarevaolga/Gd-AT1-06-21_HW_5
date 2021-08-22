import java.util.Scanner;

public class ScannerMenu {
    private final Scanner scanner;

    public ScannerMenu(Scanner scanner) {
        this.scanner = scanner;
    }

    Functionality functionality = new Functionality();

    private void printMenu() {
        System.out.println("Вы в главном меню");
        System.out.println("1. Регистрацию нового пользователя(введите 1)");
        System.out.println("2. Добавление аккаунта новому пользователю (введите 2)");
        System.out.println("3. Пополнение существующего аккаунта (введите 3)");
        System.out.println("4. Снятие средств с существующего аккаунта (введите 4)");
        System.out.println("5. Закончить работу (введите 5)");
    }

    public int getInt() {
        System.out.print("Введите цифру");
        while (!scanner.hasNextInt()) {
            System.out.print("Вы ввели не цифру! Попробуйте еще раз");
            scanner.next();
        }
        return scanner.nextInt();
    }

    public void start() {
        if (this.scanner != null) {
            int key;
            printMenu();

            do {
                key = getInt();

                switch (key) {
                    case 1 -> {
                        System.out.println("Введите имя");
                        String userName = scanner.next();
                        System.out.println("Город или '-'(необязат поле)");
                        String address = scanner.next();
                        if (address.equals("-")) {
                            address = null;
                        }
                        functionality.registration(userName, address);
                        printMenu();
                    }
                    case 2 -> {
                        System.out.println("Введите ID пользователя");
                        try {
                            int userID = Integer.parseInt(scanner.next());
                            if (functionality.checkIsUserIdExist(userID)) {
                                System.out.println("Выберите валюту: BYN, RUR, EUR, USD");
                                String currency = scanner.next().toUpperCase();
                                functionality.addAccount(userID, currency);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("некорректный ввод");
                        }
                        printMenu();
                    }
                    case 3 -> {
                        System.out.println("Введите ID Аккаунта");
                        try {
                            int accountID = Integer.parseInt(scanner.next());
                            if (functionality.checkIsAccountIdExist(accountID)) {
                                System.out.println("Выберите сумму пополнения");
                                double amount = Double.parseDouble(scanner.next());
                                functionality.accountReplenishment(accountID, amount);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("некорректный ввод");
                        }
                        printMenu();
                    }
                    case 4 -> {
                        System.out.println("Введите ID Аккаунта");
                        try {
                            int accountIdExpense = Integer.parseInt(scanner.next());
                            if (functionality.checkIsAccountIdExist(accountIdExpense)) {
                                System.out.println("Выберите сумму снятия");
                                double amountExpense = Double.parseDouble(scanner.next());
                                functionality.accountExpense(accountIdExpense, amountExpense);
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("некорректный ввод");
                        }
                        printMenu();
                    }
                    case 5 -> scanner.close();
                    default -> System.out.println("Вы ввели неверное значение меню...\n");

                }
            } while (key != 5);
        }
    }
}
