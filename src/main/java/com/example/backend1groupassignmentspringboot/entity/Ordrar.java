package com.example.backend1groupassignmentspringboot.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
public class Ordrar {

    @Id
    @GeneratedValue
    private Long id;

    private LocalDateTime createdDate;

    /*@ManyToOne
    private Kund customer;*/

    @ManyToMany
    @JoinTable
    private List<Varor> products;

    public Ordrar(LocalDateTime createdDate/*, Kund customer,*/, Varor item) {
        this.createdDate = createdDate;
//        this.customer = customer;
        products.add(item);
    }
    // TODO

}
