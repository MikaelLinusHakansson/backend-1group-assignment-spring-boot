package com.example.backend1groupassignmentspringboot.service;

import com.example.backend1groupassignmentspringboot.dao.OrdrarRepository;
import com.example.backend1groupassignmentspringboot.entity.Ordrar;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrdrarServiceImpl implements OrdrarService{
    OrdrarRepository ordrarRepository;

    @Autowired
    public OrdrarServiceImpl(OrdrarRepository ordrarRepository) {
        this.ordrarRepository = ordrarRepository;
    }

    @Override
    public List<Ordrar> findAll() {
        return ordrarRepository.findAll();
    }

    @Override
    public Ordrar findById(Long id) {
        Optional<Ordrar> result = ordrarRepository.findById(id);

        Ordrar theOrder;

        if (result.isPresent()) {
            theOrder = result.get();
        }
        else {
            // we didn't find the employee
            throw new RuntimeException("Did not find order id - " + id);
        }

        return theOrder;
    }

    @Override
    public void save(Ordrar order) {
        ordrarRepository.save(order);
    }

    @Override
    public void deleteById(Long id) {
        ordrarRepository.deleteById(id);
    }
}
