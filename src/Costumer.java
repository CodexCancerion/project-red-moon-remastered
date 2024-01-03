import java.util.ArrayList;
import java.util.Scanner;

public class Costumer {
    Scanner scan = new Scanner(System.in);
    private ArrayList<Product> orderList = new ArrayList<>();
    private double totalPrice;

    public Costumer(){}

    public void addOrder(Product order){
        orderList.add(order);
    }
    public void initiateOrdersModification(){
        System.out.println(getOrderListInfoStr());

        System.out.println("\nOPTIONS:" +
                "\n[1]\tProceed to Check Out" +
                "\n[2]\tModify Orders" +
                "\n[3]\tReset Orders");
        int optionChoice;
        do {
            System.out.print("Your Choice: ");
            optionChoice = takeUserInputInt();
        } while (optionChoice > 3 && optionChoice < 0);
        switch (optionChoice){
            case 1:
                initiateCheckOutThread();
                break;
            case 2:
                modifyOrders();
                break;
            case 3:
                resetOrders();
        }

    }

    public void resetOrders(){
        orderList = new ArrayList<>();
    }

    public void generateTotalPrice(){
        double sum = 0;
        for (Product i: orderList) {
            sum += i.getProductPrice()*i.getQuantity();
        }
        setTotalPrice(sum);
    }

    public void initiateCheckOutThread(){
        System.out.println(getOrderListInfoStr());

        System.out.println("\nPAYMENT >>");
        System.out.print("\nEnter amount of payment: ");
        double payment;
        do{
            payment = takeUserInputInt();
            if (payment<totalPrice){
                System.out.print("\nInsufficient payment. Enter higher than or equal to "+totalPrice+": ");
                payment = takeUserInputDouble();
            }
            else break;
        } while(payment<totalPrice);

        double change = payment-totalPrice;

        System.out.println("\nORDERS RECEIPT >>");
        System.out.println("RED MOON FOODS" +
                "\nSELF-ORDERING SYSTEM" +
                "\nThank You for Ordering!");

        System.out.println(getOrderListInfoStr());
        System.out.println("Payment Reveiced:\t" +payment);
        System.out.println("Change:\t\t\t" +change);
        System.exit(0);
    }

    public void modifyOrders(){
        int orderChoice;
        do {
            System.out.print("\nMODIFY ORDERS >>" +
                    "\nEnter which of your orders would you like to modify: ");
            orderChoice = takeUserInputInt()-1;
        } while (orderChoice > orderList.size() && orderChoice < 0);

        System.out.println("\nYOU SELECTED >>");
        System.out.println("||============================================================||");
        System.out.println(orderList.get(orderChoice).getProductOrderInformation());
        System.out.println("||============================================================||");

        System.out.println("\nOPTIONS:" +
                "\n[1]\tRemove Order" +
                "\n[2]\tAdd More" +
                "\n[3]\tRemove Some" +
                "\n[4]\tBack");

        int optionChoice;
        do {
            System.out.print("Your Choice: ");
            optionChoice = takeUserInputInt();
        } while (optionChoice > 4 && optionChoice < 0);

        switch (optionChoice){
            case 1:
                orderList.remove(orderChoice);
                System.out.println("\nORDER WAS REMOVED SUCCESSFULLY >>");
                initiateOrdersModification();
                break;
            case 2:
                System.out.print("\nADD MORE ORDERS >>" +
                        "\nEnter quantity of order you want to add: ");
                orderList.get(orderChoice).setQuantity(orderList.get(orderChoice).getQuantity()+ takeUserInputInt());
                System.out.println("ORDER WAS SAVED SUCCESSFULLY >>");
                initiateOrdersModification();
                break;
            case 3:
                int quantityInput;
                do {
                    System.out.print("\nREMOVE SOME ORDERS >>" +
                            "\nEnter quantity of order you want to remove: ");
                    quantityInput = takeUserInputInt();
                } while(quantityInput >= orderList.get(orderChoice).getQuantity());
                orderList.get(orderChoice).setQuantity(orderList.get(orderChoice).getQuantity()-quantityInput);
                System.out.println("ORDER WAS SAVED SUCCESSFULLY >>");
                initiateOrdersModification();
                break;
            case 4:
                initiateOrdersModification();
                break;
            default:
                modifyOrders();
                break;
        }
    }

    public String getOrderListInfoStr(){
        String message = "";
        message += "\nSUMMARY OF YOUR ORDERS >>" +
                "\n||============================================================||" +
                String.format("\n%5s%20s%15s%10s%10s", "INDEX", "PRODUCT NAME", "PRICE/UNIT", "QNTY.", "TOTAL");
        int counter=1;
        for (Product i: orderList) {
            message += String.format("\n%5s%20s%15.2f%10d%10.2f", "["+counter+++"]", i.getProductName(), i.getProductPrice(), i.getQuantity(), i.getQuantity()*i.getProductPrice());
        }
        generateTotalPrice();
        message += "\n\nTOTAL PRICE:\t" +totalPrice;
        message += "\n||============================================================||";
        return message;
    }

    public int takeUserInputInt(){
        return scan.nextInt();
    }
    public double takeUserInputDouble(){
        return scan.nextDouble();
    }
    public String takeUserChoiceStr(){
        return scan.nextLine();
    }
    public char takeUserChoiceChar(){
        return scan.next().charAt(0);
    }
    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public double getTotalPrice() {
        return totalPrice;
    }
    public ArrayList<Product> getOrderList() {
        return orderList;
    }
}
