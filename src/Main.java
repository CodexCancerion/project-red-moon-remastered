import java.io.IOException;
import java.util.Scanner;

public class Main {
    private static Scanner scan = new Scanner(System.in);
    private static AdminAccess admin;
    private static CustomerAccess customerAccess;
    public static void main(String[] args) throws IOException {
        admin = new AdminAccess();
        customerAccess = new CustomerAccess();

        printWelcomeMessage();


    }

    public static void printWelcomeMessage() throws IOException {
        System.out.print("""
                RED MOON FOODS
                SELF-ORDERING SYSTEM
                
                [1] Want to order
                [2] Admin Access
                [exit]
                """);

        System.out.print(">>YOUR CHOICE:\t");
        String userChoice = scan.nextLine();
        switch (userChoice) {
            case "1":
                customerAccess.startCustomerProcess();
                System.exit(0);
                break;
            case "2":
                admin.startAdminProcess();
                printWelcomeMessage();
                break;
            case "exit":
                System.exit(0);
                break;
            default:
                System.out.println("Ugly command.");
        }
    }
}
