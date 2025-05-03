package com.boriskoba.eventreader.service;

import com.boriskoba.eventreader.dto.ProductDto;

import java.util.*;

public interface EventService {

    void processFile(java.nio.file.Path filePath);

    Map<String, List<ProductDto>> getProductsGroupedBySourceCompany(String insuredId);
}
