package model;

import java.util.regex.Pattern;

public class Customer {
    String firstName;
    String lastName;
    String email;

    public Customer(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        String reg = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(reg);

        if(pattern.matcher(email).matches()) {
            this.email = email;
        } else {
            throw new IllegalArgumentException("Please provide a valid email");
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}
