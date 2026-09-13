/*
    Name: Donnie Ranjel
    Date: 3/25/2026
    File Name: Bundle.java
    Program Name: Store Apex
    File Description: Represents a bundle of products and calculates the the price of nested items.
    Inputs: Product objects added to the bundle.
    Outputs: Total price of the bundle.
*/
import java.util.ArrayList;

public class Bundle extends Product {

    // List to hold the nested prodeucts in the bundle
    private ArrayList<Product> nestedItems = new ArrayList<>();

    // Constructor to intialize bundle
    public Bundle(String name, int quantity) {
        super(name, 0, quantity);
    }

    // Method to add items to bundle
    public void addNestedItem(Product p) {
        nestedItems.add(p);
    }


    //Override the calculateTotalPrice method to calculate the total priceof the bundle
    @Override
    public double calculateTotalPrice() {
        double total = 0;
        for (Product p : nestedItems) {
            total+= p.calculateTotalPrice();
        }

        return total;
    }
    
}
