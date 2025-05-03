package com.boriskoba.eventreader.scheduler;

import com.boriskoba.eventreader.service.EventService;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;


class ScheduledFileProcessorTest {

    private ScheduledFileProcessor scheduledFileProcessor;
    private EventService eventService;
    private Path tempDir;

    @BeforeEach
    void setUp() throws IOException {
        eventService = mock(EventService.class);
        tempDir = Files.createTempDirectory("input-folder-test");
        scheduledFileProcessor = new ScheduledFileProcessor(eventService, tempDir);
    }

    @AfterEach
    void tearDown() throws IOException {
        if (Files.exists(tempDir)) {
            Files.walk(tempDir)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(file -> file.delete());
        }
    }

    @Test
    void testScheduleFileProcessing_WhenFilesExist() throws IOException {
        Path testFile = Files.createTempFile(tempDir, "test-", ".xml");
        scheduledFileProcessor.scheduleFileProcessing();
        verify(eventService, times(1)).processFile(testFile);
    }

    @Test
    void testScheduleFileProcessing_WhenNoFiles() {
        scheduledFileProcessor.scheduleFileProcessing();
        verify(eventService, never()).processFile(any());
    }

    @Test
    void testScheduleFileProcessing_WhenIOExceptionOccurs() throws IOException {
        Path invalidPath = Paths.get("Z:/this/path/does/not/exist/and/will/fail");
        scheduledFileProcessor = new ScheduledFileProcessor(eventService, invalidPath);
        scheduledFileProcessor.scheduleFileProcessing();
        verifyNoInteractions(eventService);
    }

    @Test
    void testEnsureInputFolderExists_CreatesFolderIfMissing() throws IOException {
        Path newTempDir = Files.createTempDirectory("parent-").resolve("missing-folder");
        scheduledFileProcessor = new ScheduledFileProcessor(eventService, newTempDir);

        if (Files.exists(newTempDir)) {
            Files.delete(newTempDir);
        }

        scheduledFileProcessor.scheduleFileProcessing();

        assertTrue(Files.exists(newTempDir));
    }
}
