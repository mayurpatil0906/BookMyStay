package com.bookmystay.search;

import java.util.Map;

import com.bookmystay.Inventory.InventoryService;
import com.bookmystay.model.Room;

public class SearchService {

    private InventoryService inventoryService;

    public SearchService(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // Show all available rooms
    public void showAvailableRooms() {

        Map<String, Room> inventory = inventoryService.getInventory();

        System.out.println("\n---- Available Rooms ----");

        for (Room room : inventory.values()) {

            if (room.getAvailableCount() > 0) {

                System.out.println(
                        "Room Type: " + room.getRoomType() +
                        " | Available: " + room.getAvailableCount() +
                        " | Price: " + room.getPricePerNight() +
                        " | Amenities: WiFi, AC, TV"
                );
            }
        }
    }

    // Search specific room type
    public void searchRoom(String type) {

        Map<String, Room> inventory = inventoryService.getInventory();

        Room room = inventory.get(type);

        if (room == null) {
            System.out.println("Room type not found.");
            return;
        }

        if (room.getAvailableCount() <= 0) {
            System.out.println("Sorry! No rooms available for " + type);
            return;
        }

        System.out.println("\nRoom Found!");
        System.out.println("Type: " + room.getRoomType());
        System.out.println("Available: " + room.getAvailableCount());
        System.out.println("Price: " + room.getPricePerNight());
        System.out.println("Amenities: WiFi, AC, TV");
    }
}