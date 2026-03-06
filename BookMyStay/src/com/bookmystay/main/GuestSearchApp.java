package com.bookmystay.main;

import java.util.Scanner;

import com.bookmystay.Inventory.InventoryService;
import com.bookmystay.search.SearchService;

public class GuestSearchApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        InventoryService inventory = new InventoryService();

        // Temporary data for testing
        inventory.addRoomType("Single", 10, 2000);
        inventory.addRoomType("Double", 5, 3500);
        inventory.addRoomType("Suite", 0, 6000);

        SearchService searchService = new SearchService(inventory);

        int choice;

        do {

            System.out.println("\n===== Guest Room Search =====");

            System.out.println("1. Show Available Rooms");
            System.out.println("2. Search Room Type");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    searchService.showAvailableRooms();
                    break;

                case 2:

                    System.out.print("Enter room type to search: ");
                    String type = scanner.nextLine();

                    searchService.searchRoom(type);
                    break;

                case 3:

                    System.out.println("Thank you for visiting BookMyStay!");
                    break;

                default:

                    System.out.println("Invalid choice");
            }

        } while (choice != 3);

        scanner.close();
    }
}