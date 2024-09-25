package org.example.controllers;

import org.example.models.Customer;
import org.springframework.web.bind.annotation.*;
import org.example.services.CustomerService;
import org.example.services.DTO.CustomerDTO;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping
    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();
        return customers.stream()
                .map(customer -> new CustomerDTO(customer.getId(), customer.getName(), customer.getEmail(), customer.getAddress()))
                .toList();
    }

    @GetMapping("/{id}")
    public CustomerDTO getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomerById(id);
        if (customer.isEmpty()) {
            throw new RuntimeException("Customer not found");
        }
        Customer c = customer.get();
        return new CustomerDTO(c.getId(), c.getName(), c.getEmail(), c.getAddress());
    }

    @PostMapping
    public CustomerDTO createCustomer(@RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer(null, customerDTO.getName(), customerDTO.getEmail(), customerDTO.getAddress());
        Customer savedCustomer = customerService.createCustomer(customer);
        return new CustomerDTO(savedCustomer.getId(), savedCustomer.getName(), savedCustomer.getEmail(), savedCustomer.getAddress());
    }

    @PutMapping("/{id}")
    public CustomerDTO updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        Customer customer = new Customer(id, customerDTO.getName(), customerDTO.getEmail(), customerDTO.getAddress());
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return new CustomerDTO(updatedCustomer.getId(), updatedCustomer.getName(), updatedCustomer.getEmail(), updatedCustomer.getAddress());
    }

    @DeleteMapping("/{id}")
    public void deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
    }
}
