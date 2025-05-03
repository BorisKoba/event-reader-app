package com.boriskoba.eventreader.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.*;

import com.boriskoba.eventreader.dto.ProductDto;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    private String type;
    private BigDecimal price;
    private String startDate;
    private String endDate;
    @ManyToOne
    private Event event;

    public static Product fromDto(ProductDto dto, Event event) {
        Product product = new Product();
        product.setType(dto.getType());
        product.setPrice(dto.getPrice());
        product.setStartDate(dto.getStartDate());
        product.setEndDate(dto.getEndDate());
        product.setEvent(event);
        return product;
    }

    public ProductDto toDto() {
        return new ProductDto(type, price, startDate, endDate);
    }
}
