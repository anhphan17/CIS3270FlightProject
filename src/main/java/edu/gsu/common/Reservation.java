package edu.gsu.common;
import java.util.ArrayList;
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Flight flightNumber;
    private String confirmationNumber;

    //list of reservations
    private static final ArrayList<Reservation> reservationList = new ArrayList<>();
    private static int reservationCounter = 1;

    //constructor reservation
    public Reservation(String reservationId,Customer customer, Flight flightNumber, String confirmationNumber){
        this.reservationId = reservationId;
        this.customer = customer;
        this.flightNumber = flightNumber;
        this.confirmationNumber = confirmationNumber;
        reservationList.add(this);
    }
    //getter and setter
    public String getReservationId(){
        return reservationId;
    }
    public void setReservationId(String reservationId){
        this.reservationId = reservationId;
    }
    public Customer getCustomer(){
        return customer;
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    public Flight getFlightNumber(){
        return flightNumber;
    }
    public void setFlightNumber(Flight flightNumber){
        this.flightNumber = flightNumber;
    }
    public String getConfirmationNumber(){
        return confirmationNumber;
    }
    public void setConfirmationNumber(String confirmationNumber){
        this.confirmationNumber = confirmationNumber;
    }



    public static String addTrip (Customer customer, String confirmationNumber, String lastName, Flight flight){
        if (!customer.getLastname().equalsIgnoreCase(lastName)){
            return "Your last name or Confirmation Number does not match";
        }
        for (Reservation reservation : reservationList) {
            if (reservation.getCustomer().getUsername().equals(customer.getUsername()) &&
                    reservation.getConfirmationNumber().equals(confirmationNumber)) {
                return "This trip is already added to your account";
            }
        }
            new Reservation(generateNewReservationId(), customer, flight, confirmationNumber);
            return "Flight was added to your account";
    }

    public static String deleteTrip(Customer customer, String confirmationNumber) {
        for (int i = 0; i < reservationList.size(); i++) {
            Reservation reservation = reservationList.get(i);
            if (reservation.getCustomer().getUsername().equals(customer.getUsername()) &&
                    reservation.getConfirmationNumber().equalsIgnoreCase(confirmationNumber)) {
                reservationList.remove(i);
                return "Flight was removed from your account";
            }
        }
        return "Can't find matching reservation";
    }
    private static String generateNewReservationId() {
        return "Reservation" + (reservationCounter++);
    }
    //warn customer about conflict date and time
    public static boolean flightConflict(Customer customer, Flight newFlight){
        for (Reservation reservation : reservationList){
            if (reservation.getCustomer().equals(customer) && reservation.getFlight().conflictsWith(newFlight)){
                return true;
            }
        }
        return false;
    }

    private Flight getFlight() {
        return flightNumber;
    }
}

