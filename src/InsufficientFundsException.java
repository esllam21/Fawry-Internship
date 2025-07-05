public class InsufficientFundsException extends CheckoutException {
    private final double available;
    private final double required;
    
    public InsufficientFundsException(double available, double required) {
        super(String.format("Insufficient funds: Available $%.2f, Required $%.2f", available, required));
        this.available = available;
        this.required = required;
    }
    
    public double getAvailableAmount() {
        return available;
    }
    
    public double getRequiredAmount() {
        return required;
    }
    
    public double getShortfall() {
        return required - available;
    }
}
