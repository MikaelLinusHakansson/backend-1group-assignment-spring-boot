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
        Varor vara1 = new Varor(1L, "vara1", 200);
        Varor vara2 = new Varor(2L, "vara2", 200);
        Varor vara3 = new Varor(3L, "vara3", 200);

        List<Varor> varorList1 = new ArrayList<>();
        varorList1.add(vara1);

        List<Varor> varorList2 = new ArrayList<>();
        varorList2.add(vara2);

        List<Varor> varorList3 = new ArrayList<>();
        varorList3.add(vara3);

        Customer customer1 = new Customer("customer1", "111");
        Customer customer2 = new Customer("customer2", "222");
        Customer customer3 = new Customer("customer3", "333");


        Ordrar order1 = new Ordrar(1L, null, null, varorList1, customer1);
        Ordrar order2 = new Ordrar(2L, null, null, varorList2, customer2);
        Ordrar order3 = new Ordrar(3L, null, null, varorList3, customer3);

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
                .andExpect(content().json("[" +
                        "{\"id\":1, \"createdDate\": null, \"lastModifiedDate\": null, \"products\": " +
                        "[{\"id\":1, \"name\":\"vara1\", \"price\":200.0}], \"customer\": " +
                        "{\"name\":\"customer1\", \"personalNumber\":\"111\"}}," +
                        "{\"id\":2, \"createdDate\": null, \"lastModifiedDate\": null, \"products\": " +
                        "[{\"id\":2, \"name\":\"vara2\", \"price\":200.0}], \"customer\": " +
                        "{\"name\":\"customer2\", \"personalNumber\":\"222\"}}," +
                        "{\"id\":3, \"createdDate\": null, \"lastModifiedDate\": null, \"products\": " +
                        "[{\"id\":3, \"name\":\"vara3\", \"price\":200.0}], \"customer\": " +
                        "{\"name\":\"customer3\", \"personalNumber\":\"333\"}}]"));

    }

    @Test
    void getByOrderId() {
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
    void addNewOrder() {
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
