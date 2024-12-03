package edu.gsu.common;

public class Admin extends Customer {
    private String adminId;
    private String adminName;
    private String adminUsername;
    private String adminPassword;
    private String addFlight;
    private String deleteFlight;
    private String updateFlight;

//    An admin should be able to do all customer activities in addition to be able to add,
//    update or delete a flight.

    public Admin() {
    }


    public Admin(String adminId, String adminName,
                 String adminUsername, String adminPassword) {
        this.adminId = adminId;
        this.adminName = adminName;
        this.adminUsername = adminUsername;
        this.adminPassword = adminPassword;
    }


    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    public String getAdminId() {
        return this.adminId;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getAdminName() {
        return this.adminName;
    }

    public void setAdminUsername(String adminUsername) {
        this.adminUsername = adminUsername;
    }

    public String getAdminUsername() {
        return this.adminUsername;
    }

    public void setAdminPassword(String adminPassword) {
        this.adminPassword = adminPassword;
    }

    public String getAdminPassword() {
        return this.adminPassword;
    }

    // DOUBLE CHECK THIS only Admin can do these
    public void addFlight(String flightDetails) {
        System.out.println("Flight added: " + flightDetails);
    }

    public void deleteFlight(String flightId) {
        System.out.println("Flight deleted: " + flightId);
    }

    public void updateFlight(String flightId, String updatedDetails) {
        System.out.println("Flight updated: " + flightId + " with details " + updatedDetails);
    }
}

