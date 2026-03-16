package com.bookmystay.main;

import java.util.Scanner;

import com.bookmystay.history.BookingHistoryService;

public class BookingReportApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        BookingHistoryService history = new BookingHistoryService();

        int choice;

        do {

            System.out.println("\n===== Booking History System =====");

            System.out.println("1. Show All Reservations");
            System.out.println("2. Cancel Reservation");
            System.out.println("3. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    history.showAllReservations();
                    break;

                case 2:

                    System.out.print("Enter Reservation ID: ");
                    String id = sc.nextLine();

                    history.cancelReservation(id);
                    break;

                case 3:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 3);

        sc.close();
    }
}