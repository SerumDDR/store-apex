/*
    Name: Donnie Ranjel
    Date: 3/25/2026
    File Name: Payment.java
    Program Name: Store Apex
    File Description: Defines the required behavior for all payment method types.
    Inputs: Amount passed to processPayment().
    Outputs: Payment confirmation handled by implementing classes.
*/

// Interface for payment methods to implement
public interface Payment {
    
    void processPayment(double amount);

}
