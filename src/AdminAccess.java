import java.io.*;
import java.util.ArrayList;

public class AdminAccess {
    private static Menu menu;
    private static BufferedWriter universalWriter;
    private static BufferedReader universalReader;
    private static ArrayList<ProductCategory> tempCategoryList = new ArrayList<>();
    private static ArrayList<Product> tempProductList = new ArrayList<>();
    private static Costumer suki = new Costumer();

    public AdminAccess() throws IOException {
    }

    public static void main(String[] args) throws IOException {
        menu = new Menu();

        printWelcomeMessage();

        //DISPLAY MENU
        displayCategories();

        //USER CHOICE
//        int choice = suki.takeUserInputInt();

        //DISPLAY PRODUCTS UNDER CHOICE
//        displayProductFromCategory(choice);
//        addNewProductToCategory(choice);
//        displayProductFromCategory(choice);
//        removeProductFromCategory(choice);
//        displayProductFromCategory(choice);

        displayCategories();

    }

    public static void printWelcomeMessage() throws IOException {
        menu.populateCategoryList();
        System.out.print("""
                RED MOON FOODS
                SELF-ORDERING SYSTEM
                ADMIN ACCESS
                """);

    }

    public static void displayCategories() throws IOException {
        menu.populateCategoryList();
        System.out.print("""

                List of Categories
                ||==============================||""");
        System.out.print(menu.getCategoriesListString());
        System.out.println("\n||==============================||");
        System.out.println("""
                
                COMMANDS
                [select] select from the index above                
                [add]   add new category
                [rem]   remove category
                [mod]   modify category information     
                [exit] 
                """);

        String command = promptUserCommand();

        switch (command) {
            case "select":
                System.out.print("\nYour selection:\t");
                int input = Integer.parseInt(suki.takeUserChoiceStr());
                displayProductFromCategory(input);
                break;
            case "add":
                addNewCategory();
                displayCategories();
                break;
            case "rem":
                removeCategory();
                displayCategories();
                break;
            case "mod":

                break;
            case "exit":
                    System.exit(0);
                break;
            default:
                try {
                    int index = Integer.parseInt(command)-1;
                    System.out.println(menu.getCategoryList().get(index).getProductListString());
                } catch (Exception e) {
                    System.out.println("Ugly command.");
                }
                break;
        }
    }


    public static String promptUserCommand(){
        System.out.print("\n>>YOUR CHOICE:\t");
        return suki.takeUserChoiceStr();
    }

    public static void displayAdminAccessCommands(){
        System.out.println("""
                RED MOON SYSTEM
                ADMIN ACCESS COMMANDS
                show    = show list of <specifier>
                mod     = modify <specifier>
                add     = add <specifier>
                remove  = remove <specifier>               
                exit    = exit admin access
                                
                SPECIFIERS
                main        = main menu
                category    = categories <index>                                
                product     = products <index> 
                
                NOTE: Specify <specifier> using index                               
                """);
    }
    public static void scanUserCommand() throws IOException {
//        VALIDATE COMMAND
//        int categoryIndex, productIndex;
//        if (categoryIndex>0) categoryIndex -= 1;
//        if (productIndex>0) productIndex -= 1;
//        String categoryName;
//        String productName;

        System.out.print("\ndo >>\t");
        String command = suki.takeUserChoiceStr();

        String[] commands = command.split("\\s+");
        int counter=0;
        String cm = commands[0];
        String sp = commands[2];
//
//        switch (cm){
//            case "add" :
//                switch (sp){
//                    case "category":
//                        addNewCategory();
//                        break;
//                    case "product":
//                        addNewProductToCategory();
//
//                }
//        }


    }

    public static void displayProductFromCategory(int index) throws IOException {
        index -= 1;
        menu.getCategoryList().get(index).populateProductList();
        System.out.printf("""

                List of Products under %s
                ||==============================||
                """,
                menu.getCategoryList().get(index).getCategoryName());
        System.out.println(menu.getCategoryList().get(index).getProductListString());
        System.out.println("\n||==============================||");
        System.out.println("""
                
                COMMANDS
                [add]   add new product
                [rem]   remove product
                [mod]   modify product information     
                [back] 
                """);

        String command = promptUserCommand();

        switch (command) {
            case "add":
                addNewProductToCategory(index);
                displayProductFromCategory(index);
                break;
            case "rem":
                removeProductFromCategory(index);
                displayProductFromCategory(index);
                break;
            case "mod":

                break;
            case "back":
                    displayCategories();
                break;
            default:
                break;
        }
    }

