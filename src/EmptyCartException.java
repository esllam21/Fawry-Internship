public class EmptyCartException extends CheckoutException {
    public EmptyCartException() {
        super("Cannot checkout with an empty cart");
    }
}
