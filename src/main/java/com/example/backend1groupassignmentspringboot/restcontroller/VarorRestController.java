package com.example.backend1groupassignmentspringboot.restcontroller;


import com.example.backend1groupassignmentspringboot.dao.VarorRepository;
import com.example.backend1groupassignmentspringboot.entity.Varor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class VarorRestController {
    private final VarorRepository varorRepository;
    private final static Logger log = LoggerFactory.getLogger(VarorRestController.class);

    public VarorRestController(VarorRepository varorRepository) {
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
    public String post(@PathVariable String name, @PathVariable double price) {
        log.info("Adding a new item");
        Varor newVara = new Varor(name, price);
        varorRepository.save(newVara);
        return "Ny vara lades till " + newVara.getName();
    }

    @PostMapping("/items")
    public ResponseEntity<?> addVaror(@RequestBody Varor vara) {
        if (vara == null) {
            return ResponseEntity.badRequest().body("Body can't be null");
        } else if (vara.getName() == null || vara.getPrice() < 0) {
            return ResponseEntity.badRequest().body("All fields of the body must be present and price must be higher than 0");
        }
        varorRepository.save(vara);
        return ResponseEntity.ok().body("The request was ok and saved successfully: " + vara);
    }

}
