/*
    Name: Donnie Ranjel
    Date: 3/25/2026
    File Name: Product.java
    Program Name: Store Apex
    File Description: Defines shared product fields and behavior for all product types.
    Inputs: Name, price, quantity passed to constructor.
    Outputs: Total price via subclass implementation, formatted product details from toString().
*/

public abstract class Product {

    // Qualities of a product
    private String name;
    private double price;
    private int quantity;

    // Conctructor for Product class called by subclasses using super()
    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    // Abstract method to calculate total price
    public abstract double calculateTotalPrice();

    // Private to protect data and is seen through getters
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getQuantity() { return quantity; }
    public void setQuantity(int quantity) { 
        this.quantity = quantity; 
    }

    // Override toString to provide a readable product
    @Override
    public String toString() {
        return name + " - $" + price + " (Stock: " + quantity +  ")";
    }   
}