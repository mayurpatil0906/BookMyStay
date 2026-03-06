package com.bookmystay.booking;

import java.util.LinkedList;
import java.util.Queue;

import com.bookmystay.model.Reservation;

public class BookingQueueService {

    private Queue<Reservation> bookingQueue = new LinkedList<>();


    // Add booking request
    public void addBookingRequest(String guestName, String roomType) {

        Reservation reservation = new Reservation(guestName, roomType);

        bookingQueue.offer(reservation);

        System.out.println("Booking request added to queue.");
    }


    // Process next booking request (FIFO)
    public void processNextRequest() {

        if (bookingQueue.isEmpty()) {
            System.out.println("No booking requests in queue.");
            return;
        }

        Reservation reservation = bookingQueue.poll();

        System.out.println("\nProcessing Booking Request");
        System.out.println("Guest: " + reservation.getGuestName());
        System.out.println("Room Type: " + reservation.getRoomType());
    }


    // Show pending requests
    public void showQueue() {

        if (bookingQueue.isEmpty()) {
            System.out.println("Booking queue is empty.");
            return;
        }

        System.out.println("\n--- Pending Booking Requests ---");

        for (Reservation r : bookingQueue) {

            System.out.println(
                    "Guest: " + r.getGuestName() +
                    " | Room Type: " + r.getRoomType()
            );
        }
    }
}