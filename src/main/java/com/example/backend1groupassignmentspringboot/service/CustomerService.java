package com.example.backend1groupassignmentspringboot.service;

import com.example.backend1groupassignmentspringboot.entity.Customer;

import java.util.List;

public interface CustomerService {
    List<Customer> findAll();
    Customer findById(Long id);

    void save(Customer customer);
    void deleteById(Long id);
}
