package com.boriskoba.eventreader.service;

import com.boriskoba.eventreader.dto.*;
import com.boriskoba.eventreader.entity.*;
import com.boriskoba.eventreader.parser.XmlParser;
import com.boriskoba.eventreader.repository.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class EventServiceImplTest {

	@Mock
	private XmlParser xmlParser;
	@Mock
	private RequestDetailsRepository requestDetailsRepository;
	@Mock
	private EventRepository eventRepository;
	@Mock
	private ProductRepository productRepository;

	private EventServiceImpl eventService;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);
		eventService = new EventServiceImpl(xmlParser, requestDetailsRepository, eventRepository, productRepository);
	}

	@Test
	void testProcessFile_SuccessfulSaveAndMove(@TempDir Path tempDir) throws IOException, Exception {
		RootDto rootDto = new RootDto(new RequestDetailsDto("id", "2024-05-02 12:00:00", "Company"),
				List.of(new EventDto("eventId", "type", "insuredId",
						List.of(new ProductDto("policy", new BigDecimal("1000"), "2024-05-01", "2025-05-01")))));

		Path tempFile = tempDir.resolve("test-file.xml");
		Files.createFile(tempFile);
		when(xmlParser.parse(tempFile)).thenReturn(rootDto);

		eventService.processFile(tempFile);

		verify(requestDetailsRepository, times(1)).save(any());
		verify(eventRepository, times(1)).save(any());

		assertFalse(Files.exists(tempFile));
	}

	@Test
	void testGetProductsGroupedBySourceCompany() {
		Product product = new Product();
		Event event = new Event();
		RequestDetails requestDetails = new RequestDetails();
		requestDetails.setSourceCompany("CompanyX");
		event.setRequestDetails(requestDetails);
		product.setEvent(event);

		when(productRepository.findByEventInsuredId("insured123")).thenReturn(List.of(product));

		Map<String, List<ProductDto>> result = eventService.getProductsGroupedBySourceCompany("insured123");

		assertTrue(result.containsKey("CompanyX"));
	}
}
