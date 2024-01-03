import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
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
