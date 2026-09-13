/*
    Name: Donnie Ranjel
    Date: 3/25/2026
    File Name: PhysicalProduct.java
    Program Name: Store Apex
    File Description: Represents a physical product and calculates its total price.
    Inputs: Name, price, quantity passed to constructor.
    Outputs: Total price returned through calculateTotalPrice().
*/

// Concrete class for physical products IS-A relationship with the abstract Product class
public class PhysicalProduct extends Product {

    public PhysicalProduct(String name, double p, int quantity) {
        super(name, p, quantity); // Calls parent constructor
    }

    // Implementation of the abstract class method to calculate get the total price of the physical product
    @Override
    public double calculateTotalPrice() {
        return getPrice();
    }
    
}
