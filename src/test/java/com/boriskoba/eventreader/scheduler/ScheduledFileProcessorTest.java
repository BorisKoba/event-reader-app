package com.boriskoba.eventreader.scheduler;

import com.boriskoba.eventreader.service.EventService;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.*;

import static org.mockito.Mockito.*;

@SpringBootTest
class ScheduledFileProcessorTest {

	@Mock
	private EventService eventService;
	private Path tempDir;
	private ScheduledFileProcessor fileProcessor;

	@BeforeEach
	void setUp() throws IOException {
		MockitoAnnotations.openMocks(this);
		tempDir = Files.createTempDirectory("input-folder-test");
		fileProcessor = new ScheduledFileProcessor(eventService, tempDir);
	}

	@Test
	void testScheduleFileProcessing_CreatesFolderAndProcessesFiles() throws IOException {

		Path testFile = Files.createTempFile(tempDir, "test-", ".xml");
		fileProcessor.scheduleFileProcessing();
		verify(eventService, times(1)).processFile(testFile);
	}
}
