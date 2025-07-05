public class ExpiredProductException extends CheckoutException {
    private final String productName;
    
    public ExpiredProductException(String productName) {
        super(String.format("Product '%s' has expired and cannot be purchased", productName));
        this.productName = productName;
    }
    
    public String getProductName() {
        return productName;
    }
}