    public static void removeProductFromCategory(int categoryIndex) throws IOException {
        System.out.print("""

                PRODUCT REMOVAL >>
                Enter Index of product to remove:\t
                """);
        suki.takeUserChoiceStr(); //dispose line
        int removeProductIndex = suki.takeUserInputInt();
        removeProduct(categoryIndex, removeProductIndex);
    }

    public static void addNewProductToCategory(int categoryIndex) throws IOException {
//        suki.takeUserChoiceStr(); //dispose line
        System.out.print("\nEnter new product name:\t");
        String newName = suki.takeUserChoiceStr();

        System.out.print("Enter new product price:\t");
        double newPrice = suki.takeUserInputDouble();

        //FILE READING SESSION START
        //initialize list to clear contents before using again
        tempProductList = new ArrayList<>();
        //initialize reader
        if (categoryIndex>0) categoryIndex -= 1;
        menu.getCategoryList().get(categoryIndex).populateProductList();
        String categoryName = menu.getCategoryList().get(categoryIndex).getCategoryName();
        universalReader = new BufferedReader(new FileReader("src/text_database/"+categoryName+".txt"));
        //read all line and put to list using loop
        String line;
        int counter=0;
        do {
            line = universalReader.readLine();
            if (line==null) break;
            String name = universalReader.readLine();
            double price = Double.parseDouble(universalReader.readLine());
            tempProductList.add(new Product(Integer.parseInt(line), name, price));
            counter++;
        } while(line!=null);
        //close reader
        universalReader.close();
        //FILE READING SESSION END

        //NEW PRODUCT INSERTION
        tempProductList.add(new Product(counter+1, newName, newPrice));

        //FILE WRITING SESSION START
        universalWriter = new BufferedWriter(new FileWriter("src/text_database/"+categoryName+".txt"));
        System.out.println("Adding new Category to text database...");
        double counter2 = 0;
        String message = "";
        for (Product i: tempProductList) {
            message += (int)(counter2+1) + "\n" +i.getProductName() +"\n"+ i.getProductPrice() +"\n";
            double progress = (++counter2/tempProductList.size())*100;
            System.out.printf("%d%% in progress\n", (int) progress);
        }
        universalWriter.write(message);
        universalWriter.close();
        //FILE WRITING SESSION END

        System.out.println("Successfully added "+newName+" on "+categoryName+" text database.");

        //clear list once again
        tempProductList = new ArrayList<>();
    }

    public static void insertNewProduct(int categoryIndex, String newName, double newPrice) throws IOException {
        //FILE READING SESSION START
        //initialize list to clear contents before using again
        tempProductList = new ArrayList<>();
        //initialize reader
        if (categoryIndex>0) categoryIndex -= 1;
        menu.getCategoryList().get(categoryIndex).populateProductList();
        String categoryName = menu.getCategoryList().get(categoryIndex).getCategoryName();
        universalReader = new BufferedReader(new FileReader("src/text_database/"+categoryName+".txt"));
        //read all line and put to list using loop
        String line;
        int counter=0;
        do {
            line = universalReader.readLine();
            if (line==null) break;
            String name = universalReader.readLine();
            double price = Double.parseDouble(universalReader.readLine());
            tempProductList.add(new Product(Integer.parseInt(line), name, price));
            counter++;
        } while(line!=null);
        //close reader
        universalReader.close();
        //FILE READING SESSION END

        //NEW PRODUCT INSERTION
        tempProductList.add(new Product(counter+1, newName, newPrice));

        //FILE WRITING SESSION START
        universalWriter = new BufferedWriter(new FileWriter("src/text_database/"+categoryName+".txt"));
        System.out.println("Adding new Category to text database...");
        double counter2 = 0;
        String message = "";
        for (Product i: tempProductList) {
            message += (int)(counter2+1) + "\n" +i.getProductName() +"\n"+ i.getProductPrice() +"\n";
            double progress = (++counter2/tempProductList.size())*100;
            System.out.printf("%d%% in progress\n", (int) progress);
        }
        universalWriter.write(message);
        universalWriter.close();
        //FILE WRITING SESSION END

        System.out.println("Successfully added "+newName+" on "+categoryName+" text database.");

        //clear list once again
        tempProductList = new ArrayList<>();
    }

