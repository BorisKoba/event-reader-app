package com.boriskoba.eventreader.repository;

import com.boriskoba.eventreader.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByEventInsuredId(String insuredId);
}