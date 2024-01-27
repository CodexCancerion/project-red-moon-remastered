import java.io.*;
import java.util.ArrayList;

public class Menu {
    private ArrayList<ProductCategory> categoryList;

    public Menu() throws FileNotFoundException {}

    public void populateCategoryList() throws IOException, FileNotFoundException {
        categoryList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/text_database/Categories.txt"));
            String line;
            do {
                line = reader.readLine();
                if (line==null) break;
                categoryList.add(new ProductCategory(Integer.parseInt(line), reader.readLine()));
            } while(line!=null);
            reader.close();
        } catch (Exception e){
            System.out.println("Problem reading Categories file.");
            e.printStackTrace();
        }
    }

    public void updateMenuDatabase(ArrayList<ProductCategory> tempCategoryList) throws IOException {
        //FILE WRITING SESSION START
        BufferedWriter universalWriter = new BufferedWriter(new FileWriter("src/text_database/Categories.txt"));
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
    }

    public String getCategoriesListString(){
        String message = "";
        for (ProductCategory i: categoryList) {
            message += String.format("\n\t["+i.getCategoryIndex()+"]\t" +i.getCategoryName());
        }
        return message;
    }
    public ArrayList<ProductCategory> getCategoryList() {
        return categoryList;
    }
}
