package com.bookmystay.booking;

import java.util.LinkedList;
import java.util.Queue;

import com.bookmystay.model.Reservation;

public class BookingQueueService {

    private Queue<Reservation> bookingQueue = new LinkedList<>();

    // Add booking request using guest name and room type
    public void addBookingRequest(String guestName, String roomType) {

        Reservation reservation = new Reservation(guestName, roomType);

        bookingQueue.add(reservation);

        System.out.println("Booking request added to queue.");
    }

    // Process next booking request (FIFO)
    public void processNextRequest() {

        if (bookingQueue.isEmpty()) {

            System.out.println("No booking requests in queue.");
            return;
        }

        Reservation r = bookingQueue.poll();

        System.out.println("\nProcessing Booking Request");
        System.out.println("Guest: " + r.getGuestName());
        System.out.println("Room Type: " + r.getRoomType());
    }

    // Used later in UC4
    public Reservation getNextBooking() {
        return bookingQueue.poll();
    }

    // Check queue status
    public boolean isQueueEmpty() {
        return bookingQueue.isEmpty();
    }

    // Display queue
    public void showQueue() {

        if (bookingQueue.isEmpty()) {
            System.out.println("Booking queue is empty.");
            return;
        }

        System.out.println("\nCurrent Booking Queue:");

        for (Reservation r : bookingQueue) {
            System.out.println(r.getGuestName() + " -> " + r.getRoomType());
        }
    }
}