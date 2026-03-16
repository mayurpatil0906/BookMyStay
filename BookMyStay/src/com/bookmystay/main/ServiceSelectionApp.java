package com.bookmystay.main;

import java.util.Scanner;

import com.bookmystay.model.Service;
import com.bookmystay.service.ServiceManagement;

public class ServiceSelectionApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        ServiceManagement serviceManager = new ServiceManagement();

        System.out.print("Enter Reservation ID: ");
        String reservationId = sc.nextLine();

        int choice;

        do {

            System.out.println("\n===== Add-On Services =====");
            System.out.println("1. Add Breakfast (₹500)");
            System.out.println("2. Add Spa (₹1500)");
            System.out.println("3. Add Airport Pickup (₹800)");
            System.out.println("4. Show Selected Services");
            System.out.println("5. Exit");

            System.out.print("Enter choice: ");
            choice = sc.nextInt();

            switch (choice) {

                case 1:
                    serviceManager.addService(reservationId, new Service("Breakfast", 500));
                    break;

                case 2:
                    serviceManager.addService(reservationId, new Service("Spa", 1500));
                    break;

                case 3:
                    serviceManager.addService(reservationId, new Service("Airport Pickup", 800));
                    break;

                case 4:
                    serviceManager.showServices(reservationId);
                    break;

                case 5:
                    System.out.println("Exiting Service Selection...");
                    break;

                default:
                    System.out.println("Invalid choice");
            }

        } while (choice != 5);
    }
}