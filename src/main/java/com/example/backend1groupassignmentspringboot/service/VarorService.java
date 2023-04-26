package com.example.backend1groupassignmentspringboot.service;

import com.example.backend1groupassignmentspringboot.entity.Varor;

import java.util.List;

public interface VarorService {
    List<Varor> findAll();
    Varor findById(Long id);

    void save(Varor vara);
    void deleteById(Long id);
}
