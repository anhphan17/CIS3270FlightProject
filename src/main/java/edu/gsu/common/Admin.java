package edu.gsu.common;


import java.util.ArrayList;

public class Admin extends User {
    // Constructor
    public Admin() {

    }

    public void addFlight(Flight flight, ArrayList<Flight> flightList) {
        flightList.add(flight);
        System.out.println("Flight added: " + flight);
    }

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