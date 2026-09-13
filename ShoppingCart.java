/*
    Name: Donnie Ranjel
    Date: 3/25/2026
    File Name: ShoppingCart.java
    Program Name: Store Apex
    File Description: Manages cart items and calculates the subtotal.
    Inputs: Product objects added or removed, user-selected item index.
    Outputs: Subtotal amount returned, cart display.
*/

import java.util.ArrayList;

public class ShoppingCart {

    // Array using generics Product to hold items in the cart
    private ArrayList<Product> items = new ArrayList<>();

    // Method to add items in the cart
    public void addProduct(Product p) throws ShopExceptions.OutOfStockException {

        // Check if the product is in stock before adding to cart
        if (p.getQuantity() <= 0) {
            throw new ShopExceptions.OutOfStockException("Sorry, " + p.getName() + " is out of stock.");
        }
        items.add(p);

        p.setQuantity(p.getQuantity() - 1); // Decrease stock quantity when added to cart
    }

    // Method to remove items from the cart
    public void removeProduct(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
        } else {
            System.out.println("Error: That item number is not in your cart.");
        }
        
    }

    // Method to clear all items from the cart
    public void clear() {
        items.clear();
    }

    // Getter method to retrieve the items in the cart
    public ArrayList<Product> getItems() {
        return items;
    }

    // Method to view the items in the cart
    public void viewCart() {
        System.out.println("<-- Your Cart -->");
        for (Product p : items) {
            System.out.println("- " + p.getName());
        }
    }

    // Method to calculate the subtotal of the items in the cart
    public double calculateSubtotal() {

        double subtotal = 0;

        for (Product p : items) {
            subtotal += p.calculateTotalPrice();
        }

        return subtotal;
    }

}
