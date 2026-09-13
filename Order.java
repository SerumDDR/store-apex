/*
    Name: Donnie Ranjel
    Date: 3/25/2026
    File Name: Order.java
    Program Name: Store Apex
    File Description: Represents a customer order, calculates final total with discount and tax, and generates an order summary.
    Inputs: Customer object, list of Product items, senior discount flag.
    Outputs: Final cost value, printed order summary.
*/

import java.util.ArrayList;

// Class to represent an order place by a customer
public class Order {
    
    private Customer customer;
    private ArrayList<Product> orderedItems;
    private double finalCost;

    // A constructor that connects the Order to a Customer and their items
    public Order(Customer cust, ArrayList<Product> items) {
        this.customer = cust;
        this.orderedItems = new ArrayList<>(items);
    }

    // A method that calculates the price using 10% senior discount and 2.9% sales tax
    public void calculateFinalTotal(boolean isSenior) {
        double subtotal = 0;

        for (Product p : orderedItems) {
            subtotal += p.calculateTotalPrice();
        }

        // Senior discount (10%)
        double discountAmount = 0;
        if (isSenior) {
            discountAmount = subtotal * 0.10;
        }

        double discountedPrice = subtotal - discountAmount; // Price after discount.

        double taxAmount = discountedPrice * 0.029; // Calculated sales tax

        this.finalCost = discountedPrice + taxAmount;   // Final Cost
    }

    // A method that prints the results to the console
    public void generateOrderSummary() {
        System.out.println("\n<-- Order Summary -->");
        System.out.println("Customer: " + customer.getName());
        System.out.println("Final total: $" + String.format("%.2f", finalCost));
    }

    // A getter method to retrieve the final cost
    public double getFinalCost() {
        return finalCost;
    }

    public void setFinalCost(double cost) {
        this.finalCost = cost;
    }

}
