import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProductInventory {
    private List<Product> products;
    
    public ProductInventory() {
        this.products = new ArrayList<>();
        initializeProducts();
    }
    private void initializeProducts() {
        products.add(new ShippableProduct("Laptop", 1299.99f, (short)10, 2.5));
        products.add(new ShippableProduct("Smartphone", 899.99f, (short)20, 0.3));
        products.add(new ShippableProduct("Headphones", 199.99f, (short)30, 0.25));
        products.add(new ShippableProduct("Monitor", 349.99f, (short)15, 5.0));
        products.add(new ShippableProduct("Keyboard", 79.99f, (short)40, 0.9));
        products.add(new ExpirableProduct("Milk", 2.99, 50, LocalDate.now().plusDays(7)));
        products.add(new ExpirableProduct("Bread", 3.49, 40, LocalDate.now().plusDays(5)));
        products.add(new ExpirableProduct("Yogurt", 1.99, 60, LocalDate.now().plusDays(14)));
        products.add(new ShippableExpirableProduct("Chocolate", 5.99, 100,
                LocalDate.now().plusMonths(3), 0.2));
        products.add(new ShippableExpirableProduct("Coffee Beans", 12.99, 30, 
                LocalDate.now().plusMonths(6), 0.5));
        products.add(new SimpleProduct("E-Book", 9.99, 999));
        products.add(new SimpleProduct("Digital Music", 1.29, 999));
    }
    public List<Product> getAllProducts() {
        return new ArrayList<>(products);
    }
    
    public void addProduct(Product product) {
        if (product != null) {
            products.add(product);
        }
    }

    public void displayAllProducts() {
        System.out.println("**  PRODUCT INVENTORY  **");
        System.out.println("--------------------------------------------------------");
        for (Product product : products) {
            System.out.println(product.name+", "+ NumberFormat.getCurrencyInstance().format(product.getPrice()));
        }
        System.out.println("----------------------------------------------");
    }
}
