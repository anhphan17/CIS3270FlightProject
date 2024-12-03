package edu.gsu.common;

public class Flight {
    private String airLine;
    private int flightNumber;
    private String flightDepartureCity;
    private String flightDestinationCity;
    private String flightDate;
    private String flightDepartureTime;
    private String flightArrivalTime;


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
        this.flightDepartureCity = fdc;
    }
    public String getFlightDepartureCity() {
        return this.flightDepartureCity;
    }


    public void setFlightDestinationCity(String fdc) {
        this.flightDestinationCity = fdc;
    }
    public String getFlightDestinationCity() {
        return this.flightDestinationCity;
    }


    public void setFlightDate(String fd) {
        this.flightDate = fd;
    }
    public String getFlightDate() {
        return this.flightDate;
    }


    public void setFlightDepartureTime(String fdt) {
        this.flightDepartureTime = fdt;
    }
    public String getFlightDepartureTime() {
        return this.flightDepartureTime;
    }


    public void setFlightArrivalTime(String fat) {
        this.flightArrivalTime = fat;
    }
    public String getFlightArrivalTime() {
        return this.flightArrivalTime;
    }
}

