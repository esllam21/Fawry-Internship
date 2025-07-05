import java.text.NumberFormat;
import java.util.List;
import java.util.Map;

public class Checkout {
    private Customer customer;
    private Cart cart;
    private static final double baseShippingFee = 5.99;
    private static final double shippingRate = 2.50;
    private static final double threshold = 100.00;

    public Checkout(Customer customer, Cart cart) {
        this.customer = customer;
        this.cart = cart;
    }
    
    public boolean processCheckout() throws CheckoutException {
        if (cart.isEmpty()) {
            throw new EmptyCartException();
        }
        cart.checkForExpiredProducts();
        double subtotal = cart.getTotalPrice();
        double shippingFee = calculateShippingFee(subtotal);
        double total = subtotal + shippingFee;
        generateCheckoutReceipt(subtotal, shippingFee, total);
        customer.processPayment(total);
        List<Shippable> shippableItems = cart.getShippableItems();
        if (!shippableItems.isEmpty()) {
            generateShippingNotice(shippableItems);
        } else {
            System.out.println("No physical items to ship in this order.");
        }
        System.out.println("Remaining balance: "+NumberFormat.getCurrencyInstance().format(customer.getBalance()));
        return true;
    }

    private void generateShippingNotice(List<Shippable> shippableItems) {
        ShippingServices shippingServices = new ShippingServices();
        shippingServices.addItems(shippableItems);
        System.out.println("** Shipping Notice  **");
        shippingServices.ship();
    }

    private void generateCheckoutReceipt(double subtotal, double shippingFee, double total) {
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
        System.out.println("** Checkout Receipt  **");
        Map<Product, Integer> cartItems = cart.getItems();
        for (Map.Entry<Product, Integer> cart : cartItems.entrySet()) {
            Product product = cart.getKey();
            int quantity = cart.getValue();
            double unitPrice = product.getPrice();
            double price = unitPrice * quantity;
            String type = getProductTypeLabel(product);
            System.out.println(quantity+"x "+product.getName()+"\t\t"+currencyFormat.format(price));
        }
        System.out.println("---------------------------------------------");
        System.out.println("Subtotal \t\t"+ currencyFormat.format(subtotal));
        if (shippingFee > 0) {
            System.out.println("Shipping /t/t"+ currencyFormat.format(shippingFee));
        } else {
            System.out.println("Shipping \t\t"+ "Free");
        }
        System.out.println("Amount\t\t"+ currencyFormat.format(total));
    }

    private double calculateShippingFee(double subtotal) {
        if (subtotal >= threshold) {
            return 0.0;
        }
        
        List<Shippable> shippableItems = cart.getShippableItems();
        if (shippableItems.isEmpty()) {
            return 0.0;
        }
        
        ShippingServices shippingServices = new ShippingServices();
        shippingServices.addItems(shippableItems);
        double totalWeight = shippingServices.getTotalWeight();
        return baseShippingFee + (totalWeight * shippingRate);
    }
    
    private String getProductTypeLabel(Product product) {
        if (product instanceof ShippableExpirableProduct) {
            return "Ship+Exp";
        } else if (product instanceof ShippableProduct) {
            return "Shippable";
        } else if (product instanceof ExpirableProduct) {
            return "Expirable";
        } else {
            return "Digital";
        }
    }
}
