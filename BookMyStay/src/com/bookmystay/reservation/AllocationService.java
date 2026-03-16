package com.bookmystay.reservation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.bookmystay.Inventory.InventoryService;
import com.bookmystay.history.BookingHistoryService;
import com.bookmystay.model.Reservation;

public class AllocationService {

    private Set<String> bookedRoomIds = new HashSet<>();

    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();


    public void confirmReservation(Reservation reservation,
                                   InventoryService inventory,
                                   BookingHistoryService history) {

        String roomType = reservation.getRoomType().toLowerCase();

        if (!inventory.isRoomAvailable(roomType)) {
            System.out.println("No rooms available for type: " + roomType);
            return;
        }

        String roomId = roomType.substring(0,1).toUpperCase() + (bookedRoomIds.size() + 1);

        bookedRoomIds.add(roomId);

        allocatedRooms.putIfAbsent(roomType, new HashSet<>());

        allocatedRooms.get(roomType).add(roomId);

        inventory.decreaseRoomCount(roomType);

        reservation.setReservationId(roomId);

        history.addReservation(reservation);

        System.out.println("\nReservation Confirmed");
        System.out.println(reservation);
    }
}