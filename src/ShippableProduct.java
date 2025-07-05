public class ShippableProduct extends Product {
    public ShippableProduct(String name, float price, short quantity, double weight) {
        super(name, price, quantity);
        this.shippingService = new ShippingServices(name, weight);
    }
    @Override
    public boolean isExpired() {
        return false;
    }
    @Override
    public boolean isShippable() {
        return true;
    }
}
