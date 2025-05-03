package com.boriskoba.eventreader.service;

import com.boriskoba.eventreader.config.EventReaderProperties;
import com.boriskoba.eventreader.dto.*;
import com.boriskoba.eventreader.entity.*;
import com.boriskoba.eventreader.parser.XmlParser;
import com.boriskoba.eventreader.repository.*;
import org.junit.jupiter.api.*;
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

    @Mock private XmlParser xmlParser;
    @Mock private RequestDetailsRepository requestDetailsRepository;
    @Mock private EventRepository eventRepository;
    @Mock private ProductRepository productRepository;
    @Mock private EventReaderProperties eventReaderProperties;
    private EventServiceImpl eventService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        eventService = new EventServiceImpl(xmlParser, requestDetailsRepository, eventRepository, productRepository, eventReaderProperties);
    }

    @Test
    void testProcessFile_SuccessfulSaveAndMove() throws IOException, Exception {
        RootDto rootDto = new RootDto(
                new RequestDetailsDto("id", "2024-05-02 12:00:00", "Company"),
                List.of(new EventDto("eventId", "type", "insuredId", List.of(
                        new ProductDto("policy", new BigDecimal("1000"), "2024-05-01", "2025-05-01")
                ))));
        Path tempFile = Files.createTempFile("test-", ".xml");
        Path backupFolder = Files.createTempDirectory("backup-");

        when(xmlParser.parse(tempFile)).thenReturn(rootDto);
        when(eventReaderProperties.getBackupFolderPath()).thenReturn(backupFolder.toString());

        eventService.processFile(tempFile);

        verify(requestDetailsRepository, times(1)).save(any());
        verify(eventRepository, times(1)).save(any());
        assertFalse(Files.exists(tempFile));
        Path movedFile = backupFolder.resolve(tempFile.getFileName());
        assertTrue(Files.exists(movedFile));
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

    @Test
    void testScheduledProcessFiles_withExistingXmlFiles() throws IOException {
        Path tempDir = Files.createTempDirectory("input-");
        Path tempFile = Files.createTempFile(tempDir, "test-", ".xml");

        when(eventReaderProperties.getInputFolderPath()).thenReturn(tempDir.toString());

        EventServiceImpl serviceSpy = spy(eventService);
        doNothing().when(serviceSpy).processFile(any(Path.class));

        serviceSpy.scheduledProcessFiles();

        verify(serviceSpy, atLeastOnce()).processFile(any(Path.class));
    }

    @Test
    void testScheduledProcessFiles_withMissingFolder() {
        when(eventReaderProperties.getInputFolderPath()).thenReturn("nonexistent-folder");

        EventServiceImpl serviceSpy = spy(eventService);
        serviceSpy.scheduledProcessFiles();

        verify(serviceSpy, never()).processFile(any(Path.class));
    }
    

  }
