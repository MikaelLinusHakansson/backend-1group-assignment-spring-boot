package com.example.backend1groupassignmentspringboot.service;

import com.example.backend1groupassignmentspringboot.dao.VarorRepository;
import com.example.backend1groupassignmentspringboot.entity.Varor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VarorServiceImpl implements VarorService{
    VarorRepository varorRepository;

    @Autowired
    public VarorServiceImpl(VarorRepository varorRepository) {
        this.varorRepository = varorRepository;
    }

    @Override
    public List<Varor> findAll() {
        return varorRepository.findAll();
    }

    @Override
    public Varor findById(Long id) {
        Optional<Varor> result = varorRepository.findById(id);

        Varor theVara = null;

        if (result.isPresent()) {
            theVara = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find employee id - " + id);
        }

        return theVara;
    }

    @Override
    public void save(Varor vara) {
        varorRepository.save(vara);
    }

    @Override
    public void deleteById(Long id) {
        varorRepository.deleteById(id);
    }
}
