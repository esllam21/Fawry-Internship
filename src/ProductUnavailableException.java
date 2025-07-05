public class ProductUnavailableException extends CheckoutException {
    private final String productName;
    private final int requestedQuantity;
    private final int availableQuantity;
    
    public ProductUnavailableException(String productName, int requestedQuantity, int availableQuantity) {
        super(String.format("Product '%s' unavailable: Requested %d, Available %d", 
                productName, requestedQuantity, availableQuantity));
        this.productName = productName;
        this.requestedQuantity = requestedQuantity;
        this.availableQuantity = availableQuantity;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public int getRequestedQuantity() {
        return requestedQuantity;
    }
    
    public int getAvailableQuantity() {
        return availableQuantity;
    }
}
