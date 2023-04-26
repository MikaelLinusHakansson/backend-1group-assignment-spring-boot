package com.example.backend1groupassignmentspringboot.controller;

import com.example.backend1groupassignmentspringboot.dao.OrdrarRepository;
import com.example.backend1groupassignmentspringboot.dao.VarorRepository;
import com.example.backend1groupassignmentspringboot.entity.Ordrar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
public class OrdrarController {

    private final OrdrarRepository ordrarRepo;
    private final KundRepository kundRepo;
    private final VarorRepository varorRepo;

    private final static Logger log = LoggerFactory.getLogger(OrdrarController.class);

    OrdrarController(OrdrarRepository ordrarRepo, KundRepository kundRepo,
                     VarorRepository varorRepo) {
        this.ordrarRepo = ordrarRepo;
        this.kundRepo = kundRepo;
        this.varorRepo = varorRepo;
    }

    @RequestMapping("/ordrar")
    public List<Ordrar> getAllOrdrar() {
        log.info("Successfully returned all the orders");
        return ordrarRepo.findAll();
    }

    @RequestMapping("/ordrar/{id}")
    public Ordrar getOrdrarById(@PathVariable Long id) {
        log.info("Fetching one order");
        return ordrarRepo.findById(id).orElse(null);
    }

    @RequestMapping("/ordar/add/{customerId}/{itemId}")
    public Ordrar addNewOrder(@PathVariable Long customerId, @PathVariable Long itemId) {
        log.info("Adding a new order");
        Kund k = kundRepo.findById(customerId).orElse(null);
        if (k != null) {
            Ordrar newOrdrar = new Ordrar(LocalDateTime.now(), );
            ordrarRepo.save(newOrdrar);
            return newOrdrar;
        }
        else {
            return null;
        }
    }
}
