package com.boriskoba.eventreader.service;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import com.boriskoba.eventreader.dto.ProductDto;

public interface EventService {
    void processFile(Path filePath);
    Map<String, List<ProductDto>> getProductsGroupedBySourceCompany(String insuredId);
}
