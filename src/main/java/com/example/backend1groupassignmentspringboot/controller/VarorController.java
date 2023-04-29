package com.example.backend1groupassignmentspringboot.controller;

import com.example.backend1groupassignmentspringboot.entity.Varor;
import com.example.backend1groupassignmentspringboot.service.VarorService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/varors")
public class VarorController {
    private final VarorService varorService;

    public VarorController(VarorService theVarorService) {
        this.varorService = theVarorService;
    }

    @GetMapping("/list")
    public String listVaror(Model model) {
        List<Varor> theItems = varorService.findAll();
        model.addAttribute("varors", theItems);
        return "list-varor";
    }

    @GetMapping("/showFormForAdd")
    public String showFormForAdd(Model theModel) {
        Varor theVaror = new Varor();
        theVaror.setId(0L); // set the id to a default value of 0
        theModel.addAttribute("varor", theVaror);
        return "varor-form";
    }

    @GetMapping("/showFormForUpdate")
    public String showFormForUpdate(@RequestParam("varorId") Long id, Model theModel) {
        Varor theVaror = varorService.findById(id);
        if (theVaror == null) {
            throw new RuntimeException("Varor id not found: " + id);
        }
        theModel.addAttribute("varor", theVaror);
        return "varor-form";
    }


    @GetMapping("/delete")
    public String delete(@RequestParam("varorId") Long theId, RedirectAttributes redirectAttributes) {
        try {
            varorService.deleteById(theId);
            redirectAttributes.addFlashAttribute("successMessage", "The item has been successfully deleted");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "An error occurred while deleting the item");
        }
        return "redirect:/varors/list";
    }

    @PostMapping("/save")
    public String saveVaror(@ModelAttribute("varor") Varor theVaror) {
        varorService.save(theVaror);
        return "redirect:/varors/list";
    }
}
