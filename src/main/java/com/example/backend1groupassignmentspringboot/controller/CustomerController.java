package com.example.backend1groupassignmentspringboot.controller;

import com.example.backend1groupassignmentspringboot.entity.Customer;
import com.example.backend1groupassignmentspringboot.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    private final CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public String listCustomer(Model model) {
        List<Customer> theCustomers = customerService.findAll();
        model.addAttribute("customer", theCustomers);
        return "customer-list";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Customer theCustomer = new Customer();
        theModel.addAttribute("customer", theCustomer);
        return "customer-form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam("customerId") Long theId, RedirectAttributes redirectAttributes) {
        try {
            customerService.deleteById(theId);
            redirectAttributes.addFlashAttribute("successMessage",
                    "The customer has been successfully deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage",
                    "An error occurred while deleting the customer");
        }
        return "redirect:/customer/list";
    }


    @PostMapping("/save")
    public String saveCustomer(@ModelAttribute("customer") Customer customer) {
        // Update the existing Customer and save to the database
        customerService.save(customer);
        return "redirect:/customer/list";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("customerId") Long id, Model theModel) {
        Customer theCustomer = customerService.findById(id);
        if (theCustomer == null) {
            throw new RuntimeException("Customer id not found: " + id);
        }
        theModel.addAttribute("customer", theCustomer);
        return "customer-form";
    }
}
