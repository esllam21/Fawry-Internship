import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean exitProgram = false;
        ProductInventory inventory = new ProductInventory();
        Cart cart = new Cart(inventory.getAllProducts());
        
        while (!exitProgram) {
            System.out.println("\nPlease select an option:");
            System.out.println("1. View available products");
            System.out.println("2. Add products to cart");
            System.out.println("3. Checkout");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");
            
            String choice = scanner.nextLine().trim();
            
            switch (choice) {
                case "1":
                    inventory.displayAllProducts();
                    break;
                    
                case "2":
                    addProductsToCart(cart, scanner);
                    break;
                    
                case "3":
                    Customer customer = new Customer("Eslam Khaled", 1000.00);
                    Checkout checkout = new Checkout(customer, cart);
                    
                    try {
                        boolean success = checkout.processCheckout();
                        if (success) {
                            cart = new Cart(inventory.getAllProducts());
                            System.out.println("\nCheckout completed successfully!");
                        }
                    } catch (EmptyCartException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Please add items to your cart before attempting checkout.");
                    } catch (InsufficientFundsException e) {
                        System.out.println(e.getMessage());
                        System.out.printf("You need an additional $%.2f to complete this purchase.\n", 
                                e.getShortfall());
                    } catch (ExpiredProductException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Please remove this product from your cart and continue.");
                    } catch (ProductUnavailableException e) {
                        System.out.println(e.getMessage());
                        System.out.println("Please adjust the quantity in your cart.");
                    } catch (CheckoutException e) {
                        System.out.println("Checkout Error: " + e.getMessage());
                    }
                    break;
                    
                case "4":
                    exitProgram = true;
                    System.out.println("Thank you for shopping with us!");
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
        
        scanner.close();
    }
    
    private static void addProductsToCart(Cart cart, Scanner scanner) {
        System.out.print("Enter product name: ");
        String productName = scanner.nextLine().trim();
        
        if (productName.isEmpty()) {
            System.out.println("Product name cannot be empty.");
            return;
        }
        
        System.out.print("Enter quantity: ");
        try {
            int quantity = Integer.parseInt(scanner.nextLine().trim());
            try {
                cart.add(productName, quantity);
            } catch (ProductUnavailableException e) {
                System.out.println(e.getMessage());
                if (e.getAvailableQuantity() > 0) {
                    System.out.println("Would you like to add " + e.getAvailableQuantity() + 
                            " units instead? (yes/no)");
                    String response = scanner.nextLine().trim().toLowerCase();
                    if (response.equals("yes") || response.equals("y")) {
                        try {
                            cart.add(productName, e.getAvailableQuantity());
                        } catch (ProductUnavailableException ex) {
                            System.out.println("Error adding product: " + ex.getMessage());
                        }
                    }
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid quantity. Please enter a number.");
        }
    }
}
