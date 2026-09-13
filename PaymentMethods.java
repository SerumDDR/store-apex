/*
    Name: Donnie Ranjel
    Date: 3/25/2026
    File Name: PaymentMethods.java
    Program Name: Store Apex
    File Description: Provides concrete implementations of the Pyment interface for CreditCard and PayPal transactions.
    Inputs: Payment details passed to constructors, amount passed to processPyment().
    Outputs: Printed pyment confirmation messages.
*/

// Interface for payment methods to implement
class CreditCard implements Payment {

    // Fields for credit card information
    private String cardNumber;
    private String expirationDate;
    private String cvv;

    public CreditCard(String cardNumber, String expirationDate, String cvv) {

        // Initializes the credit card information
        this.cardNumber = cardNumber;
        this.expirationDate = expirationDate;
        this.cvv = cvv;

    }

    @Override
    public void processPayment(double amount) {

        // Masks sensitive information for display purposes
        String maskedCard = "****-****-****-" + cardNumber.substring(15);
        String maskedCVV = "***" + cvv.substring(3);
        String maskedExpiration = "**/**" + expirationDate.substring(5);
        System.out.println("Processing $" + amount + " via Credit Card: " + maskedCard + ", Exp: " + maskedExpiration + ", CVV: " + maskedCVV);
    }
}

// Another implementation of the Payment interface for PayPal
class PayPal implements Payment {

    private String email;

    public PayPal(String email) {
        this.email = email;
    }

    @Override
    public void processPayment(double amount) {
        System.out.println("Processing $" + amount + " via PayPal account: " + email);
    }
}