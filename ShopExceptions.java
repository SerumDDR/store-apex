/*
    Name: Donnie Ranjel
    Date: 3/25/2026
    File Name: ShopExceptions.java
    Program Name: Store Apex
    File Description: Provides custom exception types and integer input validation.
    Inputs: User input read through Scsnner, exception messages.
    Outputs: Validated integer values, printed error messages.
*/

public class ShopExceptions extends Exception {

    // Custom exception class for the shop
    public ShopExceptions(String message) {
        super(message);
    }

    // Method to validate integer input from the user
    public static int getValidInt(java.util.Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter a valid option.");
            scanner.next();
        }

        int input = scanner.nextInt();

        scanner.nextLine();

        return input;

    }

    // Custom exception for item no longer in stock
    static class OutOfStockException extends ShopExceptions {
        public OutOfStockException(String message) {
            super(message);
        }
    }
    
}
