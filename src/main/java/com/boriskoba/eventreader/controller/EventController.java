package com.boriskoba.eventreader.controller;

import com.boriskoba.eventreader.dto.ProductDto;
import com.boriskoba.eventreader.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping("/products/{insuredId}")
    public ResponseEntity<?> getProductsByInsuredId(@PathVariable String insuredId) {
        try {
            Map<String, List<ProductDto>> productsGrouped = eventService.getProductsGroupedBySourceCompany(insuredId);
            if (productsGrouped.isEmpty()) {
                return ResponseEntity.noContent().build(); 
            }
            return ResponseEntity.ok(productsGrouped); 
        } catch (Exception e) {
            Map<String, String> errorResponse = Map.of("error", "Service error: " + e.getMessage());
            return ResponseEntity.status(500).body(errorResponse); 
        }
    }
}
