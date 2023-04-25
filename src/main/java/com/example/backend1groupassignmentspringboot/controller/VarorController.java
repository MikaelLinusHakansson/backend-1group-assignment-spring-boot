package com.example.backend1groupassignmentspringboot.controller;


import com.example.backend1groupassignmentspringboot.dao.VarorRepository;
import com.example.backend1groupassignmentspringboot.entity.Varor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VarorController {
    private final VarorRepository varorRepository;
    private final static Logger log = LoggerFactory.getLogger(VarorController.class);

    public VarorController(VarorRepository varorRepository) {
        this.varorRepository = varorRepository;
    }

    @RequestMapping("/varor")
    public List<Varor> getAll() {
        log.info("Successfully returned all the items");
        return varorRepository.findAll();
    }

    @RequestMapping("/varor/{id}")
    public Varor get(@PathVariable Long id) {
        log.info("Fetching one item");
        return varorRepository.findById(id).orElse(null);
    }

    @RequestMapping("/varor/add/{name}/{price}")
    public Varor post(@PathVariable String name, @PathVariable double price){
        log.info("Adding a new item");
        Varor newVara = new Varor(name, price);
        varorRepository.save(newVara);
        return newVara;
    }

    @PostMapping("/items")
    public Varor addVaror(@RequestBody Varor vara) {
        varorRepository.save(vara);
        return vara;
    }

}
