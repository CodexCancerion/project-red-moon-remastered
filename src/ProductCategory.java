import java.io.*;
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

    public void updateCategoryDatabase(ArrayList<Product> tempProductList) throws IOException {
        //FILE WRITING SESSION START
        BufferedWriter universalWriter = new BufferedWriter(new FileWriter("src/text_database/"+categoryName+".txt"));
        System.out.println("Adding new Product to text database...");
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
