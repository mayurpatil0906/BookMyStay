package com.bookmystay.model;

public class Reservation {

    private String reservationId;
    private String guestName;
    private String roomType;
    private String status;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.status = "CONFIRMED";
    }

    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getStatus() {
        return status;
    }

    public void cancel() {
        this.status = "CANCELLED";
    }

    @Override
    public String toString() {
        return "ReservationID: " + reservationId +
                " | Guest: " + guestName +
                " | Room: " + roomType +
                " | Status: " + status;
    }
}