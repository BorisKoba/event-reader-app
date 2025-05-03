package com.boriskoba.eventreader.service;

import com.boriskoba.eventreader.dto.*;
import com.boriskoba.eventreader.entity.*;
import com.boriskoba.eventreader.parser.XmlParser;
import com.boriskoba.eventreader.repository.*;

import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventServiceImpl implements EventService {

	private final XmlParser xmlParser;
	private final RequestDetailsRepository requestDetailsRepository;
	private final EventRepository eventRepository;
	private final ProductRepository productRepository;
	private final Path backupFolder = Paths.get("backup-folder-path");

	@Override
	public void processFile(Path filePath) {
		try {
			RootDto rootDto = xmlParser.parse(filePath);
			saveToDatabase(rootDto);
			moveFileToBackup(filePath);
		} catch (Exception e) {
			log.error("Error processing file: {}", filePath.getFileName(), e);
		}
	}

	private void saveToDatabase(RootDto rootDto) {
		RequestDetails requestDetails = RequestDetails.fromDto(rootDto.getRequestDetails());
		requestDetailsRepository.save(requestDetails);

		for (EventDto eventDto : rootDto.getEvents()) {
			Event event = Event.fromDto(eventDto, requestDetails);
			eventRepository.save(event);
		}
	}

	private void moveFileToBackup(Path filePath) throws IOException {
	    if (!Files.exists(backupFolder)) {
	        Files.createDirectories(backupFolder);
	    }
	    Path targetPath = backupFolder.resolve(filePath.getFileName());
	    Files.move(filePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
	}

	@Override
	public Map<String, List<ProductDto>> getProductsGroupedBySourceCompany(String insuredId) {
		List<Product> products = productRepository.findByEventInsuredId(insuredId);

		return products.stream().collect(Collectors.groupingBy(p -> p.getEvent().getRequestDetails().getSourceCompany(),
				Collectors.mapping(Product::toDto, Collectors.toList())));
	}
}
