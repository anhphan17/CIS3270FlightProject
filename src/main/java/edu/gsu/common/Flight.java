package edu.gsu.common;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
public class Flight {
    private String airLine;
    private int flightNumber;
    private String DepartureCity;
    private String DestinationCity;
    private String flightDate;
    private String DepartureTime;
    private String ArrivalTime;

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

    @Override
    public String toString() {
        return "Flight [Airline=" + airLine + ", FlightNumber=" + flightNumber +
                ", DepartureCity=" + DepartureCity + ", DestinationCity=" + DestinationCity +
                ", FlightDate=" + flightDate + ", DepartureTime=" + DepartureTime +
                ", ArrivalTime=" + ArrivalTime + "]";
    }

    // Convenience Method: Check if two flights have the same route
    public boolean isSameRoute(Flight other) {
        return this.DepartureCity.equalsIgnoreCase(other.DepartureCity) &&
                this.DestinationCity.equalsIgnoreCase(other.DestinationCity);
    }
    public boolean conflictsWith (Flight otherFlight){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime thisDeparture = LocalDateTime.parse(this.flightDate + " "+this.DepartureTime, formatter);
    }
}