    public static void removeProduct(int categoryIndex, int removeProductIndex) throws IOException {
        //FILE READING SESSION START
        //initialize list to clear contents before using again
        tempProductList = new ArrayList<>();
        //initialize reader
        if (categoryIndex>0) categoryIndex -= 1;
        if (removeProductIndex>0) removeProductIndex -= 1;
        menu.getCategoryList().get(categoryIndex).populateProductList();
        String categoryName = menu.getCategoryList().get(categoryIndex).getCategoryName();
        universalReader = new BufferedReader(new FileReader("src/text_database/"+categoryName+".txt"));
        //read all line and put to list using loop
        String line;
        int counter=0;
        do {
            line = universalReader.readLine();
            if (line==null) break;
            String name = universalReader.readLine();
            double price = Double.parseDouble(universalReader.readLine());
            tempProductList.add(new Product(Integer.parseInt(line), name, price));
            counter++;
        } while(line!=null);
        //close reader
        universalReader.close();
        //FILE READING SESSION END

        String removeName = tempProductList.get(removeProductIndex).getProductName();
        //NEW PRODUCT INSERTION
        tempProductList.remove(removeProductIndex);

        //FILE WRITING SESSION START
        universalWriter = new BufferedWriter(new FileWriter("src/text_database/"+categoryName+".txt"));
        System.out.println("Removing Product from text database...");
        double counter2 = 0;
        String message = "";
        for (Product i: tempProductList) {
            message += (int)(counter2+1) + "\n" +i.getProductName() +"\n"+ i.getProductPrice() +"\n";
            double progress = (++counter2/tempProductList.size())*100;
            System.out.printf("%d%% in progress\n", (int) progress);
        }
        universalWriter.write(message);
        universalWriter.close();
        //FILE WRITING SESSION END

        System.out.println("Successfully removed "+removeName+" from "+categoryName+" text database.");

        //clear list once again
        tempProductList = new ArrayList<>();
    }

    public static void addNewCategory() throws IOException {
        //PROMT NEW NAME
        System.out.print("\nEnter Name of New Category:\t");
        String newName = suki.takeUserChoiceStr();
        //FILE READING SESSION START
        //initialize list to clear contents before using again
        tempCategoryList = new ArrayList<>();
        //initialize reader
        universalReader = new BufferedReader(new FileReader("src/text_database/Categories.txt"));
        //read all line and put to list using loop
        String line;
        int counter=0;
        do {
            line = universalReader.readLine();
            if (line==null) break;
            String name = universalReader.readLine();
            tempCategoryList.add(new ProductCategory(Integer.parseInt(line), name));
            counter++;
        } while(line!=null);
        //close reader
        universalReader.close();
        //FILE READING SESSION END

        //NEW CATEGORY INSERTION
        tempCategoryList.add(new ProductCategory(counter+1, newName));

        //FILE WRITING SESSION START
        universalWriter = new BufferedWriter(new FileWriter("src/text_database/Categories.txt"));
        System.out.println("Adding new Category to text database...");
        double counter2 = 0;
        String message = "";
        for (ProductCategory i: tempCategoryList) {
            message += i.getCategoryIndex() + "\n" +i.getCategoryName() +"\n";
            double progress = (++counter2/tempCategoryList.size())*100;
            System.out.printf("%d%% in progress\n", (int) progress);
        }
        universalWriter.write(message);
        universalWriter.close();
        //FILE WRITING SESSION END

        //NEW TEXT FILE CREATION
        //create new text file for the contents of the new category
        FileWriter tempFileWriter = new FileWriter("src/text_database/" + newName + ".txt");
        System.out.println("Successfully added new Category to text database.");

        //clear list once again
        tempCategoryList = new ArrayList<>();
    }

    public static void removeCategory() throws IOException {
        System.out.print("""

                CATEGORY REMOVAL >>
                Enter Index of product to remove:\t
                """);
        suki.takeUserChoiceStr(); //dispose line
        int index = suki.takeUserInputInt();

        tempCategoryList = new ArrayList<>();
        universalReader = new BufferedReader(new FileReader("src/text_database/Categories.txt"));
        String line;
        do {
            line = universalReader.readLine();
            if (line==null) break;
            String name = universalReader.readLine();
            tempCategoryList.add(new ProductCategory(Integer.parseInt(line), name));
        } while(line!=null);
        universalReader.close();

        String removeName = tempCategoryList.get(index-1).getCategoryName();
        tempCategoryList.remove(index-1);

        universalWriter = new BufferedWriter(new FileWriter("src/text_database/Categories.txt"));
        System.out.println("Removing Category from text database...");
        double counter2 = 0;
        String message = "";
        for (ProductCategory i: tempCategoryList) {
            message += (int)counter2+1 + "\n" +i.getCategoryName() +"\n";
            double progress = (++counter2/tempCategoryList.size())*100;
            System.out.printf("%d%% in progress\n", (int) progress);
        }
        universalWriter.write(message);
        universalWriter.close();

        File removeFile = new File("src/text_database/" +removeName+ ".txt");
        removeFile.delete();

        System.out.println("Successfully removed Category on text database.");
        tempCategoryList = new ArrayList<>();

    }
}
