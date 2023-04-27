package com.example.backend1groupassignmentspringboot;

import com.example.backend1groupassignmentspringboot.dao.OrdrarRepository;
import com.example.backend1groupassignmentspringboot.dao.VarorRepository;
import com.example.backend1groupassignmentspringboot.entity.Ordrar;
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
import java.util.Optional;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class VarorControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private OrdrarRepository ordrarLongJpaRepository;

    @MockBean
    private VarorRepository varorRepository;


    @BeforeEach
    public void init() {
        Varor vara1 = new Varor(1L, "Kaffe", 200, null);
        Varor vara2 = new Varor(2L, "Skor", 199.99, null);
        Varor vara3 = new Varor(3L, "Strumpor", 20.99, null);


        when(varorRepository.findById(1L)).thenReturn(Optional.of(vara1));
        when(varorRepository.findById(2L)).thenReturn(Optional.of(vara2));
        when(varorRepository.findById(3L)).thenReturn(Optional.of(vara3));

        when(varorRepository.findAll()).thenReturn(Arrays.asList(vara1, vara2, vara3));
        when(varorRepository.save(vara1)).thenReturn(vara1);

    }

    @Test
    void getVaraById() throws Exception {
        Varor vara1 = new Varor(1L, "Kaffe", 200, null);
        // Save the order
        Varor savedVara = varorRepository.save(vara1);
        assertEquals(1L, savedVara.getId());

    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/varor"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1, \"name\": \"Kaffe\", \"price\": 200,\"order\": null}," +
                        "{\"id\":2, \"name\": \"Skor\", \"price\": 199.99,\"order\": null}," +
                        "{\"id\":3, \"name\": \"Strumpor\", \"price\": 20.99,\"order\": null}]"));

    }


    @Test
    void addVaraWithParams() throws Exception {
        String name = "Nokia 3310";
        double price = 100.0;
        this.mockMvc.perform(get("/varor/add/{name}/{price}", name, price))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("Ny vara lades till " + name)));

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

    @Test
    void addNewVara() throws Exception {
        Varor varor = new Varor("item1", 10.0);
        varorRepository.save(varor);

        Ordrar ordrar = new Ordrar();
        ordrar.addVaror(varor);

        when(ordrarLongJpaRepository.save(any(Ordrar.class))).thenReturn(ordrar);

        Ordrar actualOrdrar = ordrarLongJpaRepository.save(ordrar);

        verify(ordrarLongJpaRepository, times(1)).save(actualOrdrar);
        assertEquals(ordrar, actualOrdrar);
    }

}
