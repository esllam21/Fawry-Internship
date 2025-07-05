public class SimpleProduct extends Product{
    public SimpleProduct(String name, double price, int qty) {
        super(name, price, qty);
    }
    @Override
    public boolean isExpired() {
        return false;
    }
    @Override
    public boolean isShippable() {
        return false;
    }
}
