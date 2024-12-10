package edu.gsu.common;

public abstract class User {
    //ID, name, login credentials)
    //switch these from customer class^

    private int userId;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private String address;
    private String zipcode;
    private String state;
    private String ssn;
    private String securityQuestion;
    private String securityAnswer;
    private String role;



    public User() {}

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public User(int userId, String firstName, String lastName, String username, String password, String email, String role){
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    // ACCOUNT INFO
    public void setUserId (int userid) {
        this.userId = userid;
    }
    public int getUserId() {
        return this.userId;
    }

    public void setFirstName(String name) {
        this.firstName = name;
    }

    public String getFirstName() {
        return this.firstName;
    }

    public void setLastName(String name) {
        this.lastName = name;
    }

    public String getLastName() {
        return this.lastName;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getSSN() {
        return ssn;
    }

    public void setSSN(String ssn) {
        this.ssn = ssn;
    }

    public String getSecurityQuestion(){
        return securityQuestion;
    }

    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }

    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    // (Polymorphism can extend this) GO BACK AND LOOK
    // FOR ADMIN, ADD POSSIBLE ADMINKEY THAT SHOWS TRUE ADMIN LOGIN
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "User ID: " + userId +
                ", First Name: " + firstName +
                ", Last Name: " + lastName +
                ", Email: " + email +
                ", Role: " + role;
    }
}
