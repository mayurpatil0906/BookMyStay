package com.bookmystay.main;

import java.util.Scanner;

import com.bookmystay.booking.BookingQueueService;

public class BookingRequestApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        BookingQueueService bookingService = new BookingQueueService();

        int choice;

        do {

            System.out.println("\n===== Booking Request System =====");

            System.out.println("1. Add Booking Request");
            System.out.println("2. Process Next Booking");
            System.out.println("3. Show Booking Queue");
            System.out.println("4. Exit");

            System.out.print("Enter choice: ");
            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:

                    System.out.print("Enter Guest Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Room Type: ");
                    String roomType = scanner.nextLine();

                    bookingService.addBookingRequest(name, roomType);

                    break;

                case 2:

                    bookingService.processNextRequest();
                    break;

                case 3:

                    bookingService.showQueue();
                    break;

                case 4:

                    System.out.println("Exiting Booking System...");
                    break;

                default:

                    System.out.println("Invalid choice");
            }

        } while (choice != 4);

        scanner.close();
    }
}