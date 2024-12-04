package edu.gsu.common;

public class User {
    //ID, name, login credentials)
    //switch these from customer class^
    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;

    User(){}

    User(int userId, String firstName, String lastName, String username, String password, String email ){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;

    }

    // ACCOUNT INFO
    public void setUserId(int userid){this.userId = userid;}
    public int getUserId(){return this.userId;}

    public void setFirstName(String name){this.firstName = name;}
    public String getFirstName(){return this.firstName;}
    public void setLastName(String name){this.lastName = name;}
    public String getLastName(){return this.lastName;}
    public void setFullName(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getPassword() {
        return this.password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public String getEmail() {
        return this.email;
    }

    // (Polymorphism can extend this) GO BACK AND LOOK
    // FOR ADMIN, ADD POSSIBLE ADMINKEY THAT SHOWS TRUE ADMIN LOGIN
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User ID: " + userId +
                ", First Name: " + firstName +
                ", Last Name: " + lastName +
                ", Full Name: " + getFullName() +
                ", Email: " + email;
    }
}
