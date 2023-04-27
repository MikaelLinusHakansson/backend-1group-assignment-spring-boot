package com.example.backend1groupassignmentspringboot.dao;

import com.example.backend1groupassignmentspringboot.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
