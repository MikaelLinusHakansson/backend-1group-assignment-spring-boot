package com.example.backend1groupassignmentspringboot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor(access = AccessLevel.PUBLIC)
public class Varor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double price;

    /*@ManyToOne
    @JoinColumn
    @JsonIgnore
    private Ordrar order;*/

    public Varor(String name, double price) {
        this.name = name;
        this.price = price;
    }



    public void setOrder(Ordrar order){
//        this.order = order;
    }




}
