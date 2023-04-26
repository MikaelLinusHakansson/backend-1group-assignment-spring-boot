package com.example.backend1groupassignmentspringboot;

import com.example.backend1groupassignmentspringboot.entity.Varor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class VarorControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private JpaRepository<Varor, Long> mockRepo;


    @BeforeEach
    public void init() {
        Varor vara1 = new Varor(1L, "Kaffe", 200);
        Varor vara2 = new Varor(2L, "Skor", 199.99);
        Varor vara3 = new Varor(3L, "Strumpor", 20.99);

        when(mockRepo.findById(1L)).thenReturn(Optional.of(vara1));
        when(mockRepo.findById(2L)).thenReturn(Optional.of(vara2));
        when(mockRepo.findById(3L)).thenReturn(Optional.of(vara3));

        when(mockRepo.findAll()).thenReturn(Arrays.asList(vara1, vara2, vara3));

        Optional<Varor> varor = mockRepo.findById(1L);
        varor.ifPresent(value -> System.out.println(value.getName()));

        List<Varor> kategoris = mockRepo.findAll();
        kategoris.forEach(s -> System.out.println(s.getName() + s.getPrice() + s.getId()));

    }

    @Test
    void addVaraWithParams() throws Exception {
        // Create two new variables "name" and "price".
        String name = "Nokia 3310";
        double price = 100.0;
        // Perform a POST request to the url
        mockMvc.perform(post("/varor/add/{name}/{price}", name, price))
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":4,\"name\":\"Nokia 3310\",\"price\":100.0}"));
        // Expected results

        System.out.println(mockRepo.findAll());
    }


    @Test
    void getAll() throws Exception {
        // Perform a GET request to the url
        mockMvc.perform(get("/varor"))
                .andExpect(status().isOk())
                .andExpect(content().json("[" +
                        "{\"id\":1,\"name\":\"Kaffe\", \"price\": 200}," +
                        "{\"id\":2,\"name\":\"Skor\", \"price\": 199.99}," +
                        "{\"id\":3,\"name\":\"Strumpor\", \"price\": 20.99}]"));
        // Expected result in JSON
    }


    @Test
    public void addVarorWithBody() throws Exception {
        // Create a new Varor object
        Varor vara = new Varor();
        vara.setName("TestVara");
        vara.setPrice(100);
        vara.setId(4L);

        // Convert the Varor object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String varaJson = objectMapper.writeValueAsString(vara);

        // Perform a POST request to the /items endpoint with the Varor object as the request body
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post("/items")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(varaJson))
                .andExpect(status().isOk())
                .andReturn();

        // Get the response body as a string
        String responseBody = result.getResponse().getContentAsString();

        // Assert that the response body contains the expected message
        assertEquals("The request was ok and saved successfully: " + vara, responseBody);
    }
}
