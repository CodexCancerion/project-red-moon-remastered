
import java.io.IOException;

public class UserAccess {
    private static Costumer suki;
    private static Menu menu;
    public static void main(String[] args) throws IOException {

        suki = new Costumer();
        menu = new Menu();

        menu.populateCategoryList();

        printWelcomeMessage();
        initiateOrderingThread();
        suki.initiateOrdersModification();


    }

    public static void initiateOrderingThread() throws IOException {
        boolean orderAgain = true;
        do {
            //printing of Main Menu >> Product Categories
            printMenu();
            //prompt user for choice from the menu
            int userChoiceFromMain = takeUserChoiceInt(menu.getCategoryList().size())-1;

            if (userChoiceFromMain==-1) suki.initiateOrdersModification();
            menu.getCategoryList().get(userChoiceFromMain).populateProductList();
            //printing of Category Menu >> Product List under category
            System.out.println("\n" +menu.getCategoryList().get(userChoiceFromMain).getCategoryName()+ " MENU >>" +
                    "\n||============================================================||");
            System.out.println(menu.getCategoryList().get(userChoiceFromMain).getProductListString());
            System.out.println("||============================================================||");
            //prompt user for choice from the menu
            int userChoiceFromCategory = takeUserChoiceInt(menu.getCategoryList().get(userChoiceFromMain).getProductList().size())-1; // -1 since it will be used as index

            //initialization of selection information based on user choice >> Index, Name, Price
            int selectionIndex = menu.getCategoryList().get(userChoiceFromMain).getProductList().get(userChoiceFromCategory).getProductIndex();
            String selectionName = menu.getCategoryList().get(userChoiceFromMain).getProductList().get(userChoiceFromCategory).getProductName();
            double selectionPrice = menu.getCategoryList().get(userChoiceFromMain).getProductList().get(userChoiceFromCategory).getProductPrice();

            //prompt user for order Quantity
            System.out.println("\nORDER QUANTITY >>" +
                    "\nYou selected [" + selectionIndex + "] " + selectionName + " for " + selectionPrice + " each.");
            System.out.print("Enter Quantity of order: ");
            int orderQuantity = suki.takeUserInputInt();

            //order confirmation
            confirmOrder(selectionIndex, selectionName, selectionPrice, orderQuantity);

            //prompt user if order again
            System.out.print("\nDo you want to order again? [y/n]");
            char userChoice = suki.takeUserChoiceChar();
            if (userChoice=='n' || userChoice=='N') orderAgain = false;
        } while(orderAgain);
    }

    public static void initiateCheckOutThread(){
        suki.initiateOrdersModification();
        System.out.println("\nOPTIONS:" +
                "\n[1]\tProceed to Check Out" +
                "\n[2]\tModify Orders" +
                "\n[3]\tReset Orders");
        int input = takeUserChoiceInt(3);
        switch (input){
            case 1:
                suki.initiateCheckOutThread();
                break;
            case 2:
                suki.modifyOrders();
                break;
            case 3:
                suki.resetOrders();
        }

    }

    public static void printWelcomeMessage(){
        System.out.println("Welcome to" +
                "\nRED MOON FOODS" +
                "\nSELF-ORDERING SYSTEM" +
                "\nOrder Your Foods Now!");
    }

    public static void printMenu(){
        System.out.print("\nMAIN MENU >> Choose here to order" +
                "\n||==============================||");
        System.out.print(menu.getCategoriesListString());
        System.out.println("\n\t[0]\tView Orders");
        System.out.println("\n||==============================||");
    }
    public static int takeUserChoiceInt(int i){
        int input;
        do {
            System.out.print("Your Choice: ");
            input = suki.takeUserInputInt();
        } while (input > i && input < 0);
        return input;
    }

    public static void confirmOrder(int index, String name, double price, int quantity){
        System.out.println("\nORDER CONFIRMATION" +
                "\nYou would like to order " +quantity+ " " + name+ " with a total of " +price*quantity+".");
        System.out.print("Do you want to proceed? [y/n] ");
        char input = suki.takeUserChoiceChar();
        if (input=='y' || input=='Y') {
            suki.addOrder(new Product(suki.getOrderList().size()+1, name, price, quantity));
            System.out.println("Order was saved.");
        }
        else if (input=='n' || input=='N') System.out.println("Order was discarded.");
    }

    public static void generateTestOrders() throws IOException {
        menu.getCategoryList().get(1).populateProductList();
        for (Product i: menu.getCategoryList().get(1).getProductList()) {
            suki.addOrder(new Product(i.getProductIndex(),i.getProductName(),i.getProductPrice(),5));
        }
    }
}
