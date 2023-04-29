package com.example.backend1groupassignmentspringboot.service;

import com.example.backend1groupassignmentspringboot.dao.CustomerRepository;
import com.example.backend1groupassignmentspringboot.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService{
    CustomerRepository customerRepository;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository) {

        this.customerRepository = customerRepository;
    }

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        Optional<Customer> result = customerRepository.findById(id);

        Customer theCustomer;

        if (result.isPresent()) {
            theCustomer = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find Customer id - " + id);
        }

        return theCustomer;
    }

    @Override
    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    @Override
    public void deleteById(Long id) {
        customerRepository.deleteById(id);
    }
}
