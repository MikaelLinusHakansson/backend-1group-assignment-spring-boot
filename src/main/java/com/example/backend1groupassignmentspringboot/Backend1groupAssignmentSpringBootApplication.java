package com.example.backend1groupassignmentspringboot;

import com.example.backend1groupassignmentspringboot.dao.VarorRepository;
import com.example.backend1groupassignmentspringboot.entity.Varor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class Backend1groupAssignmentSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Backend1groupAssignmentSpringBootApplication.class, args);
    }

    @Bean
    CommandLineRunner demo(VarorRepository varorRepository){
        return (args) -> {
            Varor vara1 = new Varor("Kaffe", 200);
            Varor vara2 = new Varor("Skor", 199.99);
            Varor vara3 = new Varor("Strumpor", 20.99);

            varorRepository.save(vara1);
            varorRepository.save(vara2);
            varorRepository.save(vara3);


        };
    }

}
