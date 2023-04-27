package com.example.backend1groupassignmentspringboot.service;

import com.example.backend1groupassignmentspringboot.entity.Ordrar;

import java.util.List;

public interface OrdrarService{
    List<Ordrar> findAll();

    Ordrar findById(Long id);

    void save(Ordrar order);
    void deleteById(Long id);
}
