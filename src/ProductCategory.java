import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ProductCategory {
    private int categoryIndex;
    private String categoryName;
    private ArrayList<Product> productList = new ArrayList<>();

    //FILEREADERS

    public ProductCategory() throws FileNotFoundException {}
    public ProductCategory(int categoryIndex, String categoryName) throws FileNotFoundException {
        setCategoryIndex(categoryIndex);
        setCategoryName(categoryName);
    }

    public void populateProductList() throws IOException {
        productList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("src/text_database/" +categoryName+ ".txt"));
            String line;
            do {
                line = reader.readLine();
                if (line==null) break;
                productList.add(new Product(Integer.parseInt(line), reader.readLine(), Double.parseDouble(reader.readLine())));
            } while(line!=null);
            reader.close();
        } catch (Exception e){
            System.out.println("Problem reading burgers file.");
            e.printStackTrace();
        }
    }

    public String getProductListString(){
        String str = String.format("%5s%20s%15s", "INDEX", "PRODUCT NAME", "PRICE/UNIT");
        for (Product i: productList) {
            str += String.format("\n%5s%20s%15.2f", "["+i.getProductIndex()+"]", i.getProductName(), i.getProductPrice());
        }
        return str;
    }

    public void setCategoryIndex(int categoryIndex) {
        this.categoryIndex = categoryIndex;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public int getCategoryIndex() {
        return categoryIndex;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public ArrayList<Product> getProductList() {
        return productList;
    }
}
