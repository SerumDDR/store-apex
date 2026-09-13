/*
    Name: Donnie Ranjel
    Date: 3/25/2026
    File Name: Main.java
    Program Name: Store Apex
    Program Description: A simple e-commerce application that handles customer setup, menu navigation, product browsing, cart management, checkout, and file-based inventory and order history management.
    Inputs: User info, menu selections, product choices, discount eligibility, payment methods.
    Outputs: Product list, cart contents, order summary, confirmation messages, updated files.

    <--- RUN THIS FILE --->
 */

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    
    // List of inventory for product to be sold
    private static ArrayList<Product> storeInventory = new ArrayList<>();

    // Connects to the file ShoppingCart.java
    private static ShoppingCart customerCart = new ShoppingCart();
    private static Customer currentCustomer;    // Current user shopping
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        runAutomatedTests(); // Run automated tests to validate key functionalities

        loadInventory();    // Loads inventory from .txt file
        System.out.println("Welcome to Store Apex!\n");

        // Get user information for Customer object
        System.out.print("Customer Name: ");
        String customerName = scanner.nextLine();
        System.out.print("email: ");
        String customerEmail = scanner.nextLine();
        System.out.print("Shipping address: ");
        String customerAddress = scanner.nextLine();

        currentCustomer = new Customer(customerName, customerEmail, customerAddress);  // Creates Customer object

        // Load order history from file if it exists
        currentCustomer.loadOrderHistory(); // Loads order history from file if it exists
        System.out.println("Hello, " + currentCustomer.getName() + "! Let's start shopping.");

        boolean isAppRunning = true; // Flag tp keep the program running

        // Loop for main menu
        while (isAppRunning) {
            System.out.println("\n<--Main Menu-->");
            System.out.println("1. Browse Products and add to cart.");
            System.out.println("2. View shopping cart.");
            System.out.println("3. Remove an item from the cart.");
            System.out.println("4. Checkout.");
            System.out.println("5. View order history.");
            System.out.println("6. Exit.");
            System.out.print("Select an option: ");
            int userChoice = ShopExceptions.getValidInt(scanner);  // Validates an integer is entered

            // switch statement to hndle menu options
            switch (userChoice) {
                case 1:
                    displayAndSelectProducts();
                    break;
                case 2:
                    customerCart.viewCart();
                    break;
                case 3:
                    removeFromCart();
                    break;
                case 4:
                    checkout();
                    saveInventory();
                    break;
                case 5:
                    currentCustomer.viewOrderHistory();
                    break;
                case 6:
                    isAppRunning = false;
                    break;
                default:
                    System.out.println("Invalid option. Please select a number between 1 and 6.");

            }
        }

        scanner.close();

    }

    private static void loadInventory() {
        File inventoryFile = new File("inventory.txt"); //searches saved inventory
        if (!inventoryFile.exists()) {
            storeInventory.add(new PhysicalProduct("A Great Book", 14.99, 20)); // If no file, add a default product
            return;
        }

        // Try-with-resources
        try (Scanner fileScanner = new Scanner(inventoryFile)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] data = line.split(",");

                // Converts CSV text fields into proper data types
                String name = data[0];
                double price = Double.parseDouble(data[1]);
                int quantity = Integer.parseInt(data[2]);
            
                // Adds a PhysicalProduct to the Product list
                storeInventory.add(new PhysicalProduct(name,price, quantity));
            }

        }

        catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }

    // Method to overwrite the inventory.txt file
    private static void saveInventory() {

        //PrintWriter that writes the text to the file
        try (PrintWriter writer = new PrintWriter(new FileWriter("inventory.txt"))) {
            for (Product p : storeInventory) {

                // Formats as CSV (Comma Separated Values)
                writer.println(p.getName() + "," + p.getPrice() + "," + p.getQuantity());
            }
        } catch (IOException e) {
            System.out.println("Error: Could not save inventory to file.");
        }
    }

    // Method to connect 
    private static void displayAndSelectProducts() {
        System.out.println("\n<--Available Products-->");

        // Loop through inventory
        for (int i = 0; i < storeInventory.size(); i++) {
            System.out.println((i + 1) + ". " + storeInventory.get(i).toString());
        }

        System.out.print("Enter item number to add to your cart (0 to cancel): ");
        int menuChoice = ShopExceptions.getValidInt(scanner);

        // Check if the choice is valid and add to cart, otherwise do nothing
        if (menuChoice > 0 && menuChoice <= storeInventory.size()) {
            try {
                customerCart.addProduct(storeInventory.get(menuChoice - 1));
                System.out.println("Added successfully!");
            } catch (ShopExceptions.OutOfStockException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    //Method to handle polymorphism for checkout
    private static void checkout() {
        
        if (customerCart.calculateSubtotal() == 0) {
            System.out.println("Your cart is Empty!");
            return;
        }

        System.out.println("\n<--Checkout-->");

        // Discount for seniors
        System.out.println("Are you a senior (65+)? y for yes, n for no: ");
        scanner.nextLine(); // Consume newline left-over
        String seniorInput = scanner.nextLine().toLowerCase();
        boolean isSenior = seniorInput.equals("y");

        // If not y or n ask again
        if (!seniorInput.equals("y") && !seniorInput.equals("n")) {
            System.out.println("Invalid input. Please enter 'y' or 'n'.");
            return;
        }

        // Create an Oreder object with the current customer and their cart items
        Order order = new Order(currentCustomer, customerCart.getItems());

        order.calculateFinalTotal(isSenior);
        order.generateOrderSummary();

        // Polymorphic payment method
        System.out.println("Payment: 1. Credit card 2. PayPal");
        int paymentChoice = ShopExceptions.getValidInt(scanner); 

        // 
        Payment paymentMethod = ((paymentChoice == 1) ? new CreditCard("1234-4321-5678-8765", "03/26", "213") : new PayPal("SerumDDR@frcc.edu"));
        paymentMethod.processPayment(order.getFinalCost());

        // Add to customer's order history and save to file
        currentCustomer.addToOrderHistory(order);
        currentCustomer.saveOrderHistory();
        customerCart.clear(); // Empties cart after purchase
    }

    // Method to remove an item from the cart
    private static void removeFromCart() {
        customerCart.viewCart();
        System.out.print("Enter item number to remove from cart (0 to cancel): ");
        int removeChoice = ShopExceptions.getValidInt(scanner);
        if (removeChoice > 0) {
            customerCart.removeProduct(removeChoice - 1);
            System.out.println("Product removed from cart.");
        }
    }

    // Automated tests to validate key functionalities
    public static void runAutomatedTests() {
        System.out.println("\n<--Running Automated Tests-->");
        int passCount = 0;

        // Test physical product price calculation
        PhysicalProduct book = new PhysicalProduct("Test Book", 10.00, 5);

        if (book.calculateTotalPrice() == 10.00) {
            System.out.println("[PASS] PhysicalProduct total price calculation");
            passCount++;
        } else {
            System.out.println("[FAIL] PhysicalProduct total price calculation");
        }

        // Test bundle with nested items
        Bundle gamerKit = new Bundle("Gamer Kit", 1);
        gamerKit.addNestedItem(new PhysicalProduct("Mouse", 20.00, 1));
        gamerKit.addNestedItem(new PhysicalProduct("Keyboard", 50.00, 1));

        // Allowing for a small margin of error in floating-point calculations
        if (gamerKit.calculateTotalPrice() == 70.00) {
            System.out.println("[PASS] Bundle total price calculation");
            passCount++;
        } else {
            System.out.println("[FAIL] Bundle total price calculation");
        }

        // Test order with a physical product and a senior discount
        Customer testCustomer = new Customer("Test User", "test@email.com", "123 Test St");
        ArrayList<Product> testItems = new ArrayList<>();
        testItems.add(new PhysicalProduct("Test Item", 15.00, 1));

        // Create an order with the test customer and items, then calculate the final total with a senior discount  
        Order testOrder = new Order(testCustomer, testItems);
        testOrder.calculateFinalTotal(true);

        // Allowing for a small margin of error in floating-point calculations
        if (Math.abs(testOrder.getFinalCost() - 13.50) < 0.01) {
            System.out.println("[PASS] Order final total calculation with senior discount");
            passCount++;
        } else {
            System.out.println("[FAIL] Order final total calculation with senior discount");
        }

        // Summary of test results
        System.out.println("Automated Tests Passed: " + passCount + "/3");
        System.out.println("<--End of Tests-->");
    }

}
