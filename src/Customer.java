public class Customer {
    private String name;
    private double balance;

    public Customer(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public double getBalance() {
        return balance;
    }
    
    public boolean hasEnoughBalance(double amount) {
        return balance >= amount;
    }
    
    public void processPayment(double amount) throws InsufficientFundsException {
        if (hasEnoughBalance(amount)) {
            pay(amount);
        } else {
            throw new InsufficientFundsException(balance, amount);
        }
    }
    
    private void pay(double amount) {
        balance -= amount;
    }
    
    public String getName() {
        return name;
    }

}
