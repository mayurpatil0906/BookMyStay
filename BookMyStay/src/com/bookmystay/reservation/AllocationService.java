package com.bookmystay.reservation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import com.bookmystay.Inventory.InventoryService;
import com.bookmystay.model.Reservation;

public class AllocationService {

    // Stores all booked room IDs
    private Set<String> bookedRoomIds = new HashSet<>();

    // Maps room type -> allocated room IDs
    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();

    public void confirmReservation(Reservation reservation, InventoryService inventory) {

        String roomType = reservation.getRoomType().toLowerCase();

        // Check availability before allocation
        if (!inventory.isRoomAvailable(roomType)) {
            System.out.println("No rooms available for type: " + roomType);
            return;
        }

        // Simple unique room id generation
        String roomId = roomType.substring(0,1).toUpperCase() + (bookedRoomIds.size() + 1);

        // HashSet ensures room id is never duplicated
        bookedRoomIds.add(roomId);

        // Create set for room type if it doesn't exist
        allocatedRooms.putIfAbsent(roomType, new HashSet<>());

        // Store assigned room
        allocatedRooms.get(roomType).add(roomId);

        // Update inventory immediately
        inventory.decreaseRoomCount(roomType);

        System.out.println("\nReservation Confirmed");
        System.out.println("Guest Name : " + reservation.getGuestName());
        System.out.println("Room Type  : " + roomType);
        System.out.println("Room ID    : " + roomId);
    }
}