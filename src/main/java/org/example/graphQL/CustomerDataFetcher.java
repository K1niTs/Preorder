package org.example.graphQL;

import org.example.models.Customer;
import org.example.services.CustomerService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.DgsMutation;

import java.util.List;

@DgsComponent
public class CustomerDataFetcher {

    private final CustomerService customerService;

    public CustomerDataFetcher(CustomerService customerService) {
        this.customerService = customerService;
    }

    @DgsQuery
    public List<Customer> allCustomers() {
        return customerService.getAllCustomers();
    }

    @DgsQuery
    public Customer customerById(Long id) {
        return customerService.getCustomerById(id).orElse(null);
    }

    @DgsMutation
    public Customer createCustomer(String name, String email, String address) {
        Customer customer = new Customer(null, name, email, address);
        return customerService.createCustomer(customer);
    }

    @DgsMutation
    public Customer updateCustomer(Long id, String name, String email, String address) {
        Customer customer = new Customer(id, name, email, address);
        return customerService.updateCustomer(id, customer);
    }

    @DgsMutation
    public boolean deleteCustomer(Long id) {
        try {
            customerService.deleteCustomer(id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
