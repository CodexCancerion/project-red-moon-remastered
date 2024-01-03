public class Product {
    private int productIndex;
    private String productName;
    private double productPrice;
    private int quantity = 1;

    public Product(int productIndex, String productName, double productPrice){
        setProductIndex(productIndex);
        setProductName(productName);
        setProductPrice(productPrice);
    }
    public Product(int productIndex, String productName, double productPrice, int quantity){
        setProductIndex(productIndex);
        setProductName(productName);
        setProductPrice(productPrice);
        setQuantity(quantity);
    }

    @Override
    public String toString() {
        return productIndex +
                "\n" +productName+
                "\n" +productPrice;
    }

    public String getProductOrderInformation(){
        String message = String.format("%5s%20s%15s%10s%10s", "INDEX", "PRODUCT NAME", "PRICE/UNIT", "QNTY.", "TOTAL");
        message += String.format("\n%5s%20s%15.2f%10d%10.2f", "["+getProductIndex()+"]", getProductName(), getProductPrice(), getQuantity(), getQuantity()*getProductPrice());
        return message;
    }

    public int getProductIndex() {
        return productIndex;
    }
    public void setProductIndex(int productIndex) {
        this.productIndex = productIndex;
    }
    public void setProductPrice(double productPrice) {
        this.productPrice = productPrice;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public String getProductName() {
        return productName;
    }
    public void setProductName(String productName) {
        this.productName = productName;
    }
    public double getProductPrice() {
        return productPrice;
    }
    public int getQuantity() {
        return quantity;
    }
}
