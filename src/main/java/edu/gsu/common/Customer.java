package edu.gsu.common;


public class Customer extends User{


    public Customer() {

    }

    public Customer (String username, String password, String email, String securityQuestion, String securityAnswer) {
        super(username, password, email, securityQuestion, securityAnswer);
    }

    public void SearchFlights(String departureCity, String destinationCity) {

    }

    public void bookFlight(Reservation reservation) {

    }

    public void cancelFlight(int reservationId) {

    }

}
