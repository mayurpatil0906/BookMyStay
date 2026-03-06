package com.bookmystay.Inventory;

import java.util.HashMap;
import java.util.Map;

import com.bookmystay.model.Room;

public class InventoryService {

	private Map<String, Room> inventory = new HashMap<>();

	public void addRoomType(String type, int count, double price) {  // Add new room type
		Room room = new Room(type, count, price);
		inventory.put(type, room);
		System.out.println(type + " room type added.");
	}
	public void updateRoomCount(String type, int newCount) { // Update room count
		Room room = inventory.get(type);
		if (room != null) {
			room.setAvailableCount(newCount);
			System.out.println("Room count updated for " + type);
		}
	}
	public void updateRoomPrice(String type, double newPrice) {  // Update room price
		Room room = inventory.get(type);
		if (room != null) {
			room.setPricePerNight(newPrice);
			System.out.println("Room price updated for " + type);
		}
	}

	public void showInventory() {  // Display inventory
		System.out.println("\n---- Hotel Room Inventory ----");
		for (Room room : inventory.values()) {
			System.out.println(
					"Room Type: " + room.getRoomType()
					+ " | Available: " + room.getAvailableCount()
					+ " | Price: " + room.getPricePerNight()
					);
		}
	}
}