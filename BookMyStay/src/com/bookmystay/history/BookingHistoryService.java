package com.bookmystay.history;

import java.util.ArrayList;
import java.util.List;

import com.bookmystay.model.Reservation;

public class BookingHistoryService {

    private List<Reservation> reservations = new ArrayList<>();


    // store confirmed reservation
    public void addReservation(Reservation reservation) {
        reservations.add(reservation);
    }


    // cancel reservation
    public void cancelReservation(String reservationId) {

        for (Reservation r : reservations) {

            if (r.getReservationId().equals(reservationId)) {

                r.cancel();

                System.out.println("Reservation cancelled.");

                return;
            }
        }

        System.out.println("Reservation not found.");
    }


    // show booking history
    public void showAllReservations() {

        if (reservations.isEmpty()) {
            System.out.println("No booking history available.");
            return;
        }

        System.out.println("\n===== Booking History =====");

        for (Reservation r : reservations) {
            System.out.println(r);
        }
    }
}