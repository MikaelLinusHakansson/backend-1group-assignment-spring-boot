package com.example.backend1groupassignmentspringboot;


import com.example.backend1groupassignmentspringboot.dao.CustomerRepository;
import com.example.backend1groupassignmentspringboot.dao.OrdrarRepository;
import com.example.backend1groupassignmentspringboot.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdrarRepository ordrarLongJpaRepository;

    @MockBean
    private CustomerRepository customerRepository;

    @BeforeEach
    public void init() {
        Customer customer1 = new Customer(1L, "Anna", "123456-7890", null);
        Customer customer2 = new Customer(2L, "Pelle", "234567-8901", null);


        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer1));
        when(customerRepository.findById(2L)).thenReturn(Optional.of(customer2));

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1, customer2));
        when(customerRepository.save(customer1)).thenReturn(customer1);
    }

    @Test
    void getCustomerById() {
        Customer customer1 = new Customer(1L, "Anna", "123456-7890", null);
        Customer savedCustomer = customerRepository.save(customer1);
        assertEquals(1L, savedCustomer.getId());
    }

    @Test
    void getAllCustomers() throws Exception {
        mockMvc.perform(get("/customer"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1, \"name\": \"Anna\", \"personalNumber\": \"123456-7890\"}," +
                        "{\"id\":2, \"name\": \"Pelle\", \"personalNumber\": \"234567-8901\"}]"));

    }

    @Test
    void addCustomerWithParams() throws Exception {
        String name = "Anna";
        String personalNumber = "123456-7890";
        this.mockMvc.perform(get("/customer/add/{name}/{personalNumber}", name, personalNumber))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Ny kund lades till " + name)));

    }

    @Test
    void addNewCustomerWithBody() throws Exception {
        Customer customer = new Customer();
        customer.setName("TestKund");
        customer.setPersonalNumber("456789-0123");
        customer.setId(3L);

        // convert to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String customerJson = objectMapper.writeValueAsString(customer);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/customer/save/body")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(customerJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();

        assertEquals("The request was ok and saved successfully: " + customer, responseBody);
    }

}
