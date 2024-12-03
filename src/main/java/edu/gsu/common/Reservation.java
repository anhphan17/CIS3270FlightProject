package edu.gsu.common;
import java.util.ArrayList;
public class Reservation {
    private String reservationId;
    private Customer customer;
    private Flight flight;
    private String confirmationNumber;

    //list of reservations
    private static final ArrayList<Reservation> reservationList = new ArrayList<>();

    //constructor reservation
    public Reservation(String reservationId,Customer customer, Flight flight){
        this.reservationId = reservationId;
        this.customer = customer;
        this.flight = flight;
    }
    //getter and setter
    public String getReservationId(){
        return reservationId;
    }
    public void setReservationId(String reservationId){
        this.reservationId = reservationId;
    }
    public Customer getcustomer(){
        return customer;
    }
    public void setCustomer(Customer customer){
        this.customer = customer;
    }
    public Flight getFlight(){
        return flight;
    }
    public void setFlight(Flight flight){
        this.flight = flight;
    }
    public String getConfirmationNumber(){
        return confirmationNumber;
    }
    public void setConfirmationNumber(String confirmationNumber){
        this.confirmationNumber = confirmationNumber;
    }

    //add a trip to account
    public static String addTrip (Customer customer, String confirmationNumber, String lastName, Flight flight){
        if (!customer.getLastname().equalsIgnoreCase(lastName)){
            return "Your last name or Confirmation Number does not match";
        }
        for (Reservation reservation : reservationList) {
            if (reservation.getcustomer().getUsername().equals(customer.getUsername()) && reservation.getConfirmationNumber().equals(confirmationNumber)) {
                return "This trip is already added to your account";
            }

        }
    }
}
/**Be able to book a flight and add that to his account.
 7.3. Be able to delete a flight from his account.
 7.4. Customer should not be able to book same flight more than once**/
