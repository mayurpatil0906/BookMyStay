package com.bookmystay.main;

import java.util.Scanner;
import com.bookmystay.Inventory.InventoryService;

public class BookMyStayApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);
        InventoryService inventory = new InventoryService();

        int choice;

        do {

            System.out.println("\n===== Hotel Inventory Menu =====");

            System.out.println("1. Add Room Type");
            System.out.println("2. Update Room Count");
            System.out.println("3. Update Room Price");
            System.out.println("4. Show Inventory");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter Room Type: ");
                    String type = scanner.nextLine();

                    System.out.print("Enter Room Count: ");
                    int count = scanner.nextInt();

                    System.out.print("Enter Price: ");
                    double price = scanner.nextDouble();
                    scanner.nextLine();

                    inventory.addRoomType(type, count, price);

                    break;


                case 2:

                    System.out.print("Enter Room Type to update: ");
                    String updateType = scanner.nextLine();

                    System.out.print("Enter New Room Count: ");
                    int newCount = scanner.nextInt();
                    scanner.nextLine();

                    inventory.updateRoomCount(updateType, newCount);

                    break;


                case 3:

                    System.out.print("Enter Room Type to update: ");
                    String priceType = scanner.nextLine();

                    System.out.print("Enter New Price: ");
                    double newPrice = scanner.nextDouble();
                    scanner.nextLine();

                    inventory.updateRoomPrice(priceType, newPrice);

                    break;


                case 4:

                    inventory.showInventory();

                    break;


                case 5:

                    System.out.println("Exiting system...");
                    break;


                default:

                    System.out.println("Invalid choice");

            }

        } while (choice != 5);

       
    }
}