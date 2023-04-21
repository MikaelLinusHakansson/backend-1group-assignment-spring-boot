package com.example.backend1groupassignmentspringboot.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {
    @RequestMapping("/helloworld")
    public String helloWorld(){
        return "Hello world";
    }
}
