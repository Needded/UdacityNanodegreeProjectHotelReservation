package model;

import java.util.Objects;
import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private String lastName;
    private String email;
    private final String regEx = "^(.+)@(.+).(.+)$";
    Pattern pattern = Pattern.compile(regEx);

    public Customer(String firstName,String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;

        try{

        if(pattern.matcher(email).matches()) {
            this.email = email;
        }else{
            throw new IllegalArgumentException() ;
        }

        }catch(IllegalArgumentException e){
            System.out.println("Invalid email address");
        }
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getEmail() {
        return email;
    }


    @Override
    public String toString() {
        return " Name: "+firstName +" "+ lastName + ", email= " + email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(firstName, customer.firstName) && Objects.equals(lastName, customer.lastName) && Objects.equals(email, customer.email) && Objects.equals(pattern, customer.pattern);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email);
    }
}
