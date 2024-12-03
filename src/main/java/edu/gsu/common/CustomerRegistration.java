package edu.gsu.common;

import java.util.Scanner;
public class CustomerRegistration {
    public static void main(String[] args){
        Customer customer = new Customer();

        Scanner scan1 = new Scanner(System.in);
        System.out.println("Enter firstname");
        String firstname = scan1.nextLine();
        customer.setFirstname(firstname);

        Scanner scan2 = new Scanner(System.in);
        System.out.println("Enter lastname");
        String lastname = scan2.nextLine();
        customer.setLastname(lastname);

        Scanner scan3 = new Scanner(System.in);
        System.out.println("Enter address");
        String address = scan3.nextLine();
        customer.setAddress(address);

        Scanner scan4 = new Scanner(System.in);
        System.out.println("Enter zipcode");
        String zipcode = scan4.nextLine();
        customer.setZipcode(zipcode);

        Scanner scan5 = new Scanner(System.in);
        System.out.println("Enter state");
        String state = scan5.nextLine();
        customer.setState(state);

        Scanner scan6 = new Scanner(System.in);
        System.out.println("Enter username");
        String username = scan6.nextLine();
        customer.setUsername(username);

        Scanner scan7 = new Scanner(System.in);
        System.out.println("Enter password");
        String password = scan7.nextLine();
        customer.setPassword(password);

        Scanner scan8 = new Scanner(System.in);
        System.out.println("Enter SSN");
        String SSN = scan8.nextLine();
        customer.setSSN(SSN);

        Scanner scan9 = new Scanner(System.in);
        System.out.println("Enter a security question");
        String securityquestion = scan9.nextLine();
        customer.setSecurityQuestion(securityquestion);

        Scanner scan10 = new Scanner(System.in);
        System.out.println("Enter the answer to the security question");
        String securityQanswer = scan10.nextLine();
        customer.setSecurityAnswer(securityQanswer);

    }

}
