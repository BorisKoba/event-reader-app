package com.boriskoba.eventreader.controller;

import com.boriskoba.eventreader.dto.ProductDto;
import com.boriskoba.eventreader.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.*;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class EventControllerTest {

    private MockMvc mockMvc;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }

    @Test
    void getProductsByInsuredId_ReturnsProducts() throws Exception {
        Map<String, List<ProductDto>> productsGrouped = Map.of(
                "CompanyA", List.of(new ProductDto("policy1", new BigDecimal("1000"), "2024-01-01", "2025-01-01"))
        );
        when(eventService.getProductsGroupedBySourceCompany("insured123")).thenReturn(productsGrouped);

        mockMvc.perform(get("/api/events/products/insured123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.CompanyA[0].type").value("policy1"));
    }

    @Test
    void getProductsByInsuredId_NoProducts() throws Exception {
        when(eventService.getProductsGroupedBySourceCompany("insured123")).thenReturn(Collections.emptyMap());

        mockMvc.perform(get("/api/events/products/insured123"))
                .andExpect(status().isNoContent());
    }

    @Test
    void getProductsByInsuredId_ServiceError() throws Exception {
        when(eventService.getProductsGroupedBySourceCompany("insured123")).thenThrow(new RuntimeException("Service error"));

        mockMvc.perform(get("/api/events/products/insured123"))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.error").value("Service error: Service error"));
    }
}
