/*
    Name: Donnie Ranjel
    Date: 3/25/2026
    File Name: Customer.java
    Program Name: Store Apex
    File Description: Stores customer information and manages their order history.
    Inputs: Customer name, email, address, order objects to history.
    Outputs: Display order history and saves it to a file.
*/

import java.util.ArrayList;

public class Customer {

    // private fields to give the customer attributes and protect data
    private String name;
    private String email;
    private String address;
    private ArrayList<Order> orderHistory = new ArrayList<>();

    // Constructor for customer to intialize the fields
    public Customer(String name, String email, String address) {
        this.name = name;
        this.email = email;
        this.address = address;
    }

    // Methods to add orders to the customer's order history
    public void addToOrderHistory(Order order) {
        orderHistory.add(order);
    }

    // Getter for customer's name
    public String getName() {
        return name;
    }

    // Getter for order history
    public ArrayList<Order> getOrderHistory() {
        return orderHistory;
    }

    // view order history
    public void viewOrderHistory() {
        System.out.println("<-- Order History -->");

        if (orderHistory.isEmpty()) {
            System.out.println("No orders have been placed yet.");
        } else {
            for (Order o : orderHistory) {
                o.generateOrderSummary();
            }
          }
    }

    // Method to save order history to a file named after the customer
    public void saveOrderHistory() {
        String fileName = this.name + "_order_history.txt";

        try (java.io.PrintWriter writer = new java.io.PrintWriter(new java.io.FileWriter(fileName, false))) {
            for (Order o : orderHistory) {
                writer.println(this.name + "," + this.email + "," + this.address + "," + String.format("%.2f", o.getFinalCost()));
            }

            System.out.println("Order history saved to " + fileName);
        } catch (java.io.IOException e) {
            System.out.println("Error: could not save file.");
        }

    }

    // Method to load order history from a file named after the customer
    public void loadOrderHistory() {
        String fileName = this.name + "_order_history.txt";
        java.io.File file = new java.io.File(fileName);

        if (!file.exists()) {
            System.out.println("No previous order history found for " + this.name);
            return;
        }

        // Try-with-resources to read the file and reconstruct order history
        try (java.util.Scanner fileScanner = new java.util.Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                double cost = Double.parseDouble(data[3]);

                // Create a new Order object with the loaded data (using dummy items since we only have cost)
                Order historicalOrder = new Order(this, new ArrayList<>());
                historicalOrder.setFinalCost(cost);
                this.orderHistory.add(historicalOrder);
            }
        } catch (java.io.IOException e) {
            System.out.println("Error: could not read file.");
        }
    }
}
