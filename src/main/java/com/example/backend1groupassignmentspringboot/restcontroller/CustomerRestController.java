package com.example.backend1groupassignmentspringboot.restcontroller;

import com.example.backend1groupassignmentspringboot.controller.CustomerController;
import com.example.backend1groupassignmentspringboot.dao.CustomerRepository;
import com.example.backend1groupassignmentspringboot.entity.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerRestController {
    private final CustomerRepository customerRepository;
    private final static Logger log = LoggerFactory.getLogger(CustomerController.class);

    public CustomerRestController(CustomerRepository customerRepository) {this.customerRepository = customerRepository;}

    @RequestMapping("/customer")
    public List<Customer> getAll() {
        log.info("Successfully returned all customers");
        return customerRepository.findAll();
    }

    @RequestMapping("/customer/{id}")
    public Customer get(@PathVariable Long id) {
        log.info("Fetching one customer");
        return customerRepository.findById(id).orElse(null);
    }

    @RequestMapping("/customer/add/{name}/{personalNumber}")
    public Customer post(@PathVariable String name, @PathVariable String personalNumber) {
        log.info("Adding a new customer");
        Customer newCustomer = new Customer(name, personalNumber);
        customerRepository.save(newCustomer);
        return newCustomer;
    }

    @PostMapping("/customer")
    public ResponseEntity<?> addCustomer(@RequestBody Customer customer) {
        if (customer == null) {
            return ResponseEntity.badRequest().body("Body can't be null");
        } else if (customer.getName() == null || customer.getPersonalNumber() == null) {
            return ResponseEntity.badRequest().body("All fields of the body must be present");
        }
        customerRepository.save(customer);
        return ResponseEntity.ok().body("The request was ok and saved successfully: " + customer);
    }
}
