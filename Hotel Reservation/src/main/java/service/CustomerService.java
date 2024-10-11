package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static CustomerService customerService;
    private static final Map <String, Customer> customers = new HashMap<>();

    public static CustomerService getInstance() {
        if (customerService == null) {
            customerService = new CustomerService();
        }
        return customerService;
    }

    public void addCustomer(String firstName, String lastName,String email) {
        if (!customers.containsKey(email)) {
        customers.put(email,new Customer(firstName,lastName,email));
        }else{
            System.out.println("Customer already exists.\n");
        }
    }

    public Customer getCustomer(String customerEmail) {

        try {
        return customers.get(customerEmail);
        }catch (NullPointerException e) {
            return null;
        }
    }

    public Collection <Customer> getAllCustomers() {

        if (customers.isEmpty()){
            return null;
        }

        return customers.values();
    }

}
