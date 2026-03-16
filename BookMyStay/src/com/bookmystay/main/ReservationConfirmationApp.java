package com.bookmystay.main;

import com.bookmystay.booking.BookingQueueService;
import com.bookmystay.Inventory.InventoryService;
import com.bookmystay.history.BookingHistoryService;
import com.bookmystay.model.Reservation;
import com.bookmystay.reservation.AllocationService;

public class ReservationConfirmationApp {

    public static void main(String[] args) {

        InventoryService inventory = new InventoryService();
        BookingQueueService queueService = new BookingQueueService();
        AllocationService allocationService = new AllocationService();
        BookingHistoryService historyService = new BookingHistoryService();

        // Example inventory
        inventory.addRoomType("single", 3, 2000);
        inventory.addRoomType("double", 2, 3500);

        // Example booking requests
        queueService.addBookingRequest("Mayur", "single");
        queueService.addBookingRequest("Akshat", "double");

        while (!queueService.isQueueEmpty()) {

            Reservation request = queueService.getNextBooking();

            allocationService.confirmReservation(
                    request,
                    inventory,
                    historyService
            );
        }

        // show booking history after confirmation
        historyService.showAllReservations();
    }
}