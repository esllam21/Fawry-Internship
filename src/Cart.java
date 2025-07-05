import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cart {
    private Map<Product, Integer> items;
    private List<Product> products;
    public Cart(List<Product> products) {
        this.items = new HashMap<>();
        this.products = products;
    }

    private Product searchProducts(String name) {
        if (name == null || name.trim().isEmpty())
            return null;
        String searchName = name.trim().toLowerCase();
        for (Product product : products) {
            if (product.getName().toLowerCase().equals(searchName)) 
                return product;
        }
        return null;
    }
    
    public boolean add(String name, int quantity) throws ProductUnavailableException {
        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero.");
            return false;
        }
        
        Product product = searchProducts(name);
        if (product == null) {
            System.out.println("Product not found: " + name);
            return false;
        }
        
        if (!product.enoughQuantity((short)quantity)) {
            throw new ProductUnavailableException(product.getName(), quantity, product.getQuantity());
        }
        
        product.reduceQuantity(quantity);
        int currentQuantity = items.getOrDefault(product, 0);
        items.put(product, currentQuantity + quantity);
        
        System.out.println("Added " + quantity + " " + name + " to cart.");
        return true;
    }
    
    public boolean isEmpty() {
        return items.isEmpty();
    }

    public double getTotalPrice() {
        double total = 0;
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            total += entry.getKey().getPrice() * entry.getValue();
        }
        return total;
    }
    
    public void checkForExpiredProducts() throws ExpiredProductException {
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            if (product.isExpired()) {
                throw new ExpiredProductException(product.getName());
            }
        }
    }
    
    public List<Shippable> getShippableItems() {
        List<Shippable> shippableItems = new ArrayList<>();
        for (Map.Entry<Product, Integer> entry : items.entrySet()) {
            Product product = entry.getKey();
            int quantity = entry.getValue();
            
            if (product.isShippable()) {
                ShippingServices productShippingService = product.getShippingService();
                for (int i = 0; i < quantity; i++) {
                    shippableItems.add(productShippingService);
                }
            }
        }
        return shippableItems;
    }
    
    public Map<Product, Integer> getItems() {
        return new HashMap<>(items);
    }
}