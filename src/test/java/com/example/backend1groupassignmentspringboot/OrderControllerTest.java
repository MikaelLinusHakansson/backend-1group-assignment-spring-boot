package com.example.backend1groupassignmentspringboot;

import com.example.backend1groupassignmentspringboot.dao.OrdrarRepository;
import com.example.backend1groupassignmentspringboot.dao.VarorRepository;
import com.example.backend1groupassignmentspringboot.entity.Customer;
import com.example.backend1groupassignmentspringboot.entity.Ordrar;
import com.example.backend1groupassignmentspringboot.entity.Varor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;



@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrdrarRepository ordrarLongJpaRepository;

    @MockBean
    private VarorRepository varorRepository;



    @BeforeEach
    public void init() {

        Ordrar order1 = new Ordrar(1L, null,null, null, null);
        Ordrar order2 = new Ordrar(2L, null, null, null, null);
        Ordrar order3 = new Ordrar(3L, null, null, null, null);

        when(ordrarLongJpaRepository.findById(1L)).thenReturn(Optional.of(order1));
        when(ordrarLongJpaRepository.findById(2L)).thenReturn(Optional.of(order2));
        when(ordrarLongJpaRepository.findById(3L)).thenReturn(Optional.of(order3));

        when(ordrarLongJpaRepository.findAll()).thenReturn(Arrays.asList(order1, order2, order3));

        Varor varor = new Varor(1L, "Kaffe", 200.0);
        when(varorRepository.findById(1L)).thenReturn(Optional.of(varor));

        Optional<Ordrar> ordrar = ordrarLongJpaRepository.findById(1L);
        ordrar.ifPresent(value -> System.out.println(value.getId()));

        List<Ordrar> ordrars = ordrarLongJpaRepository.findAll();
        ordrars.forEach(s -> System.out.println(s.getId()));
    }

    @Test
    void getAll() throws Exception {
        mockMvc.perform(get("/ordrar"))
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":1, \"createdDate\": null, \"lastModifiedDate\": null,\"products\": null, \"customer\": null}," +
                        "{\"id\":2, \"createdDate\": null, \"lastModifiedDate\": null,\"products\": null, \"customer\": null}," +
                        "{\"id\":3, \"createdDate\": null, \"lastModifiedDate\": null,\"products\": null, \"customer\": null}]"));
    }

    @Test
    void getByOrderId(){
        LocalDateTime now = LocalDateTime.now();
        Ordrar order = new Ordrar(1L, now, now, null, null);

        // Mock the behavior of the repository
        when(ordrarLongJpaRepository.save(order)).thenReturn(order);

        // Save the order
        Ordrar savedOrder = ordrarLongJpaRepository.save(order);

        // Check that the createdDate and lastModifiedDate fields are not null
        assertNotNull(savedOrder.getCreatedDate());
        assertNotNull(savedOrder.getLastModifiedDate());

        // Check that the createdDate and lastModifiedDate fields are equal to the current time
        assertEquals(now.truncatedTo(ChronoUnit.SECONDS), savedOrder.getCreatedDate().truncatedTo(ChronoUnit.SECONDS));
        assertEquals(now.truncatedTo(ChronoUnit.SECONDS), savedOrder.getLastModifiedDate().truncatedTo(ChronoUnit.SECONDS));
        assertEquals(1L, savedOrder.getId());
        assertNull(savedOrder.getProducts());
        assertNull(savedOrder.getCustomer());

    }

    @Test
    void addNewOrder(){
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
