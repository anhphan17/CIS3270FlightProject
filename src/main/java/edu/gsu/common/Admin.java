package edu.gsu.common;


import java.util.ArrayList;

public class Admin extends User {
    // Constructor
    public Admin(int userId, String name, String email, String password) {
        super(userId, name, email, password);
    }

    // Method to add a new flight
    public void addFlight(Flight flight, ArrayList<Flight> flightList) {
        flightList.add(flight);
        System.out.println("Flight added: " + flight);
    }

    // Method to remove a flight
    public void removeFlight(Flight flight, ArrayList<Flight> flightList) {
        flightList.remove(flight);
        System.out.println("Flight removed: " + flight);
    }

    @Override
    public boolean login(String email, String password) {
        System.out.println("Admin-specific login logic.");
        return super.login(email, password);
    }
}