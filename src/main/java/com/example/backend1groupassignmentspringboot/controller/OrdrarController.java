package com.example.backend1groupassignmentspringboot.controller;

import com.example.backend1groupassignmentspringboot.entity.Customer;
import com.example.backend1groupassignmentspringboot.entity.Ordrar;
import com.example.backend1groupassignmentspringboot.entity.Varor;
import com.example.backend1groupassignmentspringboot.service.CustomerService;
import com.example.backend1groupassignmentspringboot.service.OrdrarService;
import com.example.backend1groupassignmentspringboot.service.VarorService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ordrar/thymeleaf")
public class OrdrarController {
    private final OrdrarService ordrarService;
    private final VarorService varorService;
    private final CustomerService customerService;


    public OrdrarController(OrdrarService ordrarService, VarorService varorService, CustomerService customerService) {
        this.ordrarService = ordrarService;
        this.varorService = varorService;
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public String listOrdrar(Model model) {
        List<Ordrar> theOrders = ordrarService.findAll();
        model.addAttribute("ordrar", theOrders);
        return "ordrar-list";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Ordrar theOrdrar = new Ordrar();
        theModel.addAttribute("ordrar", theOrdrar);
        return "ordrar-form";
    }


    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("orderId") Long id, Model theModel) {
        Ordrar theOrder = ordrarService.findById(id);
        if (theOrder == null) {
            throw new RuntimeException("Ordrar id not found: " + id);
        }
        theModel.addAttribute("ordrar", theOrder);
        return "ordrar-form";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("orderId") Long theId) {
        ordrarService.deleteById(theId);
        return "redirect:/ordrar/thymeleaf/list";
    }

    @PostMapping("/save")
    public String saveOrder(@ModelAttribute("ordrar") Ordrar theOrdrar,
                            @RequestParam("customerName") String customerName,
                            @RequestParam("personalNumber") String personalNumber,
                            @RequestParam("name") String productName,
                            @RequestParam("price") Double productPrice) {

        // Create a new Customer and save to the database
        Customer theCustomer = new Customer();
        theCustomer.setName(customerName);
        theCustomer.setPersonalNumber(personalNumber);
        customerService.save(theCustomer);

        // Create a new Product and save to the database
        Varor theProduct = new Varor();
        theProduct.setName(productName);
        theProduct.setPrice(productPrice);
        varorService.save(theProduct);

        // Add the Customer and Product to the Order and save to the database
        theOrdrar.setCustomer(theCustomer);
        theOrdrar.setProducts(theProduct);
        ordrarService.save(theOrdrar);

        return "redirect:/ordrar/thymeleaf/list";
    }
}
