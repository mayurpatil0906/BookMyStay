package com.bookmystay.service;

import java.util.*;

import com.bookmystay.model.Service;

public class ServiceManagement {

    // reservationID -> list of services
    private Map<String, List<Service>> reservationServices = new HashMap<>();


    // attach service to reservation
    public void addService(String reservationId, Service service) {

        reservationServices.putIfAbsent(reservationId, new ArrayList<>());

        reservationServices.get(reservationId).add(service);

        System.out.println(service.getServiceName() + " added to reservation " + reservationId);
    }


    // display services of a reservation
    public void showServices(String reservationId) {

        List<Service> services = reservationServices.get(reservationId);

        if (services == null || services.isEmpty()) {
            System.out.println("No services selected.");
            return;
        }

        double total = 0;

        System.out.println("\nServices for Reservation " + reservationId);

        for (Service s : services) {
            System.out.println(s);
            total += s.getPrice();
        }

        System.out.println("Total Service Cost: ₹" + total);
    }
}