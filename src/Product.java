public abstract class Product {
    protected String name;
    protected double price;
    protected int quantity;
    protected ShippingServices shippingService;
    
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.shippingService = null;
    }

    public boolean enoughQuantity(short quantity){
        return this.quantity>=quantity;
    }
    public void reduceQuantity(int amount) {
        if (amount > 0 && amount <= this.quantity) {
            this.quantity -= amount;
        }
    }
    public int getQuantity() {
        return this.quantity;
    }
    public ShippingServices getShippingService() {
        return shippingService;
    }
    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public abstract boolean isExpired();
    public abstract boolean isShippable();

}
