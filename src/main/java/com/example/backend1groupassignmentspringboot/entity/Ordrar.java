package com.example.backend1groupassignmentspringboot.entity;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Ordrar {

    @Id
    @GeneratedValue
    private Long id;

    @GeneratedValue
    @CreatedDate
    private LocalDateTime createdDate;

    @GeneratedValue
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;


    @OneToMany(cascade = {CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private List<Varor> products = new ArrayList<>();

    @ManyToOne
    private Customer customer;


    public Ordrar(LocalDateTime createdDate, List<Varor> products) {
        this.createdDate = createdDate;
        this.products = new ArrayList<>();
        if (products != null) {
            this.products.addAll(products);
        }
    }






    public void setCustomer(Customer customer){
        this.customer = customer;
    }


    public Ordrar(Long id) {
        this.id = id;
    }

    public void addItem(Varor item){
        products.add(item);
    }

    public void addVaror(Varor theVaror) {
        if (this.products == null) {
            this.products = new ArrayList<>();
        }
        this.products.add(theVaror);
        theVaror.setOrder(this);
    }

    public List<Varor> getProducts() {
        return products;
    }

    public void setProducts(Varor products) {
        this.products.add(products);
    }

    public Customer getCustomer() {
        return customer;
    }

    public Ordrar(List<Varor> products, Customer customer) {
        this.products = products;
        this.customer = customer;
    }

    @PrePersist
    public void setCreatedDate() {
        this.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void setModifiedDate() {
        this.lastModifiedDate = LocalDateTime.now();
    }

    // TODO

}