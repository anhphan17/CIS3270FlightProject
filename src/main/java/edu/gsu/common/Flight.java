package edu.gsu.common;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class Flight {
    private int flightId;
    private String airLine;
    private int flightNumber;
    private String DepartureCity;
    private String DestinationCity;
    private String flightDate;
    private String DepartureTime;
    private String ArrivalTime;
    private int capacity;
    private int bookedSeats;

    public Flight(String airline, int flightNumber, String departureCity, String destinationCity,
                  String flightDate, String departureTime, String arrivalTime) {
        this.airLine = airline;
        this.flightNumber = flightNumber;
        this.DepartureCity = departureCity;
        this.DestinationCity = destinationCity;
        this.flightDate = flightDate;
        this.DepartureTime = departureTime;
        this.ArrivalTime = arrivalTime;
    }



    public void setAirLine(String al) {
        this.airLine = al;
    }
    public String getAirLine() {
        return this.airLine;
    }


    public void setFlightNumber(int fl) {
        this.flightNumber = fl;
    }
    public int getFlightNumber() {
        return this.flightNumber;
    }


    public void setFlightDepartureCity(String fdc) {
        this.DepartureCity = fdc;
    }
    public String getFlightDepartureCity() {
        return this.DepartureCity;
    }


    public void setFlightDestinationCity(String fdc) {
        this.DestinationCity = fdc;
    }
    public String getFlightDestinationCity() {
        return this.DestinationCity;
    }


    public void setFlightDate(String fd) {
        this.flightDate = fd;
    }
    public String getFlightDate() {
        return this.flightDate;
    }


    public void setFlightDepartureTime(String fdt) {
        this.DepartureTime = fdt;
    }
    public String getFlightDepartureTime() {
        return this.DepartureTime;
    }


    public void setFlightArrivalTime(String fat) {
        this.ArrivalTime = fat;
    }
    public String getFlightArrivalTime() {
        return this.ArrivalTime;
    }

    public int getFlightId() {
        return flightId;
    }

    public void setFlightId(int flightId) {
        this.flightId = flightId;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getBookedSeats(){
        return bookedSeats;
    }
    public void setBookedSeats(int bookedSeats) {
        this.bookedSeats = bookedSeats;
    }

    @Override
    public String toString() {
        return "Flight [Airline=" + airLine + ", FlightNumber=" + flightNumber +
                ", DepartureCity=" + DepartureCity + ", DestinationCity=" + DestinationCity +
                ", FlightDate=" + flightDate + ", DepartureTime=" + DepartureTime +
                ", ArrivalTime=" + ArrivalTime + "]";
    }


    public boolean isSameRoute(Flight other) {
        return this.DepartureCity.equalsIgnoreCase(other.DepartureCity) &&
                this.DestinationCity.equalsIgnoreCase(other.DestinationCity);
    }
    public boolean conflictsWith (Flight other){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime thisDeparture = LocalDateTime.parse(this.flightDate + " "+this.DepartureTime, formatter);
        LocalDateTime thisArrival = LocalDateTime.parse(this.flightDate + " " + this.ArrivalTime, formatter);
        LocalDateTime otherDeparture = LocalDateTime.parse(other.flightDate + " " + other.DepartureTime, formatter);
        LocalDateTime otherArrival = LocalDateTime.parse(other.flightDate + " " + other.ArrivalTime, formatter);
    return (thisDeparture.isBefore(otherArrival)&& thisArrival.isAfter(otherDeparture));
    }

    public void cancelSeat() {
        if (bookedSeats > 0) {
            bookedSeats--;
        }
    }

    public boolean isAvailable() {
        return bookedSeats < capacity;
    }

    public void bookSeat() {
        if (isAvailable()) {
            bookedSeats++;
        }
        else {
            throw new RuntimeException("FLight is fully booked.")
        }
    }
}


