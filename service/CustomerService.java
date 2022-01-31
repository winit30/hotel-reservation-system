package service;

import model.Customer;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private final static Map<String, Customer> customers = new HashMap<String, Customer>();

    public static void addCustomer(String email, String firstName, String lastName) {
        Customer newCustomer = new Customer(firstName, lastName, email);
        customers.put(email, newCustomer);
    }

    public static Customer getCustomer(String customerEmail) {
        return customers.get(customerEmail);
    }

    public static Collection<Customer> getAllCustomers() {
        return customers.values();
    }
}
