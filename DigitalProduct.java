/*
    Name: Donnie Ranjel
    Date: 3/25/2026
    File Name: DigitalProduct.java
    Program Name: Store Apex
    File Description: represents a digital product and calculates its total price.
    Inputs: Name, price, quantity passed to constructor.
    Outputs: Total price returned.
*/

public class DigitalProduct extends Product {

    // Constructor for a digital product that calls the parent using super()
    public DigitalProduct(String name, double p, int quantity) {
        super(name, p, quantity);
    }

    // Implementation of the abstract method to calculate the total price of a digital product
    @Override
    public double calculateTotalPrice() {
        return getPrice() * getQuantity();
    }
    
}
