package com.example.backend1groupassignmentspringboot.restcontroller;

import com.example.backend1groupassignmentspringboot.dao.CustomerRepository;
import com.example.backend1groupassignmentspringboot.dao.OrdrarRepository;
import com.example.backend1groupassignmentspringboot.dao.VarorRepository;
import com.example.backend1groupassignmentspringboot.entity.Customer;
import com.example.backend1groupassignmentspringboot.entity.Ordrar;
import com.example.backend1groupassignmentspringboot.entity.Varor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class OrdrarRestController {

    private final OrdrarRepository ordrarRepo;
    private final VarorRepository varorRepo;
    private final CustomerRepository customerRepository;

    private final static Logger log = LoggerFactory.getLogger(OrdrarRestController.class);

    OrdrarRestController(OrdrarRepository ordrarRepo,
                         VarorRepository varorRepo, CustomerRepository customerRepository) {
        this.ordrarRepo = ordrarRepo;
        this.varorRepo = varorRepo;
        this.customerRepository = customerRepository;
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

    @RequestMapping("/ordrar/add/{itemId}/{customerId}")
    public Ordrar addNewOrder(@PathVariable Long itemId, @PathVariable Long customerId) {
        log.info("Adding a new order");
        Varor tempVaror = varorRepo.findById(itemId).get();
        Customer tempCustomer = customerRepository.findById(customerId).get();
        Ordrar newOrdrar = new Ordrar();
        newOrdrar.setProducts(tempVaror);
        newOrdrar.setCustomer(tempCustomer);

        ordrarRepo.save(newOrdrar);
        return newOrdrar;
    }
}
