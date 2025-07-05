import java.time.LocalDate;

public class ShippableExpirableProduct extends Product {
    private LocalDate expiryDate;

    public ShippableExpirableProduct(String name, double price, int quantity, LocalDate expiryDate, double weight) {
        super(name, price, quantity);
        this.expiryDate = expiryDate;
        this.shippingService = new ShippingServices(name, weight);
    }

    @Override
    public boolean isExpired() {
        return !LocalDate.now().isBefore(expiryDate);
    }
    @Override
    public boolean isShippable(){
        return true;
    }
}
