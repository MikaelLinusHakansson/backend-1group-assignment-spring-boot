package com.example.backend1groupassignmentspringboot;

import com.example.backend1groupassignmentspringboot.dao.CustomerRepository;
import com.example.backend1groupassignmentspringboot.dao.OrdrarRepository;
import com.example.backend1groupassignmentspringboot.dao.VarorRepository;
import com.example.backend1groupassignmentspringboot.entity.Customer;
import com.example.backend1groupassignmentspringboot.entity.Ordrar;
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
    CommandLineRunner demo(VarorRepository varorRepository, OrdrarRepository ordrarRepository, CustomerRepository customerRepository){
        return (args) -> {
            Customer customer1 = new Customer("LInus håkansson", "asdasda");
            Customer customer2 = new Customer("Sofie håkansson", "asdasda");
            Customer customer3 = new Customer("Anders håkansson", "asdasda");

            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);

            Varor vara1 = new Varor("kaffe", 200);
            Varor vara2 = new Varor("Sor", 199.99);
            Varor vara3 = new Varor("Strumpor", 20.99);

            Varor vara4 = new Varor("intekaffe", 200);
            Varor vara5 = new Varor("Skor", 199.99);
            Varor vara6 = new Varor("inteStrumpor", 20.99);

            Varor vara7 = new Varor("asdkaffe", 200);
            Varor vara8 = new Varor("asdSor", 199.99);
            Varor vara9 = new Varor("asdStrumpor", 20.99);

            varorRepository.save(vara1);
            varorRepository.save(vara2);
            varorRepository.save(vara3);

            varorRepository.save(vara4);
            varorRepository.save(vara5);
            varorRepository.save(vara6);

            varorRepository.save(vara7);
            varorRepository.save(vara8);
            varorRepository.save(vara9);

            List<Varor> listOfWares = new ArrayList<>();
            listOfWares.add(vara1);
            listOfWares.add(vara2);
            listOfWares.add(vara3);

            List<Varor> listOfWares2 = new ArrayList<>();
            listOfWares2.add(vara4);
            listOfWares2.add(vara5);
            listOfWares2.add(vara6);

            List<Varor> listOfWares3 = new ArrayList<>();
            listOfWares3.add(vara7);
            listOfWares3.add(vara8);
            listOfWares3.add(vara9);

            Ordrar order1 = new Ordrar();
            order1.setCustomer(customer1);
            Ordrar order2 = new Ordrar();
            order2.setCustomer(customer2);


            Ordrar order3 = new Ordrar();
            order3.setCustomer(customer3);

            order1.addItem(vara1);
            order1.addItem(vara2);
            order1.addItem(vara3);

            order2.addItem(vara4);
            order2.addItem(vara5);
            order2.addItem(vara6);

            order3.addItem(vara7);
            order3.addItem(vara8);
            order3.addItem(vara9);


            ordrarRepository.save(order1);
            ordrarRepository.save(order2);
            ordrarRepository.save(order3);
        };
    }

}
