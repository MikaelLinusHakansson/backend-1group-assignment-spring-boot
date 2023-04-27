package com.example.backend1groupassignmentspringboot.restcontroller;

import com.example.backend1groupassignmentspringboot.dao.OrdrarRepository;
import com.example.backend1groupassignmentspringboot.dao.VarorRepository;
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
    //    private final KundRepository kundRepo;  // TODO
    private final VarorRepository varorRepo;

    private final static Logger log = LoggerFactory.getLogger(OrdrarRestController.class);

    OrdrarRestController(OrdrarRepository ordrarRepo/*, KundRepository kundRepo*/,
                         VarorRepository varorRepo) {
        this.ordrarRepo = ordrarRepo;
//        this.kundRepo = kundRepo;
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

    @RequestMapping("/ordrar/add/{itemId}")
    public Ordrar addNewOrder(@PathVariable Long itemId) {
        log.info("Adding a new order");
        Varor tempVaror = varorRepo.findById(itemId).get();
        Ordrar newOrdrar = new Ordrar();
        newOrdrar.setProducts(tempVaror);
        ordrarRepo.save(newOrdrar);
        return newOrdrar;
    }
}
