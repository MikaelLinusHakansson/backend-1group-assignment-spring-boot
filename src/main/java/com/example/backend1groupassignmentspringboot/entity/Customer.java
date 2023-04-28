package com.example.backend1groupassignmentspringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {


    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String personalNumber;

    @OneToOne(mappedBy = "customer")
    @JsonIgnore
    private Ordrar orders;

    public Customer(String name, String personalNumber, Ordrar orders) {
        this.name = name;
        this.personalNumber = personalNumber;
        this.orders = orders;
    }

    public Customer(String name, String personalNumber) {
        this.name = name;
        this.personalNumber = personalNumber;
    }
}
