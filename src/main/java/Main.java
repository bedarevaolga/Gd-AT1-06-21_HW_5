import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("No drives");
            return;
        }
        new ScannerMenu(new Scanner(System.in)).start();
    }
}